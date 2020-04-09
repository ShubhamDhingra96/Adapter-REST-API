package snap.ib.personelLoan.adapter.common.sessionutil;

import java.net.URLEncoder;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;

/**
 * @author venkat
 *
 */

public class CustomSessionServiceImpl {
	
	static Logger logger = LoggerFactory.getLogger(CustomSessionServiceImpl.class.getName());
	public static String message ="";
	public static final String KEY_LOGGED_IN = "DynaCache";
	@SuppressWarnings("unused")
	public void saveDataIntoSession(SaveCustInfoData objectToBeStoredIncache,String cacheKey) throws Exception{
	DynamicCacheUtil dynamicCacheUtil = new DynamicCacheUtil();
		try {
			
			//dynamicCacheUtil.putInCacheForSession(cacheKey, objectToBeStoredIncache,KEY_LOGGED_IN);
			
		} catch (Exception e) {
             e.printStackTrace();
		}
	}
	
	public String saveDyncacheData(String sessionKey,String deviceId,SessionData data) throws Exception{
		    String cacheUrl = "GetDataServlet?deviceid="+deviceId+"&sessionkey="+sessionKey+"&stype=s&data=";
		    String cacheResponse =  "";
		    try {
		    	
				//String reqdata = "{\"crmid\":\""+data.getCrmid()+"\",\"mobileno\":\""+data.getMobilenumber()+"\",\"leadid\":\""+data.getLeadid()+"\",\"isAllowed\":\""+data.getIsAllowed()+"\",\"checkSum\":\""+data.getCheckSum()+"\",\"txnid\":\""+data.getTxnid()+"\",\"aadharnumber\":\""+data.getAadharnumber()+"\",\"enckey\":\""+data.getEncKey()+"\",\"otp\":\""+data.getOtp()+"\",\"mpin\":\""+data.getMpin()+"\",\"serviceCompletionTime\":\""+data.getServiceCompletionTime()+"\",\"documents\":\""+data.getDocuments()+"\"}";
				String reqdata = "{\"crmid\":\""+data.getCrmid()+"\",\"mobileno\":\""+data.getMobilenumber()+"\",\"leadid\":\""+data.getLeadid()+"\",\"isAllowed\":\""+data.getIsAllowed()+"\",\"checkSum\":\""+data.getCheckSum()+"\",\"txnid\":\""+data.getTxnid()+"\",\"aadharnumber\":\""+data.getAadharnumber()+"\",\"enckey\":\""+data.getEncKey()+"\",\"otp\":\""+data.getOtp()+"\",\"mpin\":\""+data.getMpin()+"\",\"serviceCompletionTime\":\""+data.getServiceCompletionTime()+"\",\"flag1\":\""+data.getFlag1()+"\",\"flag2\":\""+data.getFlag2()+"\",\"mobileFirst\":\""+data.getMobileFirst()+"\",\"mobileSecond\":\""+data.getMobileSecond()+"\",\"documents\":\""+data.getDocuments()+"\"}";
				logger.info("reqdata in saveDyncacheData:"+reqdata);
				reqdata =  URLEncoder.encode(reqdata,"UTF-8");
				cacheUrl = cacheUrl+reqdata;
				logger.info("saveDyncacheData line number 35 request :"+cacheUrl);
				cacheResponse = CacheServices.processCacheService(cacheUrl, sessionKey);
				logger.info("saveDyncacheData line number 40 response :"+cacheResponse);
			} catch (Exception e) {
				e.printStackTrace();
			}
		return cacheResponse;
	}
	
	public String getDyncacheData(String sessionKey,String deviceId) throws Exception{
	    String cacheUrl = "GetDataServlet?deviceid="+deviceId+"&sessionkey="+sessionKey+"&stype=g";
	    String cacheResponse =  "";
	    try {
	    	logger.info("getDyncacheData line number 51 request  :"+cacheUrl);
			cacheResponse = CacheServices.processCacheService(cacheUrl, sessionKey);
			logger.info("getDyncacheData line number 53 response :"+cacheResponse);
		} catch (Exception e) {
			e.printStackTrace();
		}
	return cacheResponse;
	}	
	
