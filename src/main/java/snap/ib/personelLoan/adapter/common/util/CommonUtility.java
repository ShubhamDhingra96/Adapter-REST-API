package snap.ib.personelLoan.adapter.common.util;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;
import com.ibm.json.java.JSONObject;
import com.snap.ib.personelLoan.common.rest.client.RequestMethod;
import com.snap.ib.personelLoan.common.rest.client.RestClient;

import snap.ib.personelLoan.adapter.common.CommonAdapterResource;
import snap.ib.personelLoan.adapter.common.exception.CommonException;
import snap.ib.personelLoan.adapter.common.request.CommonRequest;
import snap.ib.personelLoan.adapter.common.request.LoginAttemptsRequest;
import snap.ib.personelLoan.adapter.common.request.ServeletEEncRequest;
import snap.ib.personelLoan.adapter.common.response.CommonResponse;
import snap.ib.personelLoan.adapter.common.response.LoginAttemptsResponse;
import snap.ib.personelLoan.adapter.common.serviceImpl.IVLCommonServiceImpl;
import snap.ib.personelLoan.adapter.common.sessionutil.CustomSessionServiceImpl;
import snap.ib.personelLoan.adapter.common.sessionutil.SessionData;

public class CommonUtility {

	private static Logger logger = LoggerFactory.getLogger(CommonAdapterResource.class);
	private static final ReadProperty readProperties = ReadProperty.getInstance();
	private final static String isEnc =readProperties.getPropertyValue("IS_ENC");
	public static String callRestClient(String payload, String serviceURL) {

		JSONObject jsonObject;
		String responseStr = null;
		try {

			logger.info("Service Request: " + payload + " serviceURL: " + serviceURL);
			jsonObject = JSONObject.parse(payload);
			RestClient client = new RestClient(serviceURL);
			client.setPostData(jsonObject.toString());
			client.AddHeader("Content-Type", "application/json");
			client.AddHeader("Accept", "application/json");
			client.AddHeader("Content-Type", "application/json");
			client.AddHeader("Accept", "application/json");
			client.AddHeader("Accept-Encoding", "gzip, deflate");
			//client.AddHeader("Accept-Encoding", "gzip");
			client.AddHeader("Content-Encoding", "gzip");
			
			client.execute(RequestMethod.POST);
			responseStr = client.getResponse();
			logger.info("ESB generated Response: " + responseStr);

		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return responseStr;
	}

	public static boolean validateRequest(String isEnc, CommonRequest request, AESEncDec aesEncDec) {

		boolean isValidRequest = false;
		boolean isValidCheckSum = false;
		String input = request.getInputString();
		logger.info("input= " + input);
		String checksum = request.getChkSum();
		logger.info("checksum= " + checksum);
		isValidCheckSum = aesEncDec.validateChecksum(input, checksum, isEnc);
		logger.info("isValidCheckSum= " + isValidCheckSum);
		
		if (!Strings.isNullOrEmpty(input) && !Strings.isNullOrEmpty(checksum) && isValidCheckSum) {
			isValidRequest = true;
		}
		logger.info("isValidRequest= " + isValidRequest);
		return isValidRequest;
	}

	public static CommonResponse generateCommonResponse(String key, String responseStr, String isEnc,
			AESEncDec aesEncDec, ReadProperty readProperties) {
        logger.info("generateCommonResponse::::");
		CommonResponse response = new CommonResponse();
		if (!Strings.isNullOrEmpty(responseStr)) {

			try {

				String encResponse = aesEncDec.encrypt(key, responseStr, isEnc);
				String encCheckSum = AESEncDec.createChecksum(encResponse, isEnc);

				response.setOutputString(encResponse);
				response.setChkSum(encCheckSum);
				response.setSuccess(true);
				response.setError_message(null);
				logger.info("CommonResponse::::"+response);
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {

			response.setOutputString(null);
			response.setChkSum(null);
			response.setSuccess(false);
			response.setError_message(readProperties.getPropertyValue("REQUESTERROR").trim());
			logger.info("CommonResponse::::"+response);
		}
		return response;
	}

	public static CommonResponse setCommonResponse(CommonResponse response, ReadProperty readProperties) {

		try {
			/*
			 * String randomKey=AESEncDec.getRandomKey(2); logger.info(
			 * "randomKey: " + randomKey); String
			 * encrandomkey=AESEncDec.encryptRamdomKey(randomKey); logger.info(
			 * "encrandomkey: " + encrandomkey); if (response != null) {
			 * response.setOutputString(null); response.setChkSum(null);
			 * response.setSuccess(false); response.setSalt(encrandomkey);
			 * response.setError_message(readProperties.getPropertyValue(
			 * "COMMONINPUTERROR").trim());
			 * }
			 */
		} catch (Exception e) {
			e.printStackTrace();
		}

		return response;
	}

	public static CommonResponse setCommonResponse(CommonResponse response, AESEncDec aesEncDec,
			String key, String isEnc, String message) {

		try {

			message = "{\"error_message\":\"" + message + "\",\"success\":false}";
			String encResponse = aesEncDec.encrypt(key, message, isEnc);
			String encCheckSum = AESEncDec.createChecksum(encResponse, isEnc);
			response.setOutputString(encResponse);
			response.setChkSum(encCheckSum);
			response.setSuccess(false);
			logger.info("common response:::"+response);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return response;
	}

	public static CommonResponse setCommonResponse(String message) {
		logger.info("consentResponse in case of error::");
		CommonResponse response=new CommonResponse();
		try {
			if (response != null) {

				response.setOutputString(null);
				response.setChkSum(null);
				response.setSuccess(false);
				response.setError_message(message);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	public static CommonResponse setCommonResponseForException(CommonResponse response, ReadProperty readProperties) {

		logger.info("setCommonResponseForException: -->");
		try {

			if (response != null) {

				response.setOutputString(null);
				response.setChkSum(null);
				response.setSuccess(false);
				response.setError_message(readProperties.getPropertyValue("EXCEPTIONERROR").trim());

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return response;
	}

	@SuppressWarnings("unused")
	public static LoginAttemptsResponse callLoginAttemptService(boolean isInCurrectOTP, String param,
			String mobileNumber) throws CommonException {

		LoginAttemptsRequest attemptsRequest = new LoginAttemptsRequest();
		LoginAttemptsResponse attemptsResponse = new LoginAttemptsResponse();
		String message = null;
		boolean _isError = false;
		logger.info("mpin/otp is inCurrect: " + isInCurrectOTP + "  otp/mpin: " + param);

		if (param.length() == 4) {
			if (isInCurrectOTP) {
				attemptsRequest.setWrong_mpin("true");
				attemptsRequest.setMpin_count("");
				attemptsRequest.setMobile_number(mobileNumber);
				attemptsRequest.setWrong_otp("");
				attemptsRequest.setOtp_count("");
				attemptsRequest.setAdditional_fld1("");
				attemptsRequest.setAdditional_fld2("");
				attemptsRequest.setAdditional_fld3("");
				logger.info("login attemptsRequest: " +attemptsRequest);
				attemptsResponse = IVLCommonServiceImpl.getLoginAttemptsStatus(attemptsRequest);
			} else {
				attemptsRequest.setWrong_mpin("false");
				attemptsRequest.setMpin_count("");
				attemptsRequest.setMobile_number(mobileNumber);
				attemptsRequest.setWrong_otp("");
				attemptsRequest.setOtp_count("");
				attemptsRequest.setAdditional_fld1("");
				attemptsRequest.setAdditional_fld2("");
				attemptsRequest.setAdditional_fld3("");
				logger.info("login attemptsRequest: " +attemptsRequest);
				attemptsResponse = IVLCommonServiceImpl.getLoginAttemptsStatus(attemptsRequest);
			}
		} else {

			if (isInCurrectOTP) {
				message = CustomSessionServiceImpl.message;
				attemptsRequest.setWrong_mpin("");
				attemptsRequest.setMpin_count("");
				attemptsRequest.setMobile_number(mobileNumber);
				attemptsRequest.setWrong_otp("true");
				attemptsRequest.setOtp_count("");
				attemptsRequest.setAdditional_fld1("");
				attemptsRequest.setAdditional_fld2("");
				attemptsRequest.setAdditional_fld3("");
				logger.info("login attemptsRequest: " +attemptsRequest);
				attemptsResponse = IVLCommonServiceImpl.getLoginAttemptsStatus(attemptsRequest);
			} else {
				attemptsRequest.setWrong_mpin("");
				attemptsRequest.setMpin_count("");
				attemptsRequest.setMobile_number(mobileNumber);
				attemptsRequest.setWrong_otp("false");
				attemptsRequest.setOtp_count("");
				attemptsRequest.setAdditional_fld1("");
				attemptsRequest.setAdditional_fld2("");
				attemptsRequest.setAdditional_fld3("");
				logger.info("login attemptsRequest: " +attemptsRequest);
				attemptsResponse = IVLCommonServiceImpl.getLoginAttemptsStatus(attemptsRequest);
			}
		}
		logger.info("attemptsResponse :" + attemptsResponse);
		return attemptsResponse;
	}

	public static String setSessionDataForCache(String mobileNumber, String deviceId, String sessionKey, String leadid,
			String crm_id, String key, String otp, String mpin, String aadharNumber, String transactionId,String serviceCompletionTime,String documentList)
			throws Exception {

		SessionData data = new SessionData();
		data.setMobilenumber(mobileNumber);
		data.setAadharnumber(aadharNumber);
		data.setServiceCompletionTime(serviceCompletionTime);
		data.setDocuments(documentList);
		data.setTxnid(transactionId);
		data.setDeviceid(deviceId);
		data.setLeadid(leadid);
		data.setCrmid(crm_id);
		data.setEncKey(key);
		data.setOtp(otp);
		data.setMpin(mpin);
		String cacheObj = new CustomSessionServiceImpl().saveDyncacheData(sessionKey, deviceId, data);
		return cacheObj;
	}

	public static CommonResponse generateCommonResponseForServletE(String responseStr, ServeletEEncRequest request) {
		logger.info("generateCommonResponseForServletE [ "+responseStr+" ]");
		CommonResponse response = new CommonResponse();
		String randomKey = "";
		String encrandomkey = "";
		String deviceId = "";
		String enckey = "";
		String sessionkey = "";
		String devicetype = "";
		try {

			if (!Strings.isNullOrEmpty(responseStr)) {

				String encResponse;
				devicetype = request.getDeviceOs();
				try {
					// response = generateCommonResponse(responseStr, request,devicetype);
					randomKey = SecureBuilderServletD.getRandomKey(2);
					logger.info("randomKey for enc : " + randomKey);
					encrandomkey = SecureBuilderServletD.encryptRamdomKey(randomKey);
					logger.info("encrandomkey for enc : " + encrandomkey);
					encResponse = SecureBuilderServletD.encryptServletEResponse(encrandomkey, responseStr, "servletdE", devicetype,isEnc);
					logger.info("enc after: " + encResponse);
					String checkSum = SecureBuilderServletD.checkSumSHA256(encResponse);
					response.setOutputString(encResponse);
					response.setChkSum(checkSum);
					response.setResponseToken(encrandomkey);
					response.setSuccess(true);
					response.setError_message(null);
				} catch (Exception e) {
					e.printStackTrace();
				}

			} else {

				response.setOutputString(null);
				response.setChkSum(null);
				response.setSuccess(false);
				response.setResponseToken(encrandomkey);
				response.setError_message(readProperties.getPropertyValue("REQUESTERROR").trim());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}
}
