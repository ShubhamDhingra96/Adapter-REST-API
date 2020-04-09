package snap.ib.personelLoan.adapter.common.serviceImpl;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.Security;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ibm.json.java.JSONObject;
import com.snap.ib.personelLoan.common.ApplicationConstant;
import com.snap.ib.personelLoan.common.BaseResource;

import net.sf.jmimemagic.Magic;
import net.sf.jmimemagic.MagicMatch;
import snap.ib.personelLoan.adapter.common.CommonAdapterResource;
import snap.ib.personelLoan.adapter.common.beans.ObjectRequestWrapper;
import snap.ib.personelLoan.adapter.common.exception.CommonException;
import snap.ib.personelLoan.adapter.common.request.*;
import snap.ib.personelLoan.adapter.common.response.*;
import snap.ib.personelLoan.adapter.common.service.ICommonService;
import snap.ib.personelLoan.adapter.common.sessionutil.*;
import snap.ib.personelLoan.adapter.common.util.*;

/**
 * @author AdityaSharma
 * 
 */
public class CommonServiceImpl extends BaseResource implements ICommonService {

	private static Logger logger = LoggerFactory.getLogger(CommonAdapterResource.class);
	private static ICommonService sevice;
	private static final ReadProperty readProperties = ReadProperty.getInstance();
	private final String isEnc = readProperties.getPropertyValue("IS_ENC");
	private final String localURL = readProperties.getPropertyValue("UATENVURL") == null ? ""
			: readProperties.getPropertyValue("UATENVURL");
	public static String vendor = "/home/snap74/Desktop/Adapter/IndiaBullsAdapter/indiabullsVentureTest";
	static String emailId = "adityan@techepoch.com";
	static String server = "demo.perfios.com";

	public static ICommonService getInstance() {

		if (sevice == null) {

				sevice = new CommonServiceImpl();
		}
		return sevice;
	}

