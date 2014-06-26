package com.amex.base;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import com.amex.utils.Constants;
import com.amex.utils.ExcelReader;
import com.amex.utils.FileReader;

public class TestBase {
	protected ExcelReader reader;
	protected Properties config;

	public void initialize() throws IOException{
		config = new Properties();
		FileInputStream ip = new FileInputStream(System.getProperty("user.dir")+Constants.REQUEST_PROP_PATH);
		config.load(ip);	

		reader = FileReader.getExcelReader(System.getProperty("user.dir")+Constants.TESTDATA_PATH);
	}



}

--------------------------------------------------------------------------------------------
request.properties

<!--Com.amex.config-->
SumCountReqSce1={ "startDt": "_startDt","endDt": "_endDt","seNbrList": ["_seNbrList1"]}
--------------------------------------------------------------------------------------------
<!-- Error Codes and Error Description -->
<!--Error Name 	Error Code 	Error Description 	Http Response Code-->
<!--com.amex.errorhandles-->

NoSEPresent=1001,EITHER GUID OR SE NO. MUST BE PROVIDED,400
InvalidGuid=1002,INVALID GUID,400
InvalidFlag=1003,INVALID FLAG,400
BadDate=1004,INVALID/UNPARSEABLE DATE,400
InvalidSeNumber=1005,INVALID SE NUMBER,400
EmptyListInput=1006,EMPTY LIST OR INVALID LIST ELEMENTS,400
StartDateIssue=1007,START DATE CAN NOT BE GREATER THAN END DATE,400
FutureStartDate=1008,START DATE CAN NOT BE GREATER THAN CURRENT DATE,400
InvalidDisputeId=1009,INVALID DISPUTE ID,400
InvalidDateFormat=1010,INVALID DATE FORMAT,400
DPFailure=1011,DATA POWER CALL FAILED,499
DPMappingFailure=1012,EMPTY RESPONSE,499
MerchantCommentsExceed=1013,Merchant Comment Should not exceed 1500 Characters,400
KeyIdBlank=1014,Dispute Id should not be blank,400
LanguageBlank=1015,Language should not be blank,400
DisputeTypeBlank=1016,Dispute Type should not be blank,400
DocTypeBlank=1017,Document Type should not be blank,400
RefundTypeBlank=1018,Refund Type should not be blank,400
ResponseCodeBlank=1019,Response Code should not be blank,400
RefundamountBlank=1020,Refund Amount should not be blank,400
RefundCurrencyBlank=1021,Refund Currency should not be blank,400
MerchantInitialBlank=1022,Merchant Initial should not be blank,400
MerchantInitialInvalid=1023,Merchant Initial should not exceed 3 character,400
InvalidResponseType=1024,INVALID RESPONSE TYPE,400
GuidOrSENotPresent=1025,GUID or SE not provided,400
FlaggedHiddenBlank=1026,Flagged and Hidden both cannot be left blank,400
FlaggedHiddenUpdateFailed=1027,Flagged and Hidden both cannot be updated in single request,400
DisputeIDBlank=1028,Dispute ids cannot be blank or null,400
DisputeIDsLimitCrossed=1029,Only 50 Dispute Cases are allowed in a single request,400
InvalidDisputeIdAndDisputeType=1030,DisputeId and DisputeType Combination is Invalid,400
InvalidInputData=1031,Invalid Input Data,400
GenericServiceError=1032,Error has been encountered,400
--------------------------------------------------------------------------------------------
com.amex.testdata.API_Details.xlsx
TCID	API	Header_Key	Header_Value	Request_Name	Request_Param	Expected_Response_Code
TC_001	/disputes-web/disputes/v1/summarycount	sm_universalid	399b7e9b0ee11f67f1595aa29011933b	SumCountReqSce1	"_startDt:2013-05-14,
_endDt:2014-05-14,
_seNbrList1:6993620004"	200

--------------------------------------------------------------------------------------------
package com.amex.testsamples;

import java.io.IOException;
import java.util.HashMap;

import com.amex.base.TestBase;
import com.amex.utils.Constants;

public class TestClass extends TestBase {

	public static void main(String[] args) throws IOException {
		TestClass test = new TestClass();
		test.initialize();
		test.test();
	}

