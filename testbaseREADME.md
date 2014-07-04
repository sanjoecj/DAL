package com.amex.main;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.amex.base.TestBase;
import com.amex.dataprovider.RowDataProvider;
import com.amex.dataprovider.RowDataWrapper;
import com.amex.utils.Constants;
import com.amex.utils.DB2Manager;
import com.amex.utils.FileUtil;
import com.amex.utils.ParamsUtil;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;

public class DriverScript extends TestBase {
    private String requestURL;
    private String headerKey;
    private String headerValue;
    private String requestName;
    private String requestParam;
    private String expectedResponseCode;
    private String requestBody;
    private String contentType;
    private String requestMethod;
    private String responseKeySet;
    private String dbResultKeySet;
    private String errorName;
    private String tcid;
    private String fileoutpath;
    private String urlParameters;
    private String dbQueries;

    private DB2Manager db2Manager; 
    private int currentTestcase;
    private boolean testFailed = false;
    private static Logger logger = LoggerFactory.getLogger(DriverScript.class);


    
    @BeforeClass
    public void init() {
	FileUtil.createDirectory(Constants.TEST_OUTPUT_PATH);
	fileoutpath = getFileOutPath(currentTimeStamp);
	db2Manager  = new DB2Manager();
    }

    /**
     * This method is the main method that starts the execution.
     * 
     * @throws IOException
     */
    @Test(dataProviderClass = RowDataProvider.class, dataProvider = "getRowData")
    public void test(RowDataWrapper rowDataWrapper) throws IOException {

	currentTestcase=rowDataWrapper.getCurrentTestcase();
	HashMap<String, String> rowData = rowDataWrapper.getRowData();
	String runMode = rowData.get(Constants.COLUMN_RUN_MODE).trim();
	tcid = rowData.get(Constants.COLUMN_TCID).trim();
	if(tcid.equals("TC_002"))
	    Assert.fail("Failing the test case");
	if (runMode.equalsIgnoreCase("YES")) {
	    // Initialize all the values from test data sheet
	    initialize(rowData);
	    replaceURLParameters(urlParameters);				

	    Response response = getResponse();

	    if (response != null) {
		FileUtil.createFile(fileoutpath, tcid + "_Response.txt", response.asString());
		validateResponse(response);

		// Updating the pass result only if there is no failure
		if (!testFailed) {
		    testPassed();
		}
	    }
	} else {
	    logger.info("Test Skipped : " + tcid);
	    testSkipped();
	}
	//    	}
    }	


    /**
     * The method clears all the previous test results from the test data sheet.
     */
    private void clearResults() {
	logger.info("Clearing all the Test results from Excel sheet");
	for (currentTestcase = 2; currentTestcase <= reader.getRowCount(Constants.SHEET_NAME); currentTestcase++) {
	    reader.setCellData(Constants.SHEET_NAME, Constants.COLUMN_FAILURE_CAUSE, currentTestcase, "");
	    reader.setCellData(Constants.SHEET_NAME, Constants.COLUMN_TEST_RESULT, currentTestcase, "");
	}
	testFailed = false;
    }


    /**
     * Initialize all the required parameters for a web service call.
     * 
     * @param rowData
     */
    private void initialize(HashMap<String, String> rowData) {
	requestURL           = Constants.URL + rowData.get(Constants.COLUMN_API).trim();
	headerKey            = rowData.get(Constants.COLUMN_HEADER_KEY).trim();
	headerValue          = rowData.get(Constants.COLUMN_HEADER_VALUE).trim();
	requestName          = rowData.get(Constants.COLUMN_REQUEST_NAME).trim();
	requestParam         = rowData.get(Constants.COLUMN_REQUEST_PARAM).trim();
	expectedResponseCode = rowData.get(Constants.COLUMN_RESPONSE_CODE).trim();
	requestMethod        = rowData.get(Constants.COLUMN_REQUEST_METHOD).trim();
	contentType          = Constants.CONTENT_TYPE_JSON;
	requestBody          = generateValidRequestBody(requestName, requestParam);
	errorName            = rowData.get(Constants.COLUMN_ERROR_NAME).trim();
	responseKeySet       = rowData.get(Constants.COLUMN_RESPONSE_KEY).trim();
	dbResultKeySet	     = rowData.get(Constants.COLUMN_DB_RESULT_KEYS).trim();
	urlParameters        = rowData.get(Constants.COLUMN_URL_PARAMETERS).trim();
	dbQueries	     = rowData.get(Constants.COLUMN_DB_QUERIES).trim();
    }