	public String deleteDyncacheData(String sessionKey,String deviceId) throws Exception{
	    String cacheUrl = "GetDataServlet?deviceid="+deviceId+"&sessionkey="+sessionKey+"&stype=d";
	    String cacheResponse =  "";
	    try {
	    	logger.info("saveDyncacheData line number 67 request  :"+cacheUrl);
			cacheResponse = CacheServices.processCacheService(cacheUrl, sessionKey);
			logger.info("saveDyncacheData line number 69 response :"+cacheResponse);
		} catch (Exception e) {
			e.printStackTrace();
		}
	return cacheResponse;
	}
	public String getEnckey(String sessionKey,String deviceId)throws Exception {
		String cacheResponse = "";
		try {
			String sessionRes =  getDyncacheData(sessionKey, deviceId);
			
			org.json.simple.JSONObject jsonObject = CacheServices.parseString(sessionRes);
			
			if(jsonObject != null){
				cacheResponse =  jsonObject.get("enckey")== null?"":jsonObject.get("enckey").toString();
				return cacheResponse;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cacheResponse;
	}
	
	public static boolean validateMobileNumber(String sessionKey,String deviceId,String mobileNumber) throws Exception{
		
		
		String cacheRes =   new CustomSessionServiceImpl().getDyncacheData(sessionKey, deviceId);
		
		if(!Strings.isNullOrEmpty(cacheRes)){
			org.json.simple.JSONObject jsonObject = CacheServices.parseString(cacheRes);
			
			if(jsonObject != null){
				String mobileNo = jsonObject.get("mobileno") == null?"":jsonObject.get("mobileno").toString();
			    if(!mobileNo.equals(mobileNumber))
			    {
			    	message =  "please enter valid details.";
			    	return true;
			    }
			    
			    
			    
			}
		}
		return false;
	}
	
public static boolean validateDetails(String mobileNumber,org.json.simple.JSONObject jsonObject ,String txnid,String leadId,String crmId) throws Exception{
		
		
//		String cacheRes =   new CustomSessionServiceImpl().getDyncacheData(sessionKey, deviceId);
		
//			org.json.simple.JSONObject jsonObject = CacheServices.parseString(cacheRes);
//			
			if(jsonObject != null){
				  if(!mobileNumber.equals("")){
						String mobileNo = jsonObject.get("mobileno") == null?"":jsonObject.get("mobileno").toString();
		
						if(!mobileNo.equals(mobileNumber))
					    {
					    	message =  "please enter valid details.";
					    	return true;
					    }
				  }
			    if(!leadId.equals("")){
			    	String lead_id = jsonObject.get("leadid") == null?"":jsonObject.get("leadid").toString();
				    if(!leadId.equals(lead_id))
				    {
				    	message =  "please enter valid details.";
				    	return true;
				    }
			    }
			    
			    if(!txnid.equals("")){
			    	String txnId = jsonObject.get("txnid") == null?"":jsonObject.get("txnid").toString();
				    if(!txnId.equals(txnid))
				    {
				    	message =  "please enter valid details.";
				    	return true;
				    }
			    }
			    
			}
		return false;
	}
	
public static boolean validateOtp(String otp,JSONObject jsonObject) throws Exception{
	try {
		String sessionOtp =  jsonObject.get("otp") == null?"":jsonObject.get("otp").toString();
		logger.info("session otp :"+sessionOtp);
		logger.info("otp :"+otp);
		if(!otp.equals(sessionOtp)){
			message =  "Please enter valid OTP";
			logger.info("message :"+message);
			return true;
		}
		
	} catch (Exception e) {
     e.printStackTrace();
	}
	
	return false;
}

public static boolean validateMpin(String mpin,JSONObject jsonObject) throws Exception{
	boolean isInvalidMPIN=false;
	try {
		String sessionMpin =  jsonObject.get("mpin") == null?"":jsonObject.get("mpin").toString();
		logger.info("mpin is: "+mpin+" sessionMpin: "+sessionMpin);
		if(!mpin.equals(sessionMpin)){
			message =  "Please enter valid password.";
			isInvalidMPIN=true;
			return isInvalidMPIN;
		}
		
	} catch (Exception e) {
     e.printStackTrace();
	}
	
	return isInvalidMPIN;
}

@SuppressWarnings("unchecked")
public String loanSummeryResManipulation(String res ){
	
	JSONObject mainObject =  new JSONObject();
	JSONObject loanObject =null;
	JSONArray  array =  new JSONArray();
	try {
		JSONObject jsonObject = CacheServices.parseString(res);

		logger.info("json :"+jsonObject);
		mainObject.put("success", jsonObject.get("success"));
		mainObject.put("status", jsonObject.get("status"));
		mainObject.put("total_POS", jsonObject.get("Total_POS"));
		mainObject.put("balance_Tenure", jsonObject.get("Balance_Tenure"));
		mainObject.put("indiabulls_Phone", jsonObject.get("Indiabulls_Phone"));
		mainObject.put("indiabulls_Email", jsonObject.get("Indiabulls_Email"));
		mainObject.put("indiabulls_SMS", jsonObject.get("Indiabulls_SMS"));
		mainObject.put("indiabulls_Var1", jsonObject.get("Indiabulls_var1"));
		mainObject.put("indiabulls_Var2", jsonObject.get("Indiabulls_var2"));
		mainObject.put("indiabulls_Var3", jsonObject.get("Indiabulls_var3"));
		mainObject.put("indiabulls_Var5", jsonObject.get("Indiabulls_var5"));
		mainObject.put("indiabulls_Var6", jsonObject.get("Indiabulls_var6"));
		mainObject.put("indiabulls_Var7", jsonObject.get("Indiabulls_var7"));
		mainObject.put("indiabulls_Var8", jsonObject.get("Indiabulls_var8"));
		
		
		JSONArray array2 = new JSONArray();
		array = (JSONArray) (jsonObject.get("List_Of_Loans") == null?"":jsonObject.get("List_Of_Loans"));
	
		for (int i = 0; i < array.size(); i++) {
		
			JSONObject jsonObject2 = (JSONObject) array.get(i);
			loanObject =  new JSONObject();
			loanObject.put("loan_Acct_No",jsonObject2.get("Loan_Acct_No"));
			loanObject.put("loan_Product",jsonObject2.get("Loan_Product"));
			loanObject.put("loan_Status",jsonObject2.get("Loan_Status"));
			loanObject.put("loan_Tenure",jsonObject2.get("Loan_Tenure"));
			loanObject.put("interest_Rate",jsonObject2.get("Interest_Rate"));
			loanObject.put("emi",jsonObject2.get("EMI"));
			loanObject.put("disbursed_Amount",jsonObject2.get("Disbursed_Amount"));
			loanObject.put("agreement_Id",jsonObject2.get("Agreement_Id"));
			loanObject.put("disbursal_date",jsonObject2.get("Disbursal_date"));
			loanObject.put("loan_Amount",jsonObject2.get("Loan_Amount"));
			array2.add(loanObject);
		
		}
		
		mainObject.put("list_Of_Loans",array2);
		logger.info(mainObject.toJSONString());
		return mainObject.toJSONString();
	} catch (Exception e) {
      e.printStackTrace();
	}finally{
		array  = null;
		mainObject =  null;
		loanObject= null;
	}

	return res;
	
}


@SuppressWarnings("unchecked")
public String loanRepaymentResManipulation(String res) throws Exception{

	JSONObject mainObject =  new JSONObject();
	JSONObject scheduleObject = null;
	JSONObject ovrdueObject = null;
	JSONArray  array =  new JSONArray();
	JSONArray array2=null;
//	String res =  "{\"success\":true,\"status\":\"success\",\"Scheduled_List\":[{\"Installment_No\":\"IPERNDA00000074\",\"Principal\":\"PERSONAL\",\"Interest\":\"Closed\",\"Recd_Amount\":\"48\",\"EMI_Amount\":\"18\",\"EMI_Date\":\"1469\",\"EMI_Billed\":\"50000\"}],\"OverDue_List\":[{\"Charge_Date\":\"IPERNDA00000074\",\"Charge_Desc\":\"PERSONAL\",\"Charge_Amt\":\"Closed\",\"Received_Amt\":\"48\",\"Pending_Amt\":\"18\"}]}";

	JSONObject jsonObject = CacheServices.parseString(res);

	logger.info("json :"+jsonObject);
	mainObject.put("success", jsonObject.get("success"));
	mainObject.put("status", jsonObject.get("status"));

	try {
		logger.info("Scheduled_List :"+jsonObject.get("Scheduled_List"));
		if (jsonObject.get("Scheduled_List")!=null) {
		array2 = new JSONArray();
		array = (JSONArray) (jsonObject.get("Scheduled_List") == null?"":jsonObject.get("Scheduled_List"));
	
		for (int i = 0; i < array.size(); i++) {
		
			JSONObject jsonObject2 = (JSONObject) array.get(i);
			scheduleObject =  new JSONObject();
			scheduleObject.put("installment_No",jsonObject2.get("Installment_No"));
			scheduleObject.put("principal",jsonObject2.get("Principal"));
			scheduleObject.put("interest",jsonObject2.get("Interest"));
			scheduleObject.put("recd_Amount",jsonObject2.get("Recd_Amount"));
			scheduleObject.put("emi_Amount",jsonObject2.get("EMI_Amount"));
			scheduleObject.put("emi_Date",jsonObject2.get("EMI_Date"));
			scheduleObject.put("emi_Billed",jsonObject2.get("EMI_Billed"));
			array2.add(scheduleObject);
		
		}
		mainObject.put("scheduled_List",array2);
		}
		
		logger.info("scheduled_List :"+mainObject.get("scheduled_List"));
		logger.info("overDue_List :"+jsonObject.get("OverDue_List"));
		if (jsonObject.get("OverDue_List")!=null) {
			array2 = new JSONArray();
			array = (JSONArray) (jsonObject.get("OverDue_List") == null?"":jsonObject.get("OverDue_List"));
		
			for (int i = 0; i < array.size(); i++) {
			
				JSONObject jsonObject2 = (JSONObject) array.get(i);
				ovrdueObject =  new JSONObject();
				ovrdueObject.put("charge_Date",jsonObject2.get("Charge_Date"));
				ovrdueObject.put("charge_Desc",jsonObject2.get("Charge_Desc"));
				ovrdueObject.put("charge_Amt",jsonObject2.get("Charge_Amt"));
				ovrdueObject.put("received_Amt",jsonObject2.get("Received_Amt"));
				ovrdueObject.put("pending_Amt",jsonObject2.get("Pending_Amt"));
				array2.add(ovrdueObject);
			}
			mainObject.put("overDue_List",array2);
		}
				
		logger.info("overDue_List :"+mainObject.get("overDue_List"));
		return mainObject.toJSONString();
	} catch (Exception e) {
      e.printStackTrace();
	}
	
	return null;
}



}