com.amex.base

TestBase.java


package com.amex.base;

import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeSuite;

import com.amex.utils.Constants;
import com.amex.utils.DateTimeUtil;
import com.amex.utils.ExcelReader;
import com.amex.utils.FileUtil;
import com.amex.utils.PropertiesReader;

public class TestBase {
    protected static ExcelReader reader;
    protected Properties requestProp;
    protected Properties errorCodesProp;
    protected Properties configProp;
    protected String currentTimeStamp;
    
    private String requestPropFileName;
    private String errorPropFileName;
    
    private static Logger logger = LoggerFactory.getLogger(TestBase.class);

    @BeforeSuite
    public void setUp() throws IOException {
	logger.info("Loading all the required files");

	//Loading config.properties
	configProp = PropertiesReader.loadPropertyFile(Constants.CONFIG_PROP_PATH);
	
	requestPropFileName = configProp.getProperty(Constants.KEY_REQ_PROP_FILE);
	errorPropFileName   = configProp.getProperty(Constants.KEY_ERR_PROP_FILE);
	
	// Loading Request property file
	requestProp = PropertiesReader.loadPropertyFile(Constants.REQUEST_PROP_PATH + requestPropFileName);

	// Loading Error Codes property file
	errorCodesProp = PropertiesReader.loadPropertyFile(Constants.ERROR_CODES_PROP_PATH + errorPropFileName);

	// Loading the Test data excel sheet
	reader = FileUtil.getExcelReader(Constants.TESTDATA_PATH + configProp.getProperty(Constants.KEY_TESTDATA));
	
	currentTimeStamp = DateTimeUtil.getCurrentTimeStamp();
    }

}



======================================================================================================




com.amex.config

config.properties

#Test data ExcelSheet filename in the folder com/amex/testdata/

#testdata.filename   = API_Details_Disputes.xlsx
#testreport.filename = API_Report_Disputes.xlsx

testdata.filename   = API_Details_Payments_Yatharth.xlsx
testreport.filename = API_Report_Payments_Yatharth.xlsx

#Base url for the Api

#api.base.url=https://dwww421.app.aexp.com/merchant/services/disputes
api.base.url = https://dwww421.app.aexp.com/merchant/services/payments

#Setup properties file
#
#error.prop.filename       = disputes_ErrorCodes.properties
#request.prop.filename     = disputes_Request.properties
#sql.queries.prop.filename = disputes_sql_queries.properties
#
error.prop.filename       = payments_ErrorCodes.properties
request.prop.filename     = payments_Request.properties
sql.queries.prop.filename = payments_sql_queries.properties


======================================================================================================

com.amex.config.errorhandles

disputes_ErrorCodes.properties

#Error Codes and Description

NoSEPresent=1001;EITHER GUID OR SE NO. MUST BE PROVIDED
InvalidGuid=1002;INVALID GUID
InvalidFlag=1003;INVALID FLAG
BadDate=1004;INVALID/UNPARSEABLE DATE
InvalidSeNumber=1005;INVALID SE NUMBER
EmptyListInput=1006;EMPTY LIST OR INVALID LIST ELEMENTS
StartDateIssue=1007;START DATE CAN NOT BE GREATER THAN END DATE
FutureStartDate=1008;START DATE CAN NOT BE GREATER THAN CURRENT DATE
InvalidDisputeId=1009;INVALID DISPUTE ID 
InvalidDateFormat=1010;INVALID DATE FORMAT
DPFailure=1011;DATA POWER CALL FAILED 
DPMappingFailure=1012;EMPTY RESPONSE
MerchantCommentsExceed=1013;Merchant Comment Should not exceed 1500 Characters 
KeyIdBlank=1014;Dispute Id should not be blank 
LanguageBlank=1015;Language should not be blank 
DisputeTypeBlank=1016;Dispute Type should not be blank 
DocTypeBlank=1017;Document Type should not be blank 
RefundTypeBlank=1018;Refund Type should not be blank 
ResponseCodeBlank=1019;Response Code should not be blank 
RefundamountBlank=1020;Refund Amount should not be blank 
RefundCurrencyBlank=1021;Refund Currency should not be blank 
MerchantInitialBlank=1022;Merchant Initial should not be blank 
MerchantInitialInvalid=1023;Merchant Initial should not exceed 3 character 
InvalidResponseType=1024;INVALID RESPONSE TYPE 
GuidOrSENotPresent=1025;GUID or SE not provided 
FlaggedHiddenBlank=1026;Flagged and Hidden both cannot be left blank 
FlaggedHiddenUpdateFailed=1027;Flagged and Hidden both cannot be updated in single request 
DisputeIDBlank=1028;Dispute ids cannot be blank or null 
DisputeIDsLimitCrossed=1029;Only 50 Dispute Cases are allowed in a single request 
InvalidDisputeIdAndDisputeType=1030;DisputeId and DisputeType Combination is Invalid 
InvalidInputData=1031;Invalid Input Data
GenericServiceError=1032;Error has been encountered
#Added on 23/07/14
RefundCurrencyNotBlank=1047;Refund Currency should not be provided in request or should be null
RefundamountNotBlank=1046;Refund Amount should not be provided in request or should be null
DisputeStageCdInvalid=1034;Dispute Stage is Invalid
disputeIdNotLocked=1048;One or More Dispute Ids are locked by other user or you haven't acquired lock previously
invalidDisputeNbr=1075;Dispute number is not valid.
userNotAuthorized=1049;User Not Authorized

======================================================================================================

com.amex.config.errorhandles

payments_ErrorCodes.properties

#Error Codes and Description

#  Payment Error Codes
invalidStartDate=1007;Start Date cannot be greater than End Date
DPFailure=1001;DATA POWER CALL FAILED
invalidSeNumber=1004;Invalid SE Number
invalidStartDateFormat=1005;Invalid Start Date Format 
invalidEndDateFormat=1006;Invalid End Date Format
userNotAuthorized=1000;User Not Authorized
invalidSettlementDtFormat =1024;Invalid Settlement Date Format.
invalidSettlementNumber=1028;Invalid Settlement Number
invalidBussinessCenter=1037;Bussiness Center is not valid only allowed values are 000,001,002,005,010,015,016,023,024,025,037,048,049,050,053,056,057,060,077,080,081,084,085,091,092
invalidRange= 1010;Lower limit can not be greater than Upper limit.
lowBoundIssue = 1008;Lower limit can not be less than zero.
lowBoundRequired = 1016;Lower Bound is required when Upper Bound is provided
invalidRangeBothZero = 1018;Invalid Range. Both lowerBound and upperBound cannot be 0
nullPayeeSeNumber=1027;Payee SE Number cannot be left null/blank.
invalidPayeeSeNumber=1026;Invalid Payee SE number.
nullSettlementDt=1025;Settlement Date cannot be left null/blank.
nullSettlementNumber=1024;Settlement Number cannot be left null/blank.
blankSortColumn = 1013;Sort Column cannot be left null/blank
invalidSortColumn =1014;Invalid sort column name
invalidSortOrder = 1015;Invalid Sort Order
invalidSOCNumber=1029;Invalid SOC Number.
invalidRangeDifferenceOver99=1019;Invalid Range. The difference between lowerBound and upperBound cannot exceed 99
multipleInputOptions=1035;Only one input option is allowed
blankEndDate=1012;End Date cannot be left null/blank
blankStartDate=1011;Start Date cannot be left null/blank
negativeUpperBound=1017;Upper bound cannot be less than 0

======================================================================================================


com.amex.config.request
disputes_Request.properties 
EmptyReqBody=
# Summary Count
SumCountReqSce1={ "startDt": "_startDt","endDt": "_endDt","seNbrList": ["_seNbrList1"]}

# Dispute Summary List 
DispListReqSce1={"seNbrList": ["seNbrList"],"sort": [{"columnName": "_columnName","sortOrder": "_sortOrder"}],"lowBound": "_lowBound","upBound": "_upBound","langCd": "_langCd","startDt": "_startDt","endDt": "_endDt","stage": "_stage","includeIndicators":_includeIndicators}
DispSumListReqSce1 = {"seNbrList":["_seNbrList1"],"sort":[{"columnName":"_columnName","sortOrder":"_sortOrder"}],"lowBound":"_lowBound","upBound":"_upBound","langCd":"_langCd","startDt":_startDt,"endDt":_endDt,"stage":"_stage","includeIndicators":_includeIndicators}
DispSumListReqSce2 = {"seNbrList":["_seNbrList1"],"sort":[{"columnName":"_columnName","sortOrder":"_sortOrder"}],"lowBound":"_lowBound","upBound":"_upBound","langCd":"_langCd","startDt":"_startDt","endDt":"_endDt","stage":"_stage","includeIndicators":_includeIndicators}

# Flag - Unflag
flagSce1 = {"flagged": _flag,"hidden": _hidden,"disputeIdList": ["_disputeId1","_disputeId2","_disputeId2"]}
flagSce2 = {"flagged": "_flag","hidden": "_hidden","disputeIdList": ["_disputeId1"]}
flagSce3 = {"flagged": _flag,"hidden": _hidden,"disputeIdList": []}

# Lock Dispute 
LockDispSce1 = {"disputeIds":["_dispId1"]}
LockDispSce2 = {"disputeIds":["_dispId1","_dispId2"]}
LockDispSce3 = {"disputeIds":["_dispId1","_dispId2","_dispId3"]}

#Dispute Response
DisputeResponseSce1 = {"disputeAcknowledgements":[{"disputeId":"_disputeId1","referenceNbr":_referenceNbr1,"refundAmt":_refundAmt1,"refundCurrencyCd":_refundCurrencyCd1,"trackingNbr":_trackingNbr1,"disputeStageCd":"_disputeStageCd1"}],"disputeRespCd":"_disputeRespCd","docTypeCd":"_docTypeCd","langCd":"_langCd","merchantComments":_merchantComments,"merchantInitials":"_merchantInitials","batchId":_batchId}
DisputeResponseSce2 = {"disputeAcknowledgements":[{"disputeId":"_disputeId1","referenceNbr":_referenceNbr1,"refundAmt":_refundAmt1,"refundCurrencyCd":_refundCurrencyCd1,"trackingNbr":_trackingNbr1,"disputeStageCd":"_disputeStageCd1"}],"disputeRespCd":_disputeRespCd,"docTypeCd":"_docTypeCd","langCd":"_langCd","merchantComments":_merchantComments,"merchantInitials":"_merchantInitials","batchId":_batchId}
DisputeResponseSce3 = {"disputeAcknowledgements":[{"disputeId":"_disputeId1","referenceNbr":_referenceNbr1,"refundAmt":"_refundAmt1","refundCurrencyCd":"_refundCurrencyCd1","trackingNbr":_trackingNbr1,"disputeStageCd":"_disputeStageCd1"}],"disputeRespCd":"_disputeRespCd","docTypeCd":"_docTypeCd","langCd":"_langCd","merchantComments":_merchantComments,"merchantInitials":"_merchantInitials","batchId":_batchId}
DisputeResponseSce4 = {"disputeAcknowledgements":[{"disputeId":"_disputeId1","referenceNbr":_referenceNbr1,"refundAmt":"_refundAmt1","refundCurrencyCd":"_refundCurrencyCd1","trackingNbr":_trackingNbr1,"disputeStageCd":"_disputeStageCd1"}],"disputeRespCd":_disputeRespCd,"docTypeCd":"_docTypeCd","langCd":"_langCd","merchantComments":_merchantComments,"merchantInitials":"_merchantInitials","batchId":_batchId}
DisputeResponseSce5 = {"disputeAcknowledgements":[{"disputeId":"_disputeId1","referenceNbr":_referenceNbr1,"refundAmt":_refundAmt1,"refundCurrencyCd":"_refundCurrencyCd1","trackingNbr":_trackingNbr1,"disputeStageCd":"_disputeStageCd1"}],"disputeRespCd":"_disputeRespCd","docTypeCd":"_docTypeCd","langCd":"_langCd","merchantComments":_merchantComments,"merchantInitials":"_merchantInitials","batchId":_batchId}
#with two dispute ids
DisputeResponseSce6 = {"disputeAcknowledgements":[{"disputeId":"_disputeId1","referenceNbr":_referenceNbr1,"refundAmt":_refundAmt1,"refundCurrencyCd":_refundCurrencyCd1,"trackingNbr":_trackingNbr1,"disputeStageCd":"_disputeStageCd1"},{"disputeId":"_disputeId2","referenceNbr":_referenceNbr2,"refundAmt":_refundAmt2,"refundCurrencyCd":_refundCurrencyCd2,"trackingNbr":_trackingNbr2,"disputeStageCd":"_disputeStageCd2"}],"disputeRespCd":"_disputeRespCd","docTypeCd":"_docTypeCd","langCd":"_langCd","merchantComments":_merchantComments,"merchantInitials":"_merchantInitials","batchId":_batchId}

#Advanced Search
#DisputeNbr Invalid Date
AdvancedSearchSce1 = {"startDt":"_startDt","endDt":"_endDt","seNbrList":["_seNbrList1"],"sort":[],"lowBound":_lowBound,"upBound":_upBound,"langCd":"_langCd","stage":"_stage","includeIndicators":_includeIndicators,"simpleSearch":{"disputeNbr":"_disputeNbr"}}
#DisputeNbr (chargeback)
AdvancedSearchSce2 ={"lowBound":_lowBound,"upBound":_upBound,"stage":"_stage","includeIndicators":_includeIndicators,"startDt":_startDt,"endDt":_endDt,"sort":[{"columnName":"_columnName","sortOrder":"_sortOrder"}],"seNbrList":[],"simpleSearch":{"disputeNbr":"_disputeNbr"}}
#cardNbr
AdvancedSearchSce3 = {"lowBound":_lowBound,"upBound":_upBound,"stage":"_stage","includeIndicators":_includeIndicators,"startDt":_startDt,"endDt":_endDt,"sort":[{"columnName":"_columnName","sortOrder":"_sortOrder"}],"seNbrList":[],"simpleSearch":{"cardNbr":"_cardNbr"}}
#chrgAmt
AdvancedSearchSce4 = {"lowBound":_lowBound,"upBound":_upBound,"stage":"_stage","includeIndicators":_includeIndicators,"startDt":"_startDt","endDt":"_endDt","sort":[],"seNbrList":[],"simpleSearch":{"chrgAmtLower": _chrgAmtLower, "chrgAmtUpper": _chrgAmtUpper}}
#disputeAmt
AdvancedSearchSce5 = {"lowBound":_lowBound,"upBound":_upBound,"stage":"_stage","includeIndicators":_includeIndicators,"startDt":"_startDt","endDt":"_endDt","sort":[{"columnName":"_columnName","sortOrder":"_sortOrder"}],"seNbrList":[],"simpleSearch":{"disputeAmtLower": _disputeAmtLower, "disputeAmtUpper": _disputeAmtUpper}}

FileUploadBatchSce1 = {"disputeIds":["_disputeId1"]}

======================================================================================================


com.amex.config.request
disputes_sql_queries.properties

GUID=select GLBL_USER_ID from OD1.TC391_USER where FIRST_NM = '_first_nm' and LST_NM = '_lst_nm'


##Queries for DisputeSummaryCount

<!--Without specific dates-->
SQLQueryDSC1Sch1=select  count(*) as "activeDisputes" from P65.GLBL_CASE_STA WHERE DERIV_CASE_STA_NM IN ('In Progress','Urgent Response Required','Response Required') and  SE_NO IN (Select SE_NO from OD1.TE381_MER_FLATTENED_HIER_REL where LVL_6_SE_NO = '_SENO')
ParamsDSC1Sch1=_SENO:4090130824

SQLQueryDSC2Sch1=select count(*) as "chargeback" from P65.GLBL_CASE_STA WHERE CASE_STAGE_CD='C' and DERIV_CASE_STA_NM IN ('Responded Offline','In Progress','Urgent Response Required','Response Required') and  SE_NO IN (Select SE_NO from OD1.TE381_MER_FLATTENED_HIER_REL where LVL_6_SE_NO = '_SENO')
ParamsDSC2Sch1=_SENO:4090130824

SQLQueryDSC3Sch1=select count(*) as "recentUpdates" from P65.GLBL_CASE_STA WHERE DERIV_CASE_STA_NM IN ('Closed Chargeback','Closed in your favor') and  SE_NO IN (Select SE_NO from OD1.TE381_MER_FLATTENED_HIER_REL where LVL_6_SE_NO = '_SENO')
ParamsDSC3Sch1=_SENO:4090130824

SQLQueryDSC4Sch1=select count(*) as "recentlyClosed" from P65.GLBL_CASE_STA WHERE DERIV_CASE_STA_NM IN ('Closed Status','Closed Chargeback','Closed in your favor') and  SE_NO IN (Select SE_NO from OD1.TE381_MER_FLATTENED_HIER_REL where LVL_6_SE_NO = '_SENO')
ParamsDSC4Sch1=_SENO:4090130824

SQLQueryDSC5Sch1=select count(*) as "responseRequired" from P65.GLBL_CASE_STA WHERE DERIV_CASE_STA_NM IN ('Urgent Response Required','Response Required') and  SE_NO IN (Select SE_NO from OD1.TE381_MER_FLATTENED_HIER_REL where LVL_6_SE_NO = '_SENO')
ParamsDSC5Sch1=_SENO:4090130824

SQLQueryDSC6Sch1=select count(*) as "inProgress" from P65.GLBL_CASE_STA WHERE DERIV_CASE_STA_NM IN ('In Progress','Responded Offline') and  SE_NO IN (Select SE_NO from OD1.TE381_MER_FLATTENED_HIER_REL where LVL_6_SE_NO = '_SENO')
ParamsDSC6Sch1=_SENO:4090130824

SQLQueryDSC7Sch1=select count(*) as "closedInFavor" from P65.GLBL_CASE_STA WHERE DERIV_CASE_STA_NM IN ('Closed in your favor') and  SE_NO IN (Select SE_NO from OD1.TE381_MER_FLATTENED_HIER_REL where LVL_6_SE_NO = '_SENO')
ParamsDSC7Sch1=_SENO:4090130824

SQLQueryDSC8Sch1=select count(*) as "closedChargebacks" from P65.GLBL_CASE_STA WHERE DERIV_CASE_STA_NM IN ('Closed Chargeback', 'Closed Status') and SE_NO IN (Select SE_NO from OD1.TE381_MER_FLATTENED_HIER_REL where LVL_6_SE_NO = '_SENO')
ParamsDSC8Sch1=_SENO:4090130824

##---With specific start and end dates---##
SQLQueryDSC1Sch2=select  count(*) as "activeDisputes" from P65.GLBL_CASE_STA WHERE DERIV_CASE_STA_NM IN ('In Progress','Urgent Response Required','Response Required') and  SE_NO IN (Select SE_NO from OD1.TE381_MER_FLATTENED_HIER_REL where LVL_6_SE_NO = '_SENO') and Date(REC_LOAD_TS) BETWEEN '_startDt' AND '_endDt' 
ParamsDSC1Sch2=_SENO:4090130824,_startDt:2012-03-11,_endDt:2014-06-02

SQLQueryDSC2Sch2=select count(*) as "chargeback" from P65.GLBL_CASE_STA WHERE CASE_STAGE_CD='C' and DERIV_CASE_STA_NM IN ('Responded Offline','In Progress','Urgent Response Required','Response Required') and  SE_NO IN (Select SE_NO from OD1.TE381_MER_FLATTENED_HIER_REL where LVL_6_SE_NO = '_SENO') and Date(REC_LOAD_TS) BETWEEN '_startDt' AND '_endDt'
ParamsDSC2Sch2=_SENO:4090130824,_startDt:2012-03-11,_endDt:2014-06-02

SQLQueryDSC3Sch2=select count(*) as "recentUpdates" from P65.GLBL_CASE_STA WHERE DERIV_CASE_STA_NM IN ('Closed Chargeback','Closed in your favor') and  SE_NO IN (Select SE_NO from OD1.TE381_MER_FLATTENED_HIER_REL where LVL_6_SE_NO = '_SENO') and Date(REC_LOAD_TS) BETWEEN '_startDt' AND '_endDt'
ParamsDSC3Sch2=_SENO:4090130824,_startDt:2012-03-11,_endDt:2014-06-02

SQLQueryDSC4Sch2=select count(*) as "recentlyClosed" from P65.GLBL_CASE_STA WHERE DERIV_CASE_STA_NM IN ('Closed Status','Closed Chargeback','Closed in your favor') and  SE_NO IN (Select SE_NO from OD1.TE381_MER_FLATTENED_HIER_REL where LVL_6_SE_NO = '_SENO') and Date(REC_LOAD_TS) BETWEEN '_startDt' AND '_endDt'
ParamsDSC4Sch2=_SENO:4090130824,_startDt:2012-03-11,_endDt:2014-06-02

SQLQueryDSC5Sch2=select count(*) as "responseRequired" from P65.GLBL_CASE_STA WHERE DERIV_CASE_STA_NM IN ('Urgent Response Required','Response Required') and  SE_NO IN (Select SE_NO from OD1.TE381_MER_FLATTENED_HIER_REL where LVL_6_SE_NO = '_SENO') and Date(REC_LOAD_TS) BETWEEN '_startDt' AND '_endDt'
ParamsDSC5Sch2=_SENO:4090130824,_startDt:2012-03-11,_endDt:2014-06-02

SQLQueryDSC6Sch2=select count(*) as "inProgress" from P65.GLBL_CASE_STA WHERE DERIV_CASE_STA_NM IN ('In Progress','Responded Offline') and  SE_NO IN (Select SE_NO from OD1.TE381_MER_FLATTENED_HIER_REL where LVL_6_SE_NO = '_SENO') and Date(REC_LOAD_TS) BETWEEN '_startDt' AND '_endDt'
ParamsDSC6Sch2=_SENO:4090130824,_startDt:2012-03-11,_endDt:2014-06-02

SQLQueryDSC7Sch2=select count(*) as "closedInFavor" from P65.GLBL_CASE_STA WHERE DERIV_CASE_STA_NM IN ('Closed in your favor') and  SE_NO IN (Select SE_NO from OD1.TE381_MER_FLATTENED_HIER_REL where LVL_6_SE_NO = '_SENO') and Date(REC_LOAD_TS) BETWEEN '_startDt' AND '_endDt'
ParamsDSC7Sch2=_SENO:4090130824,_startDt:2012-03-11,_endDt:2014-06-02

SQLQueryDSC8Sch2=select count(*) as "closedChargebacks" from P65.GLBL_CASE_STA WHERE DERIV_CASE_STA_NM IN ('Closed Chargeback', 'Closed Status') and SE_NO IN (Select SE_NO from OD1.TE381_MER_FLATTENED_HIER_REL where LVL_6_SE_NO = '_SENO') and Date(REC_LOAD_TS) BETWEEN '_startDt' AND '_endDt'
ParamsDSC8Sch2=_SENO:4090130824,_startDt:2012-03-11,_endDt:2014-06-02

<!-- ChildSE -->
SQLQueryDSC1Sch3=select  count(*) as "activeDisputes" from P65.GLBL_CASE_STA WHERE DERIV_CASE_STA_NM IN ('In Progress','Urgent Response Required','Response Required') and  SE_NO IN ('_SENO')
ParamsDSC1Sch3=_SENO:4090121872

SQLQueryDSC2Sch3=select count(*) as "chargeback" from P65.GLBL_CASE_STA WHERE CASE_STAGE_CD='C' and DERIV_CASE_STA_NM IN ('Responded Offline','In Progress','Urgent Response Required','Response Required') and  SE_NO IN ('_SENO')
ParamsDSC2Sch3=_SENO:4090121872

SQLQueryDSC3Sch3=select count(*) as "recentUpdates" from P65.GLBL_CASE_STA WHERE DERIV_CASE_STA_NM IN ('Closed Chargeback','Closed in your favor') and  SE_NO IN ('_SENO')
ParamsDSC3Sch3=_SENO:4090121872

SQLQueryDSC4Sch3=select count(*) as "recentlyClosed" from P65.GLBL_CASE_STA WHERE DERIV_CASE_STA_NM IN ('Closed Status','Closed Chargeback','Closed in your favor') and  SE_NO IN ('_SENO')
ParamsDSC4Sch3=_SENO:4090121872

SQLQueryDSC5Sch3=select count(*) as "responseRequired" from P65.GLBL_CASE_STA WHERE DERIV_CASE_STA_NM IN ('Urgent Response Required','Response Required') and  SE_NO IN ('_SENO')
ParamsDSC5Sch3=_SENO:4090121872

SQLQueryDSC6Sch3=select count(*) as "inProgress" from P65.GLBL_CASE_STA WHERE DERIV_CASE_STA_NM IN ('In Progress','Responded Offline') and  SE_NO IN ('_SENO')
ParamsDSC6Sch3=_SENO:4090121872

SQLQueryDSC7Sch3=select count(*) as "closedInFavor" from P65.GLBL_CASE_STA WHERE DERIV_CASE_STA_NM IN ('Closed in your favor') and  SE_NO IN ('_SENO')
ParamsDSC7Sch3=_SENO:4090121872

SQLQueryDSC8Sch3=select count(*) as "closedChargebacks" from P65.GLBL_CASE_STA WHERE DERIV_CASE_STA_NM IN ('Closed Chargeback', 'Closed Status') and SE_NO IN ('_SENO')
ParamsDSC8Sch3=_SENO:4090121872



# Queries for Dispute summary list

<!-- Details for ChargeBack -->
SQLQueryDSL1=select DISTINCT(B.GLBL_CASE_STA_ID) as "disputeId", B.CASE_NO as "disputeNbr", A.SE_NO as "seNbr", C.DISP_CTGY_DS as "disputeCategoryDesc", ADJ_NO as "adjustmentNbr", CASE_UPDT_TYPE_CD as "disputeUpdType", RESP_IN as "responseInd", DERIV_CASE_STA_NM as "disputeStatusCd", INQ_ID as "inqId", B.SE_RESP_BY_DT as "replyByDt", CHRGBK_ID as "chrgBkId", (select CASE count(E.USER_DSPT_FLAG_TYPE_CD) WHEN 1 THEN 'true' WHEN 0 THEN 'false' END from P65.USER_DSPT_FLAG_SLCT E where E.GLBL_CASE_STA_ID = B.GLBL_CASE_STA_ID) as "flagged", D.PG_TOT_NO as "daysPending", CASE_UPDT_ID as "disputeUpdId", varchar_format(B.UPDT_LOAD_TS,'YYYY-MM-DD') as "updDt", CASE_TYPE_NM as "disputeType", B.CHRG_CURR_CD as "chrgBkCurrencyCd", CASE_RSN_CD as "rsnCd", decimal(B.CHRG_AM,12,1) as "chrgAmt", B.DSPT_CURR_CD as "disputeCurrencyCd", decimal(B.DSPT_AM,12,1) as "disputeAmt", SUBSTR(B.CARD_NO, 1, 6) || 'XXXXX' || SUBSTR(B.CARD_NO, 12, 4) as "cardNbr", decimal(B.CHRGBK_AM,12,1) as "chrgBkAmt", B.CHRG_DT as "chrgDt", B.TRANS_ID as "chrgRefNbr", B.PAY_SE_NO as "payeeSENbr", decimal(B.SETTLE_AM,12,1) as "settleAmt", B.SETTLE_CURR_CD as "settleCurrencyCd", B.SETTLE_DT as "settleDt", B.ROC_INV_1_NO as "rocInvNbr", B.SUBM_SE_LOC_ID as "locationId", B.PAYEE_SE_LOC_ID as "payeeLocationId", B.AIR_TKT_NO as "airTktNbr", B.CAR_RENT_AGR_NO as "carRentalAgreementNbr", B.RESP_CD as "merchantRespCd", B.CHRGBK_DT as "chrgBkDt", B.BASIC_CM_NM as "cardMemberNm", B.ORIG_CARD_NO as "originalCardNbr", varchar_format(B.REC_LOAD_TS,'YYYY-MM-DD') as "recvdDt" From OD1.TE381_MER_FLATTENED_HIER_REL A, P65.GLBL_CASE_STA B, P65.GLOBAL_DISPUTE_REASON C, P65.GLBL_INQ_CASE D where A.SE_NO = B.SE_NO and B.CASE_STAGE_CD = 'C' and B.DERIV_CASE_STA_NM IN ('Responded Offline','In Progress','Urgent Response Required','Response Required') and A.SE_NO IN (Select SE_NO from OD1.TE381_MER_FLATTENED_HIER_REL where LVL_6_SE_NO = '_LVL_6_SE_NO') and C.DSPT_RSN_CD= B.CASE_RSN_CD and D.SE_NO = A.SE_NO and C.LANGUAGE_CD ='_LANGUAGE_CD' order by B.CASE_NO asc
ParamsDSL1=_LVL_6_SE_NO:4090130824,_LANGUAGE_CD:EN

<!-- Count for ChargeBack -->
SQLQueryDSL2=select decimal(Sum(DSPT_AM),12,1) as "totalDisputeAmt", count(*) as "totalCt" From OD1.TE381_MER_FLATTENED_HIER_REL A, P65.GLBL_CASE_STA B where A.SE_NO = B.SE_NO and B.CASE_STAGE_CD = 'C' and B.DERIV_CASE_STA_NM IN('Responded Offline','In Progress','Urgent Response Required','Response Required') and A.SE_NO IN (Select SE_NO from OD1.TE381_MER_FLATTENED_HIER_REL where LVL_6_SE_NO = '_LVL_6_SE_NO')
ParamsDSL2=_LVL_6_SE_NO:4090130824


<!-- Details for Active disputes -->
SQLQueryDSL3=select DISTINCT(B.GLBL_CASE_STA_ID) as "disputeId", B.CASE_NO as "disputeNbr", A.SE_NO as "seNbr", C.DISP_CTGY_DS as "disputeCategoryDesc", ADJ_NO as "adjustmentNbr", CASE_UPDT_TYPE_CD as "disputeUpdType", RESP_IN as "responseInd", DERIV_CASE_STA_NM as "disputeStatusCd", INQ_ID as "inqId", B.SE_RESP_BY_DT as "replyByDt", CHRGBK_ID as "chrgBkId", (select CASE count(E.USER_DSPT_FLAG_TYPE_CD) WHEN 1 THEN 'true' WHEN 0 THEN 'false' END from P65.USER_DSPT_FLAG_SLCT E where E.GLBL_CASE_STA_ID = B.GLBL_CASE_STA_ID) as "flagged", D.PG_TOT_NO as "daysPending", CASE_UPDT_ID as "disputeUpdId", varchar_format(B.UPDT_LOAD_TS,'YYYY-MM-DD') as "updDt", CASE_TYPE_NM as "disputeType", B.CHRG_CURR_CD as "chrgBkCurrencyCd", CASE_RSN_CD as "rsnCd", decimal(B.CHRG_AM,12,1) as "chrgAmt", B.DSPT_CURR_CD as "disputeCurrencyCd", decimal(B.DSPT_AM,12,1) as "disputeAmt", SUBSTR(B.CARD_NO, 1, 6) || 'XXXXX' || SUBSTR(B.CARD_NO, 12, 4) as "cardNbr", decimal(B.CHRGBK_AM,12,1) as "chrgBkAmt", B.CHRG_DT as "chrgDt", B.TRANS_ID as "chrgRefNbr", B.PAY_SE_NO as "payeeSENbr", decimal(B.SETTLE_AM,12,1) as "settleAmt", B.SETTLE_CURR_CD as "settleCurrencyCd", B.SETTLE_DT as "settleDt", B.ROC_INV_1_NO as "rocInvNbr", B.SUBM_SE_LOC_ID as "locationId", B.PAYEE_SE_LOC_ID as "payeeLocationId", B.AIR_TKT_NO as "airTktNbr", B.CAR_RENT_AGR_NO as "carRentalAgreementNbr", B.RESP_CD as "merchantRespCd", B.CHRGBK_DT as "chrgBkDt", B.BASIC_CM_NM as "cardMemberNm", B.ORIG_CARD_NO as "originalCardNbr", varchar_format(B.REC_LOAD_TS,'YYYY-MM-DD') as "recvdDt" From OD1.TE381_MER_FLATTENED_HIER_REL A, P65.GLBL_CASE_STA B, P65.GLOBAL_DISPUTE_REASON C, P65.GLBL_INQ_CASE D where A.SE_NO = B.SE_NO and B.DERIV_CASE_STA_NM IN ('Urgent Response Required','Response Required','In Progress') and A.SE_NO IN (Select SE_NO from OD1.TE381_MER_FLATTENED_HIER_REL where LVL_6_SE_NO = '_LVL_6_SE_NO') and C.DSPT_RSN_CD= B.CASE_RSN_CD and D.SE_NO = A.SE_NO and C.LANGUAGE_CD ='_LANGUAGE_CD' order by B.CASE_NO asc
ParamsDSL3=_LVL_6_SE_NO:4090130824,_LANGUAGE_CD:EN

<!-- Count for Active Disputes -->
SQLQueryDSL4=select decimal(Sum(DSPT_AM),12,1) as "totalDisputeAmt", count(*) as "totalCt" From OD1.TE381_MER_FLATTENED_HIER_REL A, P65.GLBL_CASE_STA B where A.SE_NO = B.SE_NO and B.DERIV_CASE_STA_NM IN('Urgent Response Required','Response Required','In Progress') and A.SE_NO IN (Select SE_NO from OD1.TE381_MER_FLATTENED_HIER_REL where LVL_6_SE_NO = '_LVL_6_SE_NO')
ParamsDSL4=_LVL_6_SE_NO:4090130824

<!-- Details for Recent updates -->
SQLQueryDSL5=select DISTINCT(B.GLBL_CASE_STA_ID) as "disputeId", B.CASE_NO as "disputeNbr", A.SE_NO as "seNbr", C.DISP_CTGY_DS as "disputeCategoryDesc", ADJ_NO as "adjustmentNbr", CASE_UPDT_TYPE_CD as "disputeUpdType", RESP_IN as "responseInd", DERIV_CASE_STA_NM as "disputeStatusCd", INQ_ID as "inqId", B.SE_RESP_BY_DT as "replyByDt", CHRGBK_ID as "chrgBkId", (select CASE count(E.USER_DSPT_FLAG_TYPE_CD) WHEN 1 THEN 'true' WHEN 0 THEN 'false' END from P65.USER_DSPT_FLAG_SLCT E where E.GLBL_CASE_STA_ID = B.GLBL_CASE_STA_ID) as "flagged", D.PG_TOT_NO as "daysPending", CASE_UPDT_ID as "disputeUpdId", varchar_format(B.UPDT_LOAD_TS,'YYYY-MM-DD') as "updDt", CASE_TYPE_NM as "disputeType", B.CHRG_CURR_CD as "chrgBkCurrencyCd", CASE_RSN_CD as "rsnCd", decimal(B.CHRG_AM,12,1) as "chrgAmt", B.DSPT_CURR_CD as "disputeCurrencyCd", decimal(B.DSPT_AM,12,1) as "disputeAmt", SUBSTR(B.CARD_NO, 1, 6) || 'XXXXX' || SUBSTR(B.CARD_NO, 12, 4) as "cardNbr", decimal(B.CHRGBK_AM,12,1) as "chrgBkAmt", B.CHRG_DT as "chrgDt", B.TRANS_ID as "chrgRefNbr", B.PAY_SE_NO as "payeeSENbr", decimal(B.SETTLE_AM,12,1) as "settleAmt", B.SETTLE_CURR_CD as "settleCurrencyCd", B.SETTLE_DT as "settleDt", B.ROC_INV_1_NO as "rocInvNbr", B.SUBM_SE_LOC_ID as "locationId", B.PAYEE_SE_LOC_ID as "payeeLocationId", B.AIR_TKT_NO as "airTktNbr", B.CAR_RENT_AGR_NO as "carRentalAgreementNbr", B.RESP_CD as "merchantRespCd", B.CHRGBK_DT as "chrgBkDt", B.BASIC_CM_NM as "cardMemberNm", B.ORIG_CARD_NO as "originalCardNbr", varchar_format(B.REC_LOAD_TS,'YYYY-MM-DD') as "recvdDt"  From OD1.TE381_MER_FLATTENED_HIER_REL A, P65.GLBL_CASE_STA B, P65.GLOBAL_DISPUTE_REASON C, P65.GLBL_INQ_CASE D where A.SE_NO = B.SE_NO and B.DERIV_CASE_STA_NM IN ('Closed Chargeback','Closed in your favor') and A.SE_NO IN (Select SE_NO from OD1.TE381_MER_FLATTENED_HIER_REL where LVL_6_SE_NO = '_LVL_6_SE_NO') and C.DSPT_RSN_CD= B.CASE_RSN_CD and D.SE_NO = A.SE_NO and C.LANGUAGE_CD ='_LANGUAGE_CD' order by B.CASE_NO asc
ParamsDSL5=_LVL_6_SE_NO:4090130824,_LANGUAGE_CD:EN

<!-- Count for Recent updates -->
SQLQueryDSL6=select decimal(Sum(DSPT_AM),12,1) as "totalDisputeAmt", count(*) as "totalCt" From OD1.TE381_MER_FLATTENED_HIER_REL A, P65.GLBL_CASE_STA B where A.SE_NO = B.SE_NO and B.DERIV_CASE_STA_NM IN('Closed Chargeback','Closed in your favor') and A.SE_NO IN (Select SE_NO from OD1.TE381_MER_FLATTENED_HIER_REL where LVL_6_SE_NO = '_LVL_6_SE_NO')
ParamsDSL6=_LVL_6_SE_NO:4090130824

<!-- Details for recently closed -->
SQLQueryDSL7=select DISTINCT(B.GLBL_CASE_STA_ID) as "disputeId", B.CASE_NO as "disputeNbr", A.SE_NO as "seNbr", C.DISP_CTGY_DS as "disputeCategoryDesc", ADJ_NO as "adjustmentNbr", CASE_UPDT_TYPE_CD as "disputeUpdType", RESP_IN as "responseInd", DERIV_CASE_STA_NM as "disputeStatusCd", INQ_ID as "inqId", B.SE_RESP_BY_DT as "replyByDt", CHRGBK_ID as "chrgBkId", (select CASE count(E.USER_DSPT_FLAG_TYPE_CD) WHEN 1 THEN 'true' WHEN 0 THEN 'false' END from P65.USER_DSPT_FLAG_SLCT E where E.GLBL_CASE_STA_ID = B.GLBL_CASE_STA_ID) as "flagged", D.PG_TOT_NO as "daysPending", CASE_UPDT_ID as "disputeUpdId", varchar_format(B.UPDT_LOAD_TS,'YYYY-MM-DD') as "updDt", CASE_TYPE_NM as "disputeType", B.CHRG_CURR_CD as "chrgBkCurrencyCd", CASE_RSN_CD as "rsnCd", decimal(B.CHRG_AM,12,1) as "chrgAmt", B.DSPT_CURR_CD as "disputeCurrencyCd", decimal(B.DSPT_AM,12,1) as "disputeAmt", SUBSTR(B.CARD_NO, 1, 6) || 'XXXXX' || SUBSTR(B.CARD_NO, 12, 4) as "cardNbr", decimal(B.CHRGBK_AM,12,1) as "chrgBkAmt", B.CHRG_DT as "chrgDt", B.TRANS_ID as "chrgRefNbr", B.PAY_SE_NO as "payeeSENbr", decimal(B.SETTLE_AM,12,1) as "settleAmt", B.SETTLE_CURR_CD as "settleCurrencyCd", B.SETTLE_DT as "settleDt", B.ROC_INV_1_NO as "rocInvNbr", B.SUBM_SE_LOC_ID as "locationId", B.PAYEE_SE_LOC_ID as "payeeLocationId", B.AIR_TKT_NO as "airTktNbr", B.CAR_RENT_AGR_NO as "carRentalAgreementNbr", B.RESP_CD as "merchantRespCd", B.CHRGBK_DT as "chrgBkDt", B.BASIC_CM_NM as "cardMemberNm", B.ORIG_CARD_NO as "originalCardNbr", varchar_format(B.REC_LOAD_TS,'YYYY-MM-DD') as "recvdDt"  From OD1.TE381_MER_FLATTENED_HIER_REL A, P65.GLBL_CASE_STA B, P65.GLOBAL_DISPUTE_REASON C, P65.GLBL_INQ_CASE D where A.SE_NO = B.SE_NO and B.DERIV_CASE_STA_NM IN ('Closed Chargeback','Closed in your favor' , 'Closed Status') and A.SE_NO IN (Select SE_NO from OD1.TE381_MER_FLATTENED_HIER_REL where LVL_6_SE_NO = '_LVL_6_SE_NO') and C.DSPT_RSN_CD= B.CASE_RSN_CD and D.SE_NO = A.SE_NO and C.LANGUAGE_CD ='_LANGUAGE_CD' order by B.CASE_NO asc
ParamsDSL7=_LVL_6_SE_NO:4090130824,_LANGUAGE_CD:EN

<!-- Count for recently closed -->
SQLQueryDSL8=select decimal(Sum(DSPT_AM),12,1) as "totalDisputeAmt", count(*) as "totalCt" From OD1.TE381_MER_FLATTENED_HIER_REL A, P65.GLBL_CASE_STA B where A.SE_NO = B.SE_NO and B.DERIV_CASE_STA_NM IN('Closed Chargeback','Closed in your favor' , 'Closed Status') and A.SE_NO IN (Select SE_NO from OD1.TE381_MER_FLATTENED_HIER_REL where LVL_6_SE_NO = '_LVL_6_SE_NO')
ParamsDSL8=_LVL_6_SE_NO:4090130824

<!-- Details for RESPONSE REQUIRED -->
SQLQueryDSL9=select DISTINCT(B.GLBL_CASE_STA_ID) as "disputeId", B.CASE_NO as "disputeNbr", A.SE_NO as "seNbr", C.DISP_CTGY_DS as "disputeCategoryDesc", ADJ_NO as "adjustmentNbr", CASE_UPDT_TYPE_CD as "disputeUpdType", RESP_IN as "responseInd", DERIV_CASE_STA_NM as "disputeStatusCd", INQ_ID as "inqId", B.SE_RESP_BY_DT as "replyByDt", CHRGBK_ID as "chrgBkId", (select CASE count(E.USER_DSPT_FLAG_TYPE_CD) WHEN 1 THEN 'true' WHEN 0 THEN 'false' END from P65.USER_DSPT_FLAG_SLCT E where E.GLBL_CASE_STA_ID = B.GLBL_CASE_STA_ID) as "flagged", D.PG_TOT_NO as "daysPending", CASE_UPDT_ID as "disputeUpdId", varchar_format(B.UPDT_LOAD_TS,'YYYY-MM-DD') as "updDt", CASE_TYPE_NM as "disputeType", B.CHRG_CURR_CD as "chrgBkCurrencyCd", CASE_RSN_CD as "rsnCd", decimal(B.CHRG_AM,12,1) as "chrgAmt", B.DSPT_CURR_CD as "disputeCurrencyCd", decimal(B.DSPT_AM,12,1) as "disputeAmt", SUBSTR(B.CARD_NO, 1, 6) || 'XXXXX' || SUBSTR(B.CARD_NO, 12, 4) as "cardNbr", decimal(B.CHRGBK_AM,12,1) as "chrgBkAmt", B.CHRG_DT as "chrgDt", B.TRANS_ID as "chrgRefNbr", B.PAY_SE_NO as "payeeSENbr", decimal(B.SETTLE_AM,12,1) as "settleAmt", B.SETTLE_CURR_CD as "settleCurrencyCd", B.SETTLE_DT as "settleDt", B.ROC_INV_1_NO as "rocInvNbr", B.SUBM_SE_LOC_ID as "locationId", B.PAYEE_SE_LOC_ID as "payeeLocationId", B.AIR_TKT_NO as "airTktNbr", B.CAR_RENT_AGR_NO as "carRentalAgreementNbr", B.RESP_CD as "merchantRespCd", B.CHRGBK_DT as "chrgBkDt", B.BASIC_CM_NM as "cardMemberNm", B.ORIG_CARD_NO as "originalCardNbr", varchar_format(B.REC_LOAD_TS,'YYYY-MM-DD') as "recvdDt"  From OD1.TE381_MER_FLATTENED_HIER_REL A, P65.GLBL_CASE_STA B, P65.GLOBAL_DISPUTE_REASON C, P65.GLBL_INQ_CASE D where A.SE_NO = B.SE_NO and B.DERIV_CASE_STA_NM IN ('Urgent Response Required','Response Required') and A.SE_NO IN (Select SE_NO from OD1.TE381_MER_FLATTENED_HIER_REL where LVL_6_SE_NO = '_LVL_6_SE_NO') and C.DSPT_RSN_CD= B.CASE_RSN_CD and D.SE_NO = A.SE_NO and C.LANGUAGE_CD ='_LANGUAGE_CD' order by B.CASE_NO asc
ParamsDSL9=_LVL_6_SE_NO:4090130824,_LANGUAGE_CD:EN

<!-- Count for RESPONSE REQUIRED -->
SQLQueryDSL10=select decimal(Sum(DSPT_AM),12,1) as "totalDisputeAmt", count(*) as "totalCt" From OD1.TE381_MER_FLATTENED_HIER_REL A, P65.GLBL_CASE_STA B where A.SE_NO = B.SE_NO and B.DERIV_CASE_STA_NM IN('Urgent Response Required','Response Required') and A.SE_NO IN (Select SE_NO from OD1.TE381_MER_FLATTENED_HIER_REL where LVL_6_SE_NO = '_LVL_6_SE_NO') 
ParamsDSL10=_LVL_6_SE_NO:4090130824

<!-- Details for In progress -->
SQLQueryDSL11=select DISTINCT(B.GLBL_CASE_STA_ID) as "disputeId", B.CASE_NO as "disputeNbr", A.SE_NO as "seNbr", C.DISP_CTGY_DS as "disputeCategoryDesc", ADJ_NO as "adjustmentNbr", CASE_UPDT_TYPE_CD as "disputeUpdType", RESP_IN as "responseInd", DERIV_CASE_STA_NM as "disputeStatusCd", INQ_ID as "inqId", B.SE_RESP_BY_DT as "replyByDt", CHRGBK_ID as "chrgBkId", (select CASE count(E.USER_DSPT_FLAG_TYPE_CD) WHEN 1 THEN 'true' WHEN 0 THEN 'false' END from P65.USER_DSPT_FLAG_SLCT E where E.GLBL_CASE_STA_ID = B.GLBL_CASE_STA_ID) as "flagged", D.PG_TOT_NO as "daysPending", CASE_UPDT_ID as "disputeUpdId", varchar_format(B.UPDT_LOAD_TS,'YYYY-MM-DD') as "updDt", CASE_TYPE_NM as "disputeType", B.CHRG_CURR_CD as "chrgBkCurrencyCd", CASE_RSN_CD as "rsnCd", decimal(B.CHRG_AM,12,1) as "chrgAmt", B.DSPT_CURR_CD as "disputeCurrencyCd", decimal(B.DSPT_AM,12,1) as "disputeAmt", SUBSTR(B.CARD_NO, 1, 6) || 'XXXXX' || SUBSTR(B.CARD_NO, 12, 4) as "cardNbr", decimal(B.CHRGBK_AM,12,1) as "chrgBkAmt", B.CHRG_DT as "chrgDt", B.TRANS_ID as "chrgRefNbr", B.PAY_SE_NO as "payeeSENbr", decimal(B.SETTLE_AM,12,1) as "settleAmt", B.SETTLE_CURR_CD as "settleCurrencyCd", B.SETTLE_DT as "settleDt", B.ROC_INV_1_NO as "rocInvNbr", B.SUBM_SE_LOC_ID as "locationId", B.PAYEE_SE_LOC_ID as "payeeLocationId", B.AIR_TKT_NO as "airTktNbr", B.CAR_RENT_AGR_NO as "carRentalAgreementNbr", B.RESP_CD as "merchantRespCd", B.CHRGBK_DT as "chrgBkDt", B.BASIC_CM_NM as "cardMemberNm", B.ORIG_CARD_NO as "originalCardNbr", varchar_format(B.REC_LOAD_TS,'YYYY-MM-DD') as "recvdDt" From OD1.TE381_MER_FLATTENED_HIER_REL A, P65.GLBL_CASE_STA B, P65.GLOBAL_DISPUTE_REASON C, P65.GLBL_INQ_CASE D where A.SE_NO = B.SE_NO and B.DERIV_CASE_STA_NM IN ('In Progress', 'Responded Offline') and A.SE_NO IN (Select SE_NO from OD1.TE381_MER_FLATTENED_HIER_REL where LVL_6_SE_NO = '_LVL_6_SE_NO') and C.DSPT_RSN_CD= B.CASE_RSN_CD and D.SE_NO = A.SE_NO and C.LANGUAGE_CD ='_LANGUAGE_CD' order by B.CASE_NO asc
ParamsDSL11=_LVL_6_SE_NO:4090130824,_LANGUAGE_CD:EN

<!-- Count for In progress -->
SQLQueryDSL12=select decimal(Sum(DSPT_AM),12,1) as "totalDisputeAmt", count(*) as "totalCt" From OD1.TE381_MER_FLATTENED_HIER_REL A, P65.GLBL_CASE_STA B where A.SE_NO = B.SE_NO and B.DERIV_CASE_STA_NM IN('In Progress', 'Responded Offline') and A.SE_NO IN (Select SE_NO from OD1.TE381_MER_FLATTENED_HIER_REL where LVL_6_SE_NO = '_LVL_6_SE_NO') 
ParamsDSL12=_LVL_6_SE_NO:4090130824

<!-- Details for closed In Favor -->
SQLQueryDSL13=select DISTINCT(B.GLBL_CASE_STA_ID) as "disputeId", B.CASE_NO as "disputeNbr", A.SE_NO as "seNbr", C.DISP_CTGY_DS as "disputeCategoryDesc", ADJ_NO as "adjustmentNbr", CASE_UPDT_TYPE_CD as "disputeUpdType", RESP_IN as "responseInd", DERIV_CASE_STA_NM as "disputeStatusCd", INQ_ID as "inqId", B.SE_RESP_BY_DT as "replyByDt", CHRGBK_ID as "chrgBkId", (select CASE count(E.USER_DSPT_FLAG_TYPE_CD) WHEN 1 THEN 'true' WHEN 0 THEN 'false' END from P65.USER_DSPT_FLAG_SLCT E where E.GLBL_CASE_STA_ID = B.GLBL_CASE_STA_ID) as "flagged", D.PG_TOT_NO as "daysPending", CASE_UPDT_ID as "disputeUpdId", varchar_format(B.UPDT_LOAD_TS,'YYYY-MM-DD') as "updDt", CASE_TYPE_NM as "disputeType", B.CHRG_CURR_CD as "chrgBkCurrencyCd", CASE_RSN_CD as "rsnCd", decimal(B.CHRG_AM,12,1) as "chrgAmt", B.DSPT_CURR_CD as "disputeCurrencyCd", decimal(B.DSPT_AM,12,1) as "disputeAmt", SUBSTR(B.CARD_NO, 1, 6) || 'XXXXX' || SUBSTR(B.CARD_NO, 12, 4) as "cardNbr", decimal(B.CHRGBK_AM,12,1) as "chrgBkAmt", B.CHRG_DT as "chrgDt", B.TRANS_ID as "chrgRefNbr", B.PAY_SE_NO as "payeeSENbr", decimal(B.SETTLE_AM,12,1) as "settleAmt", B.SETTLE_CURR_CD as "settleCurrencyCd", B.SETTLE_DT as "settleDt", B.ROC_INV_1_NO as "rocInvNbr", B.SUBM_SE_LOC_ID as "locationId", B.PAYEE_SE_LOC_ID as "payeeLocationId", B.AIR_TKT_NO as "airTktNbr", B.CAR_RENT_AGR_NO as "carRentalAgreementNbr", B.RESP_CD as "merchantRespCd", B.CHRGBK_DT as "chrgBkDt", B.BASIC_CM_NM as "cardMemberNm", B.ORIG_CARD_NO as "originalCardNbr", varchar_format(B.REC_LOAD_TS,'YYYY-MM-DD') as "recvdDt"  From OD1.TE381_MER_FLATTENED_HIER_REL A, P65.GLBL_CASE_STA B, P65.GLOBAL_DISPUTE_REASON C, P65.GLBL_INQ_CASE D where A.SE_NO = B.SE_NO and B.DERIV_CASE_STA_NM IN ('Closed in your favor') and A.SE_NO IN (Select SE_NO from OD1.TE381_MER_FLATTENED_HIER_REL where LVL_6_SE_NO = '_LVL_6_SE_NO') and C.DSPT_RSN_CD= B.CASE_RSN_CD and D.SE_NO = A.SE_NO and C.LANGUAGE_CD ='_LANGUAGE_CD' order by B.CASE_NO desc
ParamsDSL13=_LVL_6_SE_NO:4090130824,_LANGUAGE_CD:EN

<!-- Count for closed In Favor -->
SQLQueryDSL14= select decimal(Sum(DSPT_AM),12,1) as "totalDisputeAmt", count(*) as "totalCt" From OD1.TE381_MER_FLATTENED_HIER_REL A, P65.GLBL_CASE_STA B where A.SE_NO = B.SE_NO and B.DERIV_CASE_STA_NM IN('Closed in your favor') and A.SE_NO IN (Select SE_NO from OD1.TE381_MER_FLATTENED_HIER_REL where LVL_6_SE_NO = '_LVL_6_SE_NO')
ParamsDSL14=_LVL_6_SE_NO:4090130824

<!-- Details for Closed Chargebacks -->
SQLQueryDSL15=select DISTINCT(B.GLBL_CASE_STA_ID) as "disputeId", B.CASE_NO as "disputeNbr", A.SE_NO as "seNbr", C.DISP_CTGY_DS as "disputeCategoryDesc", ADJ_NO as "adjustmentNbr", CASE_UPDT_TYPE_CD as "disputeUpdType", RESP_IN as "responseInd", DERIV_CASE_STA_NM as "disputeStatusCd", INQ_ID as "inqId", B.SE_RESP_BY_DT as "replyByDt", CHRGBK_ID as "chrgBkId", (select CASE count(E.USER_DSPT_FLAG_TYPE_CD) WHEN 1 THEN 'true' WHEN 0 THEN 'false' END from P65.USER_DSPT_FLAG_SLCT E where E.GLBL_CASE_STA_ID = B.GLBL_CASE_STA_ID) as "flagged", D.PG_TOT_NO as "daysPending", CASE_UPDT_ID as "disputeUpdId", varchar_format(B.UPDT_LOAD_TS,'YYYY-MM-DD') as "updDt", CASE_TYPE_NM as "disputeType", B.CHRG_CURR_CD as "chrgBkCurrencyCd", CASE_RSN_CD as "rsnCd", decimal(B.CHRG_AM,12,1) as "chrgAmt", B.DSPT_CURR_CD as "disputeCurrencyCd", decimal(B.DSPT_AM,12,1) as "disputeAmt", SUBSTR(B.CARD_NO, 1, 6) || 'XXXXX' || SUBSTR(B.CARD_NO, 12, 4) as "cardNbr", decimal(B.CHRGBK_AM,12,1) as "chrgBkAmt", B.CHRG_DT as "chrgDt", B.TRANS_ID as "chrgRefNbr", B.PAY_SE_NO as "payeeSENbr", decimal(B.SETTLE_AM,12,1) as "settleAmt", B.SETTLE_CURR_CD as "settleCurrencyCd", B.SETTLE_DT as "settleDt", B.ROC_INV_1_NO as "rocInvNbr", B.SUBM_SE_LOC_ID as "locationId", B.PAYEE_SE_LOC_ID as "payeeLocationId", B.AIR_TKT_NO as "airTktNbr", B.CAR_RENT_AGR_NO as "carRentalAgreementNbr", B.RESP_CD as "merchantRespCd", B.CHRGBK_DT as "chrgBkDt", B.BASIC_CM_NM as "cardMemberNm", B.ORIG_CARD_NO as "originalCardNbr", varchar_format(B.REC_LOAD_TS,'YYYY-MM-DD') as "recvdDt"  From OD1.TE381_MER_FLATTENED_HIER_REL A, P65.GLBL_CASE_STA B, P65.GLOBAL_DISPUTE_REASON C, P65.GLBL_INQ_CASE D where A.SE_NO = B.SE_NO and B.DERIV_CASE_STA_NM IN ('Closed Chargeback', 'Closed Status') and A.SE_NO IN (Select SE_NO from OD1.TE381_MER_FLATTENED_HIER_REL where LVL_6_SE_NO = '_LVL_6_SE_NO') and C.DSPT_RSN_CD= B.CASE_RSN_CD and D.SE_NO = A.SE_NO and C.LANGUAGE_CD ='_LANGUAGE_CD' order by B.CASE_NO asc
ParamsDSL15=_LVL_6_SE_NO:4090130824,_LANGUAGE_CD:EN

<!-- Count for Closed Chargebacks -->
SQLQueryDSL16=select decimal(Sum(DSPT_AM),12,1) as "totalDisputeAmt", count(*) as "totalCt" From OD1.TE381_MER_FLATTENED_HIER_REL A, P65.GLBL_CASE_STA B where A.SE_NO = B.SE_NO and B.DERIV_CASE_STA_NM IN('Closed Chargeback', 'Closed Status') and A.SE_NO IN (Select SE_NO from OD1.TE381_MER_FLATTENED_HIER_REL where LVL_6_SE_NO = '_LVL_6_SE_NO') 
ParamsDSL16=_LVL_6_SE_NO:4090130824

<!-- Query for Specific start date  -->
SQLQueryDSL17= select decimal(Sum(DSPT_AM),12,1) as "totalDisputeAmt", count(*) as "totalCt" From OD1.TE381_MER_FLATTENED_HIER_REL A, P65.GLBL_CASE_STA B where A.SE_NO = B.SE_NO and B.DERIV_CASE_STA_NM IN('Urgent Response Required','Response Required','In Progress') and A.SE_NO IN(Select SE_NO from OD1.TE381_MER_FLATTENED_HIER_REL where LVL_6_SE_NO = '_LVL_6_SE_NO') and Date(REC_LOAD_TS) BETWEEN '_startDt' AND '_endDt' 
ParamsDSL17=_LVL_6_SE_NO:4090130824,_startDt:2012-06-02,_endDt:2014-06-02





#Queries for Dispute Details
SQLQueryDD1=Select DISTINCT(A.CASE_NO) as "disputeNbr", B.LOC_NO as "location",A.GLBL_CASE_STA_ID as "disputeId" ,C.DISP_CTGY_DS as "disputeCategoryDesc" ,A.SE_NO as "seNbr" ,A.ADJ_NO as "adjustmentNbr" ,A.CASE_UPDT_TYPE_CD as "disputeUpdType" ,A.RESP_IN as "responseInd" ,A.DERIV_CASE_STA_NM as "disputeStatusCd" ,A.INQ_ID as "inqId" ,A.SE_RESP_BY_DT as "replyByDt" ,A.CHRGBK_ID as "chrgBkId" ,decimal(A.CHRGBK_AM,12,1) as "chrgBkAmt" ,A.CHRGBK_CURR_CD as "chrgBkCurrencyCd" ,A.CASE_UPDT_ID as "disputeUpdId" ,A.CASE_RSN_CD as "rsnCd" ,A.DSPT_CURR_CD as "disputeCurrencyCd" ,decimal(A.DSPT_AM,12,1) as "disputeAmt" ,A.PAY_SE_NO as "payeeSENbr" ,A.SETTLE_DT as "settleDt" ,A.ROC_INV_1_NO as "rocInvNbr" ,A.SUBM_SE_LOC_ID as "submissionLocationId" ,A.ORIG_CARD_NO as "originalCardNbr" ,B.CASE_CTGY_CD as "disputeCategoryCd" ,B.CASE_CTGY_CD as "disputeType" ,decimal(B.CHRG_AM,12,1) as "chrgAmt" ,B.CHRG_CURR_CD as "chrgAmtCurrencyCd" ,B.CHRG_DT as "chrgDt" ,B.BASIC_CM_NM as "cardMemberNm" ,B.INDUS_REF_NO as "chrgRefNbr" ,SUBSTR(B.CARD_NO, 1, 6) || 'XXXXX' || SUBSTR(B.CARD_NO, 12, 4)as "cardNbr" ,decimal(B.SETTLE_AM,12,1) as "settleAmt" ,B.SETTLE_CURR_CD as "settleCurrencyCd" ,B.LOC_NO as "locationId" ,B.AIR_TKT_NO as "airTktNbr" ,B.SOC_DT as "transactionDt" ,B.TRANS_ID as "indusRefNbr" from P65.GLBL_CASE_STA A ,P65.GLBL_INQ_CASE B ,P65.GLOBAL_DISPUTE_REASON C where B.CASE_NO =A.CASE_NO AND C.DSPT_RSN_CD=A.CASE_RSN_CD AND A.GLBL_CASE_STA_ID = '_GLBL_CASE_STA_ID' AND C.LANGUAGE_CD ='_LANGUAGE_CD'
ParamsDD1=_GLBL_CASE_STA_ID:80866,_LANGUAGE_CD:EN


#Queries for Flag and Un-Flag API
SQLQueryFUF1= select CASE count(*) WHEN 1 THEN 'true' WHEN 0 THEN 'false' END AS "flag1" from P65.USER_DSPT_FLAG_SLCT where GLBL_CASE_STA_ID in ('_GLBL_CASE_STA_ID')
ParamsFUF1=_GLBL_CASE_STA_ID:80866

SQLQueryFUF2= select CASE count(*) WHEN 1 THEN 'true' WHEN 0 THEN 'false' END AS "flag2" from P65.USER_DSPT_FLAG_SLCT where GLBL_CASE_STA_ID in ('_GLBL_CASE_STA_ID')
ParamsFUF2=_GLBL_CASE_STA_ID:219189

SQLQueryFUF3= select CASE count(*) WHEN 1 THEN 'true' WHEN 0 THEN 'false' END AS "flag3" from P65.USER_DSPT_FLAG_SLCT where GLBL_CASE_STA_ID in ('_GLBL_CASE_STA_ID')
ParamsFUF3=_GLBL_CASE_STA_ID:219190


# Queries for Lock dispute API
SQLQueryLD1 = select CASE WHEN (count(*) = 1) THEN 'true' WHEN (count(*) = 0) THEN 'false' END as "locksAcquired" from p65.user_lock_out where REC_ID = '_DispId1'
ParamsLD1 =_DispId1:219193

SQLQueryLD2 = select CASE WHEN (count(*) = 2) THEN 'true' WHEN (count(*) = 0) THEN 'false' ELSE 'false' END as "locksAcquired" from p65.user_lock_out where REC_ID in ( '_DispId1','_DispId2')
ParamsLD2 =_DispId1:219193,_DispId2:219195

SQLQueryLD3 = select CASE WHEN (count(*) = 3) THEN 'true' WHEN (count(*) = 0) THEN 'false' ELSE 'false' END as "locksAcquired" from p65.user_lock_out where REC_ID in ( '_DispId1','_DispId2','_DispId3')
ParamsLD3 =_DispId1:219218,_DispId2:219229,_DispId3:80872

# Queries for Locations API
SQLQueryLOC1sc1=select Distinct(A.SE_NO) as "seNbr", LOC_NO as "location", CASE WHEN(1=1) THEN 'true' END as "cap" from P65.USER_SE_HIER_PROD A, OD1.TE381_MER_FLATTENED_HIER_REL B where A.GLBL_USER_ID ='_GLBL_USER_ID' and A.PROD_ID='_PROD_ID' and A.SE_NO=B.LVL_6_SE_NO
ParamsLOC1sc1=_GLBL_USER_ID:06eb3110fdbfde438f0c4b93166d94a8,_PROD_ID:DO2

SQLQueryLOC1sc2=Select count(*) as "totalSeNbrCt" from P65.USER_SE_HIER_PROD where GLBL_USER_ID ='_GLBL_USER_ID' and PROD_ID='_PROD_ID'
ParamsLOC1sc2=_GLBL_USER_ID:06eb3110fdbfde438f0c4b93166d94a8,_PROD_ID:DO2

SQLQueryLOC1sc3=select count(SE_NO) as "immediateChildCt" from OD1.TE381_MER_FLATTENED_HIER_REL where LVL_6_SE_NO in( select Distinct(A.SE_NO) as "seNbr" from P65.USER_SE_HIER_PROD A, OD1.TE381_MER_FLATTENED_HIER_REL B where A.GLBL_USER_ID ='_GLBL_USER_ID' and A.PROD_ID='_PROD_ID' and A.SE_NO=B.LVL_6_SE_NO) and SE_NO <> LVL_6_SE_NO
ParamsLOC1sc3=_GLBL_USER_ID:06eb3110fdbfde438f0c4b93166d94a8,_PROD_ID:DO2


SQLQueryLOC2sc1=select SE_NO as "seNbr", LOC_NO as "locationId", CASE WHEN(1=1) THEN 'false' END as "cap" from OD1.TE381_MER_FLATTENED_HIER_REL where LVL_6_SE_NO ='_LVL_6_SE_NO' and SE_NO <> LVL_6_SE_NO;
ParamsLOC2sc1=_LVL_6_SE_NO:5046436895

SQLQueryLOC2sc2=select Count(SE_NO) as "immediateChildCt" from OD1.TE381_MER_FLATTENED_HIER_REL where SE_NO <> LVL_6_SE_NO and LVL_6_SE_NO='_LVL_6_SE_NO';
ParamsLOC2sc2=_LVL_6_SE_NO:5046436895

#SQLQueryLOC2sc3=select LOC_NO as "locationIdCap" from OD1.TE381_MER_FLATTENED_HIER_REL where LVL_6_SE_NO ='_LVL_6_SE_NO' and SE_NO = LVL_6_SE_NO;
#ParamLOC2sc3=_LVL_6_SE_NO:5046436895







#Queries for DisputeResponse

<!-- Get the ResponseDt -->
SQLQueryDR1=Select SE_ONLN_RESP_TS as "responseDt" from P65.GLBL_INQ_CASE where CASE_NO IN (select CASE_NO from P65.GLBL_CASE_STA where GLBL_CASE_STA_ID ='_GLBL_CASE_STA_ID')
ParamsDR1=_GLBL_CASE_STA_ID:219218
ParamsDR1.3=_GLBL_CASE_STA_ID:80872

<!-- For chargeback -->
SQLQueryDR1.2=Select SE_ONLN_RESP_TS as "responseDt" from P65.GLBL_CHRGBK_CASE where CASE_NO IN (select CASE_NO from P65.GLBL_CASE_STA where GLBL_CASE_STA_ID ='_GLBL_CASE_STA_ID')
ParamsDR1.2=_GLBL_CASE_STA_ID:219229

<!-- Update resp in as Y in P65.GLBL_CASE_STA table -->
SQLQueryDR2=Update P65.GLBL_CASE_STA set RESP_IN='_RESP_IN' where GLBL_CASE_STA_ID ='_GLBL_CASE_STA_ID'
ParamsDR2=_RESP_IN:Y,_GLBL_CASE_STA_ID:219218
ParamsDR2.2=_RESP_IN:Y,_GLBL_CASE_STA_ID:219229
ParamsDR2.3=_RESP_IN:Y,_GLBL_CASE_STA_ID:80872

<!-- Update CASE_STA_CD as 'New' in P65.GLBL_CASE_STA table -->
SQLQueryDR3=Update P65.GLBL_CASE_STA set CASE_STA_CD='_CASE_STA_CD' where GLBL_CASE_STA_ID ='_GLBL_CASE_STA_ID'
ParamsDR3=_GLBL_CASE_STA_ID:219218,_CASE_STA_CD:New
ParamsDR3.2=_GLBL_CASE_STA_ID:219229,_CASE_STA_CD:New
ParamsDR3.3=_GLBL_CASE_STA_ID:80872,_CASE_STA_CD:New

<!-- Update DSPT_STA_CD as 'New' in P65.GLBL_INQ_CASE table -->
SQLQueryDR4=Update P65.GLBL_INQ_CASE set DSPT_STA_CD='_DSPT_STA_CD' where CASE_NO IN (select CASE_NO from P65.GLBL_CASE_STA where GLBL_CASE_STA_ID ='_GLBL_CASE_STA_ID')
ParamsDR4=_GLBL_CASE_STA_ID:219218,_DSPT_STA_CD:New
ParamsDR4.2=_GLBL_CASE_STA_ID:219229,_DSPT_STA_CD:New
ParamsDR4.3=_GLBL_CASE_STA_ID:80872,_DSPT_STA_CD:New

<!-- Delete from lock -->
SQLQueryDR5=Delete from p65.user_lock_out where rec_id='_GLBL_CASE_STA_ID'
ParamsDR5=_GLBL_CASE_STA_ID:219218
ParamsDR5.2=_GLBL_CASE_STA_ID:219229
ParamsDR5.3=_GLBL_CASE_STA_ID:80872


#Queries for Advanced Search 
#DisputeNbr chargeback
SQLQueryADVS1=select DISTINCT(B.GLBL_CASE_STA_ID) as "disputeId", B.CASE_NO as "disputeNbr", A.SE_NO as "seNbr", C.DISP_CTGY_DS as "disputeCategoryDesc", ADJ_NO as "adjustmentNbr", CASE_UPDT_TYPE_CD as "disputeUpdType", RESP_IN as "responseInd", DERIV_CASE_STA_NM as "disputeStatusCd", INQ_ID as "inqId", B.SE_RESP_BY_DT as "replyByDt", CHRGBK_ID as "chrgBkId",(select CASE count(E.USER_DSPT_FLAG_TYPE_CD) WHEN 1 THEN 'true' WHEN 0 THEN 'false' END from P65.USER_DSPT_FLAG_SLCT E where E.GLBL_CASE_STA_ID = B.GLBL_CASE_STA_ID) as "flagged", D.PG_TOT_NO as "daysPending", CASE_UPDT_ID as "disputeUpdId", varchar_format(B.UPDT_LOAD_TS,'YYYY-MM-DD') as "updDt", CASE_TYPE_NM as "disputeType", B.CHRG_CURR_CD as "chrgBkCurrencyCd", CASE_RSN_CD as "rsnCd", decimal(B.CHRG_AM,12,1) as "chrgAmt", B.DSPT_CURR_CD as "disputeCurrencyCd", decimal(B.DSPT_AM,12,1) as "disputeAmt", SUBSTR(B.CARD_NO, 1, 6) || 'XXXXX' || SUBSTR(B.CARD_NO, 12, 4) as "cardNbr", decimal(B.CHRGBK_AM,12,1) as "chrgBkAmt", B.CHRG_DT as "chrgDt", B.TRANS_ID as "chrgRefNbr", B.PAY_SE_NO as "payeeSENbr", decimal(B.SETTLE_AM,12,1) as "settleAmt", B.SETTLE_CURR_CD as "settleCurrencyCd", B.SETTLE_DT as "settleDt", B.ROC_INV_1_NO as "rocInvNbr", B.SUBM_SE_LOC_ID as "locationId", B.PAYEE_SE_LOC_ID as "payeeLocationId", B.AIR_TKT_NO as "airTktNbr", B.CAR_RENT_AGR_NO as "carRentalAgreementNbr", B.RESP_CD as "merchantRespCd", B.CHRGBK_DT as "chrgBkDt", B.BASIC_CM_NM as "cardMemberNm", B.ORIG_CARD_NO as "originalCardNbr", varchar_format(B.REC_LOAD_TS,'YYYY-MM-DD') as "recvdDt", C.DSPT_RSN_DS as "rsnCdDesc" From OD1.TE381_MER_FLATTENED_HIER_REL A, P65.GLBL_CASE_STA B, P65.GLOBAL_DISPUTE_REASON C, P65.GLBL_INQ_CASE D where A.SE_NO = B.SE_NO and B.CASE_STAGE_CD = 'C' and B.DERIV_CASE_STA_NM IN ('Responded Offline','In Progress','Urgent Response Required','Response Required') and B.CASE_NO = '_CASE_NO' and C.DSPT_RSN_CD= B.CASE_RSN_CD and D.SE_NO = A.SE_NO and C.LANGUAGE_CD ='_LANGUAGE_CD' order by B.CASE_NO asc;
ParamsADVS1=_CASE_NO:MI10108,_LANGUAGE_CD:EN

#CardNbr chargeback
SQLQueryADVS2=select DISTINCT(B.GLBL_CASE_STA_ID) as "disputeId", B.CASE_NO as "disputeNbr", A.SE_NO as "seNbr", C.DISP_CTGY_DS as "disputeCategoryDesc", ADJ_NO as "adjustmentNbr", CASE_UPDT_TYPE_CD as "disputeUpdType", RESP_IN as "responseInd", DERIV_CASE_STA_NM as "disputeStatusCd", INQ_ID as "inqId", B.SE_RESP_BY_DT as "replyByDt", CHRGBK_ID as "chrgBkId",(select CASE count(E.USER_DSPT_FLAG_TYPE_CD) WHEN 1 THEN 'true' WHEN 0 THEN 'false' END from P65.USER_DSPT_FLAG_SLCT E where E.GLBL_CASE_STA_ID = B.GLBL_CASE_STA_ID) as "flagged", D.PG_TOT_NO as "daysPending", CASE_UPDT_ID as "disputeUpdId", varchar_format(B.UPDT_LOAD_TS,'YYYY-MM-DD') as "updDt", CASE_TYPE_NM as "disputeType", B.CHRG_CURR_CD as "chrgBkCurrencyCd", CASE_RSN_CD as "rsnCd", decimal(B.CHRG_AM,12,1) as "chrgAmt", B.DSPT_CURR_CD as "disputeCurrencyCd", decimal(B.DSPT_AM,12,1) as "disputeAmt", SUBSTR(B.CARD_NO, 1, 6) || 'XXXXX' || SUBSTR(B.CARD_NO, 12, 4) as "cardNbr", decimal(B.CHRGBK_AM,12,1) as "chrgBkAmt", B.CHRG_DT as "chrgDt", B.TRANS_ID as "chrgRefNbr", B.PAY_SE_NO as "payeeSENbr", decimal(B.SETTLE_AM,12,1) as "settleAmt", B.SETTLE_CURR_CD as "settleCurrencyCd", B.SETTLE_DT as "settleDt", B.ROC_INV_1_NO as "rocInvNbr", B.SUBM_SE_LOC_ID as "locationId", B.PAYEE_SE_LOC_ID as "payeeLocationId", B.AIR_TKT_NO as "airTktNbr", B.CAR_RENT_AGR_NO as "carRentalAgreementNbr", B.RESP_CD as "merchantRespCd", B.CHRGBK_DT as "chrgBkDt", B.BASIC_CM_NM as "cardMemberNm", B.ORIG_CARD_NO as "originalCardNbr", varchar_format(B.REC_LOAD_TS,'YYYY-MM-DD') as "recvdDt", C.DSPT_RSN_DS as "rsnCdDesc" From OD1.TE381_MER_FLATTENED_HIER_REL A, P65.GLBL_CASE_STA B, P65.GLOBAL_DISPUTE_REASON C, P65.GLBL_INQ_CASE D where A.SE_NO = B.SE_NO and B.CASE_STAGE_CD = 'C' and B.DERIV_CASE_STA_NM IN ('Responded Offline','In Progress','Urgent Response Required','Response Required') and B.CARD_NO = '_CARD_NUMBER' and C.DSPT_RSN_CD= B.CASE_RSN_CD and D.SE_NO = A.SE_NO and C.LANGUAGE_CD ='_LANGUAGE_CD' order by B.CASE_NO asc;
ParamsADVS2=_CARD_NUMBER:87545969901234512,_LANGUAGE_CD:EN

#ChrgAmt chargeback
SQLQueryADVS3=select DISTINCT(B.GLBL_CASE_STA_ID) as "disputeId", B.CASE_NO as "disputeNbr", A.SE_NO as "seNbr", C.DISP_CTGY_DS as "disputeCategoryDesc", ADJ_NO as "adjustmentNbr", CASE_UPDT_TYPE_CD as "disputeUpdType", RESP_IN as "responseInd", DERIV_CASE_STA_NM as "disputeStatusCd", INQ_ID as "inqId", B.SE_RESP_BY_DT as "replyByDt", CHRGBK_ID as "chrgBkId",(select CASE count(E.USER_DSPT_FLAG_TYPE_CD) WHEN 1 THEN 'true' WHEN 0 THEN 'false' END from P65.USER_DSPT_FLAG_SLCT E where E.GLBL_CASE_STA_ID = B.GLBL_CASE_STA_ID) as "flagged", D.PG_TOT_NO as "daysPending", CASE_UPDT_ID as "disputeUpdId", varchar_format(B.UPDT_LOAD_TS,'YYYY-MM-DD') as "updDt", CASE_TYPE_NM as "disputeType", B.CHRG_CURR_CD as "chrgBkCurrencyCd", CASE_RSN_CD as "rsnCd", decimal(B.CHRG_AM,12,1) as "chrgAmt", B.DSPT_CURR_CD as "disputeCurrencyCd", decimal(B.DSPT_AM,12,1) as "disputeAmt", SUBSTR(B.CARD_NO, 1, 6) || 'XXXXX' || SUBSTR(B.CARD_NO, 12, 4) as "cardNbr", decimal(B.CHRGBK_AM,12,1) as "chrgBkAmt", B.CHRG_DT as "chrgDt", B.TRANS_ID as "chrgRefNbr", B.PAY_SE_NO as "payeeSENbr", decimal(B.SETTLE_AM,12,1) as "settleAmt", B.SETTLE_CURR_CD as "settleCurrencyCd", B.SETTLE_DT as "settleDt", B.ROC_INV_1_NO as "rocInvNbr", B.SUBM_SE_LOC_ID as "locationId", B.PAYEE_SE_LOC_ID as "payeeLocationId", B.AIR_TKT_NO as "airTktNbr", B.CAR_RENT_AGR_NO as "carRentalAgreementNbr", B.RESP_CD as "merchantRespCd", B.CHRGBK_DT as "chrgBkDt", B.BASIC_CM_NM as "cardMemberNm", B.ORIG_CARD_NO as "originalCardNbr", varchar_format(B.REC_LOAD_TS,'YYYY-MM-DD') as "recvdDt", C.DSPT_RSN_DS as "rsnCdDesc" From OD1.TE381_MER_FLATTENED_HIER_REL A, P65.GLBL_CASE_STA B, P65.GLOBAL_DISPUTE_REASON C, P65.GLBL_INQ_CASE D where A.SE_NO = B.SE_NO and B.CASE_STAGE_CD = 'C' and B.DERIV_CASE_STA_NM IN ('Responded Offline','In Progress','Urgent Response Required','Response Required') and B.CHRG_AM BETWEEN _CHRG_AM_LOWER and _CHRG_AM_UPPER and Date(B.REC_LOAD_TS) BETWEEN '_startDt' AND '_endDt' and C.DSPT_RSN_CD= B.CASE_RSN_CD and D.SE_NO = A.SE_NO and C.LANGUAGE_CD ='_LANGUAGE_CD' order by B.CASE_NO asc;
ParamsADVS3=_CHRG_AM_LOWER:90,_CHRG_AM_UPPER:1000,_startDt:2013-06-21,_endDt:2014-06-10,_LANGUAGE_CD:EN

SQLQueryADVS4=select DISTINCT(B.GLBL_CASE_STA_ID) as "disputeId", B.CASE_NO as "disputeNbr", A.SE_NO as "seNbr", C.DISP_CTGY_DS as "disputeCategoryDesc", ADJ_NO as "adjustmentNbr", CASE_UPDT_TYPE_CD as "disputeUpdType", RESP_IN as "responseInd", DERIV_CASE_STA_NM as "disputeStatusCd", INQ_ID as "inqId", B.SE_RESP_BY_DT as "replyByDt", CHRGBK_ID as "chrgBkId",(select CASE count(E.USER_DSPT_FLAG_TYPE_CD) WHEN 1 THEN 'true' WHEN 0 THEN 'false' END from P65.USER_DSPT_FLAG_SLCT E where E.GLBL_CASE_STA_ID = B.GLBL_CASE_STA_ID) as "flagged", D.PG_TOT_NO as "daysPending", CASE_UPDT_ID as "disputeUpdId", varchar_format(B.UPDT_LOAD_TS,'YYYY-MM-DD') as "updDt", CASE_TYPE_NM as "disputeType", B.CHRG_CURR_CD as "chrgBkCurrencyCd", CASE_RSN_CD as "rsnCd", decimal(B.CHRG_AM,12,1) as "chrgAmt", B.DSPT_CURR_CD as "disputeCurrencyCd", decimal(B.DSPT_AM,12,1) as "disputeAmt", SUBSTR(B.CARD_NO, 1, 6) || 'XXXXX' || SUBSTR(B.CARD_NO, 12, 4) as "cardNbr", decimal(B.CHRGBK_AM,12,1) as "chrgBkAmt", B.CHRG_DT as "chrgDt", B.TRANS_ID as "chrgRefNbr", B.PAY_SE_NO as "payeeSENbr", decimal(B.SETTLE_AM,12,1) as "settleAmt", B.SETTLE_CURR_CD as "settleCurrencyCd", B.SETTLE_DT as "settleDt", B.ROC_INV_1_NO as "rocInvNbr", B.SUBM_SE_LOC_ID as "locationId", B.PAYEE_SE_LOC_ID as "payeeLocationId", B.AIR_TKT_NO as "airTktNbr", B.CAR_RENT_AGR_NO as "carRentalAgreementNbr", B.RESP_CD as "merchantRespCd", B.CHRGBK_DT as "chrgBkDt", B.BASIC_CM_NM as "cardMemberNm", B.ORIG_CARD_NO as "originalCardNbr", varchar_format(B.REC_LOAD_TS,'YYYY-MM-DD') as "recvdDt", C.DSPT_RSN_DS as "rsnCdDesc" From OD1.TE381_MER_FLATTENED_HIER_REL A, P65.GLBL_CASE_STA B, P65.GLOBAL_DISPUTE_REASON C, P65.GLBL_INQ_CASE D where A.SE_NO = B.SE_NO and B.CASE_STAGE_CD = 'C' and B.DERIV_CASE_STA_NM IN ('Responded Offline','In Progress','Urgent Response Required','Response Required') and B.DSPT_AM BETWEEN _DSPT_AM_LOWER and _DSPT_AM_UPPER and Date(B.REC_LOAD_TS) BETWEEN '_startDt' AND '_endDt' and C.DSPT_RSN_CD= B.CASE_RSN_CD and D.SE_NO = A.SE_NO and C.LANGUAGE_CD ='_LANGUAGE_CD' order by B.CASE_NO asc;
ParamsADVS4=_DSPT_AM_LOWER:90,_DSPT_AM_UPPER:100,_startDt:2013-06-21,_endDt:2014-06-10,_LANGUAGE_CD:EN

#Queries for DisputeHistory
SQLQueryDH1=select A.GLBL_INQ_CASE_ID as "q1.inqId",varchar_format(A.SE_ONLN_RESP_TS,'YYYY-MM-DD') as "q1.changeDt", A.SE_RESP_BY_DT as "q1.replyByDt" from P65.GLBL_INQ_CASE A where GLBL_INQ_CASE_ID  in(select INQ_ID from  P65.GLBL_CASE_STA where  GLBL_CASE_STA_ID='_GLBL_CASE_STA_ID')
ParamsDH1=_GLBL_CASE_STA_ID:12123
SQLQueryDH2=select varchar_format(SPR_STA_EFF_TS,'YYYY-MM-DD') as "q2.changeDt" from P65.IMG_SPRT_STA_HIST where IMG_TRK_NO In('_IMG_TRK_NO') and SPRT_STA_CD = 'R'
ParamsDH2=_IMG_TRK_NO:00050080925D01
SQLQueryDH3=select IMG_FILE_ID as "q3.imgFileId" from P65.IMG_FILE_TRK where IMG_TRK_NO= '_IMG_TRK_NO'
ParamsDH3=_IMG_TRK_NO:00050080925D01
SQLQueryDH4=select IMG_FILE_NM as "q4.imgFileNm" from P65.IMG_FILE_NEW where IMG_FILE_ID in (select IMG_FILE_ID from P65.IMG_FILE_TRK where IMG_TRK_NO= '_IMG_TRK_NO')
ParamsDH4=_IMG_TRK_NO:00050080925D01
SQLQueryDH5=select GLBL_CHRGBK_CASE_ID as "q5.chrgBkId",SE_RESP_BY_DT as "q5.replyByDt",CHRGBK_DT as "q5.chrgBkDt" from P65.GLBL_CHRGBK_CASE where GLBL_CHRGBK_CASE_ID IN (select CHRGBK_ID from  P65.GLBL_CASE_STA where  GLBL_CASE_STA_ID='_GLBL_CASE_STA_ID')
ParamsDH5=_GLBL_CASE_STA_ID:12123
SQLQueryDH6=select varchar_format(REC_LOAD_TS,'YYYY-MM-DD') as "q6.changeDt", GLBL_CASE_UPDT_ID as "q6.disputeUpdId", decimal(CHRGBK_AM,12,1) as "q6.adjAmt",CASE_UPDT_DT as "q6.adjDt" from P65.GLBL_CASE_UPDT where GLBL_CASE_UPDT_ID IN (select CASE_UPDT_ID from  P65.GLBL_CASE_STA where  GLBL_CASE_STA_ID='_GLBL_CASE_STA_ID');
ParamsDH6=_GLBL_CASE_STA_ID:12123


#File Upload
#Get Tracking No.
SQLQueryFUPLD1=Select IMG_TRK_NO as "q1.trackingNo" from P65.IMG_SPRT_STA_HIST where IMG_TRK_NO IN(select IMG_TRK_NO from P65.DSPT_SPRT_MEMO where DSPT_ID IN (Select GLBL_INQ_CASE_ID from P65.GLBL_INQ_CASE where CASE_NO IN (select CASE_NO from P65.GLBL_CASE_STA where GLBL_CASE_STA_ID ='_GLBL_CASE_STA_ID'))) order by SPR_STA_EFF_TS desc fetch first row only 
ParamsFUPLD1=_GLBL_CASE_STA_ID:219223

#Get File ID
SQLQueryFUPLD2=select IMG_FILE_ID as "q1.fileId" from P65.IMG_FILE_TRK where IMG_TRK_NO in( Select IMG_TRK_NO from P65.IMG_SPRT_STA_HIST where IMG_TRK_NO IN (select IMG_TRK_NO from P65.DSPT_SPRT_MEMO where DSPT_ID IN (Select GLBL_INQ_CASE_ID from P65.GLBL_INQ_CASE where CASE_NO IN (select CASE_NO from P65.GLBL_CASE_STA where GLBL_CASE_STA_ID ='_GLBL_CASE_STA_ID'))) order by SPR_STA_EFF_TS desc fetch first row only)
ParamsFUPLD2=_GLBL_CASE_STA_ID:219223

#Check delete status
SQLQueryFileDLT =Select case when (IMG_STA_CD = 'E') then 'true' else 'false' end as "isDeleted" from P65.IMG_FILE_TRK where IMG_TRK_NO ='_IMG_TRK_NO'
ParamsFileDLT =_IMG_TRK_NO:TempProps[_trackingNumbers]

#Check Submit status
SQLQueryFileSBMT =Select case when (IMG_STA_CD = 'A') then 'true' else 'false' end as "isSubmitted" from P65.IMG_FILE_TRK where IMG_TRK_NO ='_IMG_TRK_NO'
ParamsFileSBMT =_IMG_TRK_NO:TempProps[_trackingNumbers]

======================================================================================================


com.amex.config.request
payments_Request.properties

# Payment Summary
PaymentSumHappyPath={ "startDt": "_startDt","endDt": "_endDt","seNbrList": ["_seNbrList1"],"clientId": "_clientId","messageId": "_messageId" }

# Payment Settlement
PaymentSettleHappyPath={ "lowerBound": "_lowerBound","upperBound": "_upperBound","sortColumn": "_sortColumn","startDt": "_startDt","endDt": "_endDt","seNbrList": ["_seNbrList1"] }
PendingSettlementPath={ "startDt": "_startDt","endDt": "_endDt","seNbrList": ["_seNbrList1"],"sortColumn": "_sortColumn","lowerBound": "_lowerBound","upperBound": "_upperBound" }

#Payments Settle Currency
SettleCurrencyReq={"guid": "_guid","seNbrList": ["_seNbr1"],"busCtrCd": "_busCtrCd"}
##More than 20 SE_No's
SettleCurrencyReq1={"guid":"","seNbrList":["_se1","_se2","_se3","_se4","_se5","_se6","_se7","_se8","_se9","_se_a","_se_b","_se_c","_se_d","_se_e","_se_f","_se_g","_se_h","_se_i","_se_j","_se_k","_se_l"],"busCtrCd":""}
##Empty seNbrlist
SettleCurrencyReq2={"guid":"","seNbrList":[],"busCtrCd":"_busCtrCd"}
##20 SE_Nbr's
SettleCurrencyReq3={"guid":"","seNbrList":["_se1","_se2","_se3","_se4","_se5","_se6","_se7","_se8","_se9","_se_a","_se_b","_se_c","_se_d","_se_e","_se_f","_se_g","_se_h","_se_i","_se_j","_se_k"],"busCtrCd":""}
##Only GUID
SettleCurrencyReq4={"guid": "_guid","seNbrList": [],"busCtrCd": "_busCtrCd"}

#paymentSubmissions

PaymentSubmissions = {"startDt":"_startDt","endDt":"_endDt","seNbrList":["_seNbrList"],"sort":[{"columnName":"_columnName","sortOrder":"_sortOrder"}],"lowerBound":_lowerBound,"upperBound":_upperBound}
PendingSettlements = {"startDt":"_startDt","endDt":"_endDt","seNbrList":["_seNbrList"]}

#Transaction List
TransListReq1 = {"settlementId":"_settlementId","settlementNbr":"_settlementNbr","settlementDt":"_settlementDt","payeeSeNbr":"_payeeSeNbr","socNbr":"_socNbr","gmdlSocId":"_gmdlSocId","sort":[{"columnName":"_columnName","sortOrder":"_sortOrder"}],"lowerBound":"_lowerBound","upperBound":"_upperBound"}
TransListReq2 = {"settlementId":"_settlementId","settlementNbr":"_settlementNbr","settlementDt":"_settlementDt","payeeSeNbr":"_payeeSeNbr","socNbr":"_socNbr","gmdlSocId":"_gmdlSocId","sort":[{"columnName":"_columnName1","sortOrder":"_sortOrder1"},{"columnName":"_columnName2","sortOrder":"_sortOrder2"}],"lowerBound":"_lowerBound","upperBound":"_upperBound"}

#Adjustment List
#singl
AdjustmentListReq1 = {"lowerBound":_lowerBound,"upperBound":_upperBound,"sort":[{"columnName":"_columnName1","sortOrder":"_sortOrder1"}],"startDt":"_startDt","endDt":"_endDt","seNbrList":["_seNbrList"]}

#multiple se
AdjustmentListReq2 = {"lowerBound":_lowerBound,"upperBound":_upperBound,"sort":[{"columnName":"_columnName1","sortOrder":"_sortOrder1"}],"startDt":"_startDt","endDt":"_endDt","seNbrList":["_seNbrList1","_seNbrList2","_seNbrList3","_seNbrList4"]}


#without end date
AdjustmentListReq3 = {"lowerBound":_lowerBound,"upperBound":_upperBound,"sort":[{"columnName":"_columnName1","sortOrder":"_sortOrder1"}],"startDt":"_startDt","seNbrList":["_seNbrList"]}

#without start date
AdjustmentListReq4 = {"lowerBound":_lowerBound,"upperBound":_upperBound,"sort":[{"columnName":"_columnName1","sortOrder":"_sortOrder1"}],"endDt":"_endDt","seNbrList":["_seNbrList"]}

#without lowerbound
AdjustmentListReq5 = {"upperBound":_upperBound,"sort":[{"columnName":"_columnName1","sortOrder":"_sortOrder1"}],"startDt":"_startDt","endDt":"_endDt","seNbrList":["_seNbrList"]}


#without sort column
AdjustmentListReq6 = {"lowerBound":_lowerBound,"upperBound":_upperBound,"sort":[{"sortOrder":"_sortOrder1"}],"startDt":"_startDt","endDt":"_endDt","seNbrList":["_seNbrList"]}

#Settlement List
SettlementList = {"startDt":"_startDt","endDt":"_endDt","seNbrList":["_seNbrList"],"sort":[{"columnName":"_columnName","sortOrder":"_sortOrder"}],"lowerBound":_lowerBound,"upperBound":_upperBound}

#Submissions List
SubmListReq1 = {"lowerBound":_lowerBound,"upperBound":_upperBound,"sort":[{"columnName":"_columnName1","sortOrder":"_sortOrder1"}],"startDt":"_startDt","endDt":"_endDt","seNbrList":["_seNbrList"]}

#multiple se
SubmListReq2 = {"lowerBound":_lowerBound,"upperBound":_upperBound,"sort":[{"columnName":"_columnName1","sortOrder":"_sortOrder1"}],"startDt":"_startDt","endDt":"_endDt","seNbrList":["_seNbrList1","_seNbrList2","_seNbrList3","_seNbrList4"]}

#without end date
SubmListReq3 = {"lowerBound":_lowerBound,"upperBound":_upperBound,"sort":[{"columnName":"_columnName1","sortOrder":"_sortOrder1"}],"startDt":"_startDt","seNbrList":["_seNbrList"]}

#without start date
SubmListReq4 = {"lowerBound":_lowerBound,"upperBound":_upperBound,"sort":[{"columnName":"_columnName1","sortOrder":"_sortOrder1"}],"endDt":"_endDt","seNbrList":["_seNbrList"]}

#without lowerbound
SubmListReq5 = {"upperBound":_upperBound,"sort":[{"columnName":"_columnName1","sortOrder":"_sortOrder1"}],"startDt":"_startDt","endDt":"_endDt","seNbrList":["_seNbrList"]}