    /**
     * Call the web service and get the response.
     * 
     * @return
     */
    private Response getResponse() {
	Response response = null;
	RestAssured.useRelaxedHTTPSValidation();
	try {

	    if (requestMethod.equalsIgnoreCase("POST")) {
		// Call POST service
		FileUtil.createFile(fileoutpath, tcid + "_Request.txt", requestBody);
		response = RestAssured.given().headers(headerKey, headerValue)
		.body(requestBody).contentType(contentType)
		.post(requestURL).andReturn();
	    } else if (requestMethod.equalsIgnoreCase("GET")) {
		// Call GET service
		response = RestAssured.given().headers(headerKey, headerValue)
		.contentType(contentType).get(requestURL).andReturn();
	    }
	} catch (Exception exception) {
	    testFailed(exception.getLocalizedMessage());
	    logger.info(exception.getMessage(), exception);

	}
	return response;
    }


    /**
     * Check the HTTP response status code and validate the reponse. If 200 OK,
     * assert the response with the values from DB If 400, 499, assert the error
     * details in the response body with values in the ErrorCodes.properties
     * file.
     * 
     * @param response
     */
    private void validateResponse(Response response) {

	int actualResponseCode = response.getStatusCode();
	int expResponseCode = (int) Float.parseFloat(expectedResponseCode);

	if (actualResponseCode == expResponseCode) {
	    if (actualResponseCode == 200) {
		validateValidResponse(response);
	    } else if (actualResponseCode == 400 || actualResponseCode == 499) {
		validateErrorResponse(response);
	    } else {
		logger.info("The response code does not fall in 200/400/499, " + actualResponseCode);
		testFailed("The response code does not fall in 200/400/499, " + actualResponseCode);
	    }
	} else {	    
	    testFailed("Exp response: " + expResponseCode + " Act response: " + actualResponseCode);
	    
	}
    }


    /**
     * When HTTP Response Code 200, use this method to validate the response
     * obtained against DB values.
     * 
     * @param response
     */
    private void validateValidResponse(Response response) {

	// Fetching the JSON response
	JsonPath json = response.getBody().jsonPath();

	// Read the expected response values to be validated
	String[] responseKeys = responseKeySet.split(",");
	String[] dbResultKeys = dbResultKeySet.split(",");
	try {
	    List<String> queriesList = db2Manager.getQueries(dbQueries);
	    List<Map<String, String>> resultsList = db2Manager.executeQueries(queriesList);

	    if(responseKeys.length == dbResultKeys.length) {
		for (int i = 0; i < responseKeys.length; i++) {
		    String key = responseKeys[i].trim();
		    String dbkey = dbResultKeys[i].trim();
		    String actualValue = json.getString(key);
		    String expectedValue = db2Manager.getItemValue(resultsList, dbkey);

		    System.out.println("Actual Value: " + key + "-----" + actualValue);
		    System.out.println("Expected Value: " + key + "-----" + expectedValue);
		}

	    }
	} catch (Exception e) {
	    testFailed(e.getMessage());
	}
    }


    /**
     * When HTTP Response Code 400/499, use this method to validate the error
     * response obtained against the values in properties file.
     * 
     * @param response
     */
    private void validateErrorResponse(Response response) {
	// Fetching the JSON response
	JsonPath json = response.getBody().jsonPath();

	// Get the expected error details from Property files
	String[] expValues = errorCodesProp.getProperty(errorName).split(",");

	// Read the expected response values to be validated
	String[] responseKeys = responseKeySet.split(",");

	// Validating the error response details
	for (int count = 0; count < expValues.length; count++) {
	    String ActualValue = json.getString((responseKeys[count].trim()));
	    if (!expValues[count].equalsIgnoreCase(ActualValue)) {		
		testFailed("Test Failed : Expected : " + expValues[count] + " Actual : " + ActualValue);
		logger.info("Test Failed : Expected : " + expValues[count] + " Actual : " + ActualValue);
	    }
	}
    }


    /**
     * Get the json request defined in the Request.properties file
     * 
     * @param requestName
     * @return
     */
    private String getRequestSchema(String requestName) {
	return requestProp.getProperty(requestName);
    }


    /**
     * Generates a valid json request from the json obatained from the
     * properties file. Uses the request parameters provided in the Excel sheet
     * to form a valid Json Request body.
     * 
     * @param requestName
     * @param requestParam
     * @return
     */
    private String generateValidRequestBody(String requestName,
	    String requestParam) {
	if (requestMethod.equalsIgnoreCase("POST")) {
	    String request = getRequestSchema(requestName);
	    String finalRequest = ParamsUtil.replaceParams(request, requestParam);
	    return finalRequest.trim();
	}
	return null;
    }


    /**
     * Replace the place holder parameters in the URL with valid parameter values
     * obtained from excel sheet.
     * 
     * @param urlParams
     */
    private void replaceURLParameters(String urlParams) {	
	if(!urlParams.isEmpty()){
	    requestURL = ParamsUtil.replaceParams(requestURL, urlParams);
	    writeURLToFile(requestURL);
	}

    }

    private void writeURLToFile(String reqURL){
	try {
	    FileUtil.createFile(fileoutpath, tcid + "_Url.txt", reqURL);
	} catch (IOException exception) {
	    logger.info(exception.getMessage(),exception);
	}
    }

    /**
     * Get the output filepath with timstamp as the last folder
     * in the folder structure. 
     * 
     * @return
     */
    private String getFileOutPath(String timestamp) {
	return Constants.TEST_OUTPUT_PATH + "\\" + timestamp + "\\";
    }

    /**
     * Set the Test_Result column in excel sheet as Skipped.
     * 
     */
    private void testSkipped() {
	reader.setCellData(Constants.SHEET_NAME, Constants.COLUMN_TEST_RESULT, currentTestcase, Constants.TEST_SKIP);
    }


    /**
     * Set the Test_Result column in excel sheet as Passed.
     * 
     */
    private void testPassed() {
	reader.setCellData(Constants.SHEET_NAME, Constants.COLUMN_TEST_RESULT, currentTestcase, Constants.TEST_PASSED);
    }


    /**
     * Set the Test_Result column in excel sheet as Failed. And also sets the
     * failure cause in Failure_Cause column.
     * 
     */
    private void testFailed(String failureCause) {
	logger.info("Test Failed : " + failureCause);
	reader.setCellData(Constants.SHEET_NAME, Constants.COLUMN_TEST_RESULT, currentTestcase, Constants.TEST_FAILED);
	reader.setCellData(Constants.SHEET_NAME, Constants.COLUMN_FAILURE_CAUSE, currentTestcase, failureCause);
	testFailed = true;
	Assert.fail("Test Failed : " + failureCause);
    }
    

    @AfterSuite
    public void tearDown() {
	try {
	    FileUtil.copyFile(Constants.TESTDATA_PATH, fileoutpath + Constants.EXCEL_TEST_REPORT_PATH);
	    clearResults();
	} catch (IOException e) {
	    logger.info(e.getMessage(), e);
	}
    }
}
===================================================================================================================


package com.amex.utils;

public class Constants {

//public static final String URL 					   = "http://192.168.1.3:8080/EmployeeData";
public static final String URL 			   = "https://dwww420.app.aexp.com/merchant/services/disputes";
public static final String REQUEST_PROP_PATH       = "src\\com\\amex\\config\\request\\Request.properties";
public static final String ERROR_CODES_PROP_PATH   = "src\\com\\amex\\config\\errorhandles\\ErrorCodes.properties";
public static final String SQL_QUERIES_PROP_PATH   = "src\\com\\amex\\config\\request\\sql_queries.properties";
public static final String SHEET_NAME              = "Sheet1";
public static final String TESTDATA_PATH           = "src\\com\\amex\\testdata\\API_Details.xlsx";
public static final String EXCEL_TEST_REPORT_PATH  = "\\API_Report.xlsx";
public static final String CONTENT_TYPE_JSON       = "application/json";
public static final String TEST_OUTPUT_PATH	   = "Test_Output_files";

public static final String COLUMN_TCID	           = "TCID";
public static final String COLUMN_API              = "API";
public static final String COLUMN_HEADER_KEY       = "Header_Key";
public static final String COLUMN_HEADER_VALUE     = "Header_Value";
public static final String COLUMN_URL_PARAMETERS   = "Url_Parameters";
public static final String COLUMN_REQUEST_NAME     = "Request_Name";
public static final String COLUMN_REQUEST_PARAM    = "Request_Parameters";
public static final String COLUMN_RESPONSE_CODE    = "Expected_Response_Code";
public static final String COLUMN_REQUEST_METHOD   = "Request_Method";
public static final String COLUMN_ERROR_NAME       = "Error_Name";
public static final String COLUMN_RESPONSE_KEY     = "Response_Keys";
public static final String COLUMN_RUN_MODE         = "Run_Mode";
public static final String COLUMN_TEST_RESULT      = "Test_Result";
public static final String COLUMN_FAILURE_CAUSE    = "Failure_Cause";
public static final String COLUMN_DB_QUERIES       = "DB_Queries";
public static final String COLUMN_DB_RESULT_KEYS   = "DB_Result_Keys";

public static final String TEST_SKIP               = "Skipped";
public static final String TEST_PASSED             = "Passed";
public static final String TEST_FAILED             = "Failed";


public static final String JDBC_DRVER_DB2          = "COM.ibm.db2os390.sqlj.jdbc.DB2SQLJDriver";
public static final String JDBC_CONNECTION_URL	   = "jdbc:db2://ccccc:7320/cccc";
public static final String DB_USER                 = "*";
public static final String DB_PASS                 = "*****";

}


================================================================================



package com.amex.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeSuite;

import com.amex.utils.Constants;
import com.amex.utils.DateTimeUtil;
import com.amex.utils.ExcelReader;
import com.amex.utils.FileReader;

public class TestBase {
    protected static ExcelReader reader;
    protected Properties requestProp;
    protected Properties errorCodesProp;
    protected String currentTimeStamp;
    private static Logger logger = LoggerFactory.getLogger(TestBase.class);

    @BeforeSuite
    public void setUp() throws IOException {
	logger.info("Loading all the required files");

	// Loading Request property file
	requestProp = loadPropertyFile(Constants.REQUEST_PROP_PATH);

	// Loading Error Codes property file
	errorCodesProp = loadPropertyFile(Constants.ERROR_CODES_PROP_PATH);

	// Loading the Test data excel sheet
	reader = FileReader.getExcelReader(Constants.TESTDATA_PATH);

	currentTimeStamp = DateTimeUtil.getCurrentTimeStamp();
    }
    
   

    /**
     * Loads the property file from the path provided and returns a Property
     * object
     * 
     * @param path
     * @return
     */
    private Properties loadPropertyFile(String path) {
	logger.info("Loading the property file : " + path);
	Properties prop = new Properties();
	FileInputStream fis = null;

	try {
	    fis = new FileInputStream(path);
	    prop.load(fis);
	} catch (FileNotFoundException e) {
	    logger.info(e.getMessage(), e);
	} catch (IOException e) {
	    logger.info(e.getMessage(), e);
	} finally {
	    if (fis != null) {
		try {
		    fis.close();
		} catch (IOException e) {
		    e.printStackTrace();
		}
	    }
	}

	return prop;
    }
}
=======================================================================

package com.amex.dataprovider;

import java.util.HashMap;

import org.testng.annotations.DataProvider;

import com.amex.base.TestBase;
import com.amex.utils.Constants;

public class RowDataProvider extends TestBase{
    
    
    @DataProvider
    public static Object[][] getRowData() {
	Object[][] object =  new Object[reader.getRowCount(Constants.SHEET_NAME)-1][1];
	for (int currentTestcase = 2; currentTestcase <= reader.getRowCount(Constants.SHEET_NAME); currentTestcase++) {
	    
	    HashMap<String, String> rowData = reader.getRowData(Constants.SHEET_NAME, currentTestcase);
	    object[currentTestcase-2][0]=new RowDataWrapper(rowData,currentTestcase);
	}
	return  object;
    }
}

=================================================================================


package com.amex.dataprovider;

import java.util.HashMap;

import com.amex.utils.Constants;

public class RowDataWrapper {
    private HashMap<String, String> rowData;
    private int currentTestcase;

    public RowDataWrapper(HashMap<String, String> rowData, int currentTestcase) {
	this.rowData = rowData;
	this.currentTestcase = currentTestcase;
    }

    public HashMap<String, String> getRowData() {
	return rowData;
    }
    
    public int getCurrentTestcase() {
	return currentTestcase;
    }

    public String toString() {
	return rowData.get(Constants.COLUMN_TCID).trim();
    }
}



=====================================================================================


package com.amex.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DB2Manager {

    private static DbReader dbReader = null;
    protected Properties requestProp;
    private static Logger logger = LoggerFactory.getLogger(DB2Manager.class);
    
    public DB2Manager() {
	dbReader = DbReader.getInstance(Constants.JDBC_DRVER_DB2, Constants.JDBC_CONNECTION_URL, Constants.DB_USER, Constants.DB_PASS);
    }
    
    
    public List<Map<String,String>> executeQueries(List<String> queries) {
	Iterator<String> queriesIt = queries.iterator();
	List<Map<String,String>> resultList = new ArrayList<Map<String,String>>();
	while(queriesIt.hasNext()) {
	    String query = queriesIt.next();
	    List<Map<String,String>> results = dbReader.executeQuery(query);
	    resultList.addAll(results);
	}
	logger.info(resultList.toString());
	return resultList;
    }
    
    
    public String getItemValue(List<Map<String,String>> resultList, String key) {
	Iterator<Map<String, String>> resultsIt = resultList.iterator();
	while (resultsIt.hasNext()) {
	    Map<String, String> resultMap = resultsIt.next();
	    for (Map.Entry<String, String> entry : resultMap.entrySet()) {
		if (entry.getKey().equals(key)) {
		    return entry.getValue();
		}
	    }
	}
	return null;
    }

    
    public List<String> getQueries(String dbQueries) throws IOException {
	requestProp = new Properties();
	FileInputStream fis = new FileInputStream(Constants.SQL_QUERIES_PROP_PATH);
	requestProp.load(fis);
	List<String> allQueries = new ArrayList<String>();
	String[] queryKeyValues = dbQueries.split(",");
	for (String queryKeyValue : queryKeyValues) {

	    queryKeyValue = queryKeyValue.trim();
	    if (!queryKeyValue.isEmpty()) {
		String[] keyValue = queryKeyValue.split(":");
		String query1 = requestProp.getProperty(keyValue[0]);
		String param1 = requestProp.getProperty(keyValue[1]);
		if (param1 != null) {
		    query1 = ParamsUtil.replaceParams(query1, param1);
		}
		allQueries.add(query1);
		logger.info(query1);
	    }

	}
	return allQueries;
    }
}


===========================================================================================


package com.amex.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 */
public class DbReader {

    private Connection connection = null;
    private ResultSet resultSet = null;
    private PreparedStatement preparedStatement = null;
    private ResultSetMetaData resultSetMetaData = null;

    private static Logger logger = LoggerFactory.getLogger(DbReader.class);

    /**
     * This method is used get the database connection
     * 
     * @param driver
     * @param url
     * @param userName
     * @param password
     * 
     */
    public DbReader(String driver, String url, String userName,
	    String password) {
	try {
	    logger.info(" Trying to connect database");
	    Class.forName(driver);
	    connection = DriverManager.getConnection(url, userName, password);
	    connection.setAutoCommit(false);
	} catch (Exception e) {
	    logger.info(e.getMessage(),e);
	}
    }
    
    public List<Map<String,String>> executeQuery(String query) {
	try {
	    preparedStatement = connection.prepareStatement(query);
	
	    resultSet = preparedStatement.executeQuery();
	    resultSetMetaData = resultSet.getMetaData();
	    
	} catch (SQLException e) {
	    // TODO Auto-generated catch block
	    logger.info(e.getMessage(), e);
	}
	return read();
    }
    

    private static volatile DbReader instance = null;

    /**
     * This method create the instance of DbReader
     * 
     * @param driver
     * @param url
     * @param userName
     * @param password
     * @return DbReader
     */
    public static DbReader getInstance(String driver, String url,
	    String userName, String password) {
	logger.info(" Trying to create instance for DB");
	if (instance == null) {
	    synchronized (DbReader.class) {
		if (instance == null) {
		    instance = new DbReader(driver, url, userName, password);
		}
	    }
	}
	return instance;
    }