	public void test() {
		//System.out.println(reader.getRowCount(Constants.SHEET_NAME));

		for(int i=2;i<=reader.getRowCount(Constants.SHEET_NAME);i++){
			System.out.println(i);
			HashMap<String,String> values = reader.getRowData(Constants.SHEET_NAME, i);

			String requestURL =Constants.URL+values.get(Constants.COLUMN_API);;
			String headerKey= values.get(Constants.COLUMN_HEADER_KEY);
			String headerValue= values.get(Constants.COLUMN_HEADER_VALUE);
			String requestName= values.get(Constants.COLUMN_REQUEST_NAME);
			String requestParam= values.get(Constants.COLUMN_REQUEST_PARAM);
			String requestCode= values.get(Constants.COLUMN_RESPONSE_CODE);
			
			getValidRequest(requestName,requestParam);

		}
	}

	private String getRequestSchema(String requestName){
		return config.getProperty(requestName);
	}

	public void getValidRequest(String requestName, String params){
		String request = getRequestSchema(requestName);
		HashMap<String, String> paramsMap = getRequestParamsMap(params);
		
		format(request,paramsMap);

	}
	
	public HashMap<String, String> getRequestParamsMap(String params) {
		HashMap<String, String> paramsMap = new HashMap<String, String>();
		String[] paramSet = params.split(",");
		for(int i=0;i<paramSet.length;i++){
			String[] param = paramSet[i].split(":");
			paramsMap.put(param[0], param[1]);
		}
		return paramsMap;
	}

	private void format(String request, HashMap<String, String> paramsMap) {
		System.out.println(request);
		
		System.out.println(paramsMap);
		
	}

}

--------------------------------------------------------------------------------------------
package com.amex.utils;

public class Constants {

	public static final String URL = "https://dwww420.app.aexp.com";
	public static final String REQUEST_PROP_PATH = "\\src\\com\\amex\\config\\Request.properties";
	public static final String SHEET_NAME = "Sheet1";
	public static final String TESTDATA_PATH = "\\src\\com\\amex\\testdata\\API_Details.xlsx";
	public static final String COLUMN_API = "API";
	public static final String COLUMN_HEADER_KEY="Header_Key";
	public static final String COLUMN_HEADER_VALUE="Header_Value";
	public static final String COLUMN_REQUEST_NAME="Request_Name";
	public static final String COLUMN_REQUEST_PARAM="Request_Param";
	public static final String COLUMN_RESPONSE_CODE="Expected_Response_Code";
}

--------------------------------------------------------------------------------------------
package com.amex.utils;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import ch.qos.logback.core.joran.spi.JoranException;

public class ExcelReader  {
	
	
	private FileInputStream fis = null;
	private Workbook workbook = null;
	private Sheet sheet = null;
	private Row row = null;
	private Cell cell = null;
	//public static final Logger logger = LoggerFactory.getLogger(ExcelReader.class);
	private static volatile ExcelReader instance = null;
	private static volatile Map<String,ExcelReader> excelReaderMap = new HashMap<String, ExcelReader>();
	
	public ExcelReader(String path) {	
		
//		LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
		try {
//			JoranConfigurator configurator = new JoranConfigurator();
//		    configurator.setContext(context);		    
//		    context.reset(); 
//		    configurator.doConfigure(getClass().getResource(Utils.CONFIG_FILENAME));   
//		    configurator.doConfigure(System.getProperty("config_path"));
		    //logger.info(" Reading the Excel file");
			fis = new FileInputStream(path);
			this.workbook = WorkbookFactory.create(fis);
			fis.close();
		} catch (JoranException je) {
			//logger.error(je.getMessage());
			} catch (Exception e) {
			//logger.error(e.getMessage());
		}
	}
	
	// creating singleton Object for each excelFile
	public static ExcelReader getInstance(String excelFilePath) {
		if (instance == null || !excelReaderMap.containsKey(excelFilePath)) {
			synchronized (ExcelReader.class) {
				if (instance == null || !excelReaderMap.containsKey(excelFilePath)) {
					instance = new ExcelReader(excelFilePath);
					excelReaderMap.put(excelFilePath, instance);
				}
			}
		}		
		return excelReaderMap.get(excelFilePath);
	}
	
	
	// returns the row count in a sheet
	public int getRowCount(String sheetName) {
		//logger.info(" inside getRowCount() method");
		int index = workbook.getSheetIndex(sheetName);
		if (index == -1)
			return 0;
		else {
			sheet = workbook.getSheetAt(index);
			int number = sheet.getLastRowNum() + 1;
			return number;
		}

	}
	