#without sort column
SubmListReq6 = {"lowerBound":_lowerBound,"upperBound":_upperBound,"sort":[{"sortOrder":"_sortOrder1"}],"startDt":"_startDt","endDt":"_endDt","seNbrList":["_seNbrList"]}


======================================================================================================


com.amex.config.request
payments_sql_queries.properties


GUID=select GLBL_USER_ID from OD1.TC391_USER where FIRST_NM = '_first_nm' and LST_NM = '_lst_nm'

# Queries for Payment Summary
SQLQueryPSHP=Select sum(SOC_NET_AM) as "q1.settlementAmt" ,sum(SUBM_GR_AM) as "a1.submissionAmt",(count(A.GMDL_Settle_ID)) as "q1.submissionCt",(Sum(SOC_GR_AM)-SUM(SOC_NET_AM)) as "q1.discountAmt" from OD1.TE130_MER_SETTLE_DTL A, od1.TE133_MER_SETTLE_SOC B WHERE A.SETTLE_SE_NO = '_SENO' and Settle_dt between '_startDt' and '_endDt' AND A.GMDL_Settle_ID = B.GMDL_SETTLE_ID and B.TRANS_TYPE_CD ='0'
ParamPSHP1=_SENO:1041161720,_startDt:2013-11-01,_endDt:2013-11-30

SQLQueryPSXSDeqED=Select sum(SOC_NET_AM) as "q1.settlementAmt" ,sum(SUBM_GR_AM) as "a1.submissionAmt",(count(A.GMDL_Settle_ID)) as "q1.submissionCt",(Sum(SOC_GR_AM)-SUM(SOC_NET_AM)) as "q1.discountAmt" from OD1.TE130_MER_SETTLE_DTL A, od1.TE133_MER_SETTLE_SOC B WHERE A.SETTLE_SE_NO = '_SENO' and Settle_dt between '_startDt' and '_endDt' AND A.GMDL_Settle_ID = B.GMDL_SETTLE_ID and B.TRANS_TYPE_CD ='0'
ParamPSXSDeqED1=_SENO:1041161720,_startDt:2013-12-01,_endDt:2013-11-30

# Queries for Payment Settlement
SQLQueryPSettleHP=Select SETTLE_DT As "q1.settlementDt", SETTLE_CHK_NO as "q1.settlementNbr",SETTLE_SE_NO as "q1.payeeSeNbr",(select SUM(SOC_NET_AM) from od1.TE133_MER_SETTLE_SOC where GMDL_Settle_ID = A.GMDL_SETTLE_ID) as "q1.settlementAmt",(select case  SETTLE_CURR_CD  when '001' then 'USD' end from OD1.TE130_MER_SETTLE_DTL where GMDL_Settle_ID = A.GMDL_SETTLE_ID) as "q1.settlementCurrencyCd",(select Distinct( count(GMDL_Settle_ID)) from od1.TE133_MER_SETTLE_SOC where GMDL_Settle_ID = A.GMDL_SETTLE_ID and TRANS_TYPE_CD ='0') as "q1.distinctSubmittersCt",(select SUM (SUBM_GR_AM) from od1.TE133_MER_SETTLE_SOC where GMDL_Settle_ID = A.GMDL_SETTLE_ID and TRANS_TYPE_CD ='0') as "q1.submissionAmt",(select SUM (SUBM_GR_AM) from od1.TE133_MER_SETTLE_SOC where GMDL_Settle_ID = A.GMDL_SETTLE_ID and TRANS_TYPE_CD ='0') as "q1.totalChargeAmt",(select Distinct( count(GMDL_Settle_ID)) from od1.TE133_MER_SETTLE_SOC where GMDL_Settle_ID = A.GMDL_SETTLE_ID and TRANS_TYPE_CD ='0') as "q1.chargeCt",(select (Sum(SOC_GR_AM)-SUM(SOC_NET_AM)) from od1.TE133_MER_SETTLE_SOC where GMDL_Settle_ID = A.GMDL_SETTLE_ID and TRANS_TYPE_CD ='0') as "q1.totalDiscountAmt" from OD1.TE130_MER_SETTLE_DTL A WHERE SETTLE_SE_NO = '_SENO' and Settle_dt between '_startDt' and '_endDt'  Order By SETTLE_CHK_NO fetch first 2 rows only
ParamPSettleHP1=_SENO:1041161720,_startDt:2013-11-01,_endDt:2013-11-30
ParamPSettleHPLY=_SENO:1041161720,_startDt:2012-02-01,_endDt:2012-02-29

#Queries for Payments Settlement Details
SQLQuerySettlementHist=SELECT A.SETTLE_CHK_NO AS "q1.settlementNbr", A.SETTLE_DT AS "q1.settlementDt", A.SETTLE_SE_NO AS "q1.payeeSeNbr", replace(rtrim(replace(varchar((A.SETTLE_LOC_AM/100)), '0', ' ')), ' ', '0') AS "q1.settlementAmt", A.GMDL_SETTLE_ID AS "q1.settlementId", C.LOC_NO AS "q1.payeeLocationId", B.ACTL_SETTLE_DT AS "q1.socDt", B.INIT_PRCS_DT AS "q1.amexReceivedDt", B.SOC_NO AS "q1.socInvoiceNumber", B.TRANS_TYPE_CD AS "q1.socType", B.SUBM_SE_NO AS "q1.submitterSE", replace(rtrim(replace(varchar((B.SUBM_GR_AM/100)), '0', ' ')), ' ', '0') AS "q1.socGrossAmt" FROM OD1.TE130_MER_SETTLE_DTL A, od1.TE133_MER_SETTLE_SOC B, OD1.TC501_MER_PRIM_CHAR_DTL C WHERE A.SETTLE_SE_NO = '-SETTLE_SE_NO' AND A.SETTLE_DT = '-SETTLE_DT' AND A.SETTLE_CHK_NO = '-SETTLE_CHK_NO' AND A.GMDL_Settle_ID = B.GMDL_SETTLE_ID AND C.SE_NO = A.SETTLE_SE_NO AND B.TRANS_TYPE_CD ='0' ORDER BY A.SETTLE_CHK_NO Fetch first 3 rows only 
ParamsSettlementHist=-SETTLE_SE_NO:1030000400,-SETTLE_DT:2013-05-28,-SETTLE_CHK_NO:145V8694
ParamsSettlementHist_1=-SETTLE_SE_NO:9300993301,-SETTLE_DT:2013-07-02,-SETTLE_CHK_NO:180N0922
ParamsSettlementHist_2=-SETTLE_SE_NO:1040958100,-SETTLE_DT:2013-05-20,-SETTLE_CHK_NO:137F5526
ParamsSettlementHist_3=-SETTLE_SE_NO:1030331300,-SETTLE_DT:2013-05-23,-SETTLE_CHK_NO:141E0616

SQLQuerySettlementHist2=SELECT A.SETTLE_CHK_NO AS "q1.settlementNbr", A.SETTLE_DT AS "q1.settlementDt", A.SETTLE_SE_NO AS "q1.payeeSeNbr", replace(rtrim(replace(varchar((A.SETTLE_LOC_AM/100)), '0', ' ')), ' ', '0') AS "q1.settlementAmt", A.GMDL_SETTLE_ID AS "q1.settlementId", C.LOC_NO AS "q1.payeeLocationId", B.ACTL_SETTLE_DT AS "q1.socDt", B.INIT_PRCS_DT AS "q1.amexReceivedDt", B.SOC_NO AS "q1.socInvoiceNumber", B.TRANS_TYPE_CD AS "q1.socType", B.SUBM_SE_NO AS "q1.submitterSE", replace(rtrim(replace(varchar((B.SUBM_GR_AM/100)), '0', ' ')), ' ', '0') AS "q1.socGrossAmt" FROM OD1.TE130_MER_SETTLE_DTL A, od1.TE133_MER_SETTLE_SOC B, OD1.TC501_MER_PRIM_CHAR_DTL C WHERE A.SETTLE_SE_NO = '-SETTLE_SE_NO' AND A.SETTLE_DT = '-SETTLE_DT' AND A.SETTLE_CHK_NO = '-SETTLE_CHK_NO' AND A.GMDL_Settle_ID = B.GMDL_SETTLE_ID AND C.SE_NO = A.SETTLE_SE_NO AND B.SOC_NO='-SOC_NO' ORDER BY A.SETTLE_CHK_NO Fetch first 3 rows only
ParamsSettlementHist2_1=-SETTLE_SE_NO:1030000400,-SETTLE_DT:2013-05-27,-SETTLE_CHK_NO:144V5914,-SOC_NO:144037
ParamsSettlementHist2_2=-SETTLE_SE_NO:9300993301,-SETTLE_DT:2013-07-01,-SETTLE_CHK_NO:179N1150,-SOC_NO:008608
ParamsSettlementHist2_3=-SETTLE_SE_NO:9300993301,-SETTLE_DT:2014-05-27,-SETTLE_CHK_NO:144B9530,-SOC_NO:000110

#Queries for Payments Adjustment Details
SQLQueryAdjustmentHist1=SELECT A.SETTLE_CHK_NO AS "q1.settlementNbr", A.SETTLE_DT AS "q1.settlementDt", A.SETTLE_SE_NO AS "q1.payeeSeNbr", replace(rtrim(replace(varchar((A.SETTLE_LOC_AM/100)), '0', ' ')), ' ', '0') AS "q1.settlementAmt", A.GMDL_SETTLE_ID AS "q1.settlementId", C.LOC_NO AS "q1.payeeLocationId", B.ACTL_SETTLE_DT AS "q1.socDt", B.INIT_PRCS_DT AS "q1.amxReceivedDt", B.SOC_NO AS "q1.socNbr", B.SUBM_SE_NO AS "q1.submittingSeNbr", replace(rtrim(replace(varchar((B.SUBM_GR_AM/100)), '0', ' ')), ' ', '0') AS "q1.submissionAmt", replace(rtrim(replace(varchar((B.SOC_DISC_AM/100)), '0', ' ')), ' ', '0') AS "q1.adj_totalDiscountAmt", replace(rtrim(replace(varchar((B.SOC_NET_AM/100)), '0', ' ')), ' ', '0') AS "q1.adj_totalAdjustmentAmt" FROM OD1.TE130_MER_SETTLE_DTL A, od1.TE133_MER_SETTLE_SOC B, OD1.TC501_MER_PRIM_CHAR_DTL C WHERE A.SETTLE_SE_NO = '-SETTLE_SE_NO' AND A.SETTLE_DT = '-SETTLE_DT' AND A.SETTLE_CHK_NO = '-SETTLE_CHK_NO' AND A.GMDL_Settle_ID = B.GMDL_SETTLE_ID AND C.SE_NO = A.SETTLE_SE_NO AND B.SOC_NO='-SOC_NO' ORDER BY A.SETTLE_CHK_NO
ParamsAdjustmentHist1_1=-SETTLE_SE_NO:1030000400,-SETTLE_DT:2013-05-27,-SETTLE_CHK_NO:144V5914,-SOC_NO:144037
ParamsAdjustmentHist1_2=-SETTLE_SE_NO:9300993301,-SETTLE_DT:2013-07-01,-SETTLE_CHK_NO:179N1150,-SOC_NO:008608
ParamsAdjustmentHist1_3=-SETTLE_SE_NO:9300993301,-SETTLE_DT:2014-05-27,-SETTLE_CHK_NO:144B9530,-SOC_NO:000110

#Queries for Payments Settle Currency
#Without Business Center Code, Single SE_NO
SQLQuerySettleCurr=SELECT DISTINCT CUR.ISO_ALPHA_CD as "q1.settleCurrCd", PR.BUS_CTR_CD as "q1.busCtrCd", CUR.DECIMALIZATION_FAC as "q1.decimalizationFactor" FROM OD1.TC501_MER_PRIM_CHAR_DTL PR, VMUS00.CURR CUR WHERE PR.SE_NO IN('-SE_NO') AND PR.SETTLE_ISO_ALPHA_CURR_CD = CUR.IDENTIFIER_ID ORDER BY CUR.ISO_ALPHA_CD
ParamsSettleCurr=-SE_NO:1041161720
ParamsSettleCurr_1=-SE_NO:8100011196
ParamsSettleCurr_2=-SE_NO:0000555571

##With 20 SE_NuMBR
SQLQuerySettleCurr1=SELECT DISTINCT CUR.ISO_ALPHA_CD as "q1.settleCurrCd", PR.BUS_CTR_CD as "q1.busCtrCd", CUR.DECIMALIZATION_FAC as "q1.decimalizationFactor" FROM OD1.TC501_MER_PRIM_CHAR_DTL PR, VMUS00.CURR CUR WHERE PR.SE_NO IN('-SE_NO1','-SE_NO2', '-SE_NO3', '-SE_NO4', '-SE_NO5', '-SE_NO6', '-SE_NO7', '-SE_NO8', '-SE_NO9', '-SE_NOa', '-SE_NOb', '-SE_NOc', '-SE_NOd', '-SE_NOe', '-SE_NOf', '-SE_NOg', '-SE_NOh', '-SE_NOi', '-SE_NOj', '-SE_NOk') AND PR.SETTLE_ISO_ALPHA_CURR_CD = CUR.IDENTIFIER_ID ORDER BY CUR.ISO_ALPHA_CD
ParamsSettleCurr1=-SE_NO1:0000555551,-SE_NO2:0000555552,-SE_NO3:0000555553,-SE_NO4:0000555554,-SE_NO5:0000555555,-SE_NO6:0000555557,-SE_NO7:0000555558,-SE_NO8:0000555559,-SE_NO9:0000555560,-SE_NOa:0000555561,-SE_NOb:0000555562,-SE_NOc:0000555563,-SE_NOd:0000555564,-SE_NOe:0000555565,-SE_NOf:0000555566,-SE_NOg:0000555567,-SE_NOh:0000555568,-SE_NOi:0000555569,-SE_NOj:0000555570,-SE_NOk:0000555571

##Only GUID passed
SQLQuerySettleCurr2=SELECT DISTINCT CUR.ISO_ALPHA_CD as "q1.settleCurrCd", PR.BUS_CTR_CD as "q1.busCtrCd", CUR.DECIMALIZATION_FAC as "q1.decimalizationFactor" FROM OD1.TC501_MER_PRIM_CHAR_DTL PR, p65.USER_SE_HIER_PROD ENR, VMUS00.CURR CUR WHERE ENR.GLBL_USER_ID = '-GLBL_USER_ID' AND ENR.PROD_ID = 'DO3' AND ENR.SE_NO = PR.SE_NO AND PR.SETTLE_ISO_ALPHA_CURR_CD = CUR.IDENTIFIER_ID ORDER BY CUR.ISO_ALPHA_CD
ParamsSettleCurr2=-GLBL_USER_ID:b666752cb78592cf284682b3e44dad68
ParamsSettleCurr2_1=-GLBL_USER_ID:b666752cb78592cf284682b3e44dad68

##Only BusinessCentreCode passed
SQLQuerySettleCurr3=SELECT DISTINCT CUR.ISO_ALPHA_CD as "q1.settleCurrCd", PR.BUS_CTR_CD as "q1.busCtrCd", CUR.DECIMALIZATION_FAC as "q1.decimalizationFactor" FROM OD1.TC501_MER_PRIM_CHAR_DTL PR, VMUS00.CURR CUR WHERE PR.SE_NO IN('') AND PR.SETTLE_ISO_ALPHA_CURR_CD = CUR.IDENTIFIER_ID AND PR.BUS_CTR_CD = '-BUS_CTR_CD' ORDER BY CUR.ISO_ALPHA_CD;
ParamsSettleCurr3=-BUS_CTR_CD:023


#Queries for Submission
SQLQuerySubmissions1 = Select A.SETTLE_CHK_NO as "q1.settlementNumber", A.SETTLE_DT as "q1.settlementDate", A.SETTLE_SE_NO as "q1.payeeSeNbr", replace(rtrim(replace(varchar((A.SETTLE_LOC_AM/100)),'0', ' ')), ' ', '0') as "q1.settlementAmt", A.SETTLE_CURR_CD as "q1.CurrencyCode", A.GMDL_SETTLE_ID as "q1.settlementId", B.ACTL_SETTLE_DT as "q1.socDt", B.INIT_PRCS_DT as "q1.amxReceivedDt", B.SOC_NO as "q1.socNbr",(Select case TRANS_TYPE_CD when (Select TRANS_TYPE_CD from od1.TE133_MER_SETTLE_SOC where GMDL_Settle_ID = B.GMDL_SETTLE_ID) then (Select TRANS_TYPE_GRP_DS from od1.TF809_TRANS_TYPE where TRANS_TYPE_CD = (Select TRANS_TYPE_CD from od1.TE133_MER_SETTLE_SOC where GMDL_Settle_ID = B.GMDL_SETTLE_ID)) end from OD1.TE133_MER_SETTLE_SOC where GMDL_Settle_ID = B.GMDL_SETTLE_ID) as "q1.socType", B.SUBM_SE_NO as "q1.submittingSeNbr", replace(rtrim(replace(varchar((B.SUBM_GR_AM/100)),'0', ' ')), ' ', '0') as "q1.submissionAmt", B.DR_ROC_CT as "q1.chargeCt" from OD1.TE130_MER_SETTLE_DTL A, od1.TE133_MER_SETTLE_SOC B WHERE A.SETTLE_SE_NO = '-SETTLE_SE_NO' and Settle_dt between '-Settle_dt_Start' and '-Settle_dt_End' AND A.GMDL_Settle_ID = B.GMDL_SETTLE_ID Order By A.GMDL_SETTLE_ID Fetch first 3 rows only
ParamSubmissions1_1 = -SETTLE_SE_NO:9300993301,-Settle_dt_Start:2013-11-01,-Settle_dt_End:2013-11-30

#Queries for Transaction List
SQLTxnList1 = SELECT DTL.GMDL_SETTLE_ID AS "q1.transactionId", DTL.SETTLE_CURR_CD AS "q1.transactionCurrencyCd", ROC.CHRG_DT AS "q1.chargeDt", SOC.BUS_DT AS "q1.merchantBussDt", SOC.INIT_PRCS_DT AS "q1.amexReceivedDt", decimal(ROC_LOC_AM/100,12,2) AS "q1.chargeAmt", ROC.SE_REFER_NO AS "q1.chargeRefNbr", CASE USR.MSK_CARD_NO_IN WHEN 'Y' then SUBSTR(ROC.CARD_NO, 1, 6) || 'XXXXX' || SUBSTR(ROC.CARD_NO, 12, 4) WHEN 'N' then ROC.CARD_NO END AS "q1.cardNbr", SOC.SUBM_SE_NO AS "q1.submitterSeNbr", ROC.AIR_TKT_NO AS "q1.airlineTicketNbr", SOC.SOC_NO AS "q1.adjustmentNbr", TT.TRANS_TYPE_GRP_DS AS "q1.description", int(ROC.TRANS_REFER_NO) AS "q1.rentalAgreementNbr" FROM OD1.TE130_MER_SETTLE_DTL DTL, OD1.TE133_MER_SETTLE_SOC SOC, OD1.TE158_MER_ROC ROC, OD1.TF809_TRANS_TYPE TT, OD1.TC391_USER USR WHERE DTL.GMDL_SETTLE_ID = SOC.GMDL_SETTLE_ID AND SOC.GMDL_SOC_ID = ROC.GMDL_SOC_ID AND SOC.TRANS_TYPE_CD = TT.TRANS_TYPE_CD AND ROC.SE_CHK_DGT_NO = SUBSTR(SOC.SUBM_SE_NO, 8, 3) AND USR.GLBL_USER_ID = '-GLBL_USER_ID' AND DTL.SETTLE_SE_NO = '-SETTLE_SE_NO' AND DTL.SE_CHK_DGT_NO = SUBSTR(-SETTLE_SE_NO, 9, 2) AND DTL.SETTLE_CHK_NO = '-SETTLE_CHK_NO' AND DTL.SETTLE_DT = '-SETTLE_DT' ORDER BY SOC.SUBM_SE_NO ASC, ROC.SE_REFER_NO, ROC.ROC_NO, ROC.CARD_NO, ROC.ROC_LOC_AM
#SQLTxnList1 = SELECT DTL.GMDL_SETTLE_ID AS "q1.transactionId", DTL.SETTLE_CURR_CD AS "q1.transactionCurrencyCd", ROC.CHRG_DT AS "q1.chargeDt", SOC.BUS_DT AS "q1.merchantBussDt", SOC.INIT_PRCS_DT AS "q1.amexReceivedDt", decimal(ROC_LOC_AM/100,12,2) AS "q1.chargeAmt", ROC.SE_REFER_NO AS "q1.chargeRefNbr", ROC.CARD_NO AS "q1.cardNbr", SOC.SUBM_SE_NO AS "q1.submitterSeNbr", ROC.AIR_TKT_NO AS "q1.airlineTicketNbr", SOC.SOC_NO AS "q1.adjustmentNbr", TT.TRANS_TYPE_GRP_DS AS "q1.description", int(ROC.TRANS_REFER_NO) AS "q1.rentalAgreementNbr" FROM OD1.TE130_MER_SETTLE_DTL DTL, OD1.TE133_MER_SETTLE_SOC SOC, OD1.TE158_MER_ROC ROC, OD1.TF809_TRANS_TYPE TT WHERE DTL.GMDL_SETTLE_ID = SOC.GMDL_SETTLE_ID AND SOC.GMDL_SOC_ID = ROC.GMDL_SOC_ID AND SOC.TRANS_TYPE_CD = TT.TRANS_TYPE_CD AND ROC.SE_CHK_DGT_NO = SUBSTR(SOC.SUBM_SE_NO, 8, 3) AND DTL.SETTLE_SE_NO = '-SETTLE_SE_NO' AND DTL.SE_CHK_DGT_NO = SUBSTR(-SETTLE_SE_NO, 9, 2) AND DTL.SETTLE_CHK_NO = '-SETTLE_CHK_NO' AND DTL.SETTLE_DT = '-SETTLE_DT' ORDER BY DTL.SUBM_SE_NO ASC, ROC.CARD_NO DESC
ParamsTxnList1 = -SETTLE_SE_NO:5020764502,-SETTLE_CHK_NO:200M8612,-SETTLE_DT:2014-07-22,-GLBL_USER_ID:241c230fcf8929e2324dc87cc49b11f7
ParamsTxnList1_1 = -SETTLE_SE_NO:1040958100,-SETTLE_CHK_NO:136F4493,-SETTLE_DT:2013-05-18,-GLBL_USER_ID:bdcfedbc888c24e5a3c636ecf26faaa9
ParamsTxnList1_2 = -SETTLE_SE_NO:1040958100,-SETTLE_CHK_NO:137F5526,-SETTLE_DT:2013-05-20,-GLBL_USER_ID:bdcfedbc888c24e5a3c636ecf26faaa9
ParamsTxnList1_3 = -SETTLE_SE_NO:5020764502,-SETTLE_CHK_NO:200M8612,-SETTLE_DT:2012-02-29,-GLBL_USER_ID:bdcfedbc888c24e5a3c636ecf26faaa9

SQLTxnList2 = SELECT DTL.GMDL_SETTLE_ID AS "q1.transactionId", DTL.SETTLE_CURR_CD AS "q1.transactionCurrencyCd", ROC.CHRG_DT AS "q1.chargeDt", SOC.BUS_DT AS "q1.merchantBussDt", SOC.INIT_PRCS_DT AS "q1.amexReceivedDt", decimal(ROC_LOC_AM/100,12,2) AS "q1.chargeAmt", ROC.SE_REFER_NO AS "q1.chargeRefNbr", CASE USR.MSK_CARD_NO_IN WHEN 'Y' then SUBSTR(ROC.CARD_NO, 1, 6) || 'XXXXX' || SUBSTR(ROC.CARD_NO, 12, 4) WHEN 'N' then ROC.CARD_NO END AS "q1.cardNbr", SOC.SUBM_SE_NO AS "q1.submitterSeNbr", ROC.AIR_TKT_NO AS "q1.airlineTicketNbr", SOC.SOC_NO AS "q1.adjustmentNbr", TT.TRANS_TYPE_GRP_DS AS "q1.description", int(ROC.TRANS_REFER_NO) AS "q1.rentalAgreementNbr" FROM OD1.TE130_MER_SETTLE_DTL DTL, OD1.TE133_MER_SETTLE_SOC SOC, OD1.TE158_MER_ROC ROC, OD1.TF809_TRANS_TYPE TT, OD1.TC391_USER USR WHERE DTL.GMDL_SETTLE_ID = SOC.GMDL_SETTLE_ID AND SOC.GMDL_SOC_ID = ROC.GMDL_SOC_ID AND SOC.TRANS_TYPE_CD = TT.TRANS_TYPE_CD AND ROC.SE_CHK_DGT_NO = SUBSTR(SOC.SUBM_SE_NO, 8, 3) AND USR.GLBL_USER_ID = '-GLBL_USER_ID' AND DTL.SETTLE_SE_NO = '-SETTLE_SE_NO' AND DTL.SE_CHK_DGT_NO = SUBSTR(-SETTLE_SE_NO, 9, 2) AND DTL.SETTLE_CHK_NO = '-SETTLE_CHK_NO' AND DTL.SETTLE_DT = '-SETTLE_DT' ORDER BY ROC.CARD_NO DESC, SOC.SUBM_SE_NO, ROC.SE_REFER_NO, ROC.ROC_NO, ROC.ROC_LOC_AM 
#SQLTxnList2 = SELECT DTL.GMDL_SETTLE_ID AS "q1.transactionId", DTL.SETTLE_CURR_CD AS "q1.transactionCurrencyCd", ROC.CHRG_DT AS "q1.chargeDt", SOC.BUS_DT AS "q1.merchantBussDt", SOC.INIT_PRCS_DT AS "q1.amexReceivedDt", decimal(ROC_LOC_AM/100,12,2) AS "q1.chargeAmt", ROC.SE_REFER_NO AS "q1.chargeRefNbr", ROC.CARD_NO AS "q1.cardNbr", SOC.SUBM_SE_NO AS "q1.submitterSeNbr", ROC.AIR_TKT_NO AS "q1.airlineTicketNbr", SOC.SOC_NO AS "q1.adjustmentNbr", TT.TRANS_TYPE_GRP_DS AS "q1.description", int(ROC.TRANS_REFER_NO) AS "q1.rentalAgreementNbr" FROM OD1.TE130_MER_SETTLE_DTL DTL, OD1.TE133_MER_SETTLE_SOC SOC, OD1.TE158_MER_ROC ROC, OD1.TF809_TRANS_TYPE TT WHERE DTL.GMDL_SETTLE_ID = SOC.GMDL_SETTLE_ID AND SOC.GMDL_SOC_ID = ROC.GMDL_SOC_ID AND SOC.TRANS_TYPE_CD = TT.TRANS_TYPE_CD AND ROC.SE_CHK_DGT_NO = SUBSTR(SOC.SUBM_SE_NO, 8, 3) AND DTL.SETTLE_SE_NO = '-SETTLE_SE_NO' AND DTL.SE_CHK_DGT_NO = SUBSTR(-SETTLE_SE_NO, 9, 2) AND DTL.SETTLE_CHK_NO = '-SETTLE_CHK_NO' AND DTL.SETTLE_DT = '-SETTLE_DT' ORDER BY ROC.CARD_NO desc, DTL.SUBM_SE_NO asc
ParamsTxnList2 = -SETTLE_SE_NO:5020764502,-SETTLE_CHK_NO:200M8612,-SETTLE_DT:2014-07-22,-GLBL_USER_ID:241c230fcf8929e2324dc87cc49b11f7

SQLTxnList3 = SELECT DTL.GMDL_SETTLE_ID AS "q1.transactionId", DTL.SETTLE_CURR_CD AS "q1.transactionCurrencyCd", ROC.CHRG_DT AS "q1.chargeDt", SOC.BUS_DT AS "q1.merchantBussDt", SOC.INIT_PRCS_DT AS "q1.amexReceivedDt", decimal(ROC_LOC_AM/100,12,2) AS "q1.chargeAmt", ROC.SE_REFER_NO AS "q1.chargeRefNbr", CASE USR.MSK_CARD_NO_IN WHEN 'Y' then SUBSTR(ROC.CARD_NO, 1, 6) || 'XXXXX' || SUBSTR(ROC.CARD_NO, 12, 4) WHEN 'N' then ROC.CARD_NO END AS "q1.cardNbr", SOC.SUBM_SE_NO AS "q1.submitterSeNbr", ROC.AIR_TKT_NO AS "q1.airlineTicketNbr", SOC.SOC_NO AS "q1.adjustmentNbr", TT.TRANS_TYPE_GRP_DS AS "q1.description", int(ROC.TRANS_REFER_NO) AS "q1.rentalAgreementNbr" FROM OD1.TE130_MER_SETTLE_DTL DTL, OD1.TE133_MER_SETTLE_SOC SOC, OD1.TE158_MER_ROC ROC, OD1.TF809_TRANS_TYPE TT, OD1.TC391_USER USR WHERE DTL.GMDL_SETTLE_ID = SOC.GMDL_SETTLE_ID AND SOC.GMDL_SOC_ID = ROC.GMDL_SOC_ID AND SOC.TRANS_TYPE_CD = TT.TRANS_TYPE_CD AND ROC.SE_CHK_DGT_NO = SUBSTR(SOC.SUBM_SE_NO, 8, 3) AND USR.GLBL_USER_ID = '-GLBL_USER_ID' AND DTL.SETTLE_SE_NO = '-SETTLE_SE_NO' AND DTL.SE_CHK_DGT_NO = SUBSTR(-SETTLE_SE_NO, 9, 2) AND DTL.SETTLE_CHK_NO = '-SETTLE_CHK_NO' AND DTL.SETTLE_DT = '-SETTLE_DT' AND SOC.SOC_NO = '-SOC_NO' ORDER BY SOC.SUBM_SE_NO ASC, ROC.SE_REFER_NO, ROC.ROC_NO, ROC.CARD_NO, ROC.ROC_LOC_AM
ParamsTxnList3 = -SETTLE_SE_NO:5020764502,-SETTLE_CHK_NO:200M8612,-SETTLE_DT:2014-07-22,-SOC_NO:644487,-GLBL_USER_ID:241c230fcf8929e2324dc87cc49b11f7
ParamsTxnList3_1 = -SETTLE_SE_NO:5020764502,-SETTLE_CHK_NO:206M2145,-SETTLE_DT:2014-07-28,-SOC_NO:206034,-GLBL_USER_ID:241c230fcf8929e2324dc87cc49b11f7
ParamsTxnList3_2 = -SETTLE_SE_NO:5020764502,-SETTLE_CHK_NO:200M8612,-SETTLE_DT:2014-07-22,-SOC_NO:135013,-GLBL_USER_ID:241c230fcf8929e2324dc87cc49b11f7