	/**
	 * Function used to get MobileRegistration status.
	 * 
	 * @param request
	 * @return response
	 */
	public CommonResponse getMobileRegistrationStatus(CommonRequest request) throws CommonException {

		long startTime = System.currentTimeMillis();
		logger.info("StartTime of mobileRegistration Service [" + startTime + " ]");
		AESEncDec aesEncDec = new AESEncDec();
		CommonResponse response = new CommonResponse();
		LoginAttemptsResponse attemptsResponse = new LoginAttemptsResponse();
		boolean _isError = false;
		String responseStr = null, message = null, deviceId = "", sessionKey = "", record_access = "";

		try {

			deviceId = request.getDevice_id();
			sessionKey = request.getSession_key();
			boolean isValidRequest = CommonUtility.validateRequest(isEnc, request, aesEncDec);

			if (isValidRequest) {

				String key = AESEncDec.generateRandomKey(deviceId, sessionKey, isEnc);
				logger.info("key= " + key);
				String inputString = aesEncDec.decrypt(key, request.getInputString(), isEnc);
				logger.info("decrypted inputString= " + inputString);

				Gson gson = new GsonBuilder().setPrettyPrinting().create();
				MobileRegistrationRequest serviceRequest = gson.fromJson(inputString, MobileRegistrationRequest.class);
				logger.info("StartTime of mobileRegistration::: " + startTime + " ::for mobile:: "
						+ serviceRequest.getMobile_number());
				String leadId = serviceRequest.getMobile_number();
				String mobileNumber = serviceRequest.getMobile_number();
				logger.info("leadId= " + leadId);
				String otp = serviceRequest.getOtp();
				String cacheRes = new CustomSessionServiceImpl().getDyncacheData(sessionKey, deviceId);

				org.json.simple.JSONObject cachejsonObject = CacheServices.parseString(cacheRes);
				boolean isInCurrectOTP = CustomSessionServiceImpl.validateOtp(otp, cachejsonObject);
				logger.info("isInCurrectOTP :" + isInCurrectOTP);

				if (Strings.isNullOrEmpty(mobileNumber)) {
					_isError = true;
					message = readProperties.getPropertyValue("MOBILEERRORMESSAGE").trim();
				}
				if (isInCurrectOTP) {
					_isError = true;
					message = CustomSessionServiceImpl.message;
					attemptsResponse = CommonUtility.callLoginAttemptService(isInCurrectOTP, otp, mobileNumber);

				} else if (CustomSessionServiceImpl.validateMobileNumber(sessionKey, deviceId, mobileNumber)) {
					_isError = true;
					message = CustomSessionServiceImpl.message;
				}

				logger.info("error message :" + message);
				if (!_isError) {

					String mpin = new MPINGenerationResponse().generateMPIN(mobileNumber);
					serviceRequest.setMpin(mpin);
					serviceRequest.setDevice_ID(serviceRequest.getDevice_number());
					serviceRequest.setLead_id("");
					// serviceRequest.setLoanExt_id("");
					// serviceRequest.setOpp_id("");
					serviceRequest.setOtp(null);
					serviceRequest.setDevice_number(null);
					String payload = gson.toJson(serviceRequest);
					final String serviceURL = localURL+readProperties.getPropertyValue("MOBILEREGISTRATIONURL").trim();
					responseStr = CommonUtility.callRestClient(payload, serviceURL);
					response = CommonUtility.generateCommonResponse(key, responseStr, isEnc, aesEncDec, readProperties);
					MobileRegistrationResponse mobileRegistrationResponse = gson.fromJson(responseStr,
							MobileRegistrationResponse.class);
					String leadid = mobileRegistrationResponse.getLead_id();
					SessionData data = new SessionData();
					data.setMobilenumber(mobileNumber);
					data.setDeviceid(deviceId);
					data.setLeadid(leadid);
					data.setEncKey(key);
					new CustomSessionServiceImpl().saveDyncacheData(sessionKey, deviceId, data);
					long endTime = System.currentTimeMillis();
					logger.info("EndTime of mobileRegistration Service:" + endTime + "\t"
							+ "TotalTime of mobileRegistration Service:" + (endTime - startTime));
				} else {
					record_access = attemptsResponse.getRecord_status();
					if (null != record_access && record_access.equals("locked")) {
						message = attemptsResponse.getError_message();
					}
					response = CommonUtility.setCommonResponse(response, aesEncDec, key, isEnc, message);
				}
			}

			else {
				message = readProperties.getPropertyValue("COMMONINPUTERROR").trim();
				response = CommonUtility.setCommonResponse(message);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return response;
	}

	/**
	 * Function is used to get personal loan status,user applied for.
	 * 
	 * @param LoanApplicationRequest
	 * @return LoanApplicationResponse
	 */

	public CommonResponse getUserLoanDetails(CommonRequest request) throws CommonException {

		long startTime = System.currentTimeMillis();
		logger.info("StartTime of UserLoanDetails Service [" + startTime + " ]");
		AESEncDec aesEncDec = new AESEncDec();
		CommonResponse response = new CommonResponse();
		String responseStr = null;
		String message = null;
		String deviceId = "", sessionKey = "";
		try {
			deviceId = request.getDevice_id();
			sessionKey = request.getSession_key();

			boolean isValidRequest = CommonUtility.validateRequest(isEnc, request, aesEncDec);
			if (isValidRequest) {

				String key = AESEncDec.generateRandomKey(deviceId, sessionKey, isEnc);
				logger.info("key= " + key);
				String inputString = aesEncDec.decrypt(key, request.getInputString(), isEnc);
				logger.info("UserLoanDetails Req Json= " + inputString);

				Gson gson = new GsonBuilder().setPrettyPrinting().create();
				LoanApplicationRequest serviceRequest = gson.fromJson(inputString, LoanApplicationRequest.class);
				String leadId = serviceRequest.getLead_id();
				logger.info("leadId= " + leadId);

				if (!Strings.isNullOrEmpty(leadId)) {

					String payload = gson.toJson(serviceRequest);
					final String serviceURL = localURL+readProperties.getKeyValue("LOANAPPLICATIONURL").trim();
					responseStr = CommonUtility.callRestClient(inputString, serviceURL);
					logger.info("UserLoanDetails responseStr from ESB:: = " + responseStr);
					response = CommonUtility.generateCommonResponse(key, responseStr, isEnc, aesEncDec, readProperties);
					long endTime = System.currentTimeMillis();
					logger.info("EndTime of UserLoanDetails Service [" + endTime + " ]");
					logger.info("TotalTime of UserLoanDetails Service [" + (endTime - startTime) + " ]");
				}
			} else {
				message = readProperties.getPropertyValue("COMMONINPUTERROR").trim();
				response = CommonUtility.setCommonResponse(message);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return response;
	}

	/**
	 * Function used to get ForgotMpin status
	 * 
	 * @param ForgotMpinRequest
	 * @return ForgotMpinResponse
	 */

	public CommonResponse getForgotMpinStatus(CommonRequest request) throws CommonException {

		long startTime = System.currentTimeMillis();
		logger.info("StartTime of ForgotMpin Service [" + startTime + " ]");
		AESEncDec aesEncDec = new AESEncDec();
		CommonResponse response = new CommonResponse();
		LoginAttemptsRequest attemptsRequest = new LoginAttemptsRequest();
		LoginAttemptsResponse attemptsResponse = new LoginAttemptsResponse();
		String responseStr = null;
		String message = null;
		String deviceId = "", sessionKey = "", record_access = "";

		try {
			deviceId = request.getDevice_id();
			sessionKey = request.getSession_key();
			boolean isValidRequest = CommonUtility.validateRequest(isEnc, request, aesEncDec);

			if (isValidRequest) {

				String key = AESEncDec.generateRandomKey(deviceId, sessionKey, isEnc);
				logger.info("key= " + key);
				String inputString = aesEncDec.decrypt(key, request.getInputString(), isEnc);
				logger.info("decrypted inputString= " + inputString);

				Gson gson = new GsonBuilder().setPrettyPrinting().create();
				ForgotMpinRequest forgotMpinRequest = gson.fromJson(inputString, ForgotMpinRequest.class);

				String mobileNumber = forgotMpinRequest.getMobile_number();
				logger.info("mobileNumber= " + mobileNumber);

				if (!Strings.isNullOrEmpty(mobileNumber)) {

					attemptsRequest.setWrong_mpin("");
					attemptsRequest.setMpin_count("");
					attemptsRequest.setMobile_number(mobileNumber);
					attemptsRequest.setWrong_otp("");
					attemptsRequest.setOtp_count("true");
					attemptsRequest.setAdditional_fld1("");
					attemptsRequest.setAdditional_fld2("");
					attemptsRequest.setAdditional_fld3("");
					attemptsResponse = IVLCommonServiceImpl.getLoginAttemptsStatus(attemptsRequest);
					record_access = attemptsResponse.getRecord_status().trim();

					if (null != record_access && !record_access.equals("locked")) {

						String otp = OTPGeneration.generateOTP(mobileNumber);
						forgotMpinRequest.setOTP(otp);
						String payload = gson.toJson(forgotMpinRequest);
						final String serviceURL = localURL+readProperties.getPropertyValue("FORGOTMPINURL").trim();
						responseStr = CommonUtility.callRestClient(payload, serviceURL);
						response = CommonUtility.generateCommonResponse(key, responseStr, isEnc, aesEncDec,
								readProperties);
						String cacheRes = new CustomSessionServiceImpl().getDyncacheData(sessionKey, deviceId);
						org.json.simple.JSONObject cachejsonObject = CacheServices.parseString(cacheRes);

						String crm_id = cachejsonObject.get("crmid") == null ? ""
								: cachejsonObject.get("crmid").toString();
						String leadid = cachejsonObject.get("leadid") == null ? ""
								: cachejsonObject.get("leadid").toString();
						String mpin = cachejsonObject.get("mpin") == null ? "" : cachejsonObject.get("mpin").toString();

						SessionData data = new SessionData();
						data.setMobilenumber(mobileNumber);
						data.setDeviceid(deviceId);
						data.setLeadid(leadid);
						data.setCrmid(crm_id);
						data.setEncKey(key);
						data.setOtp(otp);
						data.setMpin(mpin);
						new CustomSessionServiceImpl().saveDyncacheData(sessionKey, deviceId, data);
						long endTime = System.currentTimeMillis();
						logger.info("EndTime of ForgotMpin Service [" + endTime + " ]");
						logger.info("TotalTime of ForgotMpin Service [" + (endTime - startTime) + " ]");
					} else {
						record_access = attemptsResponse.getRecord_status();
						if (null != record_access && record_access.equals("locked")) {
							message = attemptsResponse.getError_message();
						}
						response = CommonUtility.setCommonResponse(response, aesEncDec, key, isEnc, message);
					}
				}
			}

			else {
				message = readProperties.getPropertyValue("COMMONINPUTERROR").trim();
				response = CommonUtility.setCommonResponse(message);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return response;
	}

	/**
	 * Function used to get AadharDropOff status
	 * 
	 * @param AadharDropOffRequest
	 * @return AadharDropOffResponse
	 */
	public CommonResponse getAadharDropOffStatus(CommonRequest request) throws CommonException {

		long startTime = System.currentTimeMillis();
		logger.info("StartTime of AadharDropOff Service [" + startTime + " ]");
		AESEncDec aesEncDec = new AESEncDec();
		CommonResponse response = new CommonResponse();
		String responseStr = null;
		String message = null;
		String deviceId = "", sessionKey = "";
		try {

			deviceId = request.getDevice_id();
			sessionKey = request.getSession_key();
			boolean isValidRequest = CommonUtility.validateRequest(isEnc, request, aesEncDec);
			if (isValidRequest) {

				String key = AESEncDec.generateRandomKey(deviceId, sessionKey, isEnc);
				logger.info("key= " + key);
				String inputString = aesEncDec.decrypt(key, request.getInputString(), isEnc);
				logger.info("decrypted inputString= " + inputString);

				Gson gson = new GsonBuilder().setPrettyPrinting().create();
				AadharDropOffRequest aadharDropOffRequest = gson.fromJson(inputString, AadharDropOffRequest.class);
				String leadId = aadharDropOffRequest.getLead_id();
				logger.info("leadId= " + leadId);

				if (!Strings.isNullOrEmpty(leadId)) {

					String payload = gson.toJson(aadharDropOffRequest);
					final String aadharDropOffURL = localURL+readProperties.getPropertyValue("AADHARDROPOFFURL").trim();
					responseStr = CommonUtility.callRestClient(payload, aadharDropOffURL);
					response = CommonUtility.generateCommonResponse(key, responseStr, isEnc, aesEncDec, readProperties);
					long endTime = System.currentTimeMillis();
					logger.info("EndTime of AadharDropOff Service [" + endTime + " ]");
					logger.info("TotalTime of AadharDropOff Service [" + (endTime - startTime) + " ]");
				}
			}

			else {
				message = readProperties.getPropertyValue("COMMONINPUTERROR").trim();
				response = CommonUtility.setCommonResponse(message);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return response;
	}

	/**
	 * Function used to authenticate OTP Token
	 * 
	 * @param OTPAuthenticationRequest
	 * @return OTPAuthenticationResponse
	 */

	public CommonResponse aadharOTPAuthentication(CommonRequest request) throws CommonException {

		long startTime = System.currentTimeMillis();
		logger.info("StartTime of OTPAuthentication Service [" + startTime + " ]");
		AESEncDec aesEncDec = new AESEncDec();
		CommonResponse response = new CommonResponse();
		String responseStr = null;
		boolean _isError = false;
		String message = null;
		String deviceId = "", sessionKey = "";

		try {
			deviceId = request.getDevice_id();
			sessionKey = request.getSession_key();
			boolean isValidRequest = CommonUtility.validateRequest(isEnc, request, aesEncDec);
			if (isValidRequest) {

				String key = AESEncDec.generateRandomKey(deviceId, sessionKey, isEnc);
				logger.info("key= " + key);
				String inputString = aesEncDec.decrypt(key, request.getInputString(), isEnc);
				logger.info("decrypted inputString= " + inputString);

				Gson gson = new GsonBuilder().setPrettyPrinting().create();
				OTPAuthenticationRequest serviceRequest = gson.fromJson(inputString, OTPAuthenticationRequest.class);
				logger.info("StartTime of aadharOTPAuth:: " + startTime + " ::for lead:: " + serviceRequest.getLead_id());
				String leadId = serviceRequest.getLead_id();
				String aadharNO = serviceRequest.getAadhaar_no();
				String otp = serviceRequest.getOtp();

				if (Strings.isNullOrEmpty(leadId)) {
					_isError = true;
					message = readProperties.getPropertyValue("LEADIDERRORMESSAGE").trim();
				} else if (Strings.isNullOrEmpty(aadharNO)) {
					_isError = true;
					message = readProperties.getPropertyValue("AADHARNUMBERERROR").trim();
				} else if (Strings.isNullOrEmpty(otp)) {
					_isError = true;
					message = readProperties.getPropertyValue("OTPERROR").trim();
				}

				String cacheRes = new CustomSessionServiceImpl().getDyncacheData(sessionKey, deviceId);
				org.json.simple.JSONObject cachejsonObject = CacheServices.parseString(cacheRes);

				if (CustomSessionServiceImpl.validateDetails("", cachejsonObject, "", leadId, "")) {
					_isError = true;
					message = CustomSessionServiceImpl.message;
				}

				otp = AaddharotpIntegration.GetEncryptedString(otp);
				serviceRequest.setOtp(otp);

				if (!_isError) {

					String payload = gson.toJson(serviceRequest);
					final String serviceURL = localURL+readProperties.getPropertyValue("OTPAUTHENTICATIONURL").trim();
					responseStr = CommonUtility.callRestClient(payload, serviceURL);
					response = CommonUtility.generateCommonResponse(key, responseStr, isEnc, aesEncDec, readProperties);
					long endTime = System.currentTimeMillis();
					logger.info("EndTime of OTPAuthentication Service [" + endTime + " ]");
					logger.info("TotalTime of OTPAuthentication Service [" + (endTime - startTime) + " ]");
				}
			}

			else {
				message = readProperties.getPropertyValue("COMMONINPUTERROR").trim();
				response = CommonUtility.setCommonResponse(message);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return response;
	}

	/**
	 * Function used to generate OTP Token
	 * 
	 * @param OTPGenerationRequest
	 * @return OTPGenerationResponse
	 */

	public CommonResponse aadharOTPGeneration(CommonRequest request, @Context HttpHeaders headers) throws Exception {

		long startTime = System.currentTimeMillis();
		logger.info("StartTime of aadharOTPGeneration Service [" + startTime + " ]");
		AESEncDec aesEncDec = new AESEncDec();
		CommonResponse response = new CommonResponse();
		String responseStr = null;
		boolean _isError = false;
		String message = null;
		long timeDifference = 0;
		String deviceId = "", sessionKey = "", mobileNumber = "";
		boolean isConsent = false;
		ConsentResponse consentResponse = null;

		try {

			deviceId = request.getDevice_id();
			sessionKey = request.getSession_key();
			boolean isValidRequest = CommonUtility.validateRequest(isEnc, request, aesEncDec);
			if (isValidRequest) {

				String key = AESEncDec.generateRandomKey(deviceId, sessionKey, isEnc);
				logger.info("key= " + key);
				String inputString = aesEncDec.decrypt(key, request.getInputString(), isEnc);
				logger.info("decrypted inputString= " + inputString);

				Gson gson = new GsonBuilder().setPrettyPrinting().create();
				OTPGenerationRequest serviceRequest = gson.fromJson(inputString, OTPGenerationRequest.class);
				logger.info("StartTime of aadharOTPGeneration:: " + startTime + " :: for lead:: "
						+ serviceRequest.getLead_id());
				String leadId = serviceRequest.getLead_id();
				String aadharNo = serviceRequest.getAadhaar_no();
				String aadharSubString = aadharNo.substring(0, 8);
				logger.info("aadharSubString:= " + aadharSubString);
				aadharNo = aadharNo.replace(aadharSubString, "XXXXXXXX");
				logger.info("aadharNo:= " + aadharNo);
				String consentText = readProperties.getPropertyValue("CONSENTTEXTKEY").trim();
				consentText = consentText.replace("XXXXXXXXXXXX", aadharNo);
				logger.info("consentText:= " + consentText);
				serviceRequest.setConsent_data(consentText);

				try {

					String ipaddress = headers.getHeaderString("X-Forwarded-For");
					logger.info("ipaddress from client :" + ipaddress);
					if (ipaddress != null) {
						if (ipaddress.contains(",")) {
							String araay[] = ipaddress.split(",");
							ipaddress = araay[0];
						}
					}

					String consentRes = new IVLCommonServiceImpl().callCustomerConsentService("kyc", leadId, ipaddress,
							"");
					consentResponse = gson.fromJson(consentRes, ConsentResponse.class);
					isConsent = consentResponse.isSuccess();

				} catch (Exception e) {
					e.printStackTrace();
				}
				if (isConsent) {

					String aadharNO = serviceRequest.getAadhaar_no();

					if (Strings.isNullOrEmpty(leadId)) {
						_isError = true;
						message = readProperties.getPropertyValue("LEADIDERRORMESSAGE").trim();
						logger.info("message" + message);
					}
					if (Strings.isNullOrEmpty(aadharNO)) {
						_isError = true;
						message = readProperties.getPropertyValue("AADHARNUMBERERROR").trim();
						logger.info("message" + message);
					}

					String cacheRes = new CustomSessionServiceImpl().getDyncacheData(sessionKey, deviceId);
					org.json.simple.JSONObject cachejsonObject = CacheServices.parseString(cacheRes);

					logger.info("cachejsonObject in otpGeneration: " + cachejsonObject);
					String serviceCompletionTime = (String) cachejsonObject.get("serviceCompletionTime");

					logger.info("serviceCompletionTime: " + serviceCompletionTime);

					if (serviceCompletionTime == null || serviceCompletionTime.equals("")
							|| serviceCompletionTime.equals("null")) {
						serviceCompletionTime = "0";
					}

					if (CustomSessionServiceImpl.validateDetails("", cachejsonObject, "", leadId, "")) {
						_isError = true;
						message = CustomSessionServiceImpl.message;
					}

					if (!serviceCompletionTime.equals("0")) {

						long previousTime = Long.parseLong(serviceCompletionTime);
						timeDifference = CalculateTime.getTimeInSeconds(previousTime, System.currentTimeMillis());

						long otpGenTime = Integer.valueOf(readProperties.getPropertyValue("OTPGENTIME").trim());
						logger.info("otpGenTime If: " + otpGenTime);

						if (timeDifference <= otpGenTime) {
							_isError = true;
							message = readProperties.getPropertyValue("OTPGENERROR").trim();
							logger.info("timeDifference: " + timeDifference);
						}
					}

					if (!_isError) {

						String payload = gson.toJson(serviceRequest);
						logger.info("payload= " + payload);
						JSONObject jsonObject = JSONObject.parse(payload);
						logger.info("jsonObject= " + jsonObject.toString());
						logger.info("OTPGENERATIONURL= " + readProperties.getPropertyValue("OTPGENERATIONURL").trim());
						final String serviceURL = localURL+readProperties.getPropertyValue("OTPGENERATIONURL").trim();
						responseStr = CommonUtility.callRestClient(payload, serviceURL);
						response = CommonUtility.generateCommonResponse(key, responseStr, isEnc, aesEncDec,
								readProperties);
						mobileNumber = cachejsonObject.get("mobileno") == null ? ""
								: cachejsonObject.get("mobileno").toString();
						SessionData data = new SessionData();
						data.setMobilenumber(mobileNumber);
						data.setDeviceid(deviceId);
						data.setLeadid(leadId);
						data.setAadharnumber(aadharNO);
						data.setEncKey(key);
						data.setServiceCompletionTime("" + System.currentTimeMillis());
						new CustomSessionServiceImpl().saveDyncacheData(sessionKey, deviceId, data);
						long endTime = System.currentTimeMillis();
						logger.info("EndTime of aadharOTPGeneration Service [" + endTime + " ]");
						logger.info("TotalTime of aadharOTPGeneration Service [" + (endTime - startTime) + " ]");

					} else {

						response = CommonUtility.setCommonResponse(response, aesEncDec, key, isEnc, message);
					}

				} else {
					message = consentResponse.getError_message();
					response.setOutputString(null);
					response.setChkSum(null);
					response.setSuccess(false);
					response.setError_message(message);
				}
			} else {
				message = readProperties.getPropertyValue("COMMONINPUTERROR").trim();
				response = CommonUtility.setCommonResponse(message);
			}
		} catch (Exception ex) {

			ex.printStackTrace();
		}

		return response;

	}

	public CommonResponse esignAadharValidation(CommonRequest request) throws CommonException {

		long startTime = System.currentTimeMillis();
		logger.info("StartTime of esignAadharValidation Service [" + startTime + " ]");
		AESEncDec aesEncDec = new AESEncDec();
		CommonResponse response = new CommonResponse();
		String responseStr = null, message = null, deviceId = "", sessionKey = "";

		try {

			deviceId = request.getDevice_id();
			sessionKey = request.getSession_key();
			boolean isValidRequest = CommonUtility.validateRequest(isEnc, request, aesEncDec);

			if (isValidRequest) {

				String key = AESEncDec.generateRandomKey(deviceId, sessionKey, isEnc);
				String inputString = aesEncDec.decrypt(key, request.getInputString(), isEnc);
				logger.info("decrypted inputString= " + inputString);
				Gson gson = new GsonBuilder().setPrettyPrinting().create();
				EsignAadharValidationRequest aadharValidationRequest = gson.fromJson(inputString,
						EsignAadharValidationRequest.class);
				String leadId = aadharValidationRequest.getLead_id();
				logger.info("leadId= " + leadId);

				if (!Strings.isNullOrEmpty(leadId)) {

					String payload = gson.toJson(aadharValidationRequest);
					final String serviceURL = localURL+readProperties.getPropertyValue("ESIGNAADHARVALIDATEURL").trim();
					responseStr = CommonUtility.callRestClient(payload, serviceURL);
					response = CommonUtility.generateCommonResponse(key, responseStr, isEnc, aesEncDec, readProperties);

					String cacheRes = new CustomSessionServiceImpl().getDyncacheData(sessionKey, deviceId);
					org.json.simple.JSONObject cachejsonObject = CacheServices.parseString(cacheRes);
					String mobilenumber = cachejsonObject.get("mobileno") == null ? ""
							: cachejsonObject.get("mobileno").toString();
					String aadharNumber = aadharValidationRequest.getAadhaar_no();
					String leadid = aadharValidationRequest.getLead_id();
					String mpin = cachejsonObject.get("mpin") == null ? "" : cachejsonObject.get("mpin").toString();

					SessionData data = new SessionData();
					data.setMobilenumber(mobilenumber);
					data.setDeviceid(deviceId);
					data.setLeadid(leadid);
					data.setAadharnumber(aadharNumber);
					data.setEncKey(key);
					data.setMpin(mpin);
					cacheRes = new CustomSessionServiceImpl().saveDyncacheData(sessionKey, deviceId, data);
					long endTime = System.currentTimeMillis();
					logger.info("EndTime of esignAadharValidation Service [" + endTime + " ]");
					logger.info("TotalTime of esignAadharValidation Service [" + (endTime - startTime) + " ]");
				}
			}

			else {
				message = readProperties.getPropertyValue("COMMONINPUTERROR").trim();
				response = CommonUtility.setCommonResponse(message);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			CommonUtility.setCommonResponseForException(response, readProperties);
		}
		return response;
	}

	/**
	 * Function used to update MPIN
	 * 
	 * @param UpdateMpinRequest
	 * @return UpdateMpinResponse
	 */

	public CommonResponse getUpdateMpinStatus(CommonRequest request) throws CommonException {

		long startTime = System.currentTimeMillis();
		logger.info("StartTime of UpdateMpin Service [" + startTime + " ]");
		AESEncDec aesEncDec = new AESEncDec();
		CommonResponse response = new CommonResponse();
		LoginAttemptsResponse attemptsResponse = new LoginAttemptsResponse();
		String responseStr = null;
		boolean _isError = false;
		String message = null;
		String deviceId = "", sessionKey = "", record_access = "";

		try {
			deviceId = request.getDevice_id();
			sessionKey = request.getSession_key();

			boolean isValidRequest = CommonUtility.validateRequest(isEnc, request, aesEncDec);

			if (isValidRequest) {

				String key = AESEncDec.generateRandomKey(deviceId, sessionKey, isEnc);
				logger.info("key= " + key);
				String inputString = aesEncDec.decrypt(key, request.getInputString(), isEnc);
				logger.info("decrypted inputString= " + inputString);

				Gson gson = new GsonBuilder().setPrettyPrinting().create();
				UpdateMpinRequest serviceRequest = gson.fromJson(inputString, UpdateMpinRequest.class);
				String mobileNumber = serviceRequest.getMobile_number();
				logger.info("mobileNumber= " + mobileNumber);
				if (Strings.isNullOrEmpty(mobileNumber)) {
					_isError = true;
					message = readProperties.getPropertyValue("MOBILEERRORMESSAGE");
				}
				String otp = serviceRequest.getOtp();
				String cacheRes = new CustomSessionServiceImpl().getDyncacheData(sessionKey, deviceId);
				org.json.simple.JSONObject cachejsonObject = CacheServices.parseString(cacheRes);
				boolean isInCurrectOTP = CustomSessionServiceImpl.validateOtp(otp, cachejsonObject);
				logger.info("isInCurrectOTP= " + isInCurrectOTP);
				attemptsResponse = CommonUtility.callLoginAttemptService(isInCurrectOTP, otp, mobileNumber);
				
				
				if (isInCurrectOTP) {
					_isError = true;
					message =  "Please enter valid OTP";
				}

				if (!_isError) {

					String mpin = new MPINGenerationResponse().generateMPIN(mobileNumber);
					serviceRequest.setMpin(mpin);
					String payload = gson.toJson(serviceRequest);
					final String serviceURL = localURL+readProperties.getPropertyValue("UPDATEMPINURL").trim();
					responseStr = CommonUtility.callRestClient(payload, serviceURL);
					response = CommonUtility.generateCommonResponse(key, responseStr, isEnc, aesEncDec, readProperties);
					UpdateMpinResponse mpinResponse = gson.fromJson(responseStr, UpdateMpinResponse.class);

					try {
						String leadid = "";
						String mobilenumber = "";
						mobilenumber = cachejsonObject.get("mobileno") == null ? ""
								: cachejsonObject.get("mobileno").toString();
						leadid = mpinResponse.getLead_id() == null ? cachejsonObject.get("leadid").toString()
								: mpinResponse.getLead_id();
						logger.info("leadid:::= " + leadid);

						SessionData data = new SessionData();
						data.setMobilenumber(mobilenumber);
						data.setDeviceid(deviceId);
						data.setLeadid(leadid);
						data.setEncKey(key);
						data.setMpin(mpin);
						cacheRes = new CustomSessionServiceImpl().saveDyncacheData(sessionKey, deviceId, data);

					} catch (Exception e) {
						e.printStackTrace();
					}
					long endTime = System.currentTimeMillis();
					logger.info("EndTime of UpdateMpin Service [" + endTime + " ]");
					logger.info("TotalTime of UpdateMpin Service [" + (endTime - startTime) + " ]");

				} else {
					record_access = attemptsResponse.getRecord_status();
					if (null != record_access && record_access.equals("locked")) {
						message = attemptsResponse.getError_message();
					}
					response = CommonUtility.setCommonResponse(response, aesEncDec, key, isEnc, message);
				}
				
				
				
				
			}

			else {
				message = readProperties.getPropertyValue("COMMONINPUTERROR").trim();
				response = CommonUtility.setCommonResponse(message);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return response;
	}

	/**
	 * Function used to resend OTP
	 * 
	 * @param ResendOTPRequest
	 * @return ResendOTPResponse
	 */

	public CommonResponse getResendOTPStatus(CommonRequest request) throws CommonException {

		long startTime = System.currentTimeMillis();
		logger.info("StartTime of ResendOTP Service [" + startTime + " ]");
		AESEncDec aesEncDec = new AESEncDec();
		CommonResponse response = new CommonResponse();
		LoginAttemptsRequest attemptsRequest = new LoginAttemptsRequest();
		LoginAttemptsResponse attemptsResponse = new LoginAttemptsResponse();
		String responseStr = null;
		String message = null;
		String deviceId = "", sessionKey = "", record_access = "";

		try {
			deviceId = request.getDevice_id();
			sessionKey = request.getSession_key();
			boolean isValidRequest = CommonUtility.validateRequest(isEnc, request, aesEncDec);

			if (isValidRequest) {

				String key = AESEncDec.generateRandomKey(deviceId, sessionKey, isEnc);
				logger.info("key= " + key);
				String inputString = aesEncDec.decrypt(key, request.getInputString(), isEnc);
				logger.info("decrypted inputString= " + inputString);

				Gson gson = new GsonBuilder().setPrettyPrinting().create();
				ResendOTPRequest serviceRequest = gson.fromJson(inputString, ResendOTPRequest.class);
				String mobielNumber = serviceRequest.getMobile_number();
				logger.info("mobielNumber :" + mobielNumber);

				if (!Strings.isNullOrEmpty(mobielNumber)) {

					attemptsRequest.setWrong_mpin("");
					attemptsRequest.setMpin_count("");
					attemptsRequest.setMobile_number(mobielNumber);
					attemptsRequest.setWrong_otp("");
					attemptsRequest.setOtp_count("true");
					attemptsRequest.setAdditional_fld1("");
					attemptsRequest.setAdditional_fld2("");
					attemptsRequest.setAdditional_fld3("");
					attemptsResponse = IVLCommonServiceImpl.getLoginAttemptsStatus(attemptsRequest);
					record_access = attemptsResponse.getRecord_status().trim();

					if (null != record_access && !record_access.equals("locked")) {

						String otp = OTPGeneration.generateOTP(mobielNumber);
						serviceRequest.setOTP(otp);
						String payload = gson.toJson(serviceRequest);
						final String serviceURL = localURL+readProperties.getPropertyValue("RESENDOTPURL").trim();
						responseStr = CommonUtility.callRestClient(payload, serviceURL);
						response = CommonUtility.generateCommonResponse(key, responseStr, isEnc, aesEncDec,
								readProperties);

						try {

							SessionData data = new SessionData();
							data.setMobilenumber(mobielNumber);
							data.setDeviceid(deviceId);
							data.setEncKey(key);
							data.setOtp(otp);
							new CustomSessionServiceImpl().saveDyncacheData(sessionKey, deviceId, data);

						} catch (Exception e) {
							e.printStackTrace();
						}

						long endTime = System.currentTimeMillis();
						logger.info("EndTime of ResendOTP Service [" + endTime + " ]");
						logger.info("TotalTime of ResendOTP Service [" + (endTime - startTime) + " ]");
					} else {

						record_access = attemptsResponse.getRecord_status();
						if (null != record_access && record_access.equals("locked")) {
							message = attemptsResponse.getError_message();
						}
						response = CommonUtility.setCommonResponse(response, aesEncDec, key, isEnc, message);
					}
				}
			}

			else {
				message = readProperties.getPropertyValue("COMMONINPUTERROR").trim();
				response = CommonUtility.setCommonResponse(message);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return response;
	}

	/**
	 * Function used to submit Demographic information
	 * 
	 * @param DemographicInfoRequest
	 * @return DemographicInfoResponse
	 */

	public CommonResponse submitDemographicInfo(CommonRequest request) throws CommonException {

		long startTime = System.currentTimeMillis();
		logger.info("StartTime of DemographicInfo Service [" + startTime + " ]");
		AESEncDec aesEncDec = new AESEncDec();
		CommonResponse response = new CommonResponse();
		String responseStr = null;
		boolean _isError = false;
		String message = null;
		String deviceId = "", sessionKey = "";
		try {

			deviceId = request.getDevice_id();
			sessionKey = request.getSession_key();
			String key = AESEncDec.generateRandomKey(deviceId, sessionKey, isEnc);

			String cacheRes = new CustomSessionServiceImpl().getDyncacheData(sessionKey, deviceId);
			org.json.simple.JSONObject cachejsonObject = CacheServices.parseString(cacheRes);

			String isAllowed = cachejsonObject.get("isAllowed") == null ? ""
					: cachejsonObject.get("isAllowed").toString();
			String mobileNumber = cachejsonObject.get("mobileno") == null ? ""
					: cachejsonObject.get("mobileno").toString();
			String leadid = cachejsonObject.get("leadid") == null ? "" : cachejsonObject.get("leadid").toString();
			String crmid = cachejsonObject.get("crmid") == null ? "" : cachejsonObject.get("leadid").toString();
			String encKey = cachejsonObject.get("enckey") == null ? "" : cachejsonObject.get("enckey").toString();
			String otp = cachejsonObject.get("otp") == null ? "" : cachejsonObject.get("otp").toString();
			String mpin = cachejsonObject.get("mpin") == null ? "" : cachejsonObject.get("mpin").toString();

			SessionData data = new SessionData();
			data.setMobilenumber(mobileNumber);
			data.setLeadid(leadid);
			data.setCrmid(crmid);
			data.setEncKey(encKey);
			data.setOtp(otp);
			data.setMpin(mpin);
			data.setIsAllowed("No");
			data.setCheckSum(request.getChkSum());
			new CustomSessionServiceImpl().saveDyncacheData(sessionKey, deviceId, data);

			if (!Strings.isNullOrEmpty(isAllowed) && !isAllowed.equals("No")) {

				boolean isValidRequest = CommonUtility.validateRequest(isEnc, request, aesEncDec);

				if (isValidRequest) {

					logger.info("key= " + key);
					String inputString = aesEncDec.decrypt(key, request.getInputString(), isEnc);
					logger.info("SubmitDemographicRequest= " + inputString);

					Gson gson = new GsonBuilder().setPrettyPrinting().create();
					DemographicInfoRequest serviceRequest = gson.fromJson(inputString, DemographicInfoRequest.class);
					logger.info("StartTime of submitDemographic:: " + startTime + " ::for Lead:: "
							+ serviceRequest.getObjRequestWrapper().getLead_id());
					String lead_id = serviceRequest.getObjRequestWrapper().getLead_id();
					logger.info("leadId= " + lead_id);
					if (Strings.isNullOrEmpty(lead_id)) {
						_isError = true;
						message = readProperties.getPropertyValue("LEADIDERRORMESSAGE").trim();
					}

					if (CustomSessionServiceImpl.validateDetails("", cachejsonObject, "", lead_id, "")) {
						_isError = true;
						message = CustomSessionServiceImpl.message;
					}

					if (!_isError) {

						String source = readProperties.getPropertyValue("SOURCEKEY") == null ? "MOBILE"
								: readProperties.getPropertyValue("SOURCEKEY");
						logger.info("source param= " + source);
						serviceRequest.setSource(source);
						ObjectRequestWrapper objRequestWrapper = serviceRequest.getObjRequestWrapper();
						objRequestWrapper.setSource(source);
						serviceRequest.setObjRequestWrapper(objRequestWrapper);
						logger.info("objRequestWrapper source param= " + objRequestWrapper.getSource());
						logger.info("serviceRequest source param= " + serviceRequest.getSource());

						String payload = gson.toJson(serviceRequest);
						logger.info("payload= " + payload);
						JSONObject jsonObject = JSONObject.parse(payload);
						logger.info("jsonObject= " + jsonObject.toString());
						final String serviceURL = localURL+readProperties.getPropertyValue("DEMOGRAPHICINFOURL").trim();
						responseStr = CommonUtility.callRestClient(payload, serviceURL);

						DemographicInfoResponse demoInfoResponse = gson.fromJson(responseStr,
								DemographicInfoResponse.class);
						/*
						 * jsonObject = new JSONObject(); jsonObject.put("success",
						 * demoInfoResponse.isSuccess()); jsonObject.put("error_message",
						 * demoInfoResponse.getError_message()); jsonObject.put("crm_id",
						 * demoInfoResponse.getCrm_id()); jsonObject.put("status",
						 * demoInfoResponse.getStatus()); jsonObject.put("SoftApprovalDecision",
						 * demoInfoResponse.getSoftApprovalDecision());
						 * jsonObject.put("HardApprovalDecision",
						 * demoInfoResponse.getHardApprovalDecision());
						 * jsonObject.put("PerfiosRequired", demoInfoResponse.getPerfiosRequired());
						 * jsonObject.put("MaxEligibleLoanAmount",
						 * demoInfoResponse.getMaxEligibleLoanAmount());
						 * jsonObject.put("LoanAmountApplied", demoInfoResponse.getLoanAmountApplied());
						 * jsonObject.put("RateofInterest", demoInfoResponse.getRateofInterest());
						 * jsonObject.put("Tenor", demoInfoResponse.getTenor());
						 * jsonObject.put("ProcessingFees", demoInfoResponse.getProcessingFees());
						 * jsonObject.put("EMI", demoInfoResponse.getEMI());
						 * jsonObject.put("MaxEligibleEMI", demoInfoResponse.getMaxEligibleEMI());
						 * jsonObject.put("panRequired", demoInfoResponse.isPANRequired());
						 * jsonObject.put("product_type", demoInfoResponse.getProduct_type());
						 * jsonObject.put("is_bureau_decline", demoInfoResponse.isIs_bureau_decline());
						 * jsonObject.put("pincode_only_stp", demoInfoResponse.getPincode_only_stp());
						 * 
						 * responseStr = jsonObject.toString();
						 */
						boolean success = demoInfoResponse.isSuccess();
						String error = demoInfoResponse.getError_message();

						if (Strings.isNullOrEmpty(responseStr) || success != true || !Strings.isNullOrEmpty(error)) {

							logger.info("if block" + success);
							data.setMobilenumber(mobileNumber);
							data.setLeadid(leadid);
							data.setCrmid(crmid);
							data.setEncKey(encKey);
							data.setOtp(otp);
							data.setMpin(mpin);
							data.setIsAllowed("Yes");
							new CustomSessionServiceImpl().saveDyncacheData(sessionKey, deviceId, data);
						} else {

							logger.info("else block " + success);
							data.setMobilenumber(mobileNumber);
							data.setLeadid(leadid);
							data.setCrmid(crmid);
							data.setEncKey(encKey);
							data.setOtp(otp);
							data.setMpin(mpin);
							data.setIsAllowed("No");
							new CustomSessionServiceImpl().saveDyncacheData(sessionKey, deviceId, data);
						}
						response = CommonUtility.generateCommonResponse(key, responseStr, isEnc, aesEncDec,
								readProperties);
						long endTime = System.currentTimeMillis();
						logger.info("EndTime of DemographicInfo Service [" + endTime + " ]");
						logger.info("TotalTime of DemographicInfo Service [" + (endTime - startTime) + " ]");
					}
				}

				else {
					message = readProperties.getPropertyValue("COMMONINPUTERROR").trim();
					response = CommonUtility.setCommonResponse(message);
				}

			} else {
				message = readProperties.getPropertyValue("DUPREQUESTERROR").trim();
				response = CommonUtility.setCommonResponse(response, aesEncDec, key, isEnc, message);
			}

		} catch (Exception ex) {
			ex.printStackTrace();

		}

		return response;
	}

	/**
	 * Function used to fetch country and states
	 * 
	 * @param MasterDataRequest
	 * @return MasterDataResponse
	 */

	public CommonResponse getMasterData(CommonRequest request) throws CommonException {

		long startTime = System.currentTimeMillis();
		logger.info("StartTime of MasterData Service [" + startTime + " ]");
		AESEncDec aesEncDec = new AESEncDec();
		CommonResponse response = new CommonResponse();
		String responseStr = null;
		String message = null;
		String deviceId = "", sessionKey = "";

		try {
			deviceId = request.getDevice_id();
			sessionKey = request.getSession_key();
			boolean isValidRequest = CommonUtility.validateRequest(isEnc, request, aesEncDec);

			if (isValidRequest) {

				String key = AESEncDec.generateRandomKey(deviceId, sessionKey, isEnc);
				logger.info("key= " + key);
				String inputString = aesEncDec.decrypt(key, request.getInputString(), isEnc);
				logger.info("decrypted inputString= " + inputString);

				Gson gson = new GsonBuilder().setPrettyPrinting().create();
				MasterDataRequest serviceRequest = gson.fromJson(inputString, MasterDataRequest.class);
				logger.info("StartTime of MasterData:: " + startTime + " ::for state & type:: "
						+ serviceRequest.getState() + " :: " + serviceRequest.getType());
				String leadId = serviceRequest.getType();
				logger.info("leadId= " + leadId);

				if (!Strings.isNullOrEmpty(leadId)) {

					String payload = gson.toJson(serviceRequest);
					final String serviceURL = localURL+readProperties.getPropertyValue("MASTERDATAURL").trim();
					responseStr = CommonUtility.callRestClient(payload, serviceURL);
					response = CommonUtility.generateCommonResponse(key, responseStr, isEnc, aesEncDec, readProperties);
					long endTime = System.currentTimeMillis();
					logger.info("EndTime of MasterData Service [" + endTime + " ]");
					logger.info("TotalTime of MasterData Service [" + (endTime - startTime) + " ]");
				}
			}

			else {
				message = readProperties.getPropertyValue("COMMONINPUTERROR").trim();
				response = CommonUtility.setCommonResponse(message);
			}
		} catch (Exception ex) {

			ex.printStackTrace();
		}

		return response;

	}

	/**
	 * Function used for PAN verification
	 * 
	 * @param PANVarificationRequest
	 * @return PANVarificationResponse
	 */

	public CommonResponse getPANVarificationStatus(CommonRequest request) throws CommonException {

		long startTime = System.currentTimeMillis();
		logger.info("StartTime of PANVarification Service [" + startTime + " ]");
		AESEncDec aesEncDec = new AESEncDec();
		CommonResponse response = new CommonResponse();
		String responseStr = null;
		boolean _isError = false;
		String message = null;
		String deviceId = "", sessionKey = "";

		try {
			deviceId = request.getDevice_id();
			sessionKey = request.getSession_key();
			boolean isValidRequest = CommonUtility.validateRequest(isEnc, request, aesEncDec);

			if (isValidRequest) {

				String key = AESEncDec.generateRandomKey(deviceId, sessionKey, isEnc);
				logger.info("key= " + key);
				String inputString = aesEncDec.decrypt(key, request.getInputString(), isEnc);
				logger.info("decrypted inputString= " + inputString);

				Gson gson = new GsonBuilder().setPrettyPrinting().create();
				PANVarificationRequest serviceRequest = gson.fromJson(inputString, PANVarificationRequest.class);
				String panNumber = serviceRequest.getPan_number();
				String leadid = serviceRequest.getLead_id();
				logger.info("panNumber= " + panNumber);
				if (Strings.isNullOrEmpty(panNumber)) {
					_isError = true;
					message = readProperties.getPropertyValue("INVALIDPANERROR");
				}
				/*
				 * String cacheRes = new CustomSessionServiceImpl().getDyncacheData(sessionKey,
				 * deviceId); org.json.simple.JSONObject cachejsonObject =
				 * CacheServices.parseString(cacheRes);
				 * 
				 * if (CustomSessionServiceImpl.validateDetails("", cachejsonObject, "", leadid,
				 * "")) { _isError = true; message = CustomSessionServiceImpl.message; }
				 */

				if (!_isError) {

					String payload = gson.toJson(serviceRequest);
					final String serviceURL = localURL+readProperties.getPropertyValue("PANVARIFICATIONURL").trim();
					responseStr = CommonUtility.callRestClient(payload, serviceURL);
					response = CommonUtility.generateCommonResponse(key, responseStr, isEnc, aesEncDec, readProperties);
					long endTime = System.currentTimeMillis();
					logger.info("EndTime of PANVarification Service [ " + endTime + " ]");
					logger.info("TotalTime of PANVarification Service [ " + (endTime - startTime) + " ]");
				}
			}

			else {
				message = readProperties.getPropertyValue("COMMONINPUTERROR").trim();
				response = CommonUtility.setCommonResponse(message);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return response;
	}

	/**
	 * Function used to get decrypted payment data
	 * 
	 * @param AxisPaymentDecRequest
	 * @return AxisPaymentDecResponse
	 */

	public AxisURLRedirectDecResponse getAxisURLRedirectDecStatus(CommonRequest request1)
			throws CommonException {

		long startTime = System.currentTimeMillis();
		logger.info("StartTime of AxisRedirection Service [" + startTime + " ]");
		AxisURLRedirectDecResponse response = new AxisURLRedirectDecResponse();
		boolean _isError = false;
		AESEncDec aesEncDec = new AESEncDec();
		String deviceId = null, sessionKey = null;
		//AxisURLRedirectDecRequest
		try {
			deviceId = request1.getDevice_id();
			sessionKey = request1.getSession_key();
			boolean isValidRequest = CommonUtility.validateRequest(isEnc, request1, aesEncDec);

			if(isValidRequest){

				String key = AESEncDec.generateRandomKey(deviceId, sessionKey, isEnc);
				logger.info("key= " + key);
				String inputString = aesEncDec.decrypt(key, request1.getInputString(), isEnc);
				logger.info("decrypted inputString= " + inputString);
				Gson gson = new GsonBuilder().setPrettyPrinting().create();
				AxisURLRedirectDecRequest request = gson.fromJson(inputString, AxisURLRedirectDecRequest.class);

				String enc_key = readProperties.getPropertyValue("ENCKEY").trim();
				logger.info("enc_key:" + enc_key);
				String encData = request.getEncData();
				if (Strings.isNullOrEmpty(encData)) {
					_isError = true;
				}
				if (!_isError) {

					String decryptedData = AES_Encrption.decrypt(encData, enc_key);
					logger.info("decryptedData:" + decryptedData);
					if (!Strings.isNullOrEmpty(decryptedData)) {

						String[] result = decryptedData.split("&");
						String[] reqParams = new String[result.length];

						for (int i = 0; i < result.length; i++) {
							reqParams[i] = result[i].trim();
							logger.info("split response1:" + reqParams[i]);
						}

						String BRN = reqParams[0].replace("BRN=", "").trim();
						String STC = reqParams[1].replace("STC=", "").trim();
						String RMK = reqParams[2].replace("RMK=", "").trim();
						String TRN = reqParams[3].replace("TRN=", "").trim();
						String TET = reqParams[4].replace("TET=", "").trim();
						String RID = reqParams[6].replace("RID=", "").trim();
						String AMT = reqParams[12].replace("AMT=", "").trim();
						String MDN = reqParams[14].replace("MDN=", "").trim();

						response.setTransaction_ref_no(BRN);
						response.setTransaction_id(TRN);
						response.setStatus(STC);
						response.setStatus_msg(RMK);
						response.setEmandate_date(TET);
						response.setEmandate_time(TET);
						response.setPayment_gateway_ref_id(RID);
						response.setMandate_status(STC);
						response.setPayment_amount(AMT);
						response.setMandate_registration_num(MDN);
						response.setAdditional_1("");
						response.setAdditional_2("");
						response.setAdditional_3("");
						response.setAdditional_4("");
						response.setAdditional_5("");
						response.setAdditional_6("");
						response.setAdditional_7("");
						response.setAdditional_8("");
						response.setSuccess(true);
						response.setDec_Data(decryptedData);
						response.setError_message(null);
						response.setSuccess(true);
						logger.info("response :: " + response);
						long endTime = System.currentTimeMillis();
						logger.info("EndTime of AxisRedirection Service [" + endTime + " ]");
						logger.info("TotalTime of AxisRedirection Service [" + (endTime - startTime) + " ]");

					} else {

						response.setDec_Data(null);
						response.setError_message(readProperties.getPropertyValue("DATADECERROR").trim());
						response.setSuccess(false);
					}

				} else {
					response.setDec_Data(null);
					response.setError_message(readProperties.getPropertyValue("DATADECERROR").trim());
					response.setSuccess(false);
					response.setAdditional_1(null);
					response.setAdditional_2(null);
					response.setAdditional_3(null);
					response.setAdditional_4(null);
					response.setAdditional_5(null);
					response.setAdditional_6(null);
					response.setAdditional_7(null);
					response.setAdditional_8(null);
					response.setEmandate_date(null);
					response.setAdditional_1(null);
					response.setEmandate_time(null);
					response.setMandate_registration_num(null);
					response.setMandate_status(null);
					response.setPayment_amount(null);
					response.setPayment_gateway_ref_id(null);
					response.setStatus(null);
					response.setStatus_msg(null);
					response.setTransaction_id(null);
					response.setTransaction_ref_no(null);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return response;
	}

	/**
	 * Function used to get axis netbanking payload status
	 * 
	 * @param PayloadNetbankingRequest
	 * @return PayloadNetbankingResponse
	 */

	public CommonResponse getPayloadTransactionStatus(CommonRequest request) throws CommonException {

		long startTime = System.currentTimeMillis();
		logger.info("StartTime of PayloadTransaction Service [" + startTime + " ]");
		AESEncDec aesEncDec = new AESEncDec();
		CommonResponse response = new CommonResponse();
		boolean _isError = false;
		String message = "", responseStr = "";
		String deviceId = "", sessionKey = "";

		try {
			deviceId = request.getDevice_id();
			sessionKey = request.getSession_key();
			Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
			JSONObject jsonObject = new JSONObject();
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			String input = request.getInputString();
			logger.info("inputString= " + input);
			String checksum = request.getChkSum();
			logger.info("checksum= " + checksum);
			boolean isValid = aesEncDec.validateChecksum(input, checksum, isEnc);
			logger.info("CheckSum isValid= " + isValid);

			if (!_isError && isValid) {

				String key = AESEncDec.generateRandomKey(deviceId, sessionKey, isEnc);
				logger.info("key= " + key);
				String decInputStr = aesEncDec.decrypt(key, input, isEnc);
				logger.info("decInputStr= " + decInputStr);
				PayloadNetbankingRequest payloadRequest = gson.fromJson(decInputStr, PayloadNetbankingRequest.class);
				emailId = payloadRequest.getEmailId();

				if (!Strings.isNullOrEmpty(emailId)) {

					File folder = new File(vendor);
					if (!folder.exists())
						folder.mkdir();
					logger.info("File location:" + folder.getAbsolutePath());

					String myHTML = NetbankingPayload.genericCreateHTML(NetbankingPayload.payloadNetbanking);
					NetbankingPayload.createFile("netbanking", myHTML);

					jsonObject.put("status", "success");
					jsonObject.put("success", true);
					responseStr = jsonObject.toString();
					logger.info("jsonObject:\t" + responseStr);

				} else {
					jsonObject.put("status", "failure");
					jsonObject.put("success", false);
					responseStr = jsonObject.toString();
					logger.info("jsonObject:\t" + responseStr);
				}

				if (!Strings.isNullOrEmpty(responseStr)) {

					logger.info("ResponseStr =" + responseStr);
					String encResponse = aesEncDec.encrypt(key, responseStr, isEnc);
					String encCheckSum = AESEncDec.createChecksum(encResponse, isEnc);

					logger.info("encResponse\t:" + encResponse);
					logger.info("encCheckSum\t:" + encCheckSum);

					response.setOutputString(encResponse);
					response.setChkSum(encCheckSum);
					response.setSuccess(true);
					response.setError_message(null);
				} else {
					message = readProperties.getPropertyValue("REQUESTERROR").trim();
					response.setOutputString(null);
					response.setChkSum(null);
					response.setSuccess(false);
					response.setError_message(message);
				}
				logger.info("response :: " + response);
				long endTime = System.currentTimeMillis();
				logger.info("EndTime of PayloadTransaction Service [" + endTime + " ]");
				logger.info("TotalTime of PayloadTransaction Service [" + (endTime - startTime) + " ]");

			} else {

				response.setChkSum(null);
				response.setError_message(message);
				response.setOutputString(null);
				response.setSuccess(false);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return response;
	}

	/**
	 * Function used to redirect Perfios URl
	 * 
	 * @param PerfiosUrlRedirectRequest
	 * @return PerfiosUrlRedirectResponse
	 */

	@SuppressWarnings("unused")
	public CommonResponse getPerfiosUrlRedirectStatus(CommonRequest request) throws CommonException {

		long startTime = System.currentTimeMillis();
		logger.info("StartTime of PerfiosUrlRedirection Service [" + startTime + " ]");
		AESEncDec aesEncDec = new AESEncDec();
		CommonResponse response = new CommonResponse();
		// GenerateOTPResponse otpResponse = new GenerateOTPResponse();
		boolean _isError = false;
		String message = "", responseStr = "", otp = "";
		StringBuffer buffer = null;
		String apiVersion = "", vendor = "", txnId = "", emailId = "", destination = "", loanAmount = "",
				loanDuration = "", loanType = "", institutionId = "", form26ASDOB = "", returnUrl = "",
				yearMonthFrom = "", yearMonthTo = "", acceptancePolicy = "";
		String deviceId = "", sessionKey = "";
		try {
			deviceId = request.getDevice_id();
			sessionKey = request.getSession_key();
			JSONObject jsonObject = new JSONObject();
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			String input = request.getInputString();
			logger.info("inputString= " + input);
			String checksum = request.getChkSum();
			logger.info("checksum= " + checksum);
			boolean isValid = aesEncDec.validateChecksum(input, checksum, isEnc);
			acceptancePolicy = readProperties.getPropertyValue("ACCEPTPOLICYKEY").trim();

			if (Strings.isNullOrEmpty(checksum) && Strings.isNullOrEmpty(input)) {
				_isError = true;
				message = readProperties.getPropertyValue("COMMONINPUTERROR").trim();
			}
			if (!_isError && isValid) {

				logger.info("CheckSum isValid= " + isValid);
				String key = AESEncDec.generateRandomKey(deviceId, sessionKey, isEnc);
				logger.info("key= " + key);
				String decInputStr = aesEncDec.decrypt(key, input, isEnc);
				logger.info("decInputStr= " + decInputStr);
				PerfiosUrlRedirectRequest perfiosUrlRedirectRequest = gson.fromJson(decInputStr,
						PerfiosUrlRedirectRequest.class);
				emailId = perfiosUrlRedirectRequest.getEmailId();
				String lead_id = perfiosUrlRedirectRequest.getLead_id();
				logger.info("lead_id: " + lead_id);

				if (!Strings.isNullOrEmpty(emailId)) {

					logger.info("Email id is valid and not empty");
					apiVersion = readProperties.getPropertyValue("APIVERKEY").trim();
					vendor = readProperties.getPropertyValue("VENDERKEY").trim();
					txnId = lead_id + NetbankingPayload.generateRandom(2);
					logger.info("txnId: " + txnId + "lead_id: " + lead_id);
					destination = readProperties.getPropertyValue("DESTKEY").trim();
					loanAmount = perfiosUrlRedirectRequest.getLoanAmount();
					loanDuration = perfiosUrlRedirectRequest.getLoanDuration();
					loanType = perfiosUrlRedirectRequest.getLoanType();
					institutionId = perfiosUrlRedirectRequest.getInstitutionId();
					form26ASDOB = perfiosUrlRedirectRequest.getForm26ASDOB();
					returnUrl = perfiosUrlRedirectRequest.getReturnUrl();
					yearMonthFrom = perfiosUrlRedirectRequest.getYearMonthFrom();
					yearMonthTo = perfiosUrlRedirectRequest.getYearMonthTo();
					Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
					String perfiosPayload = NetbankingPayload.buildPerfiosPayload(vendor, txnId, emailId, destination,
							returnUrl);
					String signature = NetbankingPayload.getPerfiosSignature(perfiosPayload);

					jsonObject.put("signature", signature);
					jsonObject.put("success", true);
					jsonObject.put("payload", perfiosPayload);
					responseStr = jsonObject.toString();
					logger.info("ResponseStr =" + responseStr);
					try {
						String cacheRes = new CustomSessionServiceImpl().getDyncacheData(sessionKey, deviceId);
						org.json.simple.JSONObject cachejsonObject = CacheServices.parseString(cacheRes);
						logger.info("cachejsonObject :::::::::::: " + cachejsonObject);
						String leadid = "";
						String mobilenumber = "";
						String aadharNumber = "";

						mobilenumber = cachejsonObject.get("mobileno") == null ? ""
								: cachejsonObject.get("mobileno").toString();
						aadharNumber = cachejsonObject.get("aadharnumber") == null ? ""
								: cachejsonObject.get("aadharnumber").toString();
						leadid = cachejsonObject.get("leadid") == null ? "" : cachejsonObject.get("leadid").toString();

						SessionData data = new SessionData();
						data.setMobilenumber(mobilenumber);
						data.setDeviceid(deviceId);
						data.setLeadid(leadid);
						data.setAadharnumber(aadharNumber);
						data.setTxnid(txnId);
						data.setEncKey(key);
						cacheRes = new CustomSessionServiceImpl().saveDyncacheData(sessionKey, deviceId, data);
						logger.info("cacheRes :::::::::::: " + cacheRes);
					} catch (Exception e) {
						e.printStackTrace();
						logger.info("cacheRes :::::::::::: " + e);
					}

				} else {
					logger.info("Email id is not valid or empty");
					jsonObject.put("signature", null);
					jsonObject.put("success", false);
					jsonObject.put("payload", null);
					responseStr = jsonObject.toString();
					logger.info("ResponseStr =" + responseStr);
				}

				if (!Strings.isNullOrEmpty(responseStr)) {

					String encResponse = aesEncDec.encrypt(key, responseStr, isEnc);
					String encCheckSum = AESEncDec.createChecksum(encResponse, isEnc);

					logger.info("encResponse\t:" + encResponse);
					logger.info("encCheckSum\t:" + encCheckSum);

					response.setOutputString(encResponse);
					response.setChkSum(encCheckSum);
					response.setSuccess(true);
					response.setError_message(null);
				} else {
					message = readProperties.getPropertyValue("REQUESTERROR").trim();
					response.setOutputString(null);
					response.setChkSum(null);
					response.setSuccess(false);
					response.setError_message(message);
				}

				long endTime = System.currentTimeMillis();
				logger.info("EndTime of PerfiosUrlRedirection Service [" + endTime + " ]");
				logger.info("TotalTime of PerfiosUrlRedirection Service [" + (endTime - startTime) + " ]");
			} else {

				logger.info("CheckSum isValid= " + isValid);
				response.setOutputString(null);
				response.setChkSum(null);
				response.setError_message(message);
				response.setSuccess(false);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return response;
	}

	/**
	 * Function used to get loan revise details from esb.
	 * 
	 * @param UpdateLoanReviseDetailsRequest
	 * @return UpdateLoanReviseDetailsResponse
	 */

	public CommonResponse getUpdateReviseLoanStatus(CommonRequest request) throws CommonException {

		long startTime = System.currentTimeMillis();
		logger.info("StartTime of UpdateReviseLoan Service [" + startTime + " ]");
		AESEncDec aesEncDec = new AESEncDec();
		CommonResponse response = new CommonResponse();
		String responseStr = null;
		String message = null;
		String deviceId = "", sessionKey = "";
		try {
			deviceId = request.getDevice_id();
			sessionKey = request.getSession_key();
			boolean isValidRequest = CommonUtility.validateRequest(isEnc, request, aesEncDec);

			if (isValidRequest) {

				String key = AESEncDec.generateRandomKey(deviceId, sessionKey, isEnc);
				logger.info("key= " + key);
				String inputString = aesEncDec.decrypt(key, request.getInputString(), isEnc);
				logger.info("decrypted inputString= " + inputString);

				Gson gson = new GsonBuilder().setPrettyPrinting().create();
				UpdateLoanReviseDetailsRequest serviceRequest = gson.fromJson(inputString,
						UpdateLoanReviseDetailsRequest.class);
				String leadId = serviceRequest.getLead_id();

				if (!Strings.isNullOrEmpty(leadId)) {

					String payload = gson.toJson(serviceRequest);
					final String serviceURL = localURL+readProperties.getPropertyValue("UPDATEREVISEDLOANURL").trim();
					responseStr = CommonUtility.callRestClient(payload, serviceURL);
					response = CommonUtility.generateCommonResponse(key, responseStr, isEnc, aesEncDec, readProperties);
					long endTime = System.currentTimeMillis();
					logger.info("EndTime of UpdateReviseLoan Service [" + endTime + " ]");
					logger.info("TotalTime of UpdateReviseLoan Service [" + (endTime - startTime) + " ]");
				}
			} else {
				message = readProperties.getPropertyValue("COMMONINPUTERROR").trim();
				response = CommonUtility.setCommonResponse(message);
			}
		} catch (Exception ex) {
			ex.printStackTrace();

		}
		return response;
	}

	/**
	 * Function used to get captured selfi status.
	 * 
	 * @param CaptureSelfiRequest
	 * @return CaptureSelfiResponse
	 */

	public CommonResponse getCaptureSelfiStatus(CommonRequest request) throws CommonException {

		long startTime = System.currentTimeMillis();
		logger.info("StartTime of CaptureSelfi Service [" + startTime + " ]");
		AESEncDec aesEncDec = new AESEncDec();
		CommonResponse response = new CommonResponse();
		String responseStr = null;
		boolean _isError = false;
		String message = null;
		String deviceId = "", sessionKey = "";
		try {

			deviceId = request.getDevice_id();
			sessionKey = request.getSession_key();
			boolean isValidRequest = CommonUtility.validateRequest(isEnc, request, aesEncDec);

			if (isValidRequest) {

				String key = AESEncDec.generateRandomKey(deviceId, sessionKey, isEnc);
				logger.info("key= " + key);
				String inputString = aesEncDec.decrypt(key, request.getInputString(), isEnc);
				logger.info("decrypted inputString= " + inputString);

				Gson gson = new GsonBuilder().setPrettyPrinting().create();
				CaptureSelfiRequest serviceRequest = gson.fromJson(inputString, CaptureSelfiRequest.class);
				String leadId = serviceRequest.getLead_id();
				logger.info("leadId= " + leadId);
				if (Strings.isNullOrEmpty(leadId)) {
					_isError = true;
					message = readProperties.getPropertyValue("LEADIDERRORMESSAGE").trim();
				}

				/*
				 * String cacheRes = new CustomSessionServiceImpl().getDyncacheData(sessionKey,
				 * deviceId); org.json.simple.JSONObject cachejsonObject =
				 * CacheServices.parseString(cacheRes);
				 * 
				 * if (CustomSessionServiceImpl.validateDetails("", cachejsonObject, "", leadId,
				 * "")) { _isError = true; message = CustomSessionServiceImpl.message; }
				 */
				if (!_isError) {

					String payload = gson.toJson(serviceRequest);
					final String serviceURL = localURL+readProperties.getPropertyValue("CAPTURESELFIURL").trim();
					logger.info("Service URL:" + serviceURL);
					responseStr = CommonUtility.callRestClient(payload, serviceURL);
					response = CommonUtility.generateCommonResponse(key, responseStr, isEnc, aesEncDec, readProperties);
					long endTime = System.currentTimeMillis();
					logger.info("EndTime of CaptureSelfi Service [" + endTime + " ]");
					logger.info("TotalTime of CaptureSelfi Service [" + (endTime - startTime) + " ]");
				}
			}

			else {
				message = readProperties.getPropertyValue("COMMONINPUTERROR").trim();
				response = CommonUtility.setCommonResponse(message);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return response;
	}

	/**
	 * Function used to get loan status applied by user.
	 * 
	 * @param LoanApplicationRequest
	 * @return LoanApplicationResponse
	 */
	public CommonResponse getLoanApplicationStatus(CommonRequest request) throws CommonException {

		long startTime = System.currentTimeMillis();
		logger.info("StartTime of LoanApplication Service [" + startTime + " ]");
		AESEncDec aesEncDec = new AESEncDec();
		CommonResponse response = new CommonResponse();
		String responseStr = null;
		boolean _isError = false;
		String message = null;
		String deviceId = "", sessionKey = "";
		try {
			deviceId = request.getDevice_id();
			sessionKey = request.getSession_key();
			boolean isValidRequest = CommonUtility.validateRequest(isEnc, request, aesEncDec);

			if (isValidRequest) {

				String key = AESEncDec.generateRandomKey(deviceId, sessionKey, isEnc);
				logger.info("key= " + key);
				String inputString = aesEncDec.decrypt(key, request.getInputString(), isEnc);
				logger.info("LoanApplicationReqJson= " + inputString);

				Gson gson = new GsonBuilder().setPrettyPrinting().create();
				LoanApplicationRequest serviceRequest = gson.fromJson(inputString, LoanApplicationRequest.class);
				logger.info("StartTime of LoanApplication :: " + startTime + " ::with lead:: "
						+ serviceRequest.getLead_id());
				String leadId = serviceRequest.getLead_id();
				logger.info("leadId= " + leadId);
				if (Strings.isNullOrEmpty(leadId)) {
					_isError = true;
					message = readProperties.getPropertyValue("LEADIDERRORMESSAGE").trim();
				}

				String cacheRes = new CustomSessionServiceImpl().getDyncacheData(sessionKey, deviceId);
				org.json.simple.JSONObject cachejsonObject = CacheServices.parseString(cacheRes);

				if (CustomSessionServiceImpl.validateDetails("", cachejsonObject, "", leadId, "")) {
					_isError = true;
					message = CustomSessionServiceImpl.message;
				}

				if (!_isError) {

					String payload = gson.toJson(serviceRequest);
					final String serviceURL = localURL+readProperties.getPropertyValue("LOANAPPLICATIONURL").trim();
					responseStr = CommonUtility.callRestClient(payload, serviceURL);
					response = CommonUtility.generateCommonResponse(key, responseStr, isEnc, aesEncDec, readProperties);
					long endTime = System.currentTimeMillis();
					logger.info("EndTime of LoanApplication Service [" + endTime + " ]");
					logger.info("TotalTime of LoanApplication Service [" + (endTime - startTime) + " ]");
				}
			}

			else {
				message = readProperties.getPropertyValue("COMMONINPUTERROR").trim();
				response = CommonUtility.setCommonResponse(message);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return response;
	}

	/**
	 * Function used to redirect AxisNetbanking URL.
	 * 
	 * @param URLRedirectRequest
	 * @return URLRedirectResponse
	 */
	public URLRedirectResponse getAxisURLRedirectionStatus(CommonRequest request1) throws CommonException {

		long startTime = System.currentTimeMillis();
		logger.info("StartTime of AxisURLRedirection Service [" + startTime + " ]");

		URLRedirectResponse response = new URLRedirectResponse();
		String checkSum = "";
		boolean _isError = false;
		String message = "";
		AESEncDec aesEncDec = new AESEncDec();
		String  deviceId = null, sessionKey = null;
		try {

			deviceId = request1.getDevice_id();
			sessionKey = request1.getSession_key();
			boolean isValidRequest = CommonUtility.validateRequest(isEnc, request1, aesEncDec);
			
			if(isValidRequest){
			String generatedKey = AESEncDec.generateRandomKey(deviceId, sessionKey, isEnc);
			logger.info("key= " + generatedKey);
			String inputString = aesEncDec.decrypt(generatedKey, request1.getInputString(), isEnc);
			logger.info("decrypted inputString= " + inputString);
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			logger.info("request  :" + request1.toString());
			URLRedirectRequest request = gson.fromJson(inputString, URLRedirectRequest.class);
			
			logger.info("request  :" + request.toString());
			String key = readProperties.getPropertyValue("CHECKSUMKEY").trim();
			String enc_key = readProperties.getPropertyValue("ENCKEY").trim();
			String cid = readProperties.getPropertyValue("CORPKEY").trim();
			String cny = readProperties.getPropertyValue("CNYKEY").trim();
			String ver = readProperties.getPropertyValue("VERKEY").trim();
			String type = readProperties.getPropertyValue("TYPEKEY").trim();
			String re1 = readProperties.getPropertyValue("RE1DEFAULTKEY").trim();

			String amount = request.getAmt();
			String crn = request.getCrn();
			String rid = NetbankingPayload.generateRandom(12);
			//String rid = "123456789012";
			String rtu = request.getRtu();
			String customerName = request.getCustomer_name();
			String bankName = request.getBank_name();
			String emiAmount = request.getEmi_amount();
			String ifscCode = request.getIfsc_code();
			request.getAadhar();
			String maximumAmount = request.getMax_amount();
			//String frequency = request.getFrequency();
			String frequency = readProperties.getPropertyValue("AXISREDIRECTFREQ").trim() == null ? "ADHOC"
					: readProperties.getPropertyValue("AXISREDIRECTFREQ").trim();
			String account_number = request.getAccount_number();
			String scheduleDate = request.getSchedule_date();
			String expiryDate = request.getExpiry_date();

			String ppi = customerName + "|" + bankName + "|" + emiAmount + "|" + ifscCode + "|" + maximumAmount + "|"
					+ frequency + "|" + account_number + "|" + scheduleDate + "|" + expiryDate + "|" + amount;
			logger.info("crn  :" + crn);
			if (Strings.isNullOrEmpty(crn)) {
				_isError = true;
				message = readProperties.getPropertyValue("CRNNUMERROR").trim();
			}
			if (!_isError) {

				checkSum = cid + rid + crn + amount + key;
				String axisPayload = "CID=" + cid + "&RID=" + rid + "&CRN=" + crn + "&AMT=" + amount + "&VER=" + ver
						+ "&TYP=" + type + "&CNY=" + cny + "&RTU=" + rtu + "&PPI=" + ppi + "&RE1=" + re1
						+ "&RE2=&RE3=&RE4=&RE5=&CKS=";

				logger.info("ppi parameter in axis url redirection:" + ppi);
				logger.info("payload data without hex checksum:" + axisPayload);
				logger.info("checkSum :" + checkSum);
				String checSum = AES_Encrption.checkSum(checkSum);
				logger.info("Hex checkSum:" + checSum);
				axisPayload = axisPayload + checSum;
				logger.info("payload data with hex checksum:" + axisPayload);
				String enc_data = AES_Encrption.encrypt(axisPayload, enc_key);
				logger.info("encrypted payload:" + enc_data);

				if (!Strings.isNullOrEmpty(enc_data)) {

					response.setEnc_Data(enc_data);
					response.setSuccess(true);
					response.setError_message(null);
					long endTime = System.currentTimeMillis();
					logger.info("EndTime of AxisURLRedirection Service [" + endTime + " ]");
					logger.info("TotalTime of AxisURLRedirection Service [" + (endTime - startTime) + " ]");
				} else {

					response.setSuccess(false);
					response.setEnc_Data(null);
					response.setError_message(readProperties.getPropertyValue("DATAENCERROR").trim());
				}
			} else {

				response.setEnc_Data(null);
				response.setError_message(message);
				response.setSuccess(false);
			}
			}
		} catch (Exception e) {
			e.printStackTrace();

		}

		return response;
	}
	public static void main(String[] args) {
		String enc_data = AES_Encrption.aesEncrypt("CID=3343&RID=123456789012&CRN=PL00002267&AMT=1&VER=1.0&TYP=TEST&CNY=INR&RTU=http://loginuat.indiabullsdhani.com/dhaniweb/#!/redirectionsToBankaxis&PPI=Nikhil Kanojia|AXIS Bank|1|AXIS0001145|2|ADHOC|914010009305862|03/02/2019|02/02/2021|1&RE1=MN&RE2=&RE3=&RE4=&RE5=&CKS=caf9932463b9c03c60adde15b88e27e203d91b3132b06913d23293460ac5d52b", "R!@N2$Ih7U!AdC#L");
	System.out.println(enc_data);
	}
	public ICICIUrlRedirectResponse getICICIRedirectStatus(CommonRequest request1) throws CommonException {

		long startTime = System.currentTimeMillis();
		logger.info("StartTime of ICICIRedirection Service [" + startTime + " ]");
		ICICIUrlRedirectResponse response = new ICICIUrlRedirectResponse();
		String message = "";
		boolean _isError = false;
		String urlSubString = "", masterKey = "", accountNumber = "", auto_pay_amount = "", itc = "", prn = "",
				crn = "", ru = "", cg = "", user_lang_id = "", userType = "", si = "", pmt_dt = "", pmt_ty = "",
				pmt_frq = "", no_inst = "";
		AESEncDec aesEncDec = new AESEncDec();
		String deviceId = null, sessionKey = null;
		try {
			crn = readProperties.getPropertyValue("CRNKEY").trim();
			if (Strings.isNullOrEmpty(crn)) {
				_isError = true;
				message = readProperties.getPropertyValue("CRNNUMERROR").trim();
			}

			deviceId = request1.getDevice_id();
			sessionKey = request1.getSession_key();
			logger.info("request1 toString :" + request1.toString());
			boolean isValidRequest = CommonUtility.validateRequest(isEnc, request1, aesEncDec);

			if(isValidRequest){

				String key = AESEncDec.generateRandomKey(deviceId, sessionKey, isEnc);
				logger.info("key= " + key);
				String inputString = aesEncDec.decrypt(key, request1.getInputString(), isEnc);
				logger.info("decrypted inputString= " + inputString);
				Gson gson = new GsonBuilder().setPrettyPrinting().create();
				ICICIUrlRedirectRequest request = gson.fromJson(inputString, ICICIUrlRedirectRequest.class);

				if (!_isError) {

				auto_pay_amount = request.getAuto_pay_amount();
				itc = String.valueOf(AES_Encrption.generateRandom(12));
				prn = request.getPrn().trim();
				crn = readProperties.getPropertyValue("CRNKEY").trim();
				ru = request.getRu();
				cg = readProperties.getPropertyValue("CGYKEY").trim();
				user_lang_id = readProperties.getPropertyValue("USERLANGIDKEY").trim();
				logger.info("user_lang_id Parameter:\t" + user_lang_id);
				userType = readProperties.getPropertyValue("USERTYPEKEY").trim();
				logger.info("userType Parameter:\t" + userType);
				si = readProperties.getPropertyValue("SIKEY").trim();
				logger.info("si Parameter:\t" + si);
				pmt_dt = request.getPmt_dt();
				logger.info("pmt_dt Parameter:\t" + pmt_dt);
				pmt_ty = readProperties.getPropertyValue("PMTTYKEY").trim();
				logger.info("pmt_ty Parameter:\t" + pmt_ty);
				pmt_frq = readProperties.getPropertyValue("PMTFRQKEY").trim();
				logger.info("pmt_frq Parameter:\t" + pmt_frq);
				masterKey = readProperties.getPropertyValue("MASTERKEY").trim();
				logger.info("masterKey Parameter:\t" + masterKey);
				no_inst = request.getNo_inst().trim();
				logger.info("no_inst Parameter:\t" + no_inst);
				accountNumber = request.getAccountNumber().trim();
				logger.info("accountNumber Parameter:\t" + accountNumber);

				urlSubString = "AUTO_PAY_AMOUNT=" + auto_pay_amount + "&ITC=" + itc + "&PRN=" + prn + "&CRN=" + crn
						+ "&RU=" + ru + "&CG=" + cg + "&USER_LANG_ID=" + user_lang_id + "&UserType=" + userType + "&SI="
						+ si + "&PMT_DT=" + pmt_dt + "&PMT_FRQ=" + pmt_frq + "&PMT_TY=" + pmt_ty + "&NO_INST=" + no_inst
						+ "&ACNO=" + accountNumber;
				logger.info("ES Parameter:\t" + urlSubString);
				final String token = ShoppingMallEncryptor.encrypt(masterKey, urlSubString);
					logger.info("Token:" + urlSubString);

				response.setError_message(null);
				response.setSuccess(true);
				response.setToken(token);
				response.setTxnId(null);

			} else {

				response.setError_message(message);
				response.setSuccess(false);
				response.setToken(null);
				response.setTxnId(null);
			}
			}
			long endTime = System.currentTimeMillis();
			logger.info("EndTime of ICICIRedirection Service [" + endTime + " ]");
			logger.info("TotalTime of ICICIRedirection Service [" + (endTime - startTime) + " ]");

		} catch (Exception e) {
			e.printStackTrace();

		}

		return response;
	}

	public ICICIUrlRedirectResResponse getICICIRedirectResDataStatus(CommonRequest request1)
			throws CommonException {

		long startTime = System.currentTimeMillis();
		logger.info("StartTime of ICICIResponse Service [" + startTime + " ]");
		ICICIUrlRedirectResResponse response = new ICICIUrlRedirectResResponse();
		boolean _isError = false;
		String deviceId = null, sessionKey = null;
		//ICICIUrlRedirectResRequest
		AESEncDec aesEncDec = new AESEncDec();
		try {
			deviceId = request1.getDevice_id();
			sessionKey = request1.getSession_key();
			String key = AESEncDec.generateRandomKey(deviceId, sessionKey, isEnc);
			
			boolean isValidRequest = CommonUtility.validateRequest(isEnc, request1, aesEncDec);
			
			if(isValidRequest){
				
			logger.info("key= " + key);
			String inputString = aesEncDec.decrypt(key, request1.getInputString(), isEnc);
			logger.info("decrypted inputString= " + inputString);
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			ICICIUrlRedirectResRequest request = gson.fromJson(inputString, ICICIUrlRedirectResRequest.class);

			String enc_key = readProperties.getPropertyValue("MASTERKEY").trim();
			logger.info("enc_key:" + enc_key);
			String encData = request.getRedirectResponse();

			if (Strings.isNullOrEmpty(encData)) {
				_isError = true;
			}

			if (!_isError) {

				String decryptedData = AES_Encrption.decrypt(encData, enc_key);
				logger.info("decryptedData:" + decryptedData);
				if (!Strings.isNullOrEmpty(decryptedData)) {

					String[] result = decryptedData.split("&");
					String[] reqParams = new String[result.length];

					for (int i = 0; i < result.length; i++) {
						reqParams[i] = result[i].trim();
						logger.info("split response1:" + reqParams[i]);
					}
					/*
					 * PRN=PL00672790&ITC=827782227775&AMT=null&CRN=INR&PAID= null
					 * &SCHSTATUS=Y&SCHMSG=SUC&SCHEDULEID=1000003232
					 */

					String prn = reqParams[0].replace("PRN=", "").trim();
					String itc = reqParams[1].replace("ITC=", "").trim();
					String amt = reqParams[2].replace("AMT=", "").trim();
					String crn = reqParams[3].replace("CRN=", "").trim();
					String paid = reqParams[4].replace("PAID=", "").trim();
					String schStatus = reqParams[5].replace("SCHSTATUS=", "").trim();
					String schmsg = reqParams[6].replace("SCHMSG=", "").trim();
					String scheduleid = reqParams[7].replace("SCHEDULEID=", "").trim();

					response.setAdditional_4(scheduleid);
					response.setEmandate_date(new SimpleDateFormat("yyyy/MM/dd").format(new Date()));
					response.setEmandate_time(String.valueOf(System.currentTimeMillis()));
					response.setMandate_registration_num(scheduleid);
					response.setPAID(paid);
					if (!(Strings.isNullOrEmpty(amt) || amt.equalsIgnoreCase("null"))) {
						response.setPayment_amount(Double.parseDouble(amt));
					} else {
						response.setPayment_amount(0);
					}

					response.setPayment_gateway_ref_id(crn);
					response.setStatus(schStatus);
					response.setStatus_msg(schmsg);
					response.setTransaction_id(itc);
					response.setTransaction_ref_no(prn);
					response.setError_message(null);
					response.setSuccess(true);
					logger.info("icici response: " + response);
					long endTime = System.currentTimeMillis();
					logger.info("EndTime of ICICIResponse Service [" + endTime + " ]");
					logger.info("TotalTime of ICICIResponse Service [" + (endTime - startTime) + " ]");

				} else {
					response.setAdditional_4(null);
					response.setEmandate_date(null);
					response.setEmandate_time(null);
					response.setMandate_registration_num(null);
					response.setPAID(null);
					response.setPayment_gateway_ref_id(null);
					response.setStatus(null);
					response.setStatus_msg(null);
					response.setTransaction_id(null);
					response.setTransaction_ref_no(null);
					response.setError_message(null);
					response.setSuccess(false);
					response.setError_message(readProperties.getPropertyValue("DATADECERROR").trim());
				}

			} else {
				response.setAdditional_4(null);
				response.setEmandate_date(null);
				response.setEmandate_time(null);
				response.setMandate_registration_num(null);
				response.setPAID(null);
				response.setPayment_gateway_ref_id(null);
				response.setStatus(null);
				response.setStatus_msg(null);
				response.setTransaction_id(null);
				response.setTransaction_ref_no(null);
				response.setError_message(null);
				response.setSuccess(false);
				response.setError_message(readProperties.getPropertyValue("DATADECERROR").trim());
			}
		}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return response;
	}

	public CommonResponse getEcoAccountNameStatus(CommonRequest request) throws CommonException {

		long startTime = System.currentTimeMillis();
		logger.info("StartTime of EcoAccountName Service [" + startTime + " ]");
		AESEncDec aesEncDec = new AESEncDec();
		CommonResponse response = new CommonResponse();
		String responseStr = null;
		boolean _isError = false;
		String message = null;
		String deviceId = "", sessionKey = "";
		int counter = 0;
		try {

			deviceId = request.getDevice_id();
			sessionKey = request.getSession_key();
			boolean isValidRequest = CommonUtility.validateRequest(isEnc, request, aesEncDec);

			if (isValidRequest) {

				logger.info("isValidRequest=" + isValidRequest);
				String key = AESEncDec.generateRandomKey(deviceId, sessionKey, isEnc);
				logger.info("key= " + key);
				String inputString = aesEncDec.decrypt(key, request.getInputString(), isEnc);
				logger.info("decrypted inputString= " + inputString);

				Gson gson = new GsonBuilder().setPrettyPrinting().create();
				EcoAccountNameRequest nameRequest = gson.fromJson(inputString, EcoAccountNameRequest.class);
				String accountNumber = nameRequest.getBank_acct_number();
				logger.info("accountNumber= " + accountNumber);

				String cacheRes = new CustomSessionServiceImpl().getDyncacheData(sessionKey, deviceId);
				org.json.simple.JSONObject cachejsonObject = CacheServices.parseString(cacheRes);

				String crmid = (String) cachejsonObject.get("crmid") == null ? ""
						: cachejsonObject.get("crmid").toString();
				String leadId = (String) cachejsonObject.get("leadid") == null ? ""
						: cachejsonObject.get("leadid").toString();
				String txnid = (String) cachejsonObject.get("txnid") == null ? ""
						: cachejsonObject.get("txnid").toString();
				String aadharnumber = (String) cachejsonObject.get("aadharnumber") == null ? ""
						: cachejsonObject.get("aadharnumber").toString();
				String enckey = (String) cachejsonObject.get("enckey") == null ? ""
						: cachejsonObject.get("enckey").toString();
				String otp = (String) cachejsonObject.get("otp") == null ? "" : cachejsonObject.get("otp").toString();
				String mpin = (String) cachejsonObject.get("mpin") == null ? ""
						: cachejsonObject.get("mpin").toString();
				String checksum = (String) cachejsonObject.get("checkSum") == null ? ""
						: cachejsonObject.get("checkSum").toString();

				String serviceCompletionTime = (String) cachejsonObject.get("serviceCompletionTime") == null ? ""
						: cachejsonObject.get("serviceCompletionTime").toString();
				String documents = (String) cachejsonObject.get("documents") == null ? ""
						: cachejsonObject.get("documents").toString();
				String mobileNo = (String) cachejsonObject.get("mobileno") == null ? ""
						: cachejsonObject.get("mobileno").toString();
				// String sessionCount1 = (String) cachejsonObject.get("crmid") ==
				// null?"":cachejsonObject.get("crmid").toString();
				// String sessionCount2 = (String) cachejsonObject.get("checkSum") ==
				// null?"":cachejsonObject.get("checkSum").toString();
				String flag1 = (String) cachejsonObject.get("flag1") == null ? "0"
						: cachejsonObject.get("flag1").toString();
				String flag2 = (String) cachejsonObject.get("flag2") == null ? ""
						: cachejsonObject.get("flag2").toString();
				String mobileFirst = (String) cachejsonObject.get("mobileFirst") == null ? ""
						: cachejsonObject.get("mobileFirst").toString();
				String mobileSecond = (String) cachejsonObject.get("mobileSecond") == null ? ""
						: cachejsonObject.get("mobileSecond").toString();

				System.out.println("Flag1 as counter  :" + flag1);
				try {

					counter = Integer.valueOf(flag1);

				} catch (Exception e) {
					counter = 0;
				}
				System.out.println("Flag1 as counter value :" + counter);
				counter++;
				if (counter > 2) {
					_isError = true;
					message = readProperties.getPropertyValue("REQUESTERROR").trim();
				}
				System.out.println("Flag1 as after counter value :" + counter);

				if (Strings.isNullOrEmpty(accountNumber)) {
					_isError = true;
					message = readProperties.getPropertyValue("LEADIDERRORMESSAGE").trim();
				}

				if (!_isError) {

					String payload = gson.toJson(nameRequest);
					final String serviceURL = localURL+readProperties.getPropertyValue("ECOACCOUNTURL").trim();
					;
					responseStr = CommonUtility.callRestClient(payload, serviceURL);
					response = CommonUtility.generateCommonResponse(key, responseStr, isEnc, aesEncDec, readProperties);
					long endTime = System.currentTimeMillis();
					logger.info("EndTime of EcoAccountName Service [" + endTime + " ]");
					logger.info("TotalTime of EcoAccountName Service [" + (endTime - startTime) + " ]");

					SessionData data = new SessionData();
					data.setMobilenumber(mobileNo);
					data.setMobileFirst(mobileFirst);
					data.setFlag1(String.valueOf(counter));
					data.setDeviceid(deviceId);
					data.setLeadid(leadId);
					data.setEncKey(enckey);
					data.setServiceCompletionTime(serviceCompletionTime);
					data.setDocuments(documents);
					data.setOtp(otp);
					data.setMpin(mpin);
					data.setCrmid(crmid);
					data.setCheckSum(checksum);
					data.setAadharnumber(aadharnumber);
					data.setTxnid(txnid);
					new CustomSessionServiceImpl().saveDyncacheData(sessionKey, deviceId, data);

				} else {
					response = CommonUtility.setCommonResponse(message);
				}
			}

			else {
				message = readProperties.getPropertyValue("COMMONINPUTERROR").trim();
				response = CommonUtility.setCommonResponse(message);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return response;
	}

	public SBIUrlRedirectResponse getSBIRedirectStatus(CommonRequest request) throws CommonException {
		long startTime = System.currentTimeMillis();
		logger.info("StartTime of SBIRedirection Service [" + startTime + " ]");
		SBIUrlRedirectResponse response = new SBIUrlRedirectResponse();
		String message = "", crn = "", deviceId = null, sessionKey = null;
		boolean _isError = false;
		AESEncDec aesEncDec = new AESEncDec();
		try {
			deviceId = request.getDevice_id();
			sessionKey = request.getSession_key();
			String key = AESEncDec.generateRandomKey(deviceId, sessionKey, isEnc);
			logger.info("key= " + key);
			String inputString = aesEncDec.decrypt(key, request.getInputString(), isEnc);
			logger.info("decrypted inputString= " + inputString);
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			org.json.simple.JSONObject serviceRequest = gson.fromJson(inputString, org.json.simple.JSONObject.class);
		
			crn = readProperties.getPropertyValue("CRNKEY").trim();
			if (Strings.isNullOrEmpty(crn)) {
				_isError = true;
				message = readProperties.getPropertyValue("CRNNUMERROR").trim();
			}
			if (!_isError) {

				crn = serviceRequest.get("crn") == null ? "" : serviceRequest.get("crn").toString();
				logger.info("crn in SBIRedirectService:\t\t" + crn);
				String temp = crn;
				String rid = temp.replace("PL", String.valueOf(AES_Encrption.generateRandom(4)));// String.valueOf(AES_Encrption.generateRandom(12));
				logger.info("rid in SBIRedirectService:\t\t" + rid);
				String amount = serviceRequest.get("amount") == null ? "" : serviceRequest.get("amount").toString();
				logger.info("amount in SBIRedirectService:\t\t" + amount);
				String accountNum = serviceRequest.get("account_number") == null ? "" : serviceRequest.get("account_number").toString();
				logger.info("accountNum in SBIRedirectService:\t\t" + accountNum);
				String exp_date = serviceRequest.get("exp_date") == null ? "" : serviceRequest.get("exp_date").toString();
				logger.info("exp_date in SBIRedirectService:\t\t" + exp_date);
				String sch_date = serviceRequest.get("schedule_date") == null ? "" : serviceRequest.get("schedule_date").toString();
				logger.info("sch_date in SBIRedirectService:\t\t" + sch_date);
				String frequency = serviceRequest.get("frequency") == null ? "" : serviceRequest.get("frequency").toString();
				logger.info("frequency in SBIRedirectService:\t\t" + frequency);
				String returnUrl = serviceRequest.get("return_url") == null ? "" : serviceRequest.get("return_url").toString();
				logger.info("returnUrl in SBIRedirectService:\t\t" + returnUrl);
				String canUrl = serviceRequest.get("can_url") == null ? "" : serviceRequest.get("can_url").toString();
				logger.info("canUrl in SBIRedirectService:\t\t" + canUrl);
				String emiAmount = serviceRequest.get("emi_amount") == null ? "" : serviceRequest.get("emi_amount").toString();
				logger.info("emiAmount in SBIRedirectService:\t\t" + emiAmount);
				String ifsc_code = serviceRequest.get("ifsc_code") == null ? "" : serviceRequest.get("ifsc_code").toString();
				logger.info("ifsc_code in SBIRedirectService:\t\t" + ifsc_code);
				String bank_name = serviceRequest.get("bank_name") == null ? "" : serviceRequest.get("bank_name").toString();
				logger.info("bank_name in SBIRedirectService:\t\t" + bank_name);
				String aadhar = serviceRequest.get("aadhar") == null ? "" : serviceRequest.get("aadhar").toString();
				logger.info("aadhar in SBIRedirectService:\t\t" + aadhar);
				String maxAmount = serviceRequest.get("max_amount") == null ? "" : serviceRequest.get("max_amount").toString();
				logger.info("maxAmount in SBIRedirectService:\t\t" + maxAmount);
				String customerName = serviceRequest.get("customer_name") == null ? "" : serviceRequest.get("customer_name").toString();
				logger.info("customerName in SBIRedirectService:\t\t" + customerName);

				String man_reg_charge = serviceRequest.get("mandate_reg_charge") == null ? "" : serviceRequest.get("mandate_reg_charge").toString();
				logger.info("man_reg_charge in SBIRedirectService:\t\t" + man_reg_charge);

				String debit_accountno = serviceRequest.get("debit_accountno") == null ? "" : serviceRequest.get("debit_accountno").toString();
				logger.info("debit_accountno in SBIRedirectService:\t\t" + debit_accountno);

				String amt_type =serviceRequest.get("Amnt_type") == null ? "" : serviceRequest.get("Amnt_type").toString();;
				
				String sbiReq = "RID=" + rid + "|" + "Amount=" + amount + "|" + "Acc_Num=" + accountNum + "|"
						+ "Exp_Date=" + exp_date + "|" + "Sch_Date=" + sch_date + "|" + "Freq=" + frequency + "|"
						+ "Ret_url=" + returnUrl + "|" + "Can_url=" + canUrl + "|" + "Emi_amount=" + emiAmount + "|"
						+ "Ifsc_code=" + ifsc_code + "|" + "Bank_name=" + bank_name + "|" + "Adhaar=" + aadhar + "|"
						+ "Max_amount=" + maxAmount + "|" + "Cust_name=" + customerName + "|" + "crn=" + crn + "|"
						+ "mandate_reg_charge=" + man_reg_charge + "|" + "debit_accountno=" + debit_accountno+ "|" + "Amnt_type=" + amt_type;
				logger.info("requestWithout checksum:\t\t" + sbiReq);
				String chkSum = SBIIntegration.createChecksum(sbiReq);
				logger.info("checksum:\t\t" + chkSum);
				sbiReq = sbiReq + "|checkSum=" + chkSum;
				logger.info("requestWith checksum:\t\t" + sbiReq);
				String encrptedRequest = SBIIntegration.encryptFile(sbiReq);
				logger.info("encrptedRequest:\t\t" + encrptedRequest);
				response.setError_message(null);
				response.setSuccess(true);
				response.setToken(encrptedRequest);

			} else {

				response.setError_message(message);
				response.setSuccess(false);
				response.setToken(null);
			}
			long endTime = System.currentTimeMillis();
			logger.info("EndTime of SBIRedirection Service [" + endTime + " ]");
			logger.info("TotalTime of SBIRedirection Service [" + (endTime - startTime) + " ]");

		} catch (Exception e) {
			e.printStackTrace();

		}

		return response;
	}

	public SBIResDataResponse getSBIRedirectResStatus(CommonRequest request1) throws CommonException {

		long startTime = System.currentTimeMillis();
		logger.info("StartTime of   Service [" + startTime + " ]");
		SBIResDataResponse response = new SBIResDataResponse();
		boolean _isError = false;
		StringBuffer buffer = new StringBuffer();
		String deviceId = null, sessionKey = null;
		AESEncDec aesEncDec = new AESEncDec();
		//SBIResDataRequest
		try {
			deviceId = request1.getDevice_id();
			sessionKey = request1.getSession_key();
			
			boolean isValidRequest = CommonUtility.validateRequest(isEnc, request1, aesEncDec);
			
			if(isValidRequest){
			String key = AESEncDec.generateRandomKey(deviceId, sessionKey, isEnc);
			logger.info("key= " + key);
			String inputString = aesEncDec.decrypt(key, request1.getInputString(), isEnc);
			logger.info("decrypted inputString= " + inputString);
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			SBIResDataRequest serviceRequest = gson.fromJson(inputString, SBIResDataRequest.class);
			
			String encData = serviceRequest.getEncData();
			if (Strings.isNullOrEmpty(encData)) {
				_isError = true;
			}
			if (!_isError) {

				String decryptedData = SBIIntegration.decryptFile(encData);
				logger.info("decryptedData:" + decryptedData);
				if (!Strings.isNullOrEmpty(decryptedData)) {

					String[] result = decryptedData.split("\\|");
					String[] reqParams = new String[result.length];

					for (int i = 0; i < result.length; i++) {
						reqParams[i] = result[i].trim();
						logger.info("split response1:" + reqParams[i]);
						String data = result[i].trim();
						String array[] = data.split("=");
						if (array[0].equals("SBI_RID")) {
							response.setSbi_rid(array[1]);
						}
						if (array[0].equals("Amount")) {
							response.setAmount(array[1]);
						}
						if (array[0].equals("RID")) {
							response.setRid(array[1]);
						}
						if (array[0].equals("STATUS")) {
							response.setStatus(array[1]);
						}
						if (array[0].equals("RMK")) {
							response.setRmk(array[1]);
						}
						if (array[0].equals("TET")) {
							response.setTet(array[1]);
						}
						if (array[0].equals("TYP")) {
							response.setTyp(array[1]);
						}
						if (array[0].equals("crn")) {
							response.setCrn(array[1]);
						}
						if (array[0].equals("PMD")) {
							response.setPmd(array[1]);
						}
						if (array[0].equals("CNY")) {
							response.setCny(array[1]);
						}

					}

					String checkSum = "";
					checkSum = decryptedData.substring(decryptedData.lastIndexOf("|") + 10, decryptedData.length());

					for (int i = 0; i < result.length - 1; i++) {

						if (i == 0) {
							buffer.append(result[i]);
						} else {
							buffer.append("|");
							buffer.append(result[i]);
						}
					}

					String responseWithoutChkSum = buffer.toString();
					logger.info("responseWithoutChkSum:\t" + responseWithoutChkSum);
					String generatedCheckSum = SBIIntegration.createChecksum(responseWithoutChkSum);
					logger.info("generatedCheckSum:\t" + generatedCheckSum);
					boolean isValid = checkSum.equals(generatedCheckSum);
					if (isValid) {

						logger.info("isValid checkSum:\t" + isValid);
						response.setSuccess(true);
						response.setError_message(null);
					}

					long endTime = System.currentTimeMillis();
					logger.info("EndTime of SBIResponse Service [" + endTime + " ]");
					logger.info("TotalTime of SBIResponse Service [" + (endTime - startTime) + " ]");

				} else {

					logger.info("checkSum mismatched");
					response.setSbi_rid(null);
					response.setAmount(null);
					response.setRid(null);
					response.setStatus(null);
					response.setRmk(null);
					response.setTet(null);
					response.setCrn(null);
					response.setCny(null);
					response.setTyp(null);
					response.setPmd(null);
					response.setSuccess(false);
					response.setError_message(readProperties.getPropertyValue("DATADECERROR").trim());
				}

			} else {

				response.setSbi_rid(null);
				response.setAmount(null);
				response.setRid(null);
				response.setStatus(null);
				response.setRmk(null);
				response.setTet(null);
				response.setCrn(null);
				response.setCny(null);
				response.setTyp(null);
				response.setPmd(null);
				response.setSuccess(false);
				response.setError_message(readProperties.getPropertyValue("DATADECERROR").trim());
			}
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.info("Exceptoin :" + e);
		}

		return response;
	}

	public CommonResponse getVersionUpgradeStatus(CommonRequest request) throws CommonException {

		long startTime = System.currentTimeMillis();
		logger.info("StartTime of VersionUpgrade Service [" + startTime + " ]");
		AESEncDec aesEncDec = new AESEncDec();
		CommonResponse response = new CommonResponse();
		String responseStr = null, message = "";
		HttpURLConnection conn = null;
		DataOutputStream wr = null;
		BufferedReader in = null;
		StringBuffer response1 = new StringBuffer();
		String inputLine = "";

		try {

			boolean isValidRequest = CommonUtility.validateRequest(isEnc, request, aesEncDec);
			String sessionKey = request.getSession_key();
			String deviceId = request.getDevice_id();

			if (isValidRequest) {

				String key = AESEncDec.generateRandomKey(deviceId, sessionKey, isEnc);
				String inputString = aesEncDec.decrypt(key, request.getInputString(), isEnc);
				logger.info("decrypted inputString= " + inputString);
				Gson gson = new GsonBuilder().setPrettyPrinting().create();
				VersionUpgradeRequest upgradeRequest = gson.fromJson(inputString, VersionUpgradeRequest.class);
				String userAgent = upgradeRequest.getUser_Agent();
				logger.info("userAgent= " + userAgent);
				String appVersion = upgradeRequest.getApp_version();

				String versionUpgradeURL = localURL+readProperties.getPropertyValue("VERSIONUPGRADEURL").trim();
				versionUpgradeURL = versionUpgradeURL + "useragent=" + userAgent + "&appversion=" + appVersion;
				logger.info("versionUpgrade URL= " + versionUpgradeURL);
				URL oracle = new URL(versionUpgradeURL);
				conn = (HttpURLConnection) oracle.openConnection();
				conn.setDoOutput(true);
				conn.setAllowUserInteraction(false);
				conn.setInstanceFollowRedirects(true);
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Content-Type", "text/xml");
				conn.setConnectTimeout(30000);
				conn.setReadTimeout(30000);
				conn.setRequestProperty("Connection", "Keep-Alive");
				wr = new DataOutputStream(conn.getOutputStream());
				wr.flush();
				wr.close();
				int responseCode = conn.getResponseCode();
				logger.info("response code :" + responseCode);
				String jsonString = conn.getResponseMessage();
				logger.info("response message :" + jsonString);
				in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				response1 = new StringBuffer();
				while ((inputLine = in.readLine()) != null) {
					response1.append(inputLine);
				}
				responseStr = response1.toString();
				response = CommonUtility.generateCommonResponse(key, responseStr, isEnc, aesEncDec, readProperties);
				long endTime = System.currentTimeMillis();
				logger.info("EndTime of VersionUpgrade Service [" + endTime + " ]");
				logger.info("TotalTime of VersionUpgrade Service [" + (endTime - startTime) + " ]");
				in.close();
			} else {
				message = readProperties.getPropertyValue("COMMONINPUTERROR").trim();
				response = CommonUtility.setCommonResponse(message);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	public KotakResDataResponse getKotakRedirectResStatus(CommonRequest request) throws CommonException {

		long startTime = System.currentTimeMillis();
		logger.info("StartTime of KotakResponse Service [" + startTime + " ]");
		KotakResDataResponse response = new KotakResDataResponse();
		RSAEncryption encryption = new RSAEncryption();
		RSADecryption decryption = new RSADecryption();
		boolean _isError = false;
		AESEncDec aesEncDec = new AESEncDec();
		StringBuffer buffer = new StringBuffer();
		String deviceId = null, sessionKey = null;
		//KotakResDataRequest
		
		try {
			deviceId = request.getDevice_id();
			sessionKey = request.getSession_key();
			String key = AESEncDec.generateRandomKey(deviceId, sessionKey, isEnc);
			logger.info("key= " + key);
			String inputString = aesEncDec.decrypt(key, request.getInputString(), isEnc);
			logger.info("decrypted inputString= " + inputString);
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			KotakResDataRequest serviceRequest = gson.fromJson(inputString, KotakResDataRequest.class);
			
			String encData = serviceRequest.getEncData();
			logger.info("input request:" + encData);

			if (Strings.isNullOrEmpty(encData)) {
				_isError = true;
			}
			if (!_isError) {

				byte[] byteData = encData.getBytes();
				String privateKey = readProperties.getPropertyValue("KOTAK_PVT_KEY") == null ? ""
						: readProperties.getPropertyValue("KOTAK_PVT_KEY").toString();
				logger.info("privateKey :" + privateKey);
				String decryptedData = decryption.rsaDecrypt(byteData, privateKey);
				logger.info("decryptedData:" + decryptedData);

				if (!Strings.isNullOrEmpty(decryptedData)) {

					String[] result = decryptedData.split("\\|");

					String messageCode = result[0];
					logger.info("messageCode :" + messageCode);
					String merchantRefNumber = result[1];
					logger.info("merchantRefNumber:" + merchantRefNumber);
					String merchantCode = result[2];
					logger.info("merchantCode:" + merchantCode);
					String amount = result[3];
					logger.info("amount:" + amount);
					String accountNumber = result[4];
					logger.info("accountNumber:" + accountNumber);
					String bankRefNumber = result[5];
					logger.info("bankRefNumber:" + bankRefNumber);
					String status = result[6];
					logger.info("status:" + status);
					String customerName = result[7];
					logger.info("customerName:" + customerName);

					String checksum = result[10];
					logger.info("checksum:" + checksum);

					for (int i = 0; i < result.length - 1; i++) {

						if (i == 0) {
							buffer.append(result[i]);
						} else {
							buffer.append("|");
							buffer.append(result[i]);
						}
					}

					String responseWithoutChkSum = buffer.toString();
					logger.info("responseWithoutChkSum:\t" + responseWithoutChkSum);
					boolean isValid = encryption.validateCheckSum(responseWithoutChkSum, checksum);
					logger.info("checksum matched:\t" + isValid);

					if (isValid) {

						logger.info("isValid checkSum:\t" + isValid);
						response.setAccountNumber(accountNumber);
						response.setAmount(amount);
						response.setBankReferenceNumber(bankRefNumber);
						response.setMerchantCode(merchantCode);
						response.setMerchantReferenceNumber(merchantRefNumber);
						response.setMessageCode(messageCode);
						response.setStatus(status);
						response.setCustomerName(customerName);
						response.setSuccess(true);
						response.setError_message(null);
					}

					logger.info("response :: " + response);
					long endTime = System.currentTimeMillis();
					logger.info("EndTime of KotakResponse Service [" + endTime + " ]");
					logger.info("TotalTime of KotakResponse Service [" + (endTime - startTime) + " ]");

				} else {

					logger.info("checkSum mismatched");
					response.setAccountNumber(null);
					response.setAmount(null);
					response.setBankReferenceNumber(null);
					response.setMerchantCode(null);
					response.setMerchantReferenceNumber(null);
					response.setMessageCode(null);
					response.setSuccess(false);
					response.setStatus(null);
					response.setCustomerName(null);
					response.setError_message(readProperties.getPropertyValue("DATADECERROR").trim());
				}

			} else {

				response.setError_message(readProperties.getPropertyValue("DATADECERROR").trim());
				response.setAccountNumber(null);
				response.setAmount(null);
				response.setBankReferenceNumber(null);
				response.setMerchantCode(null);
				response.setMerchantReferenceNumber(null);
				response.setMessageCode(null);
				response.setSuccess(false);
				response.setStatus(null);
				response.setCustomerName(null);
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.info("Exceptoin :" + e);
		}

		return response;
	}

	public KotakUrlRedirectResponse getKotakRedirectStatus(CommonRequest request1) throws CommonException {

		long startTime = System.currentTimeMillis();
		logger.info("StartTime of KotakRedirection Service [" + startTime + " ]");
		KotakUrlRedirectResponse response = new KotakUrlRedirectResponse();
		RSAEncryption rsaEncryption = new RSAEncryption();
		String message = "", deviceId = null, sessionKey = null;
		boolean _isError = false;
		AESEncDec aesEncDec = new AESEncDec();
		
		try {
			
			deviceId = request1.getDevice_id();
			sessionKey = request1.getSession_key();
			String key = AESEncDec.generateRandomKey(deviceId, sessionKey, isEnc);
			logger.info("key= " + key);
			String inputString = aesEncDec.decrypt(key, request1.getInputString(), isEnc);
			logger.info("decrypted inputString= " + inputString.replace("\\/", "/"));
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			KotakUrlRedirectRequest request = gson.fromJson(inputString.replace("\\/", "/"), KotakUrlRedirectRequest.class);
			
			String merchantCode =  request.getMerchantCode();
			
			if (Strings.isNullOrEmpty(merchantCode)) {
				_isError = true;
				message = readProperties.getPropertyValue("MERCHANTCODEERROR").trim();
			}
			if (!_isError) {

				String amount = request.getAmount();
				logger.info("amount in KotakRedirectService:\t\t" + amount);
				String accountNumber = request.getAccountNumber();
				logger.info("accountNumber in KotakRedirectService:\t\t" + accountNumber);
				String dateAndTime = request.getDateTime();
				logger.info("dateAndTime in KotakRedirectService:\t\t" + dateAndTime);
				String frequency = readProperties.getPropertyValue("FREQUENCY").trim() == null ? request.getFrequency()
						: readProperties.getPropertyValue("FREQUENCY").trim();
				logger.info("frequency in KotakRedirectService:\t\t" + frequency);
				String startDate = request.getStartDate();
				logger.info("startDate in KotakRedirectService:\t\t" + startDate);
				merchantCode = readProperties.getPropertyValue("MERCHANTCODE").trim() == null
						? request.getMerchantCode()
						: readProperties.getPropertyValue("MERCHANTCODE").trim();
				logger.info("merchantCode in KotakRedirectService:\t\t" + merchantCode);
				String merchantRefNumber = String.valueOf(AES_Encrption.generateRandom(17));// request.getMerchantReferenceNumber();
				logger.info("merchantRefNumber in KotakRedirectService:\t\t" + merchantRefNumber);
				String messageCode = readProperties.getPropertyValue("MESSAGECODE").trim() == null
						? request.getMessageCode()
						: readProperties.getPropertyValue("MESSAGECODE").trim();
				logger.info("messageCode in KotakRedirectService:\t\t" + messageCode);
				String endDate = request.getEndDate();
				logger.info("endDate in KotakRedirectService:\t\t" + endDate);
				String debitType = readProperties.getPropertyValue("DEBITTYPE").trim() == null ? request.getDebitType()
						: readProperties.getPropertyValue("DEBITTYPE").trim();
				logger.info("debitType in KotakRedirectService:\t\t" + debitType);
				String returnURL = request.getReturnUrl();
				
				String kotakReq = messageCode + "|" + dateAndTime + "|" + merchantCode + "|" + merchantRefNumber + "|"
						+ amount + "|" + accountNumber + "|" + frequency + "|" + startDate + "|" + endDate + "|"
						+ debitType + "||"+returnURL+"|";
				logger.info("requestWithout checksum:\t\t" + kotakReq);
				
				
				//kotakReq="E500|1532019105217|KPMG|89275534314325192|13000|7273727828|999|18082018|18082020|F||http://loginsit.indiabullshomeloans.com:9080/ibapp/KotakReturn.jsp|";
				
				kotakReq = rsaEncryption.createCheckSum(kotakReq);
				byte[] data = kotakReq.getBytes();
				logger.info("requestWith checksum:\t\t" + kotakReq);
				String publicKey = readProperties.getPropertyValue("KOTAK_PUBLIC_KEY") == null ? ""
						: readProperties.getPropertyValue("KOTAK_PUBLIC_KEY").toString();
				logger.info("publicKey:\t\t" + publicKey);
				String encryptedRequest = rsaEncryption.rsaEncrypt(data, publicKey);
				logger.info("encrptedRequest:\t\t" + encryptedRequest);
				response.setError_message(null);
				response.setSuccess(true);
				response.setToken(encryptedRequest);

			} else {

				response.setError_message(message);
				response.setSuccess(false);
				response.setToken(null);
			}
			long endTime = System.currentTimeMillis();
			logger.info("EndTime of KotakRedirection Service [" + endTime + " ]");
			logger.info("TotalTime of KotakRedirection Service [" + (endTime - startTime) + " ]");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	public CommonResponse getChangeMpin(CommonRequest request) throws CommonException {

		long startTime = System.currentTimeMillis();
		logger.info("StartTime of ChangeMpin Service [" + startTime + " ]");
		AESEncDec aesEncDec = new AESEncDec();
		CommonResponse response = new CommonResponse();
		String responseStr = null;
		boolean _isError = false;
		String message = null;
		String deviceId = "", sessionKey = "";

		try {
			deviceId = request.getDevice_id();
			sessionKey = request.getSession_key();

			boolean isValidRequest = CommonUtility.validateRequest(isEnc, request, aesEncDec);

			if (isValidRequest) {

				String key = AESEncDec.generateRandomKey(deviceId, sessionKey, isEnc);
				logger.info("key= " + key);
				String inputString = aesEncDec.decrypt(key, request.getInputString(), isEnc);
				logger.info("decrypted inputString= " + inputString);

				Gson gson = new GsonBuilder().setPrettyPrinting().create();
				GetChangeMpinRequest serviceRequest = gson.fromJson(inputString, GetChangeMpinRequest.class);
				String mobileNumber = serviceRequest.getMobile_number();
				String oldMpin = serviceRequest.getOldmpin();
				String mpin = serviceRequest.getMpin();
				if (Strings.isNullOrEmpty(mobileNumber)) {
					_isError = true;
					message = readProperties.getPropertyValue("MOBILEERRORMESSAGE");
				}
				String cacheRes = new CustomSessionServiceImpl().getDyncacheData(sessionKey, deviceId);

				org.json.simple.JSONObject cachejsonObject = CacheServices.parseString(cacheRes);

				if (CustomSessionServiceImpl.validateMpin(oldMpin, cachejsonObject)) {
					_isError = true;
					message = CustomSessionServiceImpl.message;
				}

				if (!_isError) {

					UpdateMpinRequest mpinRequest = new UpdateMpinRequest();
					mpinRequest.setDevice_id(deviceId);
					mpinRequest.setMobile_number(mobileNumber);
					mpinRequest.setMpin(mpin);
					String payload = gson.toJson(mpinRequest);
					final String serviceURL = localURL+readProperties.getPropertyValue("UPDATEMPINURL").trim();
					message = readProperties.getPropertyValue("COMMONINPUTERROR").trim();
					responseStr = CommonUtility.callRestClient(payload, serviceURL);
					response = CommonUtility.generateCommonResponse(key, responseStr, isEnc, aesEncDec, readProperties);
					UpdateMpinResponse mpinResponse = gson.fromJson(responseStr, UpdateMpinResponse.class);
					try {
						String leadid = "";
						String mobilenumber = "";
						mobilenumber = cachejsonObject.get("mobileno") == null ? ""
								: cachejsonObject.get("mobileno").toString();
						leadid = mpinResponse.getLead_id();
						String crmId = mpinResponse.getCrm_id();
						SessionData data = new SessionData();
						data.setMobilenumber(mobilenumber);
						data.setDeviceid(deviceId);
						data.setLeadid(leadid);
						data.setEncKey(key);
						data.setMpin(mpin);
						data.setCrmid(crmId);
						cacheRes = new CustomSessionServiceImpl().saveDyncacheData(sessionKey, deviceId, data);
						long endTime = System.currentTimeMillis();
						logger.info("EndTime of ChangeMpin Service [" + endTime + " ]");
						logger.info("TotalTime of ChangeMpin Service [" + (endTime - startTime) + " ]");

					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					response = CommonUtility.setCommonResponse(response, aesEncDec, key, isEnc, message);

				}
			}

			else {
				message = readProperties.getPropertyValue("COMMONINPUTERROR").trim();
				response = CommonUtility.setCommonResponse(message);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return response;
	}

	public CommonResponse uploadDocument(CommonRequest request) throws CommonException {

		long startTime = System.currentTimeMillis();
		logger.info("StartTime of uploadDocument Service [" + startTime + " ]");
		AESEncDec aesEncDec = new AESEncDec();
		CommonResponse response = new CommonResponse();
		String responseStr = null;
		String message = null;
		String deviceId = "", sessionKey = "";
		boolean isAllow = false;
		try {
			deviceId = request.getDevice_id();
			sessionKey = request.getSession_key();
			boolean isValidRequest = CommonUtility.validateRequest(isEnc, request, aesEncDec);

			if (isValidRequest) {

				String key = AESEncDec.generateRandomKey(deviceId, sessionKey, isEnc);

				String inputString = aesEncDec.decrypt(key, request.getInputString(), isEnc);
				logger.info("decrypted inputString= " + inputString);

				Gson gson = new GsonBuilder().setPrettyPrinting().create();
				UploadDocumentRequest serviceRequest = gson.fromJson(inputString, UploadDocumentRequest.class);
				String leadId = serviceRequest.getLead_id();
				String fileType = serviceRequest.getType();
				String Base64data = serviceRequest.getData();
				String fileName = serviceRequest.getDocument_name();

				String cacheRes = new CustomSessionServiceImpl().getDyncacheData(sessionKey, deviceId);
				org.json.simple.JSONObject cachejsonObject = CacheServices.parseString(cacheRes);

				/*
				 * String documents = cachejsonObject.get("documents") == null ? "" :
				 * cachejsonObject.get("documents").toString(); //documents=Base64data;
				 * logger.info("documents res :" + documents);
				 * 
				 * if (documents.length() > 2) { documents = documents.substring(0,
				 * documents.length() - 2); logger.info("documents subString :" + documents);
				 * String array[] = documents.split("##"); logger.info("array.length :" +
				 * array.length); for (int i = 0; i < array.length; i++) { if
				 * (array[i].equals(fileName)) {
				 * 
				 * isAllow = true; break; } } }
				 */

				/* if (isAllow) { */
				boolean isValidBase64String = new FileValidator().isValidBase64String(Base64data);
				logger.info("isValidBase64String :" + isValidBase64String);
				if (isValidBase64String) {
					isAllow = false;
					if (fileType.equalsIgnoreCase("jpg") || fileType.equalsIgnoreCase("png")
							|| fileType.equalsIgnoreCase("jpeg") || fileType.equalsIgnoreCase("pdf")) {

						byte[] data = FileValidator.decodeImage(Base64data);
						MagicMatch match = Magic.getMagicMatch(data);
						String mimeType = match.getMimeType();
						logger.info("mime type :" + mimeType);
						logger.info("fileType :" + fileType);
						if (fileType.equalsIgnoreCase("jpg") || fileType.equalsIgnoreCase("png")
								|| fileType.equalsIgnoreCase("jpeg")) {
							if (mimeType.contains("jpg") || mimeType.contains("jpeg") || mimeType.contains("png")) {
								isAllow = false;
							} else {
								logger.info("mimeType in else :" + fileType);
								isAllow = true;
							}
						} else {
							if (mimeType.contains(fileType)) {
								isAllow = false;
								logger.info("mimeType in if :" + fileType);
							} else {
								isAllow = true;
							}
						}

					}

				} else {

					isAllow = true;
				}

				logger.info("leadId= " + leadId);

				// System.out.println("serviceRequest=" +
				// serviceRequest.toString());
				if (!isAllow) {
					if (!Strings.isNullOrEmpty(leadId)) {

						String payload = gson.toJson(serviceRequest);
						final String serviceURL = localURL+readProperties.getPropertyValue("UPLOADDOCUMENTURL").trim();
						responseStr = CommonUtility.callRestClient(payload, serviceURL);
						response = CommonUtility.generateCommonResponse(key, responseStr, isEnc, aesEncDec,
								readProperties);
							long endTime = System.currentTimeMillis();
							logger.info("EndTime of uploadDocument Service [" + endTime + " ]");
							logger.info("TotalTime of uploadDocument Service [" + (endTime - startTime) + " ]");
					}
				} else {
					message = "Please upload valid File format.";
					response = CommonUtility.setCommonResponse(response, aesEncDec, key, isEnc, message);
				}
				/*
				 * }else { message = "Please upload valid File."; response =
				 * CommonUtility.setCommonResponse(response, aesEncDec, key, isEnc, message); }
				 */
			}

			else {
				message = readProperties.getPropertyValue("COMMONINPUTERROR").trim();
				response = CommonUtility.setCommonResponse(message);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			response = CommonUtility.setCommonResponseForException(response, readProperties);
		}

		return response;

	}

	public CommonResponse uploadDocumentForDemographic(CommonRequest request) throws CommonException {

		long startTime = System.currentTimeMillis();
		logger.info("StartTime of UploadDocument Service:" + startTime);
		AESEncDec aesEncDec = new AESEncDec();
		CommonResponse response = new CommonResponse();
		String responseStr = null;
		String message = null;
		String deviceId = "", sessionKey = "";
		boolean isAllow = false;

		try {
			deviceId = request.getDevice_id();
			sessionKey = request.getSession_key();
			boolean isValidRequest = CommonUtility.validateRequest(isEnc, request, aesEncDec);

			if (isValidRequest) {

				String key = AESEncDec.generateRandomKey(deviceId, sessionKey, isEnc);

				String inputString = aesEncDec.decrypt(key, request.getInputString(), isEnc);
				logger.info("decrypted inputString= " + inputString);

				Gson gson = new GsonBuilder().setPrettyPrinting().create();
				UploadDocumentRequest serviceRequest = gson.fromJson(inputString, UploadDocumentRequest.class);
				String leadId = serviceRequest.getLead_id();
				String fileType = serviceRequest.getType();
				String Base64data = serviceRequest.getData();
				String fileName = serviceRequest.getDocument_name();

				boolean isValidBase64String = new FileValidator().isValidBase64String(Base64data);
				logger.info("isValidBase64String :" + isValidBase64String);
				if (isValidBase64String) {
					isAllow = false;
					if (fileType.equalsIgnoreCase("jpg") || fileType.equalsIgnoreCase("png")
							|| fileType.equalsIgnoreCase("jpeg") || fileType.equalsIgnoreCase("pdf")) {

						byte[] data = FileValidator.decodeImage(Base64data);
						MagicMatch match = Magic.getMagicMatch(data);
						String mimeType = match.getMimeType();
						logger.info("mime type :" + mimeType);
						logger.info("fileType :" + fileType);
						if (fileType.equalsIgnoreCase("jpg") || fileType.equalsIgnoreCase("png")
								|| fileType.equalsIgnoreCase("jpeg")) {
							if (mimeType.contains("jpg") || mimeType.contains("jpeg") || mimeType.contains("png")) {
								isAllow = false;
							} else {
								logger.info("mimeType in else :" + fileType);
								isAllow = true;
							}
						} else {
							if (mimeType.contains(fileType)) {
								isAllow = false;
								logger.info("mimeType in if :" + fileType);
							} else {
								isAllow = true;
							}
						}

					}

				} else {
					logger.info("isAllow :" + isAllow);
					isAllow = true;
				}

				logger.info("leadId= " + leadId);
				logger.info("isAllow :" + isAllow);

				if (!isAllow) {
					if (!Strings.isNullOrEmpty(leadId)) {

						String payload = gson.toJson(serviceRequest);
						final String serviceURL = localURL+readProperties.getPropertyValue("UPLOADDOCUMENTURL").trim();
						responseStr = CommonUtility.callRestClient(payload, serviceURL);
						response = CommonUtility.generateCommonResponse(key, responseStr, isEnc, aesEncDec,
								readProperties);
					}
				} else {
					message = "Please upload valid File format.";
					response = CommonUtility.setCommonResponse(response, aesEncDec, key, isEnc, message);
				}
			}

			else {
				message = readProperties.getPropertyValue("COMMONINPUTERROR").trim();
				response = CommonUtility.setCommonResponse(message);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			response = CommonUtility.setCommonResponseForException(response, readProperties);
		}

		return response;

	}

	public CommonResponse previewDocument(CommonRequest request) throws CommonException {

		long startTime = System.currentTimeMillis();
		logger.info("StartTime of previewDocument Service [" + startTime + " ]");
		AESEncDec aesEncDec = new AESEncDec();
		CommonResponse response = new CommonResponse();
		String responseStr = null;
		String message = null;
		String deviceId = "", sessionKey = "";

		try {
			deviceId = request.getDevice_id();
			sessionKey = request.getSession_key();
			boolean isValidRequest = CommonUtility.validateRequest(isEnc, request, aesEncDec);

			if (isValidRequest) {

				String key = AESEncDec.generateRandomKey(deviceId, sessionKey, isEnc);
				String inputString = aesEncDec.decrypt(key, request.getInputString(), isEnc);
				logger.info("decrypted inputString= " + inputString);

				Gson gson = new GsonBuilder().setPrettyPrinting().create();
				PreviewDocumentRequest serviceRequest = gson.fromJson(inputString, PreviewDocumentRequest.class);
				String leadId = serviceRequest.getLead_id();
				logger.info("leadId= " + leadId);

				if (!Strings.isNullOrEmpty(leadId)) {

					String payload = gson.toJson(serviceRequest);
					final String serviceURL = localURL+readProperties.getPropertyValue("PREVIEWDOCUMENTURL").trim();
					responseStr = CommonUtility.callRestClient(payload, serviceURL);
					response = CommonUtility.generateCommonResponse(key, responseStr, isEnc, aesEncDec, readProperties);
					long endTime = System.currentTimeMillis();
					logger.info("EndTime of previewDocument Service [" + endTime + " ]");
					logger.info("TotalTime of previewDocument Service [" + (endTime - startTime) + " ]");
				}
			}

			else {
				message = readProperties.getPropertyValue("COMMONINPUTERROR").trim();
				response = CommonUtility.setCommonResponse(message);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			response = CommonUtility.setCommonResponseForException(response, readProperties);
		}

		return response;

	}

	public CommonResponse getDocumentList(CommonRequest request) throws CommonException {

		long startTime = System.currentTimeMillis();
		logger.info("StartTime of DocumentList Service [" + startTime + " ]");
		AESEncDec aesEncDec = new AESEncDec();
		CommonResponse response = new CommonResponse();
		String responseStr = null;
		String message = null;
		String deviceId = "", sessionKey = "";

		try {
			deviceId = request.getDevice_id();
			sessionKey = request.getSession_key();
			boolean isValidRequest = CommonUtility.validateRequest(isEnc, request, aesEncDec);

			if (isValidRequest) {

				String key = AESEncDec.generateRandomKey(deviceId, sessionKey, isEnc);
				String inputString = aesEncDec.decrypt(key, request.getInputString(), isEnc);
				logger.info("decrypted inputString= " + inputString);

				Gson gson = new GsonBuilder().setPrettyPrinting().create();
				DocumentListRequest serviceRequest = gson.fromJson(inputString, DocumentListRequest.class);
				String leadId = serviceRequest.getLead_id();
				logger.info("leadId= " + leadId);

				if (!Strings.isNullOrEmpty(leadId)) {

					String payload = gson.toJson(serviceRequest);
					final String serviceURL = localURL+readProperties.getPropertyValue("GETDOCUMENTLIST").trim();
					responseStr = CommonUtility.callRestClient(payload, serviceURL);
					if (!Strings.isNullOrEmpty(responseStr)) {

						logger.info("ResponseStr =" + responseStr);
						DocumentListResponse docListResponse = gson.fromJson(responseStr, DocumentListResponse.class);
						String fileSize = readProperties.getPropertyValue("FILESIZEKB") == null ? "5120"
								: readProperties.getPropertyValue("FILESIZEKB");
						String maxDocumentLimit = readProperties.getPropertyValue("MAXDOCLIMIT") == null ? "10"
								: readProperties.getPropertyValue("MAXDOCLIMIT");
						docListResponse.setFileSizeKB(fileSize);
						docListResponse.setMaxDocLimit(maxDocumentLimit);
						responseStr = gson.toJson(docListResponse);
						logger.info("DocumentList Response=" + response);

						JsonToList jsonToList = new JsonToList();
						String DocumentlistResponse = jsonToList.JsonResponseToList(responseStr);
						logger.info("DocumentList=" + DocumentlistResponse);

						String encResponse = aesEncDec.encrypt(key, responseStr, isEnc);
						String encCheckSum = AESEncDec.createChecksum(encResponse, isEnc);
						response.setOutputString(encResponse);
						response.setChkSum(encCheckSum);
						response.setSuccess(true);
						response.setError_message(message);

						try {
							String leadid = "";
							String mobilenumber = "";
							String cacheRes = new CustomSessionServiceImpl().getDyncacheData(sessionKey, deviceId);

							org.json.simple.JSONObject cachejsonObject = CacheServices.parseString(cacheRes);

							mobilenumber = cachejsonObject.get("mobileno") == null ? ""
									: cachejsonObject.get("mobileno").toString();
							leadid = cachejsonObject.get("leadid") == null ? ""
									: cachejsonObject.get("leadid").toString();
							String crmId = cachejsonObject.get("crmid") == null ? ""
									: cachejsonObject.get("crmid").toString();
							String mpin = cachejsonObject.get("mpin") == null ? ""
									: cachejsonObject.get("mpin").toString();
							String txnid = cachejsonObject.get("txnid") == null ? ""
									: cachejsonObject.get("txnid").toString();
							String aadharnumber = cachejsonObject.get("aadharnumber") == null ? ""
									: cachejsonObject.get("aadharnumber").toString();
							String enckey = cachejsonObject.get("enckey") == null ? ""
									: cachejsonObject.get("enckey").toString();
							String otp = cachejsonObject.get("otp") == null ? ""
									: cachejsonObject.get("otp").toString();
							String serviceCompletionTime = cachejsonObject.get("serviceCompletionTime") == null ? ""
									: cachejsonObject.get("serviceCompletionTime").toString();

							SessionData data = new SessionData();
							data.setMobilenumber(mobilenumber);
							data.setDeviceid(deviceId);
							data.setLeadid(leadid);
							data.setEncKey(key);
							data.setMpin(mpin);
							data.setCrmid(crmId);
							data.setTxnid(txnid);
							data.setAadharnumber(aadharnumber);
							data.setEncKey(enckey);
							data.setOtp(otp);
							data.setServiceCompletionTime(serviceCompletionTime);
							data.setDocuments(DocumentlistResponse);

							cacheRes = new CustomSessionServiceImpl().saveDyncacheData(sessionKey, deviceId, data);

							long endTime = System.currentTimeMillis();
							logger.info("EndTime of DocumentList Service [" + endTime + " ]");
							logger.info("TotalTime of DocumentList Service [" + (endTime - startTime) + " ]");

						} catch (Exception e) {
							e.printStackTrace();
						}

					} else {

						message = readProperties.getPropertyValue("REQUESTERROR").trim();
						response.setOutputString(null);
						response.setChkSum(null);
						response.setSuccess(false);
						response.setError_message(message);
					}
				}
			}

			else {
				message = readProperties.getPropertyValue("COMMONINPUTERROR").trim();
				response = CommonUtility.setCommonResponse(message);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			response = CommonUtility.setCommonResponseForException(response, readProperties);
		}

		return response;
	}

	public CommonResponse logout(CommonRequest request) throws CommonException {

		long startTime = System.currentTimeMillis();
		logger.info("StartTime of getDocumentList Service:" + startTime);
		AESEncDec aesEncDec = new AESEncDec();
		CommonResponse response = new CommonResponse();
		boolean _isError = false;
		String message = null;
		String deviceId = "", sessionKey = "";

		try {
			deviceId = request.getDevice_id();
			sessionKey = request.getSession_key();
			String input = request.getInputString();
			String checksum = request.getChkSum();

			boolean isValid = aesEncDec.validateChecksum(input, checksum, isEnc);
			logger.info("CheckSum isValid= " + isValid);

			if (Strings.isNullOrEmpty(input) && Strings.isNullOrEmpty(checksum)) {
				_isError = true;
				message = readProperties.getPropertyValue("COMMONINPUTERROR").trim();
			}

			if (!_isError && isValid) {

				String key = AESEncDec.generateRandomKey(deviceId, sessionKey, isEnc);

				String inputString = aesEncDec.decrypt(key, input, isEnc);
				logger.info("decrypted inputString= " + inputString);

				Gson gson = new GsonBuilder().setPrettyPrinting().create();
				LogoutRequest serviceRequest = gson.fromJson(inputString, LogoutRequest.class);
				sessionKey = serviceRequest.getSession_key();
				String res = new CustomSessionServiceImpl().deleteDyncacheData(sessionKey, deviceId);

				String encResponse = aesEncDec.encrypt(key, res, isEnc);
				String encCheckSum = AESEncDec.createChecksum(encResponse, isEnc);
				response.setOutputString(encResponse);
				response.setChkSum(encCheckSum);
				response.setSuccess(true);
				response.setError_message(message);
			}

			else {
				message = readProperties.getPropertyValue("COMMONINPUTERROR").trim();
				response = CommonUtility.setCommonResponse(message);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			response = CommonUtility.setCommonResponseForException(response, readProperties);
		}

		return response;
	}

	public CommonResponse validateMpin(CommonRequest request) throws CommonException {

		long startTime = System.currentTimeMillis();
		logger.info("StartTime of validateMpin Service [" + startTime + " ]");
		AESEncDec aesEncDec = new AESEncDec();
		CommonResponse response = new CommonResponse();
		LoginAttemptsResponse attemptsResponse = new LoginAttemptsResponse();
		boolean _isError = false;
		String message = null;
		String deviceId = "", sessionKey = "";
		try {
			logger.info("CommonRequest:= " + request.toString());
			deviceId = request.getDevice_id();
			sessionKey = request.getSession_key();
			boolean isValidRequest = CommonUtility.validateRequest(isEnc, request, aesEncDec);
			String key = AESEncDec.generateRandomKey(deviceId, sessionKey, isEnc);

			if (isValidRequest) {

				logger.info("key= " + key);
				String inputString = aesEncDec.decrypt(key, request.getInputString(), isEnc);
				logger.info("MPINValidationRequest= " + inputString);

				Gson gson = new GsonBuilder().setPrettyPrinting().create();
				MPINValidationRequest serviceRequest = gson.fromJson(inputString, MPINValidationRequest.class);
				String mpin = serviceRequest.getMpin();
				String enableTouchID = serviceRequest.getTouchId().trim();
				logger.info("mpin:= " + mpin);
				String cacheRes = new CustomSessionServiceImpl().getDyncacheData(sessionKey, deviceId);
				org.json.simple.JSONObject cachejsonObject = CacheServices.parseString(cacheRes);
				boolean isInvalidMPIN = CustomSessionServiceImpl.validateMpin(mpin, cachejsonObject);
				String mobileNumber = cachejsonObject.get("mobileno") == null ? ""
						: cachejsonObject.get("mobileno").toString();
				logger.info("getting mobileNumber from session" + mobileNumber);
				logger.info("isInvalidMPIN:= " + isInvalidMPIN);

				if (isInvalidMPIN) {
					_isError = true;
					message = CustomSessionServiceImpl.message;
				}
				if (!enableTouchID.equalsIgnoreCase("y")) {

					attemptsResponse = CommonUtility.callLoginAttemptService(isInvalidMPIN, mpin, mobileNumber);
					String record_access = attemptsResponse.getRecord_status();
					logger.info("record_access: " + record_access);
					if (record_access != null && "locked".equals(record_access)) {

						_isError = true;
						message = attemptsResponse.getError_message();
					}
				}

				if (!_isError) {

					String responseStr = "{\"error_message\":\"" + message + "\",\"success\":true}";
					response = CommonUtility.generateCommonResponse(key, responseStr, isEnc, aesEncDec, readProperties);
					long endTime = System.currentTimeMillis();
					logger.info("EndTime of validateMpin Service [" + endTime + " ]");
					logger.info("TotalTime of validateMpin Service [" + (endTime - startTime) + " ]");
				} else {
					response = CommonUtility.setCommonResponse(response, aesEncDec, key, isEnc, message);
				}
			} else {
				message = readProperties.getPropertyValue("COMMONINPUTERROR").trim();
				response = CommonUtility.setCommonResponse(message);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return response;
	}

	public CommonResponse eSignForNonPL(CommonRequest request) throws CommonException {

		long startTime = System.currentTimeMillis();
		logger.info("StartTime of eSignForNonPL Service [" + startTime + " ]");
		AESEncDec aesEncDec = new AESEncDec();
		CommonResponse response = new CommonResponse();
		ConsentResponse consentResponse = new ConsentResponse();
		String message = null;
		String deviceId = "", sessionKey = "", responseStr = "";
		try {
			logger.info("CommonRequest:= " + request.toString());
			deviceId = request.getDevice_id();
			sessionKey = request.getSession_key();
			boolean isValidRequest = CommonUtility.validateRequest(isEnc, request, aesEncDec);
			String key = AESEncDec.generateRandomKey(deviceId, sessionKey, isEnc);

			if (isValidRequest) {

				logger.info("key= " + key);
				String inputString = aesEncDec.decrypt(key, request.getInputString(), isEnc);
				logger.info("eSignForNonPLRequest= " + inputString);

				Gson gson = new GsonBuilder().setPrettyPrinting().create();
				ESignForNonPLRequest serviceRequest = gson.fromJson(inputString, ESignForNonPLRequest.class);
				String leadId = serviceRequest.getLead_id();
				String ipaddress = serviceRequest.getDeviceIP();
				String request_name = serviceRequest.getRequest_name();
				String additional_fld1 = serviceRequest.getAdditional_fld1();
				String additional_fld2 = serviceRequest.getAdditional_fld2();
				String additional_fld3 = serviceRequest.getAdditional_fld3();
				String consentRes = new IVLCommonServiceImpl().callCustomerConsentService("esign", leadId, ipaddress,
						"");
				consentResponse = gson.fromJson(consentRes, ConsentResponse.class);
				boolean isConsent = consentResponse.isSuccess();

				if (isConsent) {

					String opp_id = serviceRequest.getOpp_id();
					String con_id = serviceRequest.getCon_id();
					String loanExt_id = serviceRequest.getLoanExt_id();

					String docFromCRMRequest = "{\"lead_id\":\"" + leadId + "\",\"request_name\":\"" + request_name
							+ "\",\"additional_fld1\":\"" + additional_fld1 + "\",\"additional_fld2\":\""
							+ additional_fld2 + "\",\"additional_fld3\":\"" + additional_fld3 + "\"," + "\"opp_id\":\""
							+ opp_id + "\",\"con_id\":\"" + con_id + "\",\"loanExt_id\":\"" + loanExt_id + "\"}";

					final String serviceURL = localURL+readProperties.getPropertyValue("CAPTUREVALIDATEDATAURL").trim();
					responseStr = CommonUtility.callRestClient(docFromCRMRequest, serviceURL);
					response = CommonUtility.generateCommonResponse(key, responseStr, isEnc, aesEncDec, readProperties);

				} else {
					response = CommonUtility.generateCommonResponse(key, consentRes, isEnc, aesEncDec, readProperties);
				}

			} else {
				message = readProperties.getPropertyValue("COMMONINPUTERROR").trim();
				response = CommonUtility.setCommonResponse(message);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return response;
	}
	
	
}