	// returns the columnData in a sheet with colNum
	public List<String> getColumnData(String sheetName, int colNum) {
		//logger.info(" inside getColumnData() method");

		List<String> list = null;
		try {

			list = new ArrayList<String>();
			int index = workbook.getSheetIndex(sheetName);
			if (index == -1) return list;
			
			sheet = workbook.getSheetAt(index);
			list = this.getEntireColumnCellValues(sheet, colNum);
			return list;

		} catch (Exception e) {
			if (e.getMessage() != null && e.getMessage().isEmpty()) {
				//logger.error(e.getMessage());
			} else {
				//logger.error(" column " + colNum + " does not exist in file");
			}
			return list;
		}

	}
	
	// returns the columnData in a sheet with colName
	public List<String> getColumnData(String sheetName, String colName) {
		//logger.info(" inside getColumnData() method");
		List<String> list = null;
		try {

			list = new ArrayList<String>();
			int index = workbook.getSheetIndex(sheetName);
			if (index == -1) return list;
			
			sheet = workbook.getSheetAt(index);
			row = sheet.getRow(0);
			if (row == null) return list;
			
			int colNum = 0;
			for (int i = 0; i < row.getLastCellNum(); i++) {
				if (row.getCell(i).getStringCellValue().trim()
						.equals(colName.trim())){
					colNum = i;
				}					
			}			
			if (colNum == -1) return list;	
			
			list = this.getEntireColumnCellValues(sheet,colNum);
			return list;

		} catch (Exception e) {
			if(e.getMessage() != null && e.getMessage().isEmpty()){
				//logger.error(e.getMessage());
			} else{
				//logger.error(" column " + colName + " does not exist in file");
			}	
			return list;
		}
	}


	// returns the data from a cell
	public String getCellData(String sheetName, String colName, int rowNum) {
		//logger.info(" inside getCellData() method");
		try {
			if (rowNum <= 0) return "";

			int index = workbook.getSheetIndex(sheetName);
			int col_Num = -1;
			if (index == -1) return "";

			sheet = workbook.getSheetAt(index);
			row = sheet.getRow(0);
			for (int i = 0; i < row.getLastCellNum(); i++) {
				if (row.getCell(i).getStringCellValue().trim()
						.equals(colName.trim()))
					col_Num = i;
			}
			if (col_Num == -1) return "";

			sheet = workbook.getSheetAt(index);
			row = sheet.getRow(rowNum - 1);
			if (row == null) return "";
			
			cell = row.getCell(col_Num);
			return this.getCellValue(cell);
			
		} catch (Exception e) {
			if(e.getMessage() != null && e.getMessage().isEmpty()){
				//logger.error(e.getMessage());
			} else{
				//logger.error("row " + rowNum + " or column " + colName + " does not exist in file");
			}
			return "row " + rowNum + " or column " + colName + " does not exist in file";
		}
	}

	// returns the data from a cell
	public String getCellData(String sheetName, int colNum, int rowNum) {
		//logger.info(" inside getCellData() method");
		try {
			if (rowNum <= 0) return "";

			int index = workbook.getSheetIndex(sheetName);

			if (index == -1) return "";

			sheet = workbook.getSheetAt(index);
			row = sheet.getRow(rowNum - 1);
			if (row == null) return "";
			
			cell = row.getCell(colNum);
			
			return this.getCellValue(cell);
			
		} catch (Exception e) {
			if(e.getMessage() != null && e.getMessage().isEmpty()){
				//logger.error(e.getMessage());
			} else{
				//logger.error("row " + rowNum + " or column " + colNum + " does not exist  in file");
			}
			return "row " + rowNum + " or column " + colNum + " does not exist  in file";
		}
	}

	// find whether sheets exists
	public boolean isSheetExist(String sheetName) {
		
		//logger.info(" inside isSheetExist() method");
		int index = workbook.getSheetIndex(sheetName);
		if (index == -1) {
			index = workbook.getSheetIndex(sheetName.toUpperCase());
			if (index == -1) return false;
			else
				return true;
		} else
			return true;
	}

	// returns number of columns in a sheet
	public int getColumnCount(String sheetName) {
		
		//logger.info(" inside getColumnCount() method");
		if (!isSheetExist(sheetName)) return -1;

		sheet = workbook.getSheet(sheetName);
		row = sheet.getRow(0);

		if (row == null) return -1;

		return row.getLastCellNum();

	}

	//returns row number
	public int getCellRowNum(String sheetName, String colName, String cellValue) {
		//logger.info(" inside getCellRowNum() method");

		for (int i = 2; i <= getRowCount(sheetName); i++) {
			if (getCellData(sheetName, colName, i).equalsIgnoreCase(cellValue)) {
				return i;
			}
		}
		return -1;

	}

	// returns Row data in a sheet
	public HashMap<String, String> getRowData(String sheetName, int rowNum) {
		
		//logger.info(" inside getRowData() method");
		HashMap<String, String> hashMap = null;
		try {
			
			hashMap = new HashMap<String, String>();			
			if (rowNum <= 0) return hashMap;

			int index = workbook.getSheetIndex(sheetName);			
			if (index == -1) return hashMap;

			sheet = workbook.getSheetAt(index);
			row = sheet.getRow(rowNum - 1);
			Row headerRow = sheet.getRow(0);
			
			if (headerRow == null || row == null) return hashMap;
			
			for (int i = 0; i < headerRow.getLastCellNum(); i++) {
				hashMap.put(this.getCellValue(headerRow.getCell(i)), this.getCellValue(row.getCell(i)));				
			}
			return hashMap;
			
		} catch (Exception e) {
			if(e.getMessage() != null && e.getMessage().isEmpty()){
				//logger.error(e.getMessage());
			} else{
				//logger.error("row " + rowNum + " does not exist  in file");
			}
			return hashMap;
		}
	}
	
	// get the cell data as string.
	private String getCellValue(Cell cell){
		
		if (cell == null)
			return "";
		if (cell.getCellType() == Cell.CELL_TYPE_STRING)
			return cell.getStringCellValue();
		else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC
				|| cell.getCellType() == Cell.CELL_TYPE_FORMULA) {

			String cellText = String.valueOf(cell.getNumericCellValue());
			if (DateUtil.isCellDateFormatted(cell)) {
				// format in form of M/D/YY
				double d = cell.getNumericCellValue();

				Calendar cal = Calendar.getInstance();
				cal.setTime(DateUtil.getJavaDate(d));
				cellText = (String.valueOf(cal.get(Calendar.YEAR)))
						.substring(2);
				cellText = cal.get(Calendar.DAY_OF_MONTH) + "/"
						+ cal.get(Calendar.MONTH) + 1 + "/" + cellText;

			}
			return cellText;
		} else if (cell.getCellType() == Cell.CELL_TYPE_BLANK)
			return "";
		else
			return String.valueOf(cell.getBooleanCellValue());
		
	}

	// get entire column cell data as a list	
	private List<String> getEntireColumnCellValues(Sheet sheet, int colNum){
		
		List<String> list = new ArrayList<String>();
		for (Row row : sheet) {
			if (row.getRowNum() != 0) {
				cell = row.getCell(colNum);
				if (cell != null) {
					if (cell.getCellType() == Cell.CELL_TYPE_STRING)
						list.add(cell.getStringCellValue());
					else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC
							|| cell.getCellType() == Cell.CELL_TYPE_FORMULA) {

						String cellText = String.valueOf(cell
								.getNumericCellValue());
						if (DateUtil.isCellDateFormatted(cell)) {
							// format in form of M/D/YY
							double d = cell.getNumericCellValue();

							Calendar cal = Calendar.getInstance();
							cal.setTime(DateUtil.getJavaDate(d));
							cellText = (String.valueOf(cal
									.get(Calendar.YEAR))).substring(2);
							cellText = cal.get(Calendar.MONTH) + 1 + "/"
									+ cal.get(Calendar.DAY_OF_MONTH) + "/"
									+ cellText;
						}

						list.add(cellText);
					} else if (cell.getCellType() == Cell.CELL_TYPE_BLANK)
						list.add("");
					else
						list.add(String.valueOf(cell.getBooleanCellValue()));
				}
			}
		}		
		return list;		
	}
	
}

--------------------------------------------------------------------------------------------
package com.amex.utils;


public class FileReader {

	public static ExcelReader getExcelReader(String excelFilePath) {

		if (excelFilePath == null) {
			return null;
		} else {
			return ExcelReader.getInstance(excelFilePath);
		}
	}

}
-----------------------------------------------------
dom4j-1.6.1.jar
log4j.jar
logback-classic-0.9.29.jar
logback-core-0.9.29.jar
ooxml-schemas-1.0.jar
poi-3.8-sources.jar
poi-3.8.jar
poi-ooxml-3.5-beta5.jar
slf4j-api-1.7.6-sources.jar
slf4j-api-1.7.6.jar
slf4j-simple-1.7.6.jar
xmlbeans-2.3.0.jar