#for fetching only card numbers
SQLTxnList4 = SELECT CASE USR.MSK_CARD_NO_IN WHEN 'Y' then SUBSTR(ROC.CARD_NO, 1, 6) || 'XXXXXXXXX' WHEN 'N' then ROC.CARD_NO END AS "q1.cardNbr" FROM OD1.TE130_MER_SETTLE_DTL DTL, OD1.TE133_MER_SETTLE_SOC SOC, OD1.TE158_MER_ROC ROC, OD1.TF809_TRANS_TYPE TT, OD1.TC391_USER USR WHERE DTL.GMDL_SETTLE_ID = SOC.GMDL_SETTLE_ID AND SOC.GMDL_SOC_ID = ROC.GMDL_SOC_ID AND SOC.TRANS_TYPE_CD = TT.TRANS_TYPE_CD AND ROC.SE_CHK_DGT_NO = SUBSTR(SOC.SUBM_SE_NO, 8, 3) AND USR.GLBL_USER_ID = '-GLBL_USER_ID' AND DTL.SETTLE_SE_NO = '-SETTLE_SE_NO' AND DTL.SE_CHK_DGT_NO = SUBSTR(-SETTLE_SE_NO, 9, 2) AND DTL.SETTLE_CHK_NO = '-SETTLE_CHK_NO' AND DTL.SETTLE_DT = '-SETTLE_DT' ORDER BY SOC.SUBM_SE_NO ASC, ROC.SE_REFER_NO, ROC.ROC_NO, ROC.CARD_NO, ROC.ROC_LOC_AM
ParamsTxnList4 = -SETTLE_SE_NO:5020764502,-SETTLE_CHK_NO:200M8612,-SETTLE_DT:2014-07-22,-GLBL_USER_ID:241c230fcf8929e2324dc87cc49b11f7
#pass settlement id alone.
SQLTxnList5 = SELECT DTL.GMDL_SETTLE_ID AS "q1.transactionId", DTL.SETTLE_CURR_CD AS "q1.transactionCurrencyCd", ROC.CHRG_DT AS "q1.chargeDt", SOC.BUS_DT AS "q1.merchantBussDt", SOC.INIT_PRCS_DT AS "q1.amexReceivedDt", decimal(ROC_LOC_AM/100,12,2) AS "q1.chargeAmt", ROC.SE_REFER_NO AS "q1.chargeRefNbr", CASE USR.MSK_CARD_NO_IN WHEN 'Y' then SUBSTR(ROC.CARD_NO, 1, 6) || 'XXXXX' || SUBSTR(ROC.CARD_NO, 12, 4) WHEN 'N' then ROC.CARD_NO END AS "q1.cardNbr", SOC.SUBM_SE_NO AS "q1.submitterSeNbr", ROC.AIR_TKT_NO AS "q1.airlineTicketNbr", SOC.SOC_NO AS "q1.adjustmentNbr", TT.TRANS_TYPE_GRP_DS AS "q1.description", int(ROC.TRANS_REFER_NO) AS "q1.rentalAgreementNbr" FROM OD1.TE130_MER_SETTLE_DTL DTL, OD1.TE133_MER_SETTLE_SOC SOC, OD1.TE158_MER_ROC ROC, OD1.TF809_TRANS_TYPE TT, OD1.TC391_USER USR WHERE DTL.GMDL_SETTLE_ID = SOC.GMDL_SETTLE_ID AND SOC.GMDL_SOC_ID = ROC.GMDL_SOC_ID AND SOC.TRANS_TYPE_CD = TT.TRANS_TYPE_CD AND ROC.SE_CHK_DGT_NO = SUBSTR(SOC.SUBM_SE_NO, 8, 3) AND USR.GLBL_USER_ID = '-GLBL_USER_ID' AND DTL.GMDL_SETTLE_ID ='-GMDL_SETTLE_ID' ORDER BY SOC.SUBM_SE_NO ASC, ROC.SE_REFER_NO, ROC.ROC_NO, ROC.CARD_NO, ROC.ROC_LOC_AM
ParamsTxnList5 = -GMDL_SETTLE_ID:16430330,-GLBL_USER_ID:241c230fcf8929e2324dc87cc49b11f7

#Card number sort
SQLTxnList6=SELECT DTL.GMDL_SETTLE_ID AS "q1.transactionId", DTL.SETTLE_CURR_CD AS "q1.transactionCurrencyCd", ROC.CHRG_DT AS "q1.chargeDt", SOC.BUS_DT AS "q1.merchantBussDt", SOC.INIT_PRCS_DT AS "q1.amexReceivedDt", decimal(ROC_LOC_AM/100,12,2) AS "q1.chargeAmt", ROC.SE_REFER_NO AS "q1.chargeRefNbr", CASE USR.MSK_CARD_NO_IN WHEN 'Y' then SUBSTR(ROC.CARD_NO, 1, 6) || 'XXXXX' || SUBSTR(ROC.CARD_NO, 12, 4) WHEN 'N' then ROC.CARD_NO END AS "q1.cardNbr", SOC.SUBM_SE_NO AS "q1.submitterSeNbr", ROC.AIR_TKT_NO AS "q1.airlineTicketNbr", SOC.SOC_NO AS "q1.adjustmentNbr", TT.TRANS_TYPE_GRP_DS AS "q1.description", int(ROC.TRANS_REFER_NO) AS "q1.rentalAgreementNbr" FROM OD1.TE130_MER_SETTLE_DTL DTL, OD1.TE133_MER_SETTLE_SOC SOC, OD1.TE158_MER_ROC ROC, OD1.TF809_TRANS_TYPE TT, OD1.TC391_USER USR WHERE DTL.GMDL_SETTLE_ID = SOC.GMDL_SETTLE_ID AND SOC.GMDL_SOC_ID = ROC.GMDL_SOC_ID AND SOC.TRANS_TYPE_CD = TT.TRANS_TYPE_CD AND ROC.SE_CHK_DGT_NO = SUBSTR(SOC.SUBM_SE_NO, 8, 3) AND USR.GLBL_USER_ID = '-GLBL_USER_ID' AND DTL.SETTLE_SE_NO = '-SETTLE_SE_NO' AND DTL.SE_CHK_DGT_NO = SUBSTR(-SETTLE_SE_NO, 9, 2) AND DTL.SETTLE_CHK_NO = '-SETTLE_CHK_NO' AND DTL.SETTLE_DT = '-SETTLE_DT' ORDER BY ROC.CARD_NO -SORT_ORDER, SOC.SUBM_SE_NO, ROC.SE_REFER_NO, ROC.ROC_NO, ROC.ROC_LOC_AM
ParamsTxnList6 = -SORT_ORDER:ASC,-SETTLE_SE_NO:5020764502,-SETTLE_CHK_NO:206M2145,-SETTLE_DT:2014-07-28,-GLBL_USER_ID:241c230fcf8929e2324dc87cc49b11f7

#Charge Amount sort
SQLTxnList7=SELECT DTL.GMDL_SETTLE_ID AS "q1.transactionId", DTL.SETTLE_CURR_CD AS "q1.transactionCurrencyCd", ROC.CHRG_DT AS "q1.chargeDt", SOC.BUS_DT AS "q1.merchantBussDt", SOC.INIT_PRCS_DT AS "q1.amexReceivedDt", decimal(ROC_LOC_AM/100,12,2) AS "q1.chargeAmt", ROC.SE_REFER_NO AS "q1.chargeRefNbr", CASE USR.MSK_CARD_NO_IN WHEN 'Y' then SUBSTR(ROC.CARD_NO, 1, 6) || 'XXXXX' || SUBSTR(ROC.CARD_NO, 12, 4) WHEN 'N' then ROC.CARD_NO END AS "q1.cardNbr", SOC.SUBM_SE_NO AS "q1.submitterSeNbr", ROC.AIR_TKT_NO AS "q1.airlineTicketNbr", SOC.SOC_NO AS "q1.adjustmentNbr", TT.TRANS_TYPE_GRP_DS AS "q1.description", int(ROC.TRANS_REFER_NO) AS "q1.rentalAgreementNbr" FROM OD1.TE130_MER_SETTLE_DTL DTL, OD1.TE133_MER_SETTLE_SOC SOC, OD1.TE158_MER_ROC ROC, OD1.TF809_TRANS_TYPE TT, OD1.TC391_USER USR WHERE DTL.GMDL_SETTLE_ID = SOC.GMDL_SETTLE_ID AND SOC.GMDL_SOC_ID = ROC.GMDL_SOC_ID AND SOC.TRANS_TYPE_CD = TT.TRANS_TYPE_CD AND ROC.SE_CHK_DGT_NO = SUBSTR(SOC.SUBM_SE_NO, 8, 3) AND USR.GLBL_USER_ID = '-GLBL_USER_ID' AND DTL.SETTLE_SE_NO = '-SETTLE_SE_NO' AND DTL.SE_CHK_DGT_NO = SUBSTR(-SETTLE_SE_NO, 9, 2) AND DTL.SETTLE_CHK_NO = '-SETTLE_CHK_NO' AND DTL.SETTLE_DT = '-SETTLE_DT' ORDER BY ROC.ROC_LOC_AM -SORT_ORDER, SOC.SUBM_SE_NO, ROC.SE_REFER_NO, ROC.ROC_NO, ROC.CARD_NO
ParamsTxnList7 = -SORT_ORDER:ASC,-SETTLE_SE_NO:5020764502,-SETTLE_CHK_NO:206M2145,-SETTLE_DT:2014-07-28,-GLBL_USER_ID:241c230fcf8929e2324dc87cc49b11f7

#chargeRefNbr sort
SQLTxnList8=SELECT DTL.GMDL_SETTLE_ID AS "q1.transactionId", DTL.SETTLE_CURR_CD AS "q1.transactionCurrencyCd", ROC.CHRG_DT AS "q1.chargeDt", SOC.BUS_DT AS "q1.merchantBussDt", SOC.INIT_PRCS_DT AS "q1.amexReceivedDt", decimal(ROC_LOC_AM/100,12,2) AS "q1.chargeAmt", ROC.SE_REFER_NO AS "q1.chargeRefNbr", CASE USR.MSK_CARD_NO_IN WHEN 'Y' then SUBSTR(ROC.CARD_NO, 1, 6) || 'XXXXX' || SUBSTR(ROC.CARD_NO, 12, 4) WHEN 'N' then ROC.CARD_NO END AS "q1.cardNbr", SOC.SUBM_SE_NO AS "q1.submitterSeNbr", ROC.AIR_TKT_NO AS "q1.airlineTicketNbr", SOC.SOC_NO AS "q1.adjustmentNbr", TT.TRANS_TYPE_GRP_DS AS "q1.description", int(ROC.TRANS_REFER_NO) AS "q1.rentalAgreementNbr" FROM OD1.TE130_MER_SETTLE_DTL DTL, OD1.TE133_MER_SETTLE_SOC SOC, OD1.TE158_MER_ROC ROC, OD1.TF809_TRANS_TYPE TT, OD1.TC391_USER USR WHERE DTL.GMDL_SETTLE_ID = SOC.GMDL_SETTLE_ID AND SOC.GMDL_SOC_ID = ROC.GMDL_SOC_ID AND SOC.TRANS_TYPE_CD = TT.TRANS_TYPE_CD AND ROC.SE_CHK_DGT_NO = SUBSTR(SOC.SUBM_SE_NO, 8, 3) AND USR.GLBL_USER_ID = '-GLBL_USER_ID' AND DTL.SETTLE_SE_NO = '-SETTLE_SE_NO' AND DTL.SE_CHK_DGT_NO = SUBSTR(-SETTLE_SE_NO, 9, 2) AND DTL.SETTLE_CHK_NO = '-SETTLE_CHK_NO' AND DTL.SETTLE_DT = '-SETTLE_DT' ORDER BY ROC.SE_REFER_NO -SORT_ORDER, SOC.SUBM_SE_NO, ROC.ROC_NO, ROC.CARD_NO, ROC.ROC_LOC_AM
ParamsTxnList8 = -SORT_ORDER:ASC,-SETTLE_SE_NO:5020764502,-SETTLE_CHK_NO:206M2145,-SETTLE_DT:2014-07-28,-GLBL_USER_ID:241c230fcf8929e2324dc87cc49b11f7

#Queries for Adjustment List
#Single SE_NO Sort by Subm se nbr
SQLAdjList1 = SELECT DTL.SETTLE_CHK_NO as "q1.settlementNbr", DTL.SETTLE_DT as "q1.settlementDt", DTL.SETTLE_SE_NO as "q1.payeeSeNbr", decimal(DTL.SETTLE_LOC_AM/100,12,2) as "q1.settlementAmt", DTL.SETTLE_CURR_CD as "q1.settlementCurrencyCd", SOC.GMDL_SETTLE_ID as "q1.settlementId", SOC.INIT_PRCS_DT as "q1.socDt", SOC.INIT_PRCS_DT as "q1.amxReceivedDt", SOC.SOC_NO as "q1.socNbr", TT.TRANS_TYPE_GRP_DS as "q1.socType", SOC.SUBM_SE_NO as "q1.submittingSeNbr", SOC.SUBM_CURR_CD as "q1.currencyCd", decimal(SOC.SOC_GR_AM/100,12,2) as "q1.submissionAmt", decimal(SOC.SETTLE_CALC_GR_CR_AM/100,12,2) as "q1.totalCreditAmt", decimal((SOC.SETTLE_CALC_GR_DR_AM + SOC.SETTLE_CALC_GR_CR_AM)/100,12,2) as "q1.totalChargeAmt", SOC.CR_ROC_CT as "q1.creditCt", SOC.DR_ROC_CT as "q1.chargeCt", DTL.SETTLE_CURR_CD as "q1.adjustmentCurrencyCd",decimal(SOC.SOC_NET_AM/100,12,2) as "q1.totalAdjustmentAmt", (SOC.DR_ROC_CT + SOC.Cr_ROC_CT) as "q1.totalAdjustmentsCt", decimal(SOC.SOC_DISC_AM/100,12,2) as "q1.totalDiscountAmt", decimal((SOC.SOC_SRVC_FEE_AM + SOC.SOC_DIV_AM + SOC.SOC_DISC_AM)/100,12,2) as "q1.totalFeesAmt" FROM OD1.TE130_MER_SETTLE_DTL DTL, OD1.TE133_MER_SETTLE_SOC SOC, OD1.TF809_TRANS_TYPE TT, OD1.TE134_MER_SETTLE_SOC_BTCH_BILL_CD BAT WHERE DTL.GMDL_SETTLE_ID = SOC.GMDL_SETTLE_ID AND SOC.TRANS_TYPE_CD = TT.TRANS_TYPE_CD AND SOC.GMDL_SETTLE_ID = BAT.GMDL_SETTLE_ID AND SOC.GMDL_SETTLE_SOC_ID = BAT.GMDL_SETTLE_SOC_ID AND DTL.SETTLE_SE_NO = '-SETTLE_SE_NO' AND DTL.SE_CHK_DGT_NO = SUBSTR('-SETTLE_SE_NO',9,2) AND DTL.SETTLE_DT BETWEEN '-SETTLE_DT_START' AND '-SETTLE_DT_END' AND TT.TRANS_TYPE_GRP_DS = 'ADJUSTMENT' order by SOC.SUBM_SE_NO ASC,DTL.SETTLE_CURR_CD, DTL.BUS_CTR_CD,DTL.SETTLE_DT,DTL.SETTLE_SE_NO,DTL.SETTLE_CHK_NO,DTL.SETTLE_LOC_AM,TT.TRANS_TYPE_GRP_DS,SOC.SOC_NO    
ParamsAdjList1 = -SETTLE_SE_NO:1040958100,-SETTLE_DT_START:2011-07-22,-SETTLE_DT_END:2014-07-29
ParamsAdjList1_1 = -SETTLE_SE_NO:9301230901,-SETTLE_DT_START:2013-07-01,-SETTLE_DT_END:2014-07-01

#Multiple Se_No sort by payee se nbr
SQLAdjList2 = SELECT DTL.SETTLE_CHK_NO as "q1.settlementNbr", DTL.SETTLE_DT as "q1.settlementDt", DTL.SETTLE_SE_NO as "q1.payeeSeNbr", decimal(DTL.SETTLE_LOC_AM/100,12,2) as "q1.settlementAmt", DTL.SETTLE_CURR_CD as "q1.settlementCurrencyCd", SOC.GMDL_SETTLE_ID as "q1.settlementId", SOC.INIT_PRCS_DT as "q1.socDt", SOC.INIT_PRCS_DT as "q1.amxReceivedDt", SOC.SOC_NO as "q1.socNbr", TT.TRANS_TYPE_GRP_DS as "q1.socType", SOC.SUBM_SE_NO as "q1.submittingSeNbr", SOC.SUBM_CURR_CD as "q1.currencyCd", decimal(SOC.SOC_GR_AM/100,12,2) as "q1.submissionAmt", decimal(SOC.SETTLE_CALC_GR_CR_AM/100,12,2) as "q1.totalCreditAmt", decimal((SOC.SETTLE_CALC_GR_DR_AM + SOC.SETTLE_CALC_GR_CR_AM)/100,12,2) as "q1.totalChargeAmt", SOC.CR_ROC_CT as "q1.creditCt", SOC.DR_ROC_CT as "q1.chargeCt", DTL.SETTLE_CURR_CD as "q1.adjustmentCurrencyCd",decimal(SOC.SOC_NET_AM/100,12,2) as "q1.totalAdjustmentAmt", (SOC.DR_ROC_CT + SOC.Cr_ROC_CT) as "q1.totalAdjustmentsCt", decimal(SOC.SOC_DISC_AM/100,12,2) as "q1.totalDiscountAmt", decimal((SOC.SOC_SRVC_FEE_AM + SOC.SOC_DIV_AM + SOC.SOC_DISC_AM)/100,12,2) as "q1.totalFeesAmt" FROM OD1.TE130_MER_SETTLE_DTL DTL, OD1.TE133_MER_SETTLE_SOC SOC, OD1.TF809_TRANS_TYPE TT, OD1.TE134_MER_SETTLE_SOC_BTCH_BILL_CD BAT WHERE DTL.GMDL_SETTLE_ID = SOC.GMDL_SETTLE_ID AND SOC.TRANS_TYPE_CD = TT.TRANS_TYPE_CD AND SOC.GMDL_SETTLE_ID = BAT.GMDL_SETTLE_ID AND SOC.GMDL_SETTLE_SOC_ID = BAT.GMDL_SETTLE_SOC_ID AND DTL.SETTLE_SE_NO in ('-SETTLE_SE_NO1', '-SETTLE_SE_NO2','-SETTLE_SE_NO3','-SETTLE_SE_NO4') AND DTL.SETTLE_DT BETWEEN '-SETTLE_DT_START' AND '-SETTLE_DT_END' AND TT.TRANS_TYPE_GRP_DS = 'ADJUSTMENT' order by DTL.SETTLE_SE_NO ASC,DTL.SETTLE_CURR_CD,DTL.BUS_CTR_CD,DTL.SETTLE_DT DESC,DTL.SETTLE_CHK_NO,DTL.SETTLE_LOC_AM,SOC.SUBM_SE_NO,TT.TRANS_TYPE_GRP_DS,SOC.SOC_NO
ParamsAdjList2 = -SETTLE_SE_NO1:9300993301,-SETTLE_SE_NO2:9301230901,-SETTLE_SE_NO3:9301935103,-SETTLE_SE_NO4:9302291506,-SETTLE_DT_START:2011-07-22,-SETTLE_DT_END:2014-07-29,-SORT_FIELD:SOC.SUBM_SE_NO,-SORT_ORDER:ASC

#Single Sort by SettlementAmt
SQLAdjList3 = SELECT DTL.SETTLE_CHK_NO as "q1.settlementNbr", DTL.SETTLE_DT as "q1.settlementDt", DTL.SETTLE_SE_NO as "q1.payeeSeNbr", decimal(DTL.SETTLE_LOC_AM/100,12,2) as "q1.settlementAmt", DTL.SETTLE_CURR_CD as "q1.settlementCurrencyCd", SOC.GMDL_SETTLE_ID as "q1.settlementId", SOC.INIT_PRCS_DT as "q1.socDt", SOC.INIT_PRCS_DT as "q1.amxReceivedDt", SOC.SOC_NO as "q1.socNbr", TT.TRANS_TYPE_GRP_DS as "q1.socType", SOC.SUBM_SE_NO as "q1.submittingSeNbr", SOC.SUBM_CURR_CD as "q1.currencyCd", decimal(SOC.SOC_GR_AM/100,12,2) as "q1.submissionAmt", decimal(SOC.SETTLE_CALC_GR_CR_AM/100,12,2) as "q1.totalCreditAmt", decimal((SOC.SETTLE_CALC_GR_DR_AM + SOC.SETTLE_CALC_GR_CR_AM)/100,12,2) as "q1.totalChargeAmt", SOC.CR_ROC_CT as "q1.creditCt", SOC.DR_ROC_CT as "q1.chargeCt", DTL.SETTLE_CURR_CD as "q1.adjustmentCurrencyCd",decimal(SOC.SOC_NET_AM/100,12,2) as "q1.totalAdjustmentAmt", (SOC.DR_ROC_CT + SOC.Cr_ROC_CT) as "q1.totalAdjustmentsCt", decimal(SOC.SOC_DISC_AM/100,12,2) as "q1.totalDiscountAmt", decimal((SOC.SOC_SRVC_FEE_AM + SOC.SOC_DIV_AM + SOC.SOC_DISC_AM)/100,12,2) as "q1.totalFeesAmt" FROM OD1.TE130_MER_SETTLE_DTL DTL, OD1.TE133_MER_SETTLE_SOC SOC, OD1.TF809_TRANS_TYPE TT, OD1.TE134_MER_SETTLE_SOC_BTCH_BILL_CD BAT WHERE DTL.GMDL_SETTLE_ID = SOC.GMDL_SETTLE_ID AND SOC.TRANS_TYPE_CD = TT.TRANS_TYPE_CD AND SOC.GMDL_SETTLE_ID = BAT.GMDL_SETTLE_ID AND SOC.GMDL_SETTLE_SOC_ID = BAT.GMDL_SETTLE_SOC_ID AND DTL.SETTLE_SE_NO = '-SETTLE_SE_NO' AND DTL.SE_CHK_DGT_NO = SUBSTR('-SETTLE_SE_NO',9,2) AND DTL.SETTLE_DT BETWEEN '-SETTLE_DT_START' AND '-SETTLE_DT_END' AND TT.TRANS_TYPE_GRP_DS = 'ADJUSTMENT' order by DTL.SETTLE_LOC_AM ASC,DTL.SETTLE_CURR_CD,DTL.BUS_CTR_CD,DTL.SETTLE_DT DESC,DTL.SETTLE_SE_NO,DTL.SETTLE_CHK_NO,SOC.SUBM_SE_NO,TT.TRANS_TYPE_GRP_DS,SOC.SOC_NO
#Single Sort by SettlementDt
SQLAdjList4 = SELECT DTL.SETTLE_CHK_NO as "q1.settlementNbr", DTL.SETTLE_DT as "q1.settlementDt", DTL.SETTLE_SE_NO as "q1.payeeSeNbr", decimal(DTL.SETTLE_LOC_AM/100,12,2) as "q1.settlementAmt", DTL.SETTLE_CURR_CD as "q1.settlementCurrencyCd", SOC.GMDL_SETTLE_ID as "q1.settlementId", SOC.INIT_PRCS_DT as "q1.socDt", SOC.INIT_PRCS_DT as "q1.amxReceivedDt", SOC.SOC_NO as "q1.socNbr", TT.TRANS_TYPE_GRP_DS as "q1.socType", SOC.SUBM_SE_NO as "q1.submittingSeNbr", SOC.SUBM_CURR_CD as "q1.currencyCd", decimal(SOC.SOC_GR_AM/100,12,2) as "q1.submissionAmt", decimal(SOC.SETTLE_CALC_GR_CR_AM/100,12,2) as "q1.totalCreditAmt", decimal((SOC.SETTLE_CALC_GR_DR_AM + SOC.SETTLE_CALC_GR_CR_AM)/100,12,2) as "q1.totalChargeAmt", SOC.CR_ROC_CT as "q1.creditCt", SOC.DR_ROC_CT as "q1.chargeCt", DTL.SETTLE_CURR_CD as "q1.adjustmentCurrencyCd",decimal(SOC.SOC_NET_AM/100,12,2) as "q1.totalAdjustmentAmt", (SOC.DR_ROC_CT + SOC.Cr_ROC_CT) as "q1.totalAdjustmentsCt", decimal(SOC.SOC_DISC_AM/100,12,2) as "q1.totalDiscountAmt", decimal((SOC.SOC_SRVC_FEE_AM + SOC.SOC_DIV_AM + SOC.SOC_DISC_AM)/100,12,2) as "q1.totalFeesAmt" FROM OD1.TE130_MER_SETTLE_DTL DTL, OD1.TE133_MER_SETTLE_SOC SOC, OD1.TF809_TRANS_TYPE TT, OD1.TE134_MER_SETTLE_SOC_BTCH_BILL_CD BAT WHERE DTL.GMDL_SETTLE_ID = SOC.GMDL_SETTLE_ID AND SOC.TRANS_TYPE_CD = TT.TRANS_TYPE_CD AND SOC.GMDL_SETTLE_ID = BAT.GMDL_SETTLE_ID AND SOC.GMDL_SETTLE_SOC_ID = BAT.GMDL_SETTLE_SOC_ID AND DTL.SETTLE_SE_NO = '-SETTLE_SE_NO' AND DTL.SE_CHK_DGT_NO = SUBSTR('-SETTLE_SE_NO',9,2) AND DTL.SETTLE_DT BETWEEN '-SETTLE_DT_START' AND '-SETTLE_DT_END' AND TT.TRANS_TYPE_GRP_DS = 'ADJUSTMENT' order by DTL.SETTLE_DT ASC,DTL.SETTLE_CURR_CD,DTL.BUS_CTR_CD,DTL.SETTLE_SE_NO,DTL.SETTLE_CHK_NO,DTL.SETTLE_LOC_AM,SOC.SUBM_SE_NO,TT.TRANS_TYPE_GRP_DS,SOC.SOC_NO 
#Single Sort by SettlementNumber
SQLAdjList5 = SELECT DTL.SETTLE_CHK_NO as "q1.settlementNbr", DTL.SETTLE_DT as "q1.settlementDt", DTL.SETTLE_SE_NO as "q1.payeeSeNbr", decimal(DTL.SETTLE_LOC_AM/100,12,2) as "q1.settlementAmt", DTL.SETTLE_CURR_CD as "q1.settlementCurrencyCd", SOC.GMDL_SETTLE_ID as "q1.settlementId", SOC.INIT_PRCS_DT as "q1.socDt", SOC.INIT_PRCS_DT as "q1.amxReceivedDt", SOC.SOC_NO as "q1.socNbr", TT.TRANS_TYPE_GRP_DS as "q1.socType", SOC.SUBM_SE_NO as "q1.submittingSeNbr", SOC.SUBM_CURR_CD as "q1.currencyCd", decimal(SOC.SOC_GR_AM/100,12,2) as "q1.submissionAmt", decimal(SOC.SETTLE_CALC_GR_CR_AM/100,12,2) as "q1.totalCreditAmt", decimal((SOC.SETTLE_CALC_GR_DR_AM + SOC.SETTLE_CALC_GR_CR_AM)/100,12,2) as "q1.totalChargeAmt", SOC.CR_ROC_CT as "q1.creditCt", SOC.DR_ROC_CT as "q1.chargeCt", DTL.SETTLE_CURR_CD as "q1.adjustmentCurrencyCd",decimal(SOC.SOC_NET_AM/100,12,2) as "q1.totalAdjustmentAmt", (SOC.DR_ROC_CT + SOC.Cr_ROC_CT) as "q1.totalAdjustmentsCt", decimal(SOC.SOC_DISC_AM/100,12,2) as "q1.totalDiscountAmt", decimal((SOC.SOC_SRVC_FEE_AM + SOC.SOC_DIV_AM + SOC.SOC_DISC_AM)/100,12,2) as "q1.totalFeesAmt" FROM OD1.TE130_MER_SETTLE_DTL DTL, OD1.TE133_MER_SETTLE_SOC SOC, OD1.TF809_TRANS_TYPE TT, OD1.TE134_MER_SETTLE_SOC_BTCH_BILL_CD BAT WHERE DTL.GMDL_SETTLE_ID = SOC.GMDL_SETTLE_ID AND SOC.TRANS_TYPE_CD = TT.TRANS_TYPE_CD AND SOC.GMDL_SETTLE_ID = BAT.GMDL_SETTLE_ID AND SOC.GMDL_SETTLE_SOC_ID = BAT.GMDL_SETTLE_SOC_ID AND DTL.SETTLE_SE_NO = '-SETTLE_SE_NO' AND DTL.SE_CHK_DGT_NO = SUBSTR('-SETTLE_SE_NO',9,2) AND DTL.SETTLE_DT BETWEEN '-SETTLE_DT_START' AND '-SETTLE_DT_END' AND TT.TRANS_TYPE_GRP_DS = 'ADJUSTMENT' order by DTL.SETTLE_CHK_NO ASC,DTL.SETTLE_CURR_CD,DTL.BUS_CTR_CD,DTL.SETTLE_DT DESC,DTL.SETTLE_SE_NO,DTL.SETTLE_LOC_AM,SOC.SUBM_SE_NO,TT.TRANS_TYPE_GRP_DS,SOC.SOC_NO 
#Single Sort by SocNo
SQLAdjList6 = SELECT DTL.SETTLE_CHK_NO as "q1.settlementNbr", DTL.SETTLE_DT as "q1.settlementDt", DTL.SETTLE_SE_NO as "q1.payeeSeNbr", decimal(DTL.SETTLE_LOC_AM/100,12,2) as "q1.settlementAmt", DTL.SETTLE_CURR_CD as "q1.settlementCurrencyCd", SOC.GMDL_SETTLE_ID as "q1.settlementId", SOC.INIT_PRCS_DT as "q1.socDt", SOC.INIT_PRCS_DT as "q1.amxReceivedDt", SOC.SOC_NO as "q1.socNbr", TT.TRANS_TYPE_GRP_DS as "q1.socType", SOC.SUBM_SE_NO as "q1.submittingSeNbr", SOC.SUBM_CURR_CD as "q1.currencyCd", decimal(SOC.SOC_GR_AM/100,12,2) as "q1.submissionAmt", decimal(SOC.SETTLE_CALC_GR_CR_AM/100,12,2) as "q1.totalCreditAmt", decimal((SOC.SETTLE_CALC_GR_DR_AM + SOC.SETTLE_CALC_GR_CR_AM)/100,12,2) as "q1.totalChargeAmt", SOC.CR_ROC_CT as "q1.creditCt", SOC.DR_ROC_CT as "q1.chargeCt", DTL.SETTLE_CURR_CD as "q1.adjustmentCurrencyCd",decimal(SOC.SOC_NET_AM/100,12,2) as "q1.totalAdjustmentAmt", (SOC.DR_ROC_CT + SOC.Cr_ROC_CT) as "q1.totalAdjustmentsCt", decimal(SOC.SOC_DISC_AM/100,12,2) as "q1.totalDiscountAmt", decimal((SOC.SOC_SRVC_FEE_AM + SOC.SOC_DIV_AM + SOC.SOC_DISC_AM)/100,12,2) as "q1.totalFeesAmt" FROM OD1.TE130_MER_SETTLE_DTL DTL, OD1.TE133_MER_SETTLE_SOC SOC, OD1.TF809_TRANS_TYPE TT, OD1.TE134_MER_SETTLE_SOC_BTCH_BILL_CD BAT WHERE DTL.GMDL_SETTLE_ID = SOC.GMDL_SETTLE_ID AND SOC.TRANS_TYPE_CD = TT.TRANS_TYPE_CD AND SOC.GMDL_SETTLE_ID = BAT.GMDL_SETTLE_ID AND SOC.GMDL_SETTLE_SOC_ID = BAT.GMDL_SETTLE_SOC_ID AND DTL.SETTLE_SE_NO = '-SETTLE_SE_NO' AND DTL.SE_CHK_DGT_NO = SUBSTR('-SETTLE_SE_NO',9,2) AND DTL.SETTLE_DT BETWEEN '-SETTLE_DT_START' AND '-SETTLE_DT_END' AND TT.TRANS_TYPE_GRP_DS = 'ADJUSTMENT' order by SOC.SOC_NO ASC,DTL.SETTLE_CURR_CD,DTL.BUS_CTR_CD,DTL.SETTLE_DT DESC,DTL.SETTLE_SE_NO,DTL.SETTLE_CHK_NO,DTL.SETTLE_LOC_AM,SOC.SUBM_SE_NO,TT.TRANS_TYPE_GRP_DS	
#Single Sort by SocType
SQLAdjList7 = SELECT DTL.SETTLE_CHK_NO as "q1.settlementNbr", DTL.SETTLE_DT as "q1.settlementDt", DTL.SETTLE_SE_NO as "q1.payeeSeNbr", decimal(DTL.SETTLE_LOC_AM/100,12,2) as "q1.settlementAmt", DTL.SETTLE_CURR_CD as "q1.settlementCurrencyCd", SOC.GMDL_SETTLE_ID as "q1.settlementId", SOC.INIT_PRCS_DT as "q1.socDt", SOC.INIT_PRCS_DT as "q1.amxReceivedDt", SOC.SOC_NO as "q1.socNbr", TT.TRANS_TYPE_GRP_DS as "q1.socType", SOC.SUBM_SE_NO as "q1.submittingSeNbr", SOC.SUBM_CURR_CD as "q1.currencyCd", decimal(SOC.SOC_GR_AM/100,12,2) as "q1.submissionAmt", decimal(SOC.SETTLE_CALC_GR_CR_AM/100,12,2) as "q1.totalCreditAmt", decimal((SOC.SETTLE_CALC_GR_DR_AM + SOC.SETTLE_CALC_GR_CR_AM)/100,12,2) as "q1.totalChargeAmt", SOC.CR_ROC_CT as "q1.creditCt", SOC.DR_ROC_CT as "q1.chargeCt", DTL.SETTLE_CURR_CD as "q1.adjustmentCurrencyCd",decimal(SOC.SOC_NET_AM/100,12,2) as "q1.totalAdjustmentAmt", (SOC.DR_ROC_CT + SOC.Cr_ROC_CT) as "q1.totalAdjustmentsCt", decimal(SOC.SOC_DISC_AM/100,12,2) as "q1.totalDiscountAmt", decimal((SOC.SOC_SRVC_FEE_AM + SOC.SOC_DIV_AM + SOC.SOC_DISC_AM)/100,12,2) as "q1.totalFeesAmt" FROM OD1.TE130_MER_SETTLE_DTL DTL, OD1.TE133_MER_SETTLE_SOC SOC, OD1.TF809_TRANS_TYPE TT, OD1.TE134_MER_SETTLE_SOC_BTCH_BILL_CD BAT WHERE DTL.GMDL_SETTLE_ID = SOC.GMDL_SETTLE_ID AND SOC.TRANS_TYPE_CD = TT.TRANS_TYPE_CD AND SOC.GMDL_SETTLE_ID = BAT.GMDL_SETTLE_ID AND SOC.GMDL_SETTLE_SOC_ID = BAT.GMDL_SETTLE_SOC_ID AND DTL.SETTLE_SE_NO = '-SETTLE_SE_NO' AND DTL.SE_CHK_DGT_NO = SUBSTR('-SETTLE_SE_NO',9,2) AND DTL.SETTLE_DT BETWEEN '-SETTLE_DT_START' AND '-SETTLE_DT_END' AND TT.TRANS_TYPE_GRP_DS = 'ADJUSTMENT' order by TT.TRANS_TYPE_GRP_DS ASC,DTL.SETTLE_CURR_CD,DTL.BUS_CTR_CD,DTL.SETTLE_DT DESC,DTL.SETTLE_SE_NO,DTL.SETTLE_CHK_NO,DTL.SETTLE_LOC_AM,SOC.SUBM_SE_NO,SOC.SOC_NO