    /**
     * This method read the data from database
     * 
     * @return List of map objects
     */
    private List<Map<String, String>> read() {
	logger.debug(" inside read() method");
	List<Map<String, String>> maplist = null;
	try {
	    int colCount = resultSetMetaData.getColumnCount();
	    
	    maplist = new ArrayList<Map<String, String>>();
	    while (resultSet.next()) {
		Map<String, String> map = new HashMap<String, String>();
		for (int i = 1; i <= colCount; i++) {
		    map.put(resultSetMetaData.getColumnName(i).trim(),
			    resultSet.getString(i).trim());
		}
		maplist.add(map);
	    }
	} catch (Exception e) {
	    logger.info(e.getMessage(),e);
	} finally {
	    try {
		resultSet.close();
		
	    } catch (SQLException e) {
		if (e.getMessage() != null && e.getMessage().isEmpty()) {
		    logger.error(e.getMessage());
		} else {
		    logger.error(" Problem occured while getting data from db");
		}
	    }

	}
	return maplist;
    }
}



=======================================================================================================


package com.amex.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileUtil {
    private static Logger logger = LoggerFactory.getLogger(FileUtil.class);

    public static void createFile(String filepath, String fileName, String content) throws IOException {
	try {

	    if (createDirectory(filepath)) {
		File file = new File(filepath + fileName);
		file.createNewFile();

		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(content);
		bw.close();

		logger.info("Directory/File is created!" + filepath + " "
			+ fileName);

	    }

	} catch (Exception e) {
	    logger.info(e.getMessage(),e);
	}

    }

    public static boolean createDirectory(String path) {
	File file = new File(path);
	if (!file.exists()) {
	    if (file.mkdirs()) {
		return true;
	    }
	} else {
	    return true;
	}
	return false;
    }

    public static void copyFile(String sourcePath, String destPath) throws IOException {

	File source = new File(sourcePath);
	File dest = new File(destPath);
	InputStream input = null;
	OutputStream output = null;
	try {
	    input = new FileInputStream(source);
	    output = new FileOutputStream(dest);
	    byte[] buf = new byte[1024];
	    int bytesRead;
	    while ((bytesRead = input.read(buf)) > 0) {
		output.write(buf, 0, bytesRead);
	    }
	} catch (Exception e) {
	    logger.info(e.getMessage(), e);
	} finally {
	    input.close();
	    output.close();
	}
    }

}


===================================================================================================


package com.amex.utils;

import java.util.HashMap;
import java.util.Map;

public class ParamsUtil {

    /**
     * Generates a map of key value parameters from a string in the format:
     * "key:value,key1:value1..." -->
     * 
     * @param params
     * @return
     */
    private HashMap<String, String> generateRequestParamsMap(String params) {
	HashMap<String, String> paramsMap = new HashMap<String, String>();
	String[] paramSet = params.split(",");
	for (int i = 0; i < paramSet.length; i++) {
	    String[] param = paramSet[i].split(":");
	    if (param[1].equalsIgnoreCase("null")) {
		param[1] = "";
	    }
	    paramsMap.put(param[0].trim(), param[1].trim());
	}
	return paramsMap;
    }

    /**
     * Replace the placeholder request parameters in the json request body with
     * the values in a HashMap.
     * 
     * @param requestBody
     * @param params
     * @return
     */
    private String replaceRequestParameters(String requestBody, HashMap<String, String> params) {
	for (Map.Entry<String, String> entry : params.entrySet()) {
	    requestBody = requestBody.replace(entry.getKey(), entry.getValue());
	}
	return requestBody;
    }

    /**
     * Replaces the params in a string with corresponding values provided in the 
     * keyvalueparams in the form "_key: value,_key1:value1"
     * 
     * @param requestBody
     * @param keyvalueparams
     * @return
     */
    public static String replaceParams(String requestBody, String keyvalueparams) {
	ParamsUtil putils = new ParamsUtil();
	HashMap<String, String> params = putils
		.generateRequestParamsMap(keyvalueparams);
	return putils.replaceRequestParameters(requestBody, params);
    }
}