SQLPayeeLocId = select LOC_NO as "q1.payeeLocationId" from OD1.TC501_MER_PRIM_CHAR_DTL where SE_NO = '-SE_NO'
ParamsPayeeLocId = -SE_NO:1030000400
ParamsPayeeLocId_1 = -SE_NO:1040958100
#Queries for  Pending Settlements

SQLQueryPending1 = Select Select SCHED_SETTLE_DT as "q1.settlementDt",(Select  replace(rtrim(replace(varchar((SUM(SOC_NET_AM)/100)),'0', ' ')), ' ', '0') from od1.TE125_MER_PEND_SETTLE_SOC where Settle_SE_No = '-SETTLE_SE_NO') as "q1.settlementAmt", (select case SUBM_CURR_CD when 'CAD' then '002' end from od1.TE125_MER_PEND_SETTLE_SOC where Settle_SE_No = '-SETTLE_SE_NO') as "q1.settlementCurrencyCd", (select distinct(count(GMDL_PEND_SETTLE_ID)) from od1.TE125_MER_PEND_SETTLE_SOC where Settle_SE_No = '-SETTLE_SE_NO') as "q1.distinctSubmittersCt", (Select TOT_ROC_CT from od1.TE125_MER_PEND_SETTLE_SOC where Settle_SE_No = '-SETTLE_SE_NO') as "q1.transactionCt", (select  replace(rtrim(replace(varchar((sum(SOC_GR_AM)/100)),'0', ' ')), ' ', '0') from od1.TE125_MER_PEND_SETTLE_SOC where Settle_SE_No = '-SETTLE_SE_NO' ) as "q1.submissionAmt", (Select replace(rtrim(replace(varchar((sum(SOC_GR_AM)/100)),'0', ' ')), ' ', '0') from od1.TE125_MER_PEND_SETTLE_SOC where Settle_SE_No = '-SETTLE_SE_NO') as "q1.totalChargeAmt", (select replace(rtrim(replace(varchar(((Sum(SOC_GR_AM)-SUM(SOC_NET_AM))/100)),'0', ' ')), ' ', '0') from od1.TE125_MER_PEND_SETTLE_SOC where Settle_SE_No = '-SETTLE_SE_NO' )as "q1.DiscountAmt" from od1.TE125_MER_PEND_SETTLE_SOC where Settle_SE_No = '-SETTLE_SE_NO' and  SCHED_SETTLE_DT between '-Settle_dt_Start' and '-Settle_dt_End'
ParamPending1_1 = -SETTLE_SE_NO:9300993301,-Settle_dt_Start:2014-01-01,-Settle_dt_End:2014-12-31
# Query for Pending Settlement List

 SQLSettlementList1 = Select SETTLE_DT As "q1.settlementDt", SETTLE_CHK_NO as "q1.settlementNbr", SETTLE_SE_NO as "q1.payeeSeNbr",(select replace(rtrim(replace(varchar((SUM(SOC_NET_AM)/100)),'0', ' ')), ' ', '0') from od1.TE133_MER_SETTLE_SOC where GMDL_Settle_ID = A.GMDL_SETTLE_ID) as "q1.settlementAmt", (select case SETTLE_CURR_CD when 'CAD' then 'CAD' end from OD1.TE130_MER_SETTLE_DTL where GMDL_Settle_ID = A.GMDL_SETTLE_ID) as "q1.settlementCurrencyCd", (select Distinct( count(GMDL_Settle_ID)) from od1.TE133_MER_SETTLE_SOC where GMDL_Settle_ID = A.GMDL_SETTLE_ID) as "q1.distinctSubmittersCt", (select replace(rtrim(replace(varchar((SUM(SUBM_GR_AM)/100)),'0', ' ')), ' ', '0') from od1.TE133_MER_SETTLE_SOC where GMDL_Settle_ID = A.GMDL_SETTLE_ID and TRANS_TYPE_CD ='0') as "q1.submissionAmt", (select replace(rtrim(replace(varchar((SUM(SUBM_GR_AM)/100)),'0', ' ')), ' ', '0') from od1.TE133_MER_SETTLE_SOC where GMDL_Settle_ID = A.GMDL_SETTLE_ID and TRANS_TYPE_CD ='0') as "q1.totalChargeAmt" from OD1.TE130_MER_SETTLE_DTL A WHERE SETTLE_SE_NO = '-SETTLE_SE_NO' and Settle_dt between '-SETTLE_DT_START' and '-SETTLE_DT_END' Order By -Sort_Column -Sort_Order fetch  first 3 rows only
 ParamSettleList1_1 = -SETTLE_SE_NO:9300993301,-SETTLE_DT_START:2013-11-01,-SETTLE_DT_END:2013-11-30,-Sort_Order:asc,-Sort_Column:SETTLE_CHK_NO
 ParamSettleList1_2 = -SETTLE_SE_NO:9300993301,-SETTLE_DT_START:2011-11-01,-SETTLE_DT_END:2014-11-30,-Sort_Order:desc,-Sort_Column:SETTLE_CHK_NO
 ParamSettleList1_3 = -SETTLE_SE_NO:9300993301,-SETTLE_DT_START:2011-11-01,-SETTLE_DT_END:2014-11-30,-Sort_Order:asc,-Sort_Column:"q1.settlementAmt"
 ParamSettleList1_4 = -SETTLE_SE_NO:9300993301,-SETTLE_DT_START:2011-11-01,-SETTLE_DT_END:2014-11-30,-Sort_Order:desc,-Sort_Column:"q1.settlementAmt"
 ParamSettleList1_5 = -SETTLE_SE_NO:9300993301,-SETTLE_DT_START:2011-11-01,-SETTLE_DT_END:2014-11-30,-Sort_Order:asc,-Sort_Column:"q1.payeeSeNbr"
 ParamSettleList1_6 = -SETTLE_SE_NO:9300993301,-SETTLE_DT_START:2011-11-01,-SETTLE_DT_END:2014-11-30,-Sort_Order:desc,-Sort_Column:"q1.payeeSeNbr"
 
SQLSettlementList2 = Select SETTLE_DT As "q1.settlementDt", SETTLE_CHK_NO as "q1.settlementNbr", SETTLE_SE_NO as "q1.payeeSeNbr",(select replace(rtrim(replace(varchar((SUM(SOC_NET_AM)/100)),'0', ' ')), ' ', '0') from od1.TE133_MER_SETTLE_SOC where GMDL_Settle_ID = A.GMDL_SETTLE_ID) as "q1.settlementAmt", (select case SETTLE_CURR_CD when 'CAD' then 'CAD' end from OD1.TE130_MER_SETTLE_DTL where GMDL_Settle_ID = A.GMDL_SETTLE_ID) as "q1.settlementCurrencyCd", (select Distinct( count(GMDL_Settle_ID)) from od1.TE133_MER_SETTLE_SOC where GMDL_Settle_ID = A.GMDL_SETTLE_ID) as "q1.distinctSubmittersCt", (select replace(rtrim(replace(varchar((SUM(SUBM_GR_AM)/100)),'0', ' ')), ' ', '0') from od1.TE133_MER_SETTLE_SOC where GMDL_Settle_ID = A.GMDL_SETTLE_ID and TRANS_TYPE_CD ='0') as "q1.submissionAmt", (select replace(rtrim(replace(varchar((SUM(SUBM_GR_AM)/100)),'0', ' ')), ' ', '0') from od1.TE133_MER_SETTLE_SOC where GMDL_Settle_ID = A.GMDL_SETTLE_ID and TRANS_TYPE_CD ='0') as "q1.totalChargeAmt" from OD1.TE130_MER_SETTLE_DTL A WHERE SETTLE_SE_NO = '-SETTLE_SE_NO' and Settle_dt between '-SETTLE_DT_START' and '-SETTLE_DT_END' Order By -Sort_Column,SETTLE_CHK_NO -Sort_Order fetch  first 3 rows only 
 ParamSettleList2_1 = -SETTLE_SE_NO:9300993301,-SETTLE_DT_START:2011-11-01,-SETTLE_DT_END:2014-11-30,-Sort_Order:asc,-Sort_Column:"q1.settlementCurrencyCd"
 ParamSettleList2_2 = -SETTLE_SE_NO:9300993301,-SETTLE_DT_START:2011-11-01,-SETTLE_DT_END:2014-11-30,-Sort_Order:desc,-Sort_Column:"q1.settlementCurrencyCd"
 ParamSettleList2_3 = -SETTLE_SE_NO:9300993301,-SETTLE_DT_START:2011-11-01,-SETTLE_DT_END:2014-11-30,-Sort_Order:asc,-Sort_Column:"q1.settlementDt"
 ParamSettleList2_4 = -SETTLE_SE_NO:9300993301,-SETTLE_DT_START:2011-11-01,-SETTLE_DT_END:2014-11-30,-Sort_Order:desc,-Sort_Column:"q1.settlementDt"
 SQLSettlementList3 = Select SETTLE_DT As "q1.settlementDt", A.SETTLE_CHK_NO as "q1.settlementNbr", A.SETTLE_SE_NO as "q1.payeeSeNbr", decimal(A.SETTLE_LOC_AM/100,12,2) as "q1.settlementAmt", A.SETTLE_CURR_CD as "q1.settlementCurrencyCd",(select Distinct( count(GMDL_Settle_ID)) from od1.TE133_MER_SETTLE_SOC where GMDL_Settle_ID = A.GMDL_SETTLE_ID) as "q1.distinctSubmittersCt", decimal(SOC.SUBM_GR_AM/100,12,2) as "q1.submissionAmt", decimal(SOC.SUBM_GR_AM/100,12,2) as "q1.totalChargeAmt" from OD1.TE130_MER_SETTLE_DTL A, OD1.TE133_MER_SETTLE_SOC SOC WHERE A.SETTLE_SE_NO = '-SETTLE_SE_NO' and A.SE_CHK_DGT_NO = substr('-SETTLE_SE_NO',9,2) and SOC.SE_CHK_DGT_NO = substr('-SETTLE_SE_NO',9,2) and A.Settle_dt between '--SETTLE_DT_START' and '-SETTLE_DT_END' and A.GMDL_SETTLE_ID = SOC.GMDL_SETTLE_ID Order by A.SETTLE_DT DESC, A.SETTLE_CURR_CD, SOC.SUBM_CURR_CD, A.BUS_CTR_CD, A.SETTLE_CHK_NO, A.SETTLE_SE_NO, A.SETTLE_LOC_AM fetch first 3 rows only 
 ParamSettleList3_1 = -SETTLE_SE_NO:9300993301,-SETTLE_DT_START:2011-11-01,-SETTLE_DT_END:2014-11-30
 SQLSettlementList4 = Select ,SETTLE_DT As "q1.settlementDt", A.SETTLE_CHK_NO as "q1.settlementNbr", A.SETTLE_SE_NO as "q1.payeeSeNbr", decimal(A.SETTLE_LOC_AM/100,12,2) as "q1.settlementAmt", A.SETTLE_CURR_CD as "q1.settlementCurrencyCd",(select Distinct( count(GMDL_Settle_ID)) from od1.TE133_MER_SETTLE_SOC where GMDL_Settle_ID = A.GMDL_SETTLE_ID) as "q1.distinctSubmittersCt", decimal(SOC.SUBM_GR_AM/100,12,2) as "q1.submissionAmt", decimal(SOC.SUBM_GR_AM/100,12,2) as "q1.totalChargeAmt" from OD1.TE130_MER_SETTLE_DTL A, OD1.TE133_MER_SETTLE_SOC SOC WHERE A.SETTLE_SE_NO = '-SETTLE_SE_NO' and A.SE_CHK_DGT_NO = substr('-SETTLE_SE_NO',9,2) and SOC.SE_CHK_DGT_NO = substr('-SETTLE_SE_NO',9,2) and A.Settle_dt between '--SETTLE_DT_START' and '-SETTLE_DT_END' and A.GMDL_SETTLE_ID = SOC.GMDL_SETTLE_ID Order by A.SETTLE_DT , A.SETTLE_CURR_CD, SOC.SUBM_CURR_CD, A.BUS_CTR_CD, A.SETTLE_CHK_NO, A.SETTLE_SE_NO, A.SETTLE_LOC_AM fetch first 3 rows only 

#sort by setlement Nbr
SQLSettlementList6 = Select SETTLE_DT As "q1.settlementDt", A.SETTLE_CHK_NO as "q1.settlementNbr", A.SETTLE_SE_NO as "q1.payeeSeNbr", decimal(A.SETTLE_LOC_AM/100,12,2) as "q1.settlementAmt", A.SETTLE_CURR_CD as "q1.settlementCurrencyCd",(select count(Distinct(SUBM_SE_NO)) from od1.TE133_MER_SETTLE_SOC where GMDL_Settle_ID = A.GMDL_SETTLE_ID) as "q1.distinctSubmittersCt", COALESCE((case replace(rtrim(replace(varchar(((select sum(SUBM_GR_AM) from  OD1.TE133_MER_SETTLE_SOC where GMDL_SETTLE_ID =   A.GMDL_SETTLE_ID AND TRANS_TYPE_CD ='0' )/100)), '0', ' ')), ' ', '0') when '0.' then '0.0' else  replace(rtrim(replace(varchar(((select sum(SUBM_GR_AM) from  OD1.TE133_MER_SETTLE_SOC where GMDL_SETTLE_ID =   A.GMDL_SETTLE_ID AND TRANS_TYPE_CD ='0')/100)), '0', ' ')), ' ', '0') end), '0.0' ) AS "q1.submissionAmt", COALESCE((case replace(rtrim(replace(varchar(((select sum(SETTLE_CALC_GR_DR_AM) from  OD1.TE133_MER_SETTLE_SOC where GMDL_SETTLE_ID =   A.GMDL_SETTLE_ID AND TRANS_TYPE_CD ='0' )/100)), '0', ' ')), ' ', '0') when '0.' then '0.0' else  replace(rtrim(replace(varchar(((select sum(SETTLE_CALC_GR_DR_AM) from  OD1.TE133_MER_SETTLE_SOC where GMDL_SETTLE_ID =   A.GMDL_SETTLE_ID AND TRANS_TYPE_CD ='0' )/100)), '0', ' ')), ' ', '0') end), '0.0' ) AS "q1.totalChargeAmt" from OD1.TE130_MER_SETTLE_DTL A, OD1.TE133_MER_SETTLE_SOC SOC WHERE A.SETTLE_SE_NO = '-SETTLE_SE_NO' and A.SE_CHK_DGT_NO = substr('-SETTLE_SE_NO',9,2) and SOC.SE_CHK_DGT_NO = substr('-SETTLE_SE_NO',9,2) and A.Settle_dt between '-SETTLE_DT_START' and '-SETTLE_DT_END' and A.GMDL_SETTLE_ID = SOC.GMDL_SETTLE_ID Order by A.SETTLE_CHK_NO -SORT_ORDER, A.SETTLE_CURR_CD, SOC.SUBM_CURR_CD, A.BUS_CTR_CD, A.SETTLE_DT, A.SETTLE_SE_NO, A.SETTLE_LOC_AM fetch first 3 rows only 
ParamSettleList6_1 = -SETTLE_SE_NO:9300993301,-SETTLE_DT_START:2013-11-01,-SETTLE_DT_END:2013-11-30,-SORT_ORDER:ASC
ParamSettleList6_2 = -SETTLE_SE_NO:9300993301,-SETTLE_DT_START:2013-11-01,-SETTLE_DT_END:2013-11-30,-SORT_ORDER:DESC
 
#sort by settlement Amt
SQLSettlementList7 = Select SETTLE_DT As "q1.settlementDt", A.SETTLE_CHK_NO as "q1.settlementNbr", A.SETTLE_SE_NO as "q1.payeeSeNbr", decimal(A.SETTLE_LOC_AM/100,12,2) as "q1.settlementAmt", A.SETTLE_CURR_CD as "q1.settlementCurrencyCd",(select count(Distinct(SUBM_SE_NO)) from od1.TE133_MER_SETTLE_SOC where GMDL_Settle_ID = A.GMDL_SETTLE_ID) as "q1.distinctSubmittersCt", COALESCE((case replace(rtrim(replace(varchar(((select sum(SUBM_GR_AM) from  OD1.TE133_MER_SETTLE_SOC where GMDL_SETTLE_ID =   A.GMDL_SETTLE_ID AND TRANS_TYPE_CD ='0' )/100)), '0', ' ')), ' ', '0') when '0.' then '0.0' else  replace(rtrim(replace(varchar(((select sum(SUBM_GR_AM) from  OD1.TE133_MER_SETTLE_SOC where GMDL_SETTLE_ID =   A.GMDL_SETTLE_ID AND TRANS_TYPE_CD ='0')/100)), '0', ' ')), ' ', '0') end), '0.0' ) AS "q1.submissionAmt", COALESCE((case replace(rtrim(replace(varchar(((select sum(SETTLE_CALC_GR_DR_AM) from  OD1.TE133_MER_SETTLE_SOC where GMDL_SETTLE_ID =   A.GMDL_SETTLE_ID AND TRANS_TYPE_CD ='0' )/100)), '0', ' ')), ' ', '0') when '0.' then '0.0' else  replace(rtrim(replace(varchar(((select sum(SETTLE_CALC_GR_DR_AM) from  OD1.TE133_MER_SETTLE_SOC where GMDL_SETTLE_ID =   A.GMDL_SETTLE_ID AND TRANS_TYPE_CD ='0' )/100)), '0', ' ')), ' ', '0') end), '0.0' ) AS "q1.totalChargeAmt" from OD1.TE130_MER_SETTLE_DTL A, OD1.TE133_MER_SETTLE_SOC SOC WHERE A.SETTLE_SE_NO = '-SETTLE_SE_NO' and A.SE_CHK_DGT_NO = substr('-SETTLE_SE_NO',9,2) and SOC.SE_CHK_DGT_NO = substr('-SETTLE_SE_NO',9,2) and A.Settle_dt between '-SETTLE_DT_START' and '-SETTLE_DT_END' and A.GMDL_SETTLE_ID = SOC.GMDL_SETTLE_ID Order by A.SETTLE_LOC_AM -SORT_ORDER, A.SETTLE_CURR_CD, SOC.SUBM_CURR_CD, A.BUS_CTR_CD, A.SETTLE_DT, A.SETTLE_CHK_NO, A.SETTLE_SE_NO fetch first 3 rows only 

#sort by payee se no
SQLSettlementList8 = Select SETTLE_DT As "q1.settlementDt", A.SETTLE_CHK_NO as "q1.settlementNbr", A.SETTLE_SE_NO as "q1.payeeSeNbr", decimal(A.SETTLE_LOC_AM/100,12,2) as "q1.settlementAmt", A.SETTLE_CURR_CD as "q1.settlementCurrencyCd",(select count(Distinct(SUBM_SE_NO)) from od1.TE133_MER_SETTLE_SOC where GMDL_Settle_ID = A.GMDL_SETTLE_ID) as "q1.distinctSubmittersCt", COALESCE((case replace(rtrim(replace(varchar(((select sum(SUBM_GR_AM) from  OD1.TE133_MER_SETTLE_SOC where GMDL_SETTLE_ID =   A.GMDL_SETTLE_ID AND TRANS_TYPE_CD ='0' )/100)), '0', ' ')), ' ', '0') when '0.' then '0.0' else  replace(rtrim(replace(varchar(((select sum(SUBM_GR_AM) from  OD1.TE133_MER_SETTLE_SOC where GMDL_SETTLE_ID =   A.GMDL_SETTLE_ID AND TRANS_TYPE_CD ='0')/100)), '0', ' ')), ' ', '0') end), '0.0' ) AS "q1.submissionAmt", COALESCE((case replace(rtrim(replace(varchar(((select sum(SETTLE_CALC_GR_DR_AM) from  OD1.TE133_MER_SETTLE_SOC where GMDL_SETTLE_ID =   A.GMDL_SETTLE_ID AND TRANS_TYPE_CD ='0' )/100)), '0', ' ')), ' ', '0') when '0.' then '0.0' else  replace(rtrim(replace(varchar(((select sum(SETTLE_CALC_GR_DR_AM) from  OD1.TE133_MER_SETTLE_SOC where GMDL_SETTLE_ID =   A.GMDL_SETTLE_ID AND TRANS_TYPE_CD ='0' )/100)), '0', ' ')), ' ', '0') end), '0.0' ) AS "q1.totalChargeAmt" from OD1.TE130_MER_SETTLE_DTL A, OD1.TE133_MER_SETTLE_SOC SOC WHERE A.SETTLE_SE_NO = '-SETTLE_SE_NO' and A.SE_CHK_DGT_NO = substr('-SETTLE_SE_NO',9,2) and SOC.SE_CHK_DGT_NO = substr('-SETTLE_SE_NO',9,2) and A.Settle_dt between '-SETTLE_DT_START' and '-SETTLE_DT_END' and A.GMDL_SETTLE_ID = SOC.GMDL_SETTLE_ID Order by A.SETTLE_SE_NO -SORT_ORDER, A.SETTLE_CURR_CD, SOC.SUBM_CURR_CD, A.BUS_CTR_CD, A.SETTLE_DT, A.SETTLE_CHK_NO, A.SETTLE_LOC_AM fetch first 3 rows only 

#sort by settlement currency
SQLSettlementList9 = Select SETTLE_DT As "q1.settlementDt", A.SETTLE_CHK_NO as "q1.settlementNbr", A.SETTLE_SE_NO as "q1.payeeSeNbr", decimal(A.SETTLE_LOC_AM/100,12,2) as "q1.settlementAmt", A.SETTLE_CURR_CD as "q1.settlementCurrencyCd",(select count(Distinct(SUBM_SE_NO)) from od1.TE133_MER_SETTLE_SOC where GMDL_Settle_ID = A.GMDL_SETTLE_ID) as "q1.distinctSubmittersCt", COALESCE((case replace(rtrim(replace(varchar(((select sum(SUBM_GR_AM) from  OD1.TE133_MER_SETTLE_SOC where GMDL_SETTLE_ID =   A.GMDL_SETTLE_ID AND TRANS_TYPE_CD ='0' )/100)), '0', ' ')), ' ', '0') when '0.' then '0.0' else  replace(rtrim(replace(varchar(((select sum(SUBM_GR_AM) from  OD1.TE133_MER_SETTLE_SOC where GMDL_SETTLE_ID =   A.GMDL_SETTLE_ID AND TRANS_TYPE_CD ='0')/100)), '0', ' ')), ' ', '0') end), '0.0' ) AS "q1.submissionAmt", COALESCE((case replace(rtrim(replace(varchar(((select sum(SETTLE_CALC_GR_DR_AM) from  OD1.TE133_MER_SETTLE_SOC where GMDL_SETTLE_ID =   A.GMDL_SETTLE_ID AND TRANS_TYPE_CD ='0' )/100)), '0', ' ')), ' ', '0') when '0.' then '0.0' else  replace(rtrim(replace(varchar(((select sum(SETTLE_CALC_GR_DR_AM) from  OD1.TE133_MER_SETTLE_SOC where GMDL_SETTLE_ID =   A.GMDL_SETTLE_ID AND TRANS_TYPE_CD ='0' )/100)), '0', ' ')), ' ', '0') end), '0.0' ) AS "q1.totalChargeAmt" from OD1.TE130_MER_SETTLE_DTL A, OD1.TE133_MER_SETTLE_SOC SOC WHERE A.SETTLE_SE_NO = '-SETTLE_SE_NO' and A.SE_CHK_DGT_NO = substr('-SETTLE_SE_NO',9,2) and SOC.SE_CHK_DGT_NO = substr('-SETTLE_SE_NO',9,2) and A.Settle_dt between '-SETTLE_DT_START' and '-SETTLE_DT_END' and A.GMDL_SETTLE_ID = SOC.GMDL_SETTLE_ID Order by A.SETTLE_CURR_CD -SORT_ORDER, SOC.SUBM_CURR_CD, A.BUS_CTR_CD, A.SETTLE_DT, A.SETTLE_CHK_NO, A.SETTLE_SE_NO, A.SETTLE_LOC_AM fetch first 3 rows only 
 
#sort by settlement date
SQLSettlementList10 = Select SETTLE_DT As "q1.settlementDt", A.SETTLE_CHK_NO as "q1.settlementNbr", A.SETTLE_SE_NO as "q1.payeeSeNbr", decimal(A.SETTLE_LOC_AM/100,12,2) as "q1.settlementAmt", A.SETTLE_CURR_CD as "q1.settlementCurrencyCd",(select count(Distinct(SUBM_SE_NO)) from od1.TE133_MER_SETTLE_SOC where GMDL_Settle_ID = A.GMDL_SETTLE_ID) as "q1.distinctSubmittersCt", COALESCE((case replace(rtrim(replace(varchar(((select sum(SUBM_GR_AM) from  OD1.TE133_MER_SETTLE_SOC where GMDL_SETTLE_ID =   A.GMDL_SETTLE_ID AND TRANS_TYPE_CD ='0' )/100)), '0', ' ')), ' ', '0') when '0.' then '0.0' else  replace(rtrim(replace(varchar(((select sum(SUBM_GR_AM) from  OD1.TE133_MER_SETTLE_SOC where GMDL_SETTLE_ID =   A.GMDL_SETTLE_ID AND TRANS_TYPE_CD ='0')/100)), '0', ' ')), ' ', '0') end), '0.0' ) AS "q1.submissionAmt", COALESCE((case replace(rtrim(replace(varchar(((select sum(SETTLE_CALC_GR_DR_AM) from  OD1.TE133_MER_SETTLE_SOC where GMDL_SETTLE_ID =   A.GMDL_SETTLE_ID AND TRANS_TYPE_CD ='0' )/100)), '0', ' ')), ' ', '0') when '0.' then '0.0' else  replace(rtrim(replace(varchar(((select sum(SETTLE_CALC_GR_DR_AM) from  OD1.TE133_MER_SETTLE_SOC where GMDL_SETTLE_ID =   A.GMDL_SETTLE_ID AND TRANS_TYPE_CD ='0' )/100)), '0', ' ')), ' ', '0') end), '0.0' ) AS "q1.totalChargeAmt" from OD1.TE130_MER_SETTLE_DTL A, OD1.TE133_MER_SETTLE_SOC SOC WHERE A.SETTLE_SE_NO = '-SETTLE_SE_NO' and A.SE_CHK_DGT_NO = substr('-SETTLE_SE_NO',9,2) and SOC.SE_CHK_DGT_NO = substr('-SETTLE_SE_NO',9,2) and A.Settle_dt between '-SETTLE_DT_START' and '-SETTLE_DT_END' and A.GMDL_SETTLE_ID = SOC.GMDL_SETTLE_ID Order by A.SETTLE_DT -SORT_ORDER, A.SETTLE_CURR_CD, SOC.SUBM_CURR_CD, A.BUS_CTR_CD, A.SETTLE_CHK_NO, A.SETTLE_SE_NO, A.SETTLE_LOC_AM fetch first 3 rows only 
 
#sort by settlement Nbr
SQLSettlementList11 = Select SETTLE_DT As "q1.settlementDt", A.SETTLE_CHK_NO as "q1.settlementNbr", A.SETTLE_SE_NO as "q1.payeeSeNbr", decimal(A.SETTLE_LOC_AM/100,12,2) as "q1.settlementAmt", A.SETTLE_CURR_CD as "q1.settlementCurrencyCd",(select count(Distinct(SUBM_SE_NO)) from od1.TE133_MER_SETTLE_SOC where GMDL_Settle_ID = A.GMDL_SETTLE_ID) as "q1.distinctSubmittersCt", COALESCE((case replace(rtrim(replace(varchar(((select sum(SUBM_GR_AM) from  OD1.TE133_MER_SETTLE_SOC where GMDL_SETTLE_ID =   A.GMDL_SETTLE_ID AND TRANS_TYPE_CD ='0' )/100)), '0', ' ')), ' ', '0') when '0.' then '0.0' else  replace(rtrim(replace(varchar(((select sum(SUBM_GR_AM) from  OD1.TE133_MER_SETTLE_SOC where GMDL_SETTLE_ID =   A.GMDL_SETTLE_ID AND TRANS_TYPE_CD ='0')/100)), '0', ' ')), ' ', '0') end), '0.0' ) AS "q1.submissionAmt", COALESCE((case replace(rtrim(replace(varchar(((select sum(SETTLE_CALC_GR_DR_AM) from  OD1.TE133_MER_SETTLE_SOC where GMDL_SETTLE_ID =   A.GMDL_SETTLE_ID AND TRANS_TYPE_CD ='0' )/100)), '0', ' ')), ' ', '0') when '0.' then '0.0' else  replace(rtrim(replace(varchar(((select sum(SETTLE_CALC_GR_DR_AM) from  OD1.TE133_MER_SETTLE_SOC where GMDL_SETTLE_ID =   A.GMDL_SETTLE_ID AND TRANS_TYPE_CD ='0' )/100)), '0', ' ')), ' ', '0') end), '0.0' ) AS "q1.totalChargeAmt" from OD1.TE130_MER_SETTLE_DTL A, OD1.TE133_MER_SETTLE_SOC SOC WHERE A.SETTLE_SE_NO = '-SETTLE_SE_NO' and A.SE_CHK_DGT_NO = substr('-SETTLE_SE_NO',9,2) and SOC.SE_CHK_DGT_NO = substr('-SETTLE_SE_NO',9,2) and A.Settle_dt between '-SETTLE_DT_START' and '-SETTLE_DT_END' and A.GMDL_SETTLE_ID = SOC.GMDL_SETTLE_ID Order by A.SETTLE_CHK_NO -SORT_ORDER, A.SETTLE_CURR_CD, SOC.SUBM_CURR_CD, A.BUS_CTR_CD, A.SETTLE_DT, A.SETTLE_SE_NO, A.SETTLE_LOC_AM fetch first 3 rows only 

 
 #SubmissionsList 
 ##Single Sort by SocType
SQLSubmList1 = SELECT DTL.SETTLE_CHK_NO as "q1.settlementNbr", DTL.SETTLE_DT as "q1.settlementDt", DTL.SETTLE_SE_NO as "q1.payeeSeNbr", decimal(DTL.SETTLE_LOC_AM/100,12,2) as "q1.settlementAmt", DTL.SETTLE_CURR_CD as "q1.settlementCurrencyCd", SOC.GMDL_SETTLE_ID as "q1.settlementId", SOC.INIT_PRCS_DT as "q1.socDt", SOC.INIT_PRCS_DT as "q1.amxReceivedDt", SOC.SOC_NO as "q1.socNbr", TT.TRANS_TYPE_GRP_DS as "q1.socType", SOC.SUBM_SE_NO as "q1.submittingSeNbr", SOC.SUBM_CURR_CD as "q1.currencyCd", decimal(SOC.SOC_GR_AM/100,12,2) as "q1.submissionAmt", decimal(SOC.SETTLE_CALC_GR_CR_AM/100,12,2) as "q1.totalCreditAmt", decimal((SOC.SETTLE_CALC_GR_DR_AM + SOC.SETTLE_CALC_GR_CR_AM)/100,12,2) as "q1.totalChargeAmt", SOC.CR_ROC_CT as "q1.creditCt", SOC.DR_ROC_CT as "q1.chargeCt", DTL.SETTLE_CURR_CD as "q1.adjustmentCurrencyCd",decimal(SOC.SOC_NET_AM/100,12,2) as "q1.totalAdjustmentAmt", (SOC.DR_ROC_CT + SOC.Cr_ROC_CT) as "q1.totalAdjustmentsCt", decimal(SOC.SOC_DISC_AM/100,12,2) as "q1.totalDiscountAmt", decimal((SOC.SOC_SRVC_FEE_AM + SOC.SOC_DIV_AM + SOC.SOC_DISC_AM)/100,12,2) as "q1.totalFeesAmt" FROM OD1.TE130_MER_SETTLE_DTL DTL, OD1.TE133_MER_SETTLE_SOC SOC, OD1.TF809_TRANS_TYPE TT, OD1.TE134_MER_SETTLE_SOC_BTCH_BILL_CD BAT WHERE DTL.GMDL_SETTLE_ID = SOC.GMDL_SETTLE_ID AND SOC.TRANS_TYPE_CD = TT.TRANS_TYPE_CD AND SOC.GMDL_SETTLE_ID = BAT.GMDL_SETTLE_ID AND SOC.GMDL_SETTLE_SOC_ID = BAT.GMDL_SETTLE_SOC_ID AND DTL.SETTLE_SE_NO = '-SETTLE_SE_NO' AND DTL.SE_CHK_DGT_NO = SUBSTR('-SETTLE_SE_NO',9,2) AND DTL.SETTLE_DT BETWEEN '-SETTLE_DT_START' AND '-SETTLE_DT_END' AND (TT.TRANS_TYPE_GRP_DS = 'SUBMISSION' OR TT.TRANS_TYPE_GRP_DS = 'OTHER-FEE-REV') order by TT.TRANS_TYPE_GRP_DS ASC,DTL.SETTLE_CURR_CD,DTL.BUS_CTR_CD,DTL.SETTLE_DT DESC,DTL.SETTLE_SE_NO,DTL.SETTLE_CHK_NO,DTL.SETTLE_LOC_AM,SOC.SUBM_SE_NO,SOC.SOC_NO
ParamSubmList1 = -SETTLE_SE_NO:1040958100,-SETTLE_DT_START:2013-09-01,-SETTLE_DT_END:2013-09-03
ParamSubmList1_1 = -SETTLE_SE_NO:9301230901,-SETTLE_DT_START:2013-09-01,-SETTLE_DT_END:2013-09-03
ParamSubmList1_2 = -SETTLE_SE_NO:9301230901,-SETTLE_DT_START:2013-09-01,-SETTLE_DT_END:2013-09-03
##Single SE_NO Sort by Subm se nbr
SQLSubmList2 = SELECT DTL.SETTLE_CHK_NO as "q1.settlementNbr", DTL.SETTLE_DT as "q1.settlementDt", DTL.SETTLE_SE_NO as "q1.payeeSeNbr", decimal(DTL.SETTLE_LOC_AM/100,12,2) as "q1.settlementAmt", DTL.SETTLE_CURR_CD as "q1.settlementCurrencyCd", SOC.GMDL_SETTLE_ID as "q1.settlementId", SOC.INIT_PRCS_DT as "q1.socDt", SOC.INIT_PRCS_DT as "q1.amxReceivedDt", SOC.SOC_NO as "q1.socNbr", TT.TRANS_TYPE_GRP_DS as "q1.socType", SOC.SUBM_SE_NO as "q1.submittingSeNbr", SOC.SUBM_CURR_CD as "q1.currencyCd", decimal(SOC.SOC_GR_AM/100,12,2) as "q1.submissionAmt", decimal(SOC.SETTLE_CALC_GR_CR_AM/100,12,2) as "q1.totalCreditAmt", decimal((SOC.SETTLE_CALC_GR_DR_AM + SOC.SETTLE_CALC_GR_CR_AM)/100,12,2) as "q1.totalChargeAmt", SOC.CR_ROC_CT as "q1.creditCt", SOC.DR_ROC_CT as "q1.chargeCt", DTL.SETTLE_CURR_CD as "q1.adjustmentCurrencyCd",decimal(SOC.SOC_NET_AM/100,12,2) as "q1.totalAdjustmentAmt", (SOC.DR_ROC_CT + SOC.Cr_ROC_CT) as "q1.totalAdjustmentsCt", decimal(SOC.SOC_DISC_AM/100,12,2) as "q1.totalDiscountAmt", decimal((SOC.SOC_SRVC_FEE_AM + SOC.SOC_DIV_AM + SOC.SOC_DISC_AM)/100,12,2) as "q1.totalFeesAmt" FROM OD1.TE130_MER_SETTLE_DTL DTL, OD1.TE133_MER_SETTLE_SOC SOC, OD1.TF809_TRANS_TYPE TT, OD1.TE134_MER_SETTLE_SOC_BTCH_BILL_CD BAT WHERE DTL.GMDL_SETTLE_ID = SOC.GMDL_SETTLE_ID AND SOC.TRANS_TYPE_CD = TT.TRANS_TYPE_CD AND SOC.GMDL_SETTLE_ID = BAT.GMDL_SETTLE_ID AND SOC.GMDL_SETTLE_SOC_ID = BAT.GMDL_SETTLE_SOC_ID AND DTL.SETTLE_SE_NO = '-SETTLE_SE_NO' AND DTL.SE_CHK_DGT_NO = SUBSTR('-SETTLE_SE_NO',9,2) AND DTL.SETTLE_DT BETWEEN '-SETTLE_DT_START' AND '-SETTLE_DT_END' AND (TT.TRANS_TYPE_GRP_DS = 'SUBMISSION' OR TT.TRANS_TYPE_GRP_DS = 'OTHER-FEE-REV') order by SOC.SUBM_SE_NO ASC,DTL.SETTLE_CURR_CD, DTL.BUS_CTR_CD,DTL.SETTLE_DT,DTL.SETTLE_SE_NO,DTL.SETTLE_CHK_NO,DTL.SETTLE_LOC_AM,TT.TRANS_TYPE_GRP_DS,SOC.SOC_NO    
##Single Sort by SettlementAmt
SQLSubmList3 = SELECT DTL.SETTLE_CHK_NO as "q1.settlementNbr", DTL.SETTLE_DT as "q1.settlementDt", DTL.SETTLE_SE_NO as "q1.payeeSeNbr", decimal(DTL.SETTLE_LOC_AM/100,12,2) as "q1.settlementAmt", DTL.SETTLE_CURR_CD as "q1.settlementCurrencyCd", SOC.GMDL_SETTLE_ID as "q1.settlementId", SOC.INIT_PRCS_DT as "q1.socDt", SOC.INIT_PRCS_DT as "q1.amxReceivedDt", SOC.SOC_NO as "q1.socNbr", TT.TRANS_TYPE_GRP_DS as "q1.socType", SOC.SUBM_SE_NO as "q1.submittingSeNbr", SOC.SUBM_CURR_CD as "q1.currencyCd", decimal(SOC.SOC_GR_AM/100,12,2) as "q1.submissionAmt", decimal(SOC.SETTLE_CALC_GR_CR_AM/100,12,2) as "q1.totalCreditAmt", decimal((SOC.SETTLE_CALC_GR_DR_AM + SOC.SETTLE_CALC_GR_CR_AM)/100,12,2) as "q1.totalChargeAmt", SOC.CR_ROC_CT as "q1.creditCt", SOC.DR_ROC_CT as "q1.chargeCt", DTL.SETTLE_CURR_CD as "q1.adjustmentCurrencyCd",decimal(SOC.SOC_NET_AM/100,12,2) as "q1.totalAdjustmentAmt", (SOC.DR_ROC_CT + SOC.Cr_ROC_CT) as "q1.totalAdjustmentsCt", decimal(SOC.SOC_DISC_AM/100,12,2) as "q1.totalDiscountAmt", decimal((SOC.SOC_SRVC_FEE_AM + SOC.SOC_DIV_AM + SOC.SOC_DISC_AM)/100,12,2) as "q1.totalFeesAmt" FROM OD1.TE130_MER_SETTLE_DTL DTL, OD1.TE133_MER_SETTLE_SOC SOC, OD1.TF809_TRANS_TYPE TT, OD1.TE134_MER_SETTLE_SOC_BTCH_BILL_CD BAT WHERE DTL.GMDL_SETTLE_ID = SOC.GMDL_SETTLE_ID AND SOC.TRANS_TYPE_CD = TT.TRANS_TYPE_CD AND SOC.GMDL_SETTLE_ID = BAT.GMDL_SETTLE_ID AND SOC.GMDL_SETTLE_SOC_ID = BAT.GMDL_SETTLE_SOC_ID AND DTL.SETTLE_SE_NO = '-SETTLE_SE_NO' AND DTL.SE_CHK_DGT_NO = SUBSTR('-SETTLE_SE_NO',9,2) AND DTL.SETTLE_DT BETWEEN '-SETTLE_DT_START' AND '-SETTLE_DT_END' AND (TT.TRANS_TYPE_GRP_DS = 'SUBMISSION' OR TT.TRANS_TYPE_GRP_DS = 'OTHER-FEE-REV') order by DTL.SETTLE_LOC_AM ASC,DTL.SETTLE_CURR_CD,DTL.BUS_CTR_CD,DTL.SETTLE_DT DESC,DTL.SETTLE_SE_NO,DTL.SETTLE_CHK_NO,SOC.SUBM_SE_NO,TT.TRANS_TYPE_GRP_DS,SOC.SOC_NO
##Single Sort by SettlementDt
SQLSubmList4 = SELECT DTL.SETTLE_CHK_NO as "q1.settlementNbr", DTL.SETTLE_DT as "q1.settlementDt", DTL.SETTLE_SE_NO as "q1.payeeSeNbr", decimal(DTL.SETTLE_LOC_AM/100,12,2) as "q1.settlementAmt", DTL.SETTLE_CURR_CD as "q1.settlementCurrencyCd", SOC.GMDL_SETTLE_ID as "q1.settlementId", SOC.INIT_PRCS_DT as "q1.socDt", SOC.INIT_PRCS_DT as "q1.amxReceivedDt", SOC.SOC_NO as "q1.socNbr", TT.TRANS_TYPE_GRP_DS as "q1.socType", SOC.SUBM_SE_NO as "q1.submittingSeNbr", SOC.SUBM_CURR_CD as "q1.currencyCd", decimal(SOC.SOC_GR_AM/100,12,2) as "q1.submissionAmt", decimal(SOC.SETTLE_CALC_GR_CR_AM/100,12,2) as "q1.totalCreditAmt", decimal((SOC.SETTLE_CALC_GR_DR_AM + SOC.SETTLE_CALC_GR_CR_AM)/100,12,2) as "q1.totalChargeAmt", SOC.CR_ROC_CT as "q1.creditCt", SOC.DR_ROC_CT as "q1.chargeCt", DTL.SETTLE_CURR_CD as "q1.adjustmentCurrencyCd",decimal(SOC.SOC_NET_AM/100,12,2) as "q1.totalAdjustmentAmt", (SOC.DR_ROC_CT + SOC.Cr_ROC_CT) as "q1.totalAdjustmentsCt", decimal(SOC.SOC_DISC_AM/100,12,2) as "q1.totalDiscountAmt", decimal((SOC.SOC_SRVC_FEE_AM + SOC.SOC_DIV_AM + SOC.SOC_DISC_AM)/100,12,2) as "q1.totalFeesAmt" FROM OD1.TE130_MER_SETTLE_DTL DTL, OD1.TE133_MER_SETTLE_SOC SOC, OD1.TF809_TRANS_TYPE TT, OD1.TE134_MER_SETTLE_SOC_BTCH_BILL_CD BAT WHERE DTL.GMDL_SETTLE_ID = SOC.GMDL_SETTLE_ID AND SOC.TRANS_TYPE_CD = TT.TRANS_TYPE_CD AND SOC.GMDL_SETTLE_ID = BAT.GMDL_SETTLE_ID AND SOC.GMDL_SETTLE_SOC_ID = BAT.GMDL_SETTLE_SOC_ID AND DTL.SETTLE_SE_NO = '-SETTLE_SE_NO' AND DTL.SE_CHK_DGT_NO = SUBSTR('-SETTLE_SE_NO',9,2) AND DTL.SETTLE_DT BETWEEN '-SETTLE_DT_START' AND '-SETTLE_DT_END' AND (TT.TRANS_TYPE_GRP_DS = 'SUBMISSION' OR TT.TRANS_TYPE_GRP_DS = 'OTHER-FEE-REV') order by DTL.SETTLE_DT ASC,DTL.SETTLE_CURR_CD,DTL.BUS_CTR_CD,DTL.SETTLE_SE_NO,DTL.SETTLE_CHK_NO,DTL.SETTLE_LOC_AM,SOC.SUBM_SE_NO,TT.TRANS_TYPE_GRP_DS,SOC.SOC_NO 
##Single Sort by SettlementNumber
SQLSubmList5 = SELECT DTL.SETTLE_CHK_NO as "q1.settlementNbr", DTL.SETTLE_DT as "q1.settlementDt", DTL.SETTLE_SE_NO as "q1.payeeSeNbr", decimal(DTL.SETTLE_LOC_AM/100,12,2) as "q1.settlementAmt", DTL.SETTLE_CURR_CD as "q1.settlementCurrencyCd", SOC.GMDL_SETTLE_ID as "q1.settlementId", SOC.INIT_PRCS_DT as "q1.socDt", SOC.INIT_PRCS_DT as "q1.amxReceivedDt", SOC.SOC_NO as "q1.socNbr", TT.TRANS_TYPE_GRP_DS as "q1.socType", SOC.SUBM_SE_NO as "q1.submittingSeNbr", SOC.SUBM_CURR_CD as "q1.currencyCd", decimal(SOC.SOC_GR_AM/100,12,2) as "q1.submissionAmt", decimal(SOC.SETTLE_CALC_GR_CR_AM/100,12,2) as "q1.totalCreditAmt", decimal((SOC.SETTLE_CALC_GR_DR_AM + SOC.SETTLE_CALC_GR_CR_AM)/100,12,2) as "q1.totalChargeAmt", SOC.CR_ROC_CT as "q1.creditCt", SOC.DR_ROC_CT as "q1.chargeCt", DTL.SETTLE_CURR_CD as "q1.adjustmentCurrencyCd",decimal(SOC.SOC_NET_AM/100,12,2) as "q1.totalAdjustmentAmt", (SOC.DR_ROC_CT + SOC.Cr_ROC_CT) as "q1.totalAdjustmentsCt", decimal(SOC.SOC_DISC_AM/100,12,2) as "q1.totalDiscountAmt", decimal((SOC.SOC_SRVC_FEE_AM + SOC.SOC_DIV_AM + SOC.SOC_DISC_AM)/100,12,2) as "q1.totalFeesAmt" FROM OD1.TE130_MER_SETTLE_DTL DTL, OD1.TE133_MER_SETTLE_SOC SOC, OD1.TF809_TRANS_TYPE TT, OD1.TE134_MER_SETTLE_SOC_BTCH_BILL_CD BAT WHERE DTL.GMDL_SETTLE_ID = SOC.GMDL_SETTLE_ID AND SOC.TRANS_TYPE_CD = TT.TRANS_TYPE_CD AND SOC.GMDL_SETTLE_ID = BAT.GMDL_SETTLE_ID AND SOC.GMDL_SETTLE_SOC_ID = BAT.GMDL_SETTLE_SOC_ID AND DTL.SETTLE_SE_NO = '-SETTLE_SE_NO' AND DTL.SE_CHK_DGT_NO = SUBSTR('-SETTLE_SE_NO',9,2) AND DTL.SETTLE_DT BETWEEN '-SETTLE_DT_START' AND '-SETTLE_DT_END' AND (TT.TRANS_TYPE_GRP_DS = 'SUBMISSION' OR TT.TRANS_TYPE_GRP_DS = 'OTHER-FEE-REV') order by DTL.SETTLE_CHK_NO ASC,DTL.SETTLE_CURR_CD,DTL.BUS_CTR_CD,DTL.SETTLE_DT DESC,DTL.SETTLE_SE_NO,DTL.SETTLE_LOC_AM,SOC.SUBM_SE_NO,TT.TRANS_TYPE_GRP_DS,SOC.SOC_NO 
##Single Sort by SocNo
SQLSubmList6 = SELECT DTL.SETTLE_CHK_NO as "q1.settlementNbr", DTL.SETTLE_DT as "q1.settlementDt", DTL.SETTLE_SE_NO as "q1.payeeSeNbr", decimal(DTL.SETTLE_LOC_AM/100,12,2) as "q1.settlementAmt", DTL.SETTLE_CURR_CD as "q1.settlementCurrencyCd", SOC.GMDL_SETTLE_ID as "q1.settlementId", SOC.INIT_PRCS_DT as "q1.socDt", SOC.INIT_PRCS_DT as "q1.amxReceivedDt", SOC.SOC_NO as "q1.socNbr", TT.TRANS_TYPE_GRP_DS as "q1.socType", SOC.SUBM_SE_NO as "q1.submittingSeNbr", SOC.SUBM_CURR_CD as "q1.currencyCd", decimal(SOC.SOC_GR_AM/100,12,2) as "q1.submissionAmt", decimal(SOC.SETTLE_CALC_GR_CR_AM/100,12,2) as "q1.totalCreditAmt", decimal((SOC.SETTLE_CALC_GR_DR_AM + SOC.SETTLE_CALC_GR_CR_AM)/100,12,2) as "q1.totalChargeAmt", SOC.CR_ROC_CT as "q1.creditCt", SOC.DR_ROC_CT as "q1.chargeCt", DTL.SETTLE_CURR_CD as "q1.adjustmentCurrencyCd",decimal(SOC.SOC_NET_AM/100,12,2) as "q1.totalAdjustmentAmt", (SOC.DR_ROC_CT + SOC.Cr_ROC_CT) as "q1.totalAdjustmentsCt", decimal(SOC.SOC_DISC_AM/100,12,2) as "q1.totalDiscountAmt", decimal((SOC.SOC_SRVC_FEE_AM + SOC.SOC_DIV_AM + SOC.SOC_DISC_AM)/100,12,2) as "q1.totalFeesAmt" FROM OD1.TE130_MER_SETTLE_DTL DTL, OD1.TE133_MER_SETTLE_SOC SOC, OD1.TF809_TRANS_TYPE TT, OD1.TE134_MER_SETTLE_SOC_BTCH_BILL_CD BAT WHERE DTL.GMDL_SETTLE_ID = SOC.GMDL_SETTLE_ID AND SOC.TRANS_TYPE_CD = TT.TRANS_TYPE_CD AND SOC.GMDL_SETTLE_ID = BAT.GMDL_SETTLE_ID AND SOC.GMDL_SETTLE_SOC_ID = BAT.GMDL_SETTLE_SOC_ID AND DTL.SETTLE_SE_NO = '-SETTLE_SE_NO' AND DTL.SE_CHK_DGT_NO = SUBSTR('-SETTLE_SE_NO',9,2) AND DTL.SETTLE_DT BETWEEN '-SETTLE_DT_START' AND '-SETTLE_DT_END' AND (TT.TRANS_TYPE_GRP_DS = 'SUBMISSION' OR TT.TRANS_TYPE_GRP_DS = 'OTHER-FEE-REV') order by SOC.SOC_NO ASC,DTL.SETTLE_CURR_CD,DTL.BUS_CTR_CD,DTL.SETTLE_DT DESC,DTL.SETTLE_SE_NO,DTL.SETTLE_CHK_NO,DTL.SETTLE_LOC_AM,SOC.SUBM_SE_NO,TT.TRANS_TYPE_GRP_DS	
##Multiple Se_No sort by payee se nbr
SQLSubmList7 = SELECT DTL.SETTLE_CHK_NO as "q1.settlementNbr", DTL.SETTLE_DT as "q1.settlementDt", DTL.SETTLE_SE_NO as "q1.payeeSeNbr", decimal(DTL.SETTLE_LOC_AM/100,12,2) as "q1.settlementAmt", DTL.SETTLE_CURR_CD as "q1.settlementCurrencyCd", SOC.GMDL_SETTLE_ID as "q1.settlementId", SOC.INIT_PRCS_DT as "q1.socDt", SOC.INIT_PRCS_DT as "q1.amxReceivedDt", SOC.SOC_NO as "q1.socNbr", TT.TRANS_TYPE_GRP_DS as "q1.socType", SOC.SUBM_SE_NO as "q1.submittingSeNbr", SOC.SUBM_CURR_CD as "q1.currencyCd", decimal(SOC.SOC_GR_AM/100,12,2) as "q1.submissionAmt", decimal(SOC.SETTLE_CALC_GR_CR_AM/100,12,2) as "q1.totalCreditAmt", decimal((SOC.SETTLE_CALC_GR_DR_AM + SOC.SETTLE_CALC_GR_CR_AM)/100,12,2) as "q1.totalChargeAmt", SOC.CR_ROC_CT as "q1.creditCt", SOC.DR_ROC_CT as "q1.chargeCt", DTL.SETTLE_CURR_CD as "q1.adjustmentCurrencyCd",decimal(SOC.SOC_NET_AM/100,12,2) as "q1.totalAdjustmentAmt", (SOC.DR_ROC_CT + SOC.Cr_ROC_CT) as "q1.totalAdjustmentsCt", decimal(SOC.SOC_DISC_AM/100,12,2) as "q1.totalDiscountAmt", decimal((SOC.SOC_SRVC_FEE_AM + SOC.SOC_DIV_AM + SOC.SOC_DISC_AM)/100,12,2) as "q1.totalFeesAmt" FROM OD1.TE130_MER_SETTLE_DTL DTL, OD1.TE133_MER_SETTLE_SOC SOC, OD1.TF809_TRANS_TYPE TT, OD1.TE134_MER_SETTLE_SOC_BTCH_BILL_CD BAT WHERE DTL.GMDL_SETTLE_ID = SOC.GMDL_SETTLE_ID AND SOC.TRANS_TYPE_CD = TT.TRANS_TYPE_CD AND SOC.GMDL_SETTLE_ID = BAT.GMDL_SETTLE_ID AND SOC.GMDL_SETTLE_SOC_ID = BAT.GMDL_SETTLE_SOC_ID AND DTL.SETTLE_SE_NO in ('-SETTLE_SE_NO1', '-SETTLE_SE_NO2','-SETTLE_SE_NO3','-SETTLE_SE_NO4') AND DTL.SETTLE_DT BETWEEN '-SETTLE_DT_START' AND '-SETTLE_DT_END' AND (TT.TRANS_TYPE_GRP_DS = 'SUBMISSION' OR TT.TRANS_TYPE_GRP_DS = 'OTHER-FEE-REV') order by DTL.SETTLE_SE_NO ASC,DTL.SETTLE_CURR_CD,DTL.BUS_CTR_CD,DTL.SETTLE_DT DESC,DTL.SETTLE_CHK_NO,DTL.SETTLE_LOC_AM,SOC.SUBM_SE_NO,TT.TRANS_TYPE_GRP_DS,SOC.SOC_NO
ParamsSubmList7 = -SETTLE_SE_NO1:9300993301,-SETTLE_SE_NO2:9301230901,-SETTLE_SE_NO3:9301935103,-SETTLE_SE_NO4:9302291506,-SETTLE_DT_START:2013-09-01,-SETTLE_DT_END:2013-09-03

 
======================================================================================================




com.amex.dataprovider
RowDataProvider.java


package com.amex.dataprovider;

import java.util.HashMap;

import org.testng.annotations.DataProvider;

import com.amex.base.TestBase;
import com.amex.utils.Constants;

public class RowDataProvider extends TestBase{
    
    
    @DataProvider
    public static Object[][] getRowData() {
	Object[][] object =  new Object[reader.getRowCount(Constants.SHEET_NAME)-1][1];
	
	// Iterating through every row in the sheet
	for (int currentTestcase = 2; currentTestcase <= reader.getRowCount(Constants.SHEET_NAME); currentTestcase++) {	    
	    
		HashMap<String, String> rowData = reader.getRowData(Constants.SHEET_NAME, currentTestcase);
	    // allocating Row Data wrapper object to Object array
		object[currentTestcase-2][0]=new RowDataWrapper(rowData,currentTestcase);
	}
	return  object;
    }
}

======================================================================================================


com.amex.dataprovider
RowDataWrapper.java


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
	return rowData.get(Constants.COLUMN_TCID).trim() + ": " + rowData.get("Api_Name").trim();
    }
}

======================================================================================================




com.amex.main
DriverScript.java

package com.amex.main;

import java.io.IOException;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import com.amex.dataprovider.RowDataProvider;
import com.amex.dataprovider.RowDataWrapper;
import com.amex.testfactory.APITestFactory;
import com.amex.utils.Constants;
import com.amex.utils.FileUtil;
import com.jayway.restassured.response.Response;

public class DriverScript extends APITestFactory {

	private static Logger logger = LoggerFactory.getLogger(DriverScript.class);

	/**
	 * This method is the main method that starts the execution.
	 * 
	 * @throws IOException
	 */
	@Test(dataProviderClass = RowDataProvider.class, dataProvider = "getRowData")
	public void test(RowDataWrapper rowDataWrapper) throws IOException {
		testFailed=false;
		logger.info("\n\nExecuting test case "+rowDataWrapper+"\n");
		currentTestcase=rowDataWrapper.getCurrentTestcase();
		HashMap<String, String> rowData = rowDataWrapper.getRowData();
		String runMode = rowData.get(Constants.COLUMN_RUN_MODE).trim();
		tcid = rowData.get(Constants.COLUMN_TCID).trim();
		
		// Execute when Run Mode is Yes
		if (runMode.equalsIgnoreCase("YES")) {
			
			// Initialize all the values from test data sheet
			initialize(rowData);
			
			//TODO define runBootstrapSQLQueries()
			
			// Replaces the URL with path parameters
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
		} else if (runMode.equalsIgnoreCase("NO")) {	    
			testSkipped(tcid+" : Test Skipped as Run Mode was NO");

		}else{
			testFailed("Unable to read Run Mode");
		}
	}	
}


======================================================================================================



com.amex.testdata
API_Details_Payments_san.xlsx
API_Details_Payments_Yatharth.xlsx

======================================================================================================



com.amex.testfactory
APITestFactory.java


package com.amex.testfactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;

import com.amex.base.TestBase;
import com.amex.utils.Constants;
import com.amex.utils.DB2Manager;
import com.amex.utils.FileUtil;
import com.amex.utils.ParamsUtil;
import com.amex.utils.PropertiesReader;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;

public class APITestFactory extends TestBase{
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
	protected String tcid;
	protected String fileoutpath;
	protected String urlParameters;
	private String dbQueries;
	private String requestValues;
	private String fileUploadSrcPath;
	private String responseStoreRetr;

	private List<Map<String, String>> resultsList;

	private DB2Manager db2Manager; 
	protected int currentTestcase;
	protected boolean testFailed = false;
	private boolean isDbQueried;
	private static Logger logger = LoggerFactory.getLogger(APITestFactory.class);



	@BeforeClass
	public void init() {
		FileUtil.createDirectory(Constants.TEST_OUTPUT_PATH);
		fileoutpath = getFileOutPath(currentTimeStamp);
		try {
		    db2Manager  = new DB2Manager();
		} catch (Exception e) {
		    //Error in connection with DB
		    logger.info(e.getMessage());
		    testFailed(e.getMessage());
		    
		}
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
	protected void initialize(HashMap<String, String> rowData) {
		requestURL           = configProp.getProperty(Constants.KEY_URL) + rowData.get(Constants.COLUMN_API).trim();
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
		dbResultKeySet       = rowData.get(Constants.COLUMN_DB_RESULT_KEYS).trim();
		urlParameters        = rowData.get(Constants.COLUMN_URL_PARAMETERS).trim();
		dbQueries            = rowData.get(Constants.COLUMN_DB_QUERIES).trim();
		requestValues        = rowData.get(Constants.COLUMN_REQUEST_VALUES).trim();
		fileUploadSrcPath    = rowData.get(Constants.COLUMN_FILE_SRC_PATH).trim();
		responseStoreRetr    = rowData.get(Constants.COLUMN_RESPONSE_STORE_RETRIEVE).trim();
		isDbQueried          = false;
	}


	/**
	 * Call the web service and get the response.
	 * 
	 * @return
	 */
	protected Response getResponse() {
		Response response = null;
		RestAssured.useRelaxedHTTPSValidation();
		try {
		    	logger.info("Final request url: " + requestURL);
			if (requestMethod.equalsIgnoreCase("POST")) {
				// Call POST service
				FileUtil.createFile(fileoutpath, tcid + "_Request.txt", requestBody);
				response = RestAssured.given().headers(headerKey, headerValue)
				.body(requestBody).contentType(contentType)
				.post(requestURL).andReturn();
			} else if (requestMethod.equalsIgnoreCase("FILEPOST")) {
			    	HashMap<String, String> formParams;
			        formParams = getFormParams(requestParam);
			        if(formParams != null) {
			            response = RestAssured.given().headers(headerKey, headerValue)
				    .multiPart(new File(fileUploadSrcPath))
				    .formParams(formParams).post(requestURL);
			        }
			} else if (requestMethod.equalsIgnoreCase("DELETE")) {
			    	response = RestAssured.given().headers(headerKey, headerValue)
				.delete(requestURL).andReturn();
			    
			} else if (requestMethod.equalsIgnoreCase("GET")) {
				// Call GET service
				response = RestAssured.given().headers(headerKey, headerValue)
				.contentType(contentType).get(requestURL).andReturn();
			}
		
		storeResponseInTemp(response);

		}catch(UnknownHostException e){
			logger.info(e.getMessage(), e);
			testFailed("Host not found: " + e.getMessage());

		} catch (Exception exception) {
			testFailed(exception.getLocalizedMessage());
			logger.info(exception.getMessage(), exception);

		}
		return response;
	}


    /**
     * Get the parameters from temp.property files to get the form parameters that 
     * needs to be sent as headers.
     * @param reqParams
     * @return
     */
    private HashMap<String, String> getFormParams(String reqParams) {
	if (!reqParams.isEmpty()) {
	    // get the values from properties file
	    if (reqParams.startsWith("TempProps[")) {
		Properties tempProps = PropertiesReader.loadPropertyFile(Constants.TEMP_PROP_PATH);
		String keys = reqParams.replaceAll(".*\\[|\\].*", "");
		String[] keysArr = keys.split(",");
		StringBuilder keyvalue = new StringBuilder();
		for (int i = 0; i < keysArr.length; ++i) {
		    keyvalue.append(keysArr[i].replace("_", "") + ":" + tempProps.getProperty(keysArr[i]));
		    if (i != keysArr.length - 1) {
			keyvalue.append(",");
		    }

		}
		reqParams = keyvalue.toString();
		return ParamsUtil.generateRequestParamsMap(reqParams);
	    }
	}
	return null;
    }


	/**
	 * Store the response keys mentioned in the Excel sheet into the
	 * temp.properties file to be used in another test case.
	 * @param response
	 */
	private void storeResponseInTemp(Response response) {
	    
	    if (response != null) {
		JsonPath json = response.getBody().jsonPath();
		HashMap<String, String> paramsMap;
		Properties tempProps = PropertiesReader.loadPropertyFile(Constants.TEMP_PROP_PATH);
		if(!responseStoreRetr.isEmpty()) {
		    if(responseStoreRetr.startsWith("Save=")) {
			paramsMap = ParamsUtil.generateRequestParamsMap(responseStoreRetr.split("Save=")[1]);
		    
        		    for (Map.Entry<String, String> entry : paramsMap.entrySet()) {
        			String key = entry.getKey();
        			String value = entry.getValue();
        			String propValue = json.getString(value);
        			tempProps.put(key,propValue);
        		    }
        		    try {
				tempProps.store(new FileOutputStream(Constants.TEMP_PROP_PATH), "");
			    } catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			    } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			    }
		    }
		}
	    }
	    
	}


	/**
	 * Check the HTTP response status code and validate the reponse. If 200 OK,
	 * assert the response with the values from DB If 400, 499, assert the error
	 * details in the response body with values in the ErrorCodes.properties
	 * file.
	 * 
	 * @param response
	 */
	protected void validateResponse(Response response) {

		int actualResponseCode = response.getStatusCode();
		int expResponseCode = (int) Float.parseFloat(expectedResponseCode);

		if (actualResponseCode == expResponseCode) {
			if (actualResponseCode == 200) {

				boolean isEmpty=true;

				if(!responseKeySet.isEmpty()) {
					validateValidResponse(response);
					isEmpty=false;
				}
				if(!requestValues.isEmpty()) {
					validateRequestValues();
					isEmpty=false;
				}
				if(isEmpty){
					testSkipped("Both Request Keys and Request Values are not provided");
				}

			} else if (actualResponseCode == 400 || actualResponseCode == 499 || actualResponseCode == 401) {
				validateErrorResponse(response);
			} else {
				logger.info("The response code does not fall in 200/400/499/401, " + actualResponseCode);
				testFailed("The response code does not fall in 200/400/499/401, " + actualResponseCode);
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
			resultsList = db2Manager.executeQueries(queriesList);
			isDbQueried = true;
			String resultString="";
			if(responseKeys.length == dbResultKeys.length) {
				for (int i = 0; i < responseKeys.length; i++) {
					String key = responseKeys[i].trim();
					String dbkey = dbResultKeys[i].trim();

					// Getting value from JSON response
					String actualValue;

					try{
						actualValue = json.getString(key);
					}catch(IllegalArgumentException iae){
						logger.info("Trying to access an element that is not available : ",iae);
						actualValue="";
						testFailed("Trying to access an element that is not available : " + iae.getMessage() );
					}catch(NullPointerException npe){
						logger.info("Json object for "+key+" returned null : ",npe);
						actualValue="";						
					}
					
					// Getting value from Database
					String expectedValue = db2Manager.getRowWiseValue(resultsList, dbkey);
					expectedValue = expectedValue != null ? expectedValue.trim() : expectedValue;
					

					logger.info("Expected Value [From DB] : " + dbkey + " = " + expectedValue);
					logger.info("Actual Value [From JSON] : " + key + " = " + actualValue);

					// Replacing empty if the value returned by JSON response is null.
					if(actualValue==null){
						actualValue="";
					}else{
						// Trim the value only if it is not null
						actualValue=actualValue.trim();
					}

					// Replacing empty if the value returned by DB response is null.
					if(expectedValue==null)
						expectedValue="";

					// Check the values are not matching and append the values to a result string
					if(!actualValue.equals(expectedValue)){

						resultString=resultString+"Expected Value [From DB] : " + dbkey + " = " + expectedValue+"\n";
						resultString=resultString+"Actual Value [From JSON] : " + key + " = " + actualValue+"\n\n";

						// TODO Debugging purpose - remove the code
						System.out.println("Expected Value : " + dbkey + " = " + expectedValue);
						System.out.println("Actual Value   : " + key + " = " + actualValue);

					}
				}

				// Report all the mismatching values to the Excel report
				if(!resultString.isEmpty()){
					testFailed(resultString);
				}

			}else{
				testSkipped("Response Keys and DB keys count does not match \n" +
						" Response Keys Length : "+responseKeys.length +"\n" +
						" DB  Keys  Length     : "+dbResultKeys.length +"\n");
			}
		}catch (Exception e) {
			logger.info(e.getMessage(),e);
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

		if(!errorCodesProp.containsKey(errorName))
			testSkipped("Error Name["+ errorName +"] is not available in the Error property file");
		
		String actualErrName = json.getString("errors[0].errorNm").trim();
		if(!errorName.equalsIgnoreCase(actualErrName)){
		    testFailed("\nExpected Error Name : "+errorName + "\n" 
			    +"Actual Error Name : "+actualErrName);
		}
		
		// Get the expected error details from Property files
		String[] expValues = errorCodesProp.getProperty(errorName).split(";");

		// Read the expected response values to be validated
		String[] responseKeys = responseKeySet.split(",");

		if(expValues.length!=responseKeys.length)
			testSkipped("Response Keys and Error keys count does not match");

		// Validating the error response details
		for (int count = 0; count < expValues.length; count++) {

			String actualValue = json.getString((responseKeys[count].trim()));
			String expectedValue = expValues[count].trim();

			if (!expectedValue.equalsIgnoreCase(actualValue)) {
				testFailed("\nExpected Error Code: "
						+ expectedValue + "\nActual Error Code: " + actualValue);
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
		// Failing the test if the Request Schema key is not available
		if(!requestProp.containsKey(requestName)){
			testFailed("Request Key is not available in the JSON request properties file");
		}
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
	protected void replaceURLParameters(String urlParams) {
		if(!urlParams.isEmpty()){
		    if(urlParams.startsWith("TempProps[")) {
			Properties tempProps = PropertiesReader.loadPropertyFile(Constants.TEMP_PROP_PATH);
			String keys = urlParams.replaceAll(".*\\[|\\].*", "");
			String[] keysArr = keys.split(",");
			StringBuilder keyvalue = new StringBuilder();
			for(int i = 0; i<keysArr.length; ++i){
			    keyvalue.append(keysArr[i] + ":" + tempProps.getProperty(keysArr[i]));
			    if(i != keysArr.length-1) {
				keyvalue.append(",");
			    }
			    
			}
			urlParams=keyvalue.toString();
		    }
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
	protected void testSkipped() {
		reader.setCellData(Constants.SHEET_NAME, Constants.COLUMN_TEST_RESULT, currentTestcase, Constants.TEST_SKIP);
	}

	protected void testSkipped(String SkipCause) {
		logger.info("Test Skipped : " + SkipCause);
		try{
			reader.setCellData(Constants.SHEET_NAME, Constants.COLUMN_TEST_RESULT, currentTestcase, Constants.TEST_SKIP);
			reader.setCellData(Constants.SHEET_NAME, Constants.COLUMN_FAILURE_CAUSE, currentTestcase, SkipCause);
		}catch(Exception e){
			logger.info(e.getMessage(),e);
		}
		throw new SkipException("Test Skipped : "+SkipCause);


	}



	/**
	 * Set the Test_Result column in excel sheet as Passed.
	 * 
	 */
	protected void testPassed() {
		reader.setCellData(Constants.SHEET_NAME, Constants.COLUMN_TEST_RESULT, currentTestcase, Constants.TEST_PASSED);
	}


	/**
	 * Set the Test_Result column in excel sheet as Failed. And also sets the
	 * failure cause in Failure_Cause column.
	 * 
	 */
	protected void testFailed(String failureCause) {
		logger.info("Test Failed : " + failureCause);
		reader.setCellData(Constants.SHEET_NAME, Constants.COLUMN_TEST_RESULT, currentTestcase, Constants.TEST_FAILED);
		reader.setCellData(Constants.SHEET_NAME, Constants.COLUMN_FAILURE_CAUSE, currentTestcase, failureCause);
		testFailed = true;
		Assert.fail("Test Failed : " + failureCause);
	}


	@AfterSuite
	public void tearDown() {
		try {
			FileUtil.copyFile(Constants.TESTDATA_PATH + configProp.getProperty(Constants.KEY_TESTDATA), fileoutpath + "\\" + configProp.getProperty(Constants.KEY_TESTREPORT));
			clearResults();
		} catch (IOException e) {
			logger.info(e.getMessage(), e);
		}
	}


	private void validateRequestValues() {
		HashMap <String,String> requestValuesMap = ParamsUtil.generateRequestParamsMap(requestValues);
		String resultString="";
                try {
                    

		if(!isDbQueried) {
			List<String> queriesList;
			try {
				queriesList = db2Manager.getQueries(dbQueries);
				resultsList = db2Manager.executeQueries(queriesList);
			} catch (IOException e) {
				e.printStackTrace();
				logger.info(e.getMessage(),e);
			}
		}

		for (Map.Entry<String, String> entry : requestValuesMap.entrySet()) {
			String key = entry.getKey();
			String expectedValue = entry.getValue();
			String actualValue = db2Manager.getRowWiseValue(resultsList, key);


			if(!expectedValue.equals(actualValue)){

				resultString=resultString+"Expected Value [From Excel] : " + key + " = " + expectedValue+"\n";
				resultString=resultString+"Actual Value [From DB] : " + key + " = " + actualValue+"\n\n";

				// TODO Debugging purpose - remove the code
				System.out.println("Expected Value : " + key + " = " + expectedValue);
				System.out.println("Actual Value   : " + key + " = " + actualValue);

			}
		}
		// Report all the mismatching values to the Excel report
		if(!resultString.isEmpty()){
			testFailed(resultString);
		}
                }catch (Exception e) {
			logger.info(e.getMessage(),e);
			testFailed(e.getMessage());

		}
	}


}


======================================================================================================





com.amex.utils
Constants.java
package com.amex.utils;

public class Constants {

//public static final String URL 				   = "http://10.192.37.167:8080/EmployeeData/disputes";
//public static final String URL 			   = "https://dwww421.app.aexp.com/merchant/services/dsiputes";
public static final String REQUEST_PROP_PATH       = "src\\com\\amex\\config\\request\\";
public static final String ERROR_CODES_PROP_PATH   = "src\\com\\amex\\config\\errorhandles\\";
public static final String SQL_QUERIES_PROP_PATH   = "src\\com\\amex\\config\\request\\";
public static final String TEMP_PROP_PATH          = "src\\com\\amex\\config\\temp.properties";
public static final String CONFIG_PROP_PATH 	   = "src\\com\\amex\\config\\config.properties";
public static final String SHEET_NAME              = "Sheet1";
public static final String TESTDATA_PATH           = "src\\com\\amex\\testdata\\";
//public static final String EXCEL_TEST_REPORT_PATH  = "\\API_Report.xlsx";
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
public static final String COLUMN_REQUEST_VALUES   = "Request_Values";
public static final String COLUMN_RESPONSE_STORE_RETRIEVE = "Response_Store_Retrieve";
public static final String COLUMN_FILE_SRC_PATH    = "File_Src_Path";

public static final String TEST_SKIP               = "Skipped";
public static final String TEST_PASSED             = "Passed";
public static final String TEST_FAILED             = "Failed";


public static final String JDBC_DRVER_DB2          = "COM.ibm.db2os390.sqlj.jdbc.DB2SQLJDriver";
public static final String JDBC_CONNECTION_URL	   = "jdbc:db2://adc1db2d.ipc.us.aexp.com:7320/ADC1DB2D";


public static final String DB_USER                 = "IG8671A";
public static final String DB_PASS                 = "aug8@aug";

//Config properties keys
public static final String KEY_TESTDATA            = "testdata.filename";
public static final String KEY_TESTREPORT	   = "testreport.filename";
public static final String KEY_URL                 = "api.base.url";
public static final String KEY_REQ_PROP_FILE 	   = "request.prop.filename";
public static final String KEY_SQL_PROP_FILE	   = "sql.queries.prop.filename";
public static final String KEY_ERR_PROP_FILE	   = "error.prop.filename";






}
======================================================================================================


DateTimeUtil.java
package com.amex.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtil {

    /**
     * Get the current date and time.
     * 
     * @return returns the current date and time in the format: "dd-MM-yyyy_HH_mm_ss"
     */
    public static String getCurrentTimeStamp() {
	SimpleDateFormat sdfDate = new SimpleDateFormat("dd-MM-yyyy_HH_mm_ss");
	Date now = new Date();
	return sdfDate.format(now);
	
    }
}

======================================================================================================


DB2Manager.java

package com.amex.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author spalu1
 *
 */
/**
 * @author spalu1
 *
 */
public class DB2Manager {

	private static DbReader dbReader = null;
	protected Properties requestProp;
	private Properties configProp;
	private String sqlPropFileName;
	private static Logger logger = LoggerFactory.getLogger(DB2Manager.class);

	public DB2Manager() throws Exception {
		dbReader = DbReader.getInstance(Constants.JDBC_DRVER_DB2,
				Constants.JDBC_CONNECTION_URL, Constants.DB_USER,
				Constants.DB_PASS);
		
		configProp      = PropertiesReader.loadPropertyFile(Constants.CONFIG_PROP_PATH);
		sqlPropFileName = configProp.getProperty(Constants.KEY_SQL_PROP_FILE);
	}

	/**
	 * Executes all the SQL queries passed to it 
	 * @param queries
	 * @return List of HashMap containing all the query results where Keys are column names
	 * @throws SQLException 
	 */
	public List<Map<String, String>> executeQueries(List<String> queries) throws SQLException {
		Iterator<String> queriesIt = queries.iterator();
		List<Map<String, String>> resultList = new ArrayList<Map<String, String>>();

		// Iterating through all the passed queries
		while (queriesIt.hasNext()) {
			String query = queriesIt.next();

			// Executing query
			List<Map<String, String>> results = dbReader.executeQuery(query);
			    if (results != null) {
				resultList.addAll(results);
			}
		}
		logger.info(resultList.toString());
		return resultList;
	}


	/**
	 * Returns value from a List of HashMap if Key is provided
	 * @param resultList
	 * @param key
	 * @return
	 */
	public String getItemValue(List<Map<String, String>> resultList, String key) {
		try {
			logger.debug("In getItemValue, key: " + key );
			Iterator<Map<String, String>> resultsIt = resultList.iterator();
			while (resultsIt.hasNext()) {
				Map<String, String> resultMap = resultsIt.next();
				for (Map.Entry<String, String> entry : resultMap.entrySet()) {
					if (entry.getKey().equals(key)) {
						logger.debug("In getItemValue, value: " + entry.getValue());
						return entry.getValue();
					}
				}
			}
		} catch(Exception e){
			logger.info(e.getMessage(),e);
		}
		return null;
	}

	
	/**
	 *  Get the Complete query from the property files 
	 * @param dbQueries
	 * @return
	 * @throws IOException
	 */
	public List<String> getQueries(String dbQueries) throws IOException {
		
		requestProp = new Properties();
		FileInputStream fis = new FileInputStream(
				Constants.SQL_QUERIES_PROP_PATH + sqlPropFileName);
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
					// Combines query and parameter
					query1 = ParamsUtil.replaceParams(query1, param1);
				}
				allQueries.add(query1);
				logger.info(query1);
			}

		}
		return allQueries;
	}

	/**
	 * 
	 * @param resultList
	 *            List of results hashmap
	 * @param keyrow
	 *            String in the form
	 *            "ColumnName[RowNum], where rownum = 0,1,2,3...."
	 * @return
	 */
	public String getRowWiseValue(List<Map<String, String>> resultList,
			String keyrow) {

		try {
			logger.debug("In getRowWiseValue, keyrow: " + keyrow );
			//Get the column name
			String key = keyrow.split("\\[")[0];
			String value = null;
			Integer rowNum = 0;
			if (keyrow.indexOf(".") != -1) {
				resultList =  getItemValueByQuery(resultList,key);
			}
			//Checks whether if '[' is present
			if (keyrow.indexOf('[') != -1) {
				//gets the rownumber from inside []
				rowNum = Integer.valueOf(keyrow.replaceAll(".*\\[|\\].*", ""));
			} else {
				//if there is no row number mentioned
				return getItemValue(resultList, key);
			}
			if (0 <= rowNum && resultList.size() > rowNum) {
				Map<String, String> row = resultList.get(rowNum);
				value = row.get(key);
			}
			return value;
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
		}
		return null;
	}
    private List<Map<String, String>> getItemValueByQuery(List<Map<String, String>> resultList, String keyrow) {

	//Get query identifier
	String queryId = keyrow.substring(0, keyrow.indexOf("."));
	Iterator<Map <String,String>> it = resultList.iterator();
	List<Map<String, String>> newResultList = new ArrayList<Map<String, String>>();
	while(it.hasNext()) {
	    Map <String,String> resultMap =  it.next();
	    HashMap<String, String> newResultMap = new HashMap<String, String>();
	    for (Map.Entry<String, String> entry : resultMap.entrySet()) {
		if (entry.getKey().startsWith(queryId)) {
		    newResultMap.put(entry.getKey(), entry.getValue());
		}
	    }
	    if(!newResultMap.isEmpty()){
		newResultList.add(newResultMap);
	    }
	}
	return newResultList;
    }
}


======================================================================================================


DbReader.java
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
	 * This method is used to establish the database connection
	 * 
	 * @param driver
	 * @param url
	 * @param userName
	 * @param password
	 * @throws Exception 
	 * 
	 */
	public DbReader(String driver, String url, String userName,
			String password) throws Exception {
		try {
			logger.info(" Trying to connect database");
			Class.forName(driver);
			connection = DriverManager.getConnection(url, userName, password);
			connection.setAutoCommit(false);
		} catch (Exception e) {
			logger.info(e.getMessage(),e);
			throw e;
		}
	}
	
	
	/**
	 * Execute single query 
	 * @param query
	 * @return
	 * @throws SQLException 
	 */
	public List<Map<String,String>> executeQuery(String query) throws SQLException{
		try {
			preparedStatement = connection.prepareStatement(query);
			
			// If it is a DML query, execute teh below code
			if(!query.split(" ")[0].equalsIgnoreCase("SELECT")) {
				int status = preparedStatement.executeUpdate();

				// Checking how many rows are updated.
				String _status=(status!=0)?""+status+" Row(s) affected":" No rows affected";
				logger.info("Execute Update, staus : "+ _status);
				connection.commit();
				return null;
			}

			resultSet = preparedStatement.executeQuery();
			resultSetMetaData = resultSet.getMetaData();

		} catch (SQLException e) {   
			logger.info(e.getMessage(), e);
			throw e;
		}
		return read();
	}


	private static volatile DbReader instance = null;

	/**
	 * This method creates and returns the DbReader instance
	 * 
	 * @param driver
	 * @param url
	 * @param userName
	 * @param password
	 * @return DbReader
	 * @throws Exception 
	 */
	public static DbReader getInstance(String driver, String url,
			String userName, String password) throws Exception {
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
	 * This method reads the data from database
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

======================================================================================================


ExcelReader.java
package com.amex.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExcelReader {
    

	private FileInputStream fis = null;
	public  FileOutputStream fileOut =null;
	private Workbook workbook = null;
	private Sheet sheet = null;
	private Row row = null;
	private Cell cell = null;
	public static final Logger logger = LoggerFactory.getLogger(ExcelReader.class);
	private static volatile ExcelReader instance = null;
	private static volatile Map<String,ExcelReader> excelReaderMap = new HashMap<String, ExcelReader>();
	private String path;

	public ExcelReader(String path) {   
		// LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory(); 
		try { 
		this.path = path;
		fis = new FileInputStream(this.path); 
		this.workbook = WorkbookFactory.create(fis); 
		fis.close(); 
		} catch (Exception e) { 
				logger.error(e.getMessage()); 
				} 
			 }
		//}

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
					logger.error(e.getMessage());
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
	
		
		public boolean setCellData(String sheetName,String colName,int rowNum, String data){
			try{
//			fis = new FileInputStream(path); 
//			workbook = new Workbook(fis);
			if(rowNum<=0)
				return false;
			
			int index = workbook.getSheetIndex(sheetName);
			int colNum=-1;
			if(index==-1)
				return false;
			
			
			sheet = workbook.getSheetAt(index);
			

			row=sheet.getRow(0);
			for(int i=0;i<row.getLastCellNum();i++){
				//System.out.println(row.getCell(i).getStringCellValue().trim());
				if(row.getCell(i).getStringCellValue().trim().equals(colName))
					colNum=i;
			}
			if(colNum==-1)
				return false;

			sheet.autoSizeColumn(colNum); 
			row = sheet.getRow(rowNum-1);
			if (row == null)
				row = sheet.createRow(rowNum-1);
			
			cell = row.getCell(colNum);	
			if (cell == null)
		        cell = row.createCell(colNum);

		    // cell style
		    //CellStyle cs = workbook.createCellStyle();
		    //cs.setWrapText(true);
		    //cell.setCellStyle(cs);
		    cell.setCellValue(data);

		    fileOut = new FileOutputStream(path);

			workbook.write(fileOut);

		    fileOut.close();	
		    
		    fis = new FileInputStream(path); 
			this.workbook = WorkbookFactory.create(fis); 

			}
			catch(Exception e){
				logger.info(e.getMessage(),e);
				return false; 
			}
			return true;
		}

}
======================================================================================================


FileUtil.java
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

    public static ExcelReader getExcelReader(String excelFilePath) {

	if (excelFilePath == null) {
	    return null;
	} else {
	    logger.info("Loading the Excel file : " + excelFilePath);
	    return ExcelReader.getInstance(excelFilePath);
	}
    }
    
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

======================================================================================================


ParamsUtil.java
package com.amex.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ParamsUtil {
	
	
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
	HashMap<String, String> params = generateRequestParamsMap(keyvalueparams);
	return putils.replaceRequestParameters(requestBody, params);
    }
	

    /**
     * Generates a map of key value parameters from a string in the format:
     * "key:value,key1:value1..." -->
     * 
     * @param params
     * @return
     */
    public static HashMap<String, String> generateRequestParamsMap(String params) {
	HashMap<String, String> paramsMap = new HashMap<String, String>();
	String[] paramSet = params.split(",");
	for (int i = 0; i < paramSet.length; i++) {
	    String[] param = paramSet[i].split(":");
	    if (param[1].equalsIgnoreCase("NIL")) {
		param[1] = "";
	    }
	    if (param[1].startsWith("TempProps[")) {
		Properties tempProps = PropertiesReader.loadPropertyFile(Constants.TEMP_PROP_PATH);
		String keyInProp = param[1].replaceAll(".*\\[|\\].*", "");
		param[1] = tempProps.getProperty(keyInProp).trim();
	    }
	    paramsMap.put(param[0].trim(), param[1].trim());
	}
	return paramsMap;
    }

    /**
     * Replace the placeholder request parameters in the JSON request body with
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

    
}

======================================================================================================


PropertiesReader.java
package com.amex.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 
 */
public class PropertiesReader {
    
    private static Logger logger = LoggerFactory.getLogger(PropertiesReader.class);

    /**
     * Loads the property file from the path provided and returns a Property
     * object
     * 
     * @param path
     * @return
     */
    public static Properties loadPropertyFile(String path) {
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


======================================================================================================




log4j.xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE log4j:configuration SYSTEM "log4j.dtd">
<log4j:configuration xmlns:log4j='http://jakarta.apache.org/log4j/'>
	<appender name="CA" class="org.apache.log4j.ConsoleAppender">
		<layout class="org.apache.log4j.PatternLayout">
			<param name="ConversionPattern" value="%-4r [%t] %-5p %c %x - %m%n" />
		</layout>
	</appender>
	<root>
		<level value="error" />
		<appender-ref ref="CA" />
	</root>
</log4j:configuration>
======================================================================================================


logback.xml

<configuration>
	 <!-- Properties -->
<property name="log.pattern" value="%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n"/>
    <property name="log.history" value="7"/>
    <property name="log.folder" value="logs"/>
    <property name="log.level" value="DEBUG"/>
  <appender name="rollingCoreLog" class="ch.qos.logback.core.rolling.RollingFileAppender">
    <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
        <fileNamePattern>${log.folder}/MI_.%d{yyyy-MM-dd}.%i.log</fileNamePattern>
        <maxHistory>${log.history}</maxHistory>
        <timeBasedFileNamingAndTriggeringPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP">
            <maxFileSize>1MB</maxFileSize>
        </timeBasedFileNamingAndTriggeringPolicy>
    </rollingPolicy>
    <encoder>
        <pattern>${log.pattern}</pattern>
    </encoder>
    <prudent>true</prudent>
</appender>
        
  <root level="DEBUG">
    <appender-ref ref="rollingCoreLog" />
  </root>
</configuration>
======================================================================================================



Root - > 

DriverScript.xml

<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE suite SYSTEM "http://testng.org/testng-1.0.dtd">
<suite name="Suite" parallel="none">
  <test name="Test" preserve-order="true">
    <classes>
      <!-- <class name="com.amex.base.TestBase"/>
      <class name="com.amex.testfactory.APITestFactory"/> -->
      <class name="com.amex.main.DriverScript"/>
    </classes>
  </test> <!-- Test -->
</suite> <!-- Suite -->



==========================================================================================================



.classpath


<?xml version="1.0" encoding="UTF-8"?>
<classpath>
	<classpathentry kind="src" path="src"/>
	<classpathentry kind="con" path="org.eclipse.jdt.launching.JRE_CONTAINER/org.eclipse.jdt.internal.debug.ui.launcher.StandardVMType/JavaSE-1.7"/>
	<classpathentry kind="lib" path="lib/dom4j-1.6.jar"/>
	<classpathentry kind="lib" path="lib/poi-3.8-sources.jar"/>
	<classpathentry kind="lib" path="lib/poi-ooxml-3.5-beta5.jar"/>
	<classpathentry kind="lib" path="lib/xmlbeans-2.3.0.jar"/>
	<classpathentry kind="lib" path="lib/poi-3.8.jar"/>
	<classpathentry kind="lib" path="lib/commons-codec-1.6.jar"/>
	<classpathentry kind="lib" path="lib/commons-lang3-3.3.2.jar"/>
	<classpathentry kind="lib" path="lib/commons-logging-1.1.1.jar"/>
	<classpathentry kind="lib" path="lib/groovy-2.3.2.jar"/>
	<classpathentry kind="lib" path="lib/groovy-json-2.3.2.jar"/>
	<classpathentry kind="lib" path="lib/groovy-xml-2.3.2.jar"/>
	<classpathentry kind="lib" path="lib/hamcrest-core-1.3.jar"/>
	<classpathentry kind="lib" path="lib/hamcrest-library-1.3.jar"/>
	<classpathentry kind="lib" path="lib/httpclient-4.2.6.jar"/>
	<classpathentry kind="lib" path="lib/httpcore-4.2.5.jar"/>
	<classpathentry kind="lib" path="lib/httpmime-4.2.6.jar"/>
	<classpathentry kind="lib" path="lib/rest-assured-2.3.2.jar"/>
	<classpathentry kind="lib" path="lib/tagsoup-1.2.1.jar"/>
	<classpathentry kind="lib" path="lib/ooxml-schemas-1.0.jar"/>
	<classpathentry kind="lib" path="lib/slf4j-api-1.7.7.jar"/>
	<classpathentry kind="lib" path="lib/logback-classic-1.1.2.jar"/>
	<classpathentry kind="lib" path="lib/logback-core-1.1.2.jar"/>
	<classpathentry kind="lib" path="lib/log4j-1.2.17.jar"/>
	<classpathentry kind="lib" path="lib/db2jcc_license_cu.jar"/>
	<classpathentry kind="lib" path="lib/db2jcc.jar"/>
	<classpathentry kind="lib" path="lib/db2jcc_license_cisuz.jar"/>
	<classpathentry kind="lib" path="lib/guice-3.0.jar"/>
	<classpathentry kind="lib" path="lib/reportng-1.1.4.jar"/>
	<classpathentry kind="lib" path="lib/velocity-dep-1.4.jar"/>
	<classpathentry kind="con" path="org.testng.TESTNG_CONTAINER"/>
	<classpathentry kind="output" path="bin"/>
</classpath>

