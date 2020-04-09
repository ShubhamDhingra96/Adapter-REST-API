package snap.ib.personelLoan.adapter.common.serviceImpl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ibm.json.java.JSONObject;
import com.snap.ib.personelLoan.common.BaseResource;

import snap.ib.personelLoan.adapter.common.CommonAdapterResource;
import snap.ib.personelLoan.adapter.common.beans.List_loans_detail;
import snap.ib.personelLoan.adapter.common.exception.CommonException;
import snap.ib.personelLoan.adapter.common.request.AESKeyRequest;
import snap.ib.personelLoan.adapter.common.request.AcceptTopUpRequest;
import snap.ib.personelLoan.adapter.common.request.ActivatePrepaidCardRequest;
import snap.ib.personelLoan.adapter.common.request.BillPaymentRequest;
import snap.ib.personelLoan.adapter.common.request.BranchRequest;
import snap.ib.personelLoan.adapter.common.request.CaptureValidateDataRequest;
import snap.ib.personelLoan.adapter.common.request.CommonRequest;
import snap.ib.personelLoan.adapter.common.request.CompanyLookUpRequest;
import snap.ib.personelLoan.adapter.common.request.CreateProductRequest;
import snap.ib.personelLoan.adapter.common.request.CreditVidyaEmailFraudRequest;
import snap.ib.personelLoan.adapter.common.request.EAgreementOTPAuthRequest;
import snap.ib.personelLoan.adapter.common.request.EAgreementSignRequest;
import snap.ib.personelLoan.adapter.common.request.EMIPaymentDetailsRequest;
import snap.ib.personelLoan.adapter.common.request.EMIPaymentUpdateRequest;
import snap.ib.personelLoan.adapter.common.request.EMandateResultRequest;
import snap.ib.personelLoan.adapter.common.request.ENachDetailsRequest;
import snap.ib.personelLoan.adapter.common.request.ENachValidationRequest;
import snap.ib.personelLoan.adapter.common.request.ESignVarificationRequest;
import snap.ib.personelLoan.adapter.common.request.EmandateDropOutRequest;
import snap.ib.personelLoan.adapter.common.request.FAQRequest;
import snap.ib.personelLoan.adapter.common.request.GenerateAgreementFormsRequest;
import snap.ib.personelLoan.adapter.common.request.GetApplicationInformationRequest;
import snap.ib.personelLoan.adapter.common.request.GetContactCRMRequest;
import snap.ib.personelLoan.adapter.common.request.GetDocumentFromCRMRequest;
import snap.ib.personelLoan.adapter.common.request.GetEMandateDataRequest;
import snap.ib.personelLoan.adapter.common.request.GetProfileCRMRequest;
import snap.ib.personelLoan.adapter.common.request.HDFCRedirectResParamRequest;
import snap.ib.personelLoan.adapter.common.request.HDFCUrlRedirectionRequest;
import snap.ib.personelLoan.adapter.common.request.HerokuRequest;
import snap.ib.personelLoan.adapter.common.request.InitiateCibilRequest;
import snap.ib.personelLoan.adapter.common.request.InitiateDaasCall1Request;
import snap.ib.personelLoan.adapter.common.request.InitiateDisbursementRequest;
import snap.ib.personelLoan.adapter.common.request.LoanInsuranceRequest;
import snap.ib.personelLoan.adapter.common.request.LoanOutstandingRequest;
import snap.ib.personelLoan.adapter.common.request.LoanRepaymentDetailsRequest;
import snap.ib.personelLoan.adapter.common.request.LoanSummaryRequest;
import snap.ib.personelLoan.adapter.common.request.LoginAttemptsRequest;
import snap.ib.personelLoan.adapter.common.request.MasterDataRequest;
import snap.ib.personelLoan.adapter.common.request.NewUserSignUPRequest;
import snap.ib.personelLoan.adapter.common.request.NotifyCRMRequest;
import snap.ib.personelLoan.adapter.common.request.PANSubmissionForDASSRequest;
import snap.ib.personelLoan.adapter.common.request.PerfiosCheckStatusRequest;
import snap.ib.personelLoan.adapter.common.request.PushSignedDocRequest;
import snap.ib.personelLoan.adapter.common.request.PutAadharDocToIBRequest;
import snap.ib.personelLoan.adapter.common.request.SaveComplaintCRMRequest;
import snap.ib.personelLoan.adapter.common.request.ServiceRequestCRMRequest;
import snap.ib.personelLoan.adapter.common.request.TopUpDetailsRequest;
import snap.ib.personelLoan.adapter.common.request.UpdateAddressRequest;
import snap.ib.personelLoan.adapter.common.request.ValidatePerfiosForENachRequest;
import snap.ib.personelLoan.adapter.common.response.AESKeyResponse;
import snap.ib.personelLoan.adapter.common.response.BillPaymentResponse;
import snap.ib.personelLoan.adapter.common.response.CommonResponse;
import snap.ib.personelLoan.adapter.common.response.ConsentResponse;
import snap.ib.personelLoan.adapter.common.response.CreateProductResponse;
import snap.ib.personelLoan.adapter.common.response.GetApplicationInformationResponse;
import snap.ib.personelLoan.adapter.common.response.HDFCRedirectResParamResponse;
import snap.ib.personelLoan.adapter.common.response.HDFCUrlRedirectionResponse;
import snap.ib.personelLoan.adapter.common.response.LoanInsuranceResponse;
import snap.ib.personelLoan.adapter.common.response.LoginAttemptsResponse;
import snap.ib.personelLoan.adapter.common.response.NewUserSignUPResponse;
import snap.ib.personelLoan.adapter.common.response.ServiceRequestCRMResponse;
import snap.ib.personelLoan.adapter.common.service.IVLCommonService;
import snap.ib.personelLoan.adapter.common.sessionutil.CacheServices;
import snap.ib.personelLoan.adapter.common.sessionutil.CustomSessionServiceImpl;
import snap.ib.personelLoan.adapter.common.sessionutil.SessionData;
import snap.ib.personelLoan.adapter.common.util.AESEncDec;
import snap.ib.personelLoan.adapter.common.util.AES_Encrption;
import snap.ib.personelLoan.adapter.common.util.CalculateTime;
import snap.ib.personelLoan.adapter.common.util.CommonUtility;
import snap.ib.personelLoan.adapter.common.util.NetbankingPayload;
import snap.ib.personelLoan.adapter.common.util.OTPGeneration;
import snap.ib.personelLoan.adapter.common.util.ReadProperty;

public class IVLCommonServiceImpl extends BaseResource implements IVLCommonService {

	private static Logger logger = LoggerFactory.getLogger(CommonAdapterResource.class);
	private final String isEnc = readProperties.getPropertyValue("IS_ENC");
	private static final ReadProperty readProperties = ReadProperty.getInstance();
	private final String localURL = readProperties.getPropertyValue("UATENVURL") == null ? ""
			: readProperties.getPropertyValue("UATENVURL");
	private final String isDhaniWebUp = readProperties.getPropertyValue("ISDHANIWEBUP").trim();
	private static IVLCommonService ivlSevice;

	public static IVLCommonService getInstance() {
		if (ivlSevice == null) {
				ivlSevice = new IVLCommonServiceImpl();
		}
		return ivlSevice;
	}

	/**
	 * Function used to fetch document from CRM services.
	 * 
	 * @param GetDocumentFromCRMRequest
	 * @return GetDocumentFromCRMResponse
	 */

	public CommonResponse getDocFromCRMStatus1(CommonRequest request) throws CommonException {

		long startTime = System.currentTimeMillis();
		logger.info("StartTime of DocumentFromCRM Service [" + startTime + " ]");
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
				String userSignupRequest = aesEncDec.decrypt(key, request.getInputString(), isEnc);
				logger.info("decrypted inputString= " + userSignupRequest);

				Gson gson = new GsonBuilder().setPrettyPrinting().create();
				GetDocumentFromCRMRequest userSignUPRequest = gson.fromJson(userSignupRequest,
						GetDocumentFromCRMRequest.class);
				String leadId = userSignUPRequest.getLead_id();
				logger.info("leadId= " + leadId);
				if (Strings.isNullOrEmpty(leadId)) {
					_isError = true;
					message = readProperties.getPropertyValue("LEADIDERRORMESSAGE").trim();
				}
				// String cacheRes = new CustomSessionServiceImpl().getDyncacheData(sessionKey,
				// deviceId);
				// org.json.simple.JSONObject cachejsonObject =
				// CacheServices.parseString(cacheRes);

				/*
				 * if (CustomSessionServiceImpl.validateDetails("", cachejsonObject, "", leadId,
				 * "")) { _isError = true; message = CustomSessionServiceImpl.message; }
				 */

				if (!_isError) {

					String payload = gson.toJson(userSignUPRequest);
					final String crmServiceURL = localURL+readProperties.getPropertyValue("CRMSERVICEURL").trim();
					responseStr = CommonUtility.callRestClient(payload, crmServiceURL);
					response = CommonUtility.generateCommonResponse(key, responseStr, isEnc, aesEncDec, readProperties);
					long endTime = System.currentTimeMillis();
					logger.info("EndTime of DocumentFromCRM Service [" + endTime + " ]");
					logger.info("TotalTime of DocumentFromCRM Service [" + (endTime - startTime) + " ]");
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
	 * Function used to generate agreement form
	 * 
	 * @param GenerateAgreementFormsRequest
	 * @return GenerateAgreementFormsResponse
	 */

	public CommonResponse generateAgreementForms(CommonRequest request) throws CommonException {

		long startTime = System.currentTimeMillis();
		logger.info("StartTime of generateAgreementForms Service [" + startTime + " ]");
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
				GenerateAgreementFormsRequest agreementFormsRequest = gson.fromJson(inputString,
						GenerateAgreementFormsRequest.class);
				String leadId = agreementFormsRequest.getLead_id();
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

					String payload = gson.toJson(agreementFormsRequest);
					final String agreementFormURL = localURL+ readProperties.getPropertyValue("AGREEMENTFORMURL").trim();
					responseStr = CommonUtility.callRestClient(payload, agreementFormURL);
					response = CommonUtility.generateCommonResponse(key, responseStr, isEnc, aesEncDec, readProperties);
					long endTime = System.currentTimeMillis();
					logger.info("EndTime of generateAgreementForms Service [" + endTime + " ]");
					logger.info("TotalTime of generateAgreementForms Service [" + (endTime - startTime) + " ]");
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
	 * Function used to check perfios status
	 * 
	 * @param PerfiosCheckStatusRequest
	 * @return PerfiosCheckStatus1Response
	 */

	public CommonResponse getPerfiosCheckStatus(CommonRequest request) throws CommonException {

		long startTime = System.currentTimeMillis();
		logger.info("StartTime of PerfiosCheck Service [" + startTime + " ]");
		AESEncDec aesEncDec = new AESEncDec();
		CommonResponse response = new CommonResponse();
		String responseStr = null;
		boolean _isError = false;
		String message = null;
		String deviceId = "", sessionKey = "", leadid = "";
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
				PerfiosCheckStatusRequest checkStatusRequest = gson.fromJson(inputString,
						PerfiosCheckStatusRequest.class);
				String leadId = checkStatusRequest.getLead_id();
				logger.info("leadId= " + leadId);
				String txnID = checkStatusRequest.getTxn_id();

				if (Strings.isNullOrEmpty(txnID)) {
					_isError = true;
					message = readProperties.getPropertyValue("TXNIDERROR").trim();
				}

				String cacheRes = new CustomSessionServiceImpl().getDyncacheData(sessionKey, deviceId);
				org.json.simple.JSONObject cachejsonObject = CacheServices.parseString(cacheRes);

				if (CustomSessionServiceImpl.validateDetails("", cachejsonObject, txnID, leadid, "")) {
					_isError = true;
					message = CustomSessionServiceImpl.message;
				}

				if (!_isError) {

					String payload = gson.toJson(checkStatusRequest);
					final String perfiosChkStatusURL = localURL
							+ readProperties.getPropertyValue("PERFIOSCHKSTATUSURL").trim();
					logger.info("perfiosChkStatus URL:" + perfiosChkStatusURL);
					responseStr = CommonUtility.callRestClient(payload, perfiosChkStatusURL);
					response = CommonUtility.generateCommonResponse(key, responseStr, isEnc, aesEncDec, readProperties);
					long endTime = System.currentTimeMillis();
					logger.info("EndTime of PerfiosCheck Service [" + endTime + " ]");
					logger.info("TotalTime of PerfiosCheck Service [" + (endTime - startTime) + " ]");

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
	 * Function used to fetch ExperianGetAlterData from ESB
	 * 
	 * @param ExperianGetAlternateDataRequest
	 * @return ExperianGetAlternateDataResponse
	 */

	@SuppressWarnings("unused")
	public CommonResponse getExpAltData(CommonRequest request) throws CommonException {

		long startTime = System.currentTimeMillis();
		logger.info("StartTime of expGetAltData Service:" + startTime);
		AESEncDec aesEncDec = new AESEncDec();
		CommonResponse response = new CommonResponse();
		String responseStr = null;
		boolean _isError = false;
		String message = null;
		String deviceId = "", sessionKey = "", leadid = "";
		try {

			deviceId = request.getDevice_id();
			sessionKey = request.getSession_key();
			boolean isValidRequest = CommonUtility.validateRequest(isEnc, request, aesEncDec);

			if (isValidRequest) {

				String key = AESEncDec.generateRandomKey(deviceId, sessionKey, isEnc);
				logger.info("key= " + key);
				String inputString = aesEncDec.decrypt(key, request.getInputString(), isEnc);
				logger.info("decrypted inputString= " + inputString);

				/*
				 * Gson gson = new GsonBuilder().setPrettyPrinting().create();
				 * ExperianGetAlternateDataRequest
				 * alternateDataRequest=gson.fromJson(inputString,
				 * ExperianGetAlternateDataRequest.class); String leadId =
				 * alternateDataRequest.getLead_id(); logger.info("leadId= " + leadId); if
				 * (Strings.isNullOrEmpty(leadId)) { _isError = true; message =
				 * readProperties.getPropertyValue("LEADIDERRORMESSAGE").trim(); } String
				 * cacheRes = new CustomSessionServiceImpl().getDyncacheData(sessionKey,
				 * deviceId); org.json.simple.JSONObject cachejsonObject =
				 * CacheServices.parseString(cacheRes);
				 */

				/*
				 * if (CustomSessionServiceImpl.validateDetails("", cachejsonObject, "", leadid,
				 * "")) { _isError = true; message = CustomSessionServiceImpl.message; }
				 */

				if (!_isError) {

					// String payload = gson.toJson(alternateDataRequest);
					final String expGetAltDataURL = localURL+readProperties.getPropertyValue("EXPGETALTDATAURL").trim();
					responseStr = CommonUtility.callRestClient(inputString, expGetAltDataURL);
					response = CommonUtility.generateCommonResponse(key, responseStr, isEnc, aesEncDec, readProperties);
					long endTime = System.currentTimeMillis();
					logger.info("EndTime of expGetAltData Service:" + endTime + "\t"
							+ "TotalTime of expGetAltData Service:" + (endTime - startTime));
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
	 * Function used to get ApplicationInformation from ESB
	 * 
	 * @param GetApplicationInformationRequest
	 * @return GetApplicationInformationResponse
	 */

	public CommonResponse getApplicationInfo(CommonRequest request) throws CommonException {

		long startTime = System.currentTimeMillis();
		logger.info("StartTime of getApplicationInfo Service [" + startTime + " ]");
		AESEncDec aesEncDec = new AESEncDec();
		CommonResponse response = new CommonResponse();
		String responseStr = null;
		boolean _isError = false;
		String message = null;
		String sessionKey = "", deviceId = "";

		try {

			sessionKey = request.getSession_key();
			deviceId = request.getDevice_id();
			boolean isValidRequest = CommonUtility.validateRequest(isEnc, request, aesEncDec);

			if (isValidRequest) {

				String key = AESEncDec.generateRandomKey(deviceId, sessionKey, isEnc);
				logger.info("key= " + key);
				String inputString = aesEncDec.decrypt(key, request.getInputString(), isEnc);
				logger.info("decrypted inputString= " + inputString);

				Gson gson = new GsonBuilder().setPrettyPrinting().create();
				GetApplicationInformationRequest informationRequest = gson.fromJson(inputString,
						GetApplicationInformationRequest.class);
				logger.info("StartTime of getApplicationInfo:: " + startTime + " :: for lead:: "
						+ informationRequest.getLead_id());
				String lead_id = informationRequest.getLead_id();
				logger.info("leadId= " + lead_id);
				String enableTouchID = informationRequest.getTouchId().trim();
				logger.info("touchId Value:\t" + enableTouchID);
				if (Strings.isNullOrEmpty(lead_id)) {
					_isError = true;
					message = readProperties.getPropertyValue("LEADIDERRORMESSAGE").trim();
				}
				String mpin = informationRequest.getMpin();
				String cacheRes = new CustomSessionServiceImpl().getDyncacheData(sessionKey, deviceId);
				org.json.simple.JSONObject cachejsonObject = CacheServices.parseString(cacheRes);

				if (!_isError) {

					String payload = gson.toJson(informationRequest);
					final String getAppInfoURL = localURL+readProperties.getKeyValue("GETAPPLICATIONINFOURL").trim();
					responseStr = CommonUtility.callRestClient(payload, getAppInfoURL);
					GetApplicationInformationResponse getAppInfoResponse = gson.fromJson(responseStr,
							GetApplicationInformationResponse.class);
					getAppInfoResponse.setMpin("XXXX");
					
					logger.info("getPrepaid_card_status param =" + getAppInfoResponse.getPrepaid_card_status());
					if(Strings.isNullOrEmpty(getAppInfoResponse.getPrepaid_card_status()) )
					{
						//getAppInfoResponse.setPrepaid_card_status("X");
					}
					if(Strings.isNullOrEmpty(getAppInfoResponse.getPrepaid_card_type()) )
					{
						//getAppInfoResponse.setPrepaid_card_type("X");
					}
					if(Strings.isNullOrEmpty(getAppInfoResponse.getPrepaid_card_courier_status()) )
					{
						//getAppInfoResponse.setPrepaid_card_courier_status("X");
					}
						
					logger.info("upload_eligibilty param =" + getAppInfoResponse.isUpload_eligibilty());
					responseStr = gson.toJson(getAppInfoResponse);
					response = CommonUtility.generateCommonResponse(key, responseStr, isEnc, aesEncDec, readProperties);

					try {

						String mobilenumber = cachejsonObject.get("mobileno") == null ? ""
								: cachejsonObject.get("mobileno").toString();
						String aadharNumber = getAppInfoResponse.getAadhaar_number();
						mpin = cachejsonObject.get("mpin") == null ? "" : cachejsonObject.get("mpin").toString();
						SessionData data = new SessionData();
						data.setMobilenumber(mobilenumber);
						data.setDeviceid(deviceId);
						data.setLeadid(lead_id);
						data.setAadharnumber(aadharNumber);
						data.setEncKey(key);
						data.setMpin(mpin);
						cacheRes = new CustomSessionServiceImpl().saveDyncacheData(sessionKey, deviceId, data);

					} catch (Exception e) {
						e.printStackTrace();
					}
					long endTime = System.currentTimeMillis();
					logger.info("EndTime of getApplicationInfo Service [" + endTime + " ]");
					logger.info("TotalTime of getApplicationInfo Service [" + (endTime - startTime) + " ]");
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

	/**
	 * Function used to get EmandateDropOutStatus from ESB
	 * 
	 * @param EmandateDropOutRequest
	 * @return EmandateDropOutResponse
	 */

	public CommonResponse getEmandateDropOutStatus(CommonRequest request) throws CommonException {

		long startTime = System.currentTimeMillis();
		logger.info("StartTime of EmandateDropOut Service [" + startTime + " ]");
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
				EmandateDropOutRequest dropOutRequest = gson.fromJson(inputString, EmandateDropOutRequest.class);
				String leadId = dropOutRequest.getLead_id();
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

					String payload = gson.toJson(dropOutRequest);
					final String emandateDropOutURL = localURL
							+ readProperties.getPropertyValue("EMANDATEDROPOUTURL").trim();
					responseStr = CommonUtility.callRestClient(payload, emandateDropOutURL);
					response = CommonUtility.generateCommonResponse(key, responseStr, isEnc, aesEncDec, readProperties);
					long endTime = System.currentTimeMillis();
					logger.info("EndTime of EmandateDropOut Service [" + endTime + " ]");
					logger.info("TotalTime of EmandateDropOut Service [" + (endTime - startTime) + " ]");
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
	 * Function used to get ProfileCRMStatus from ESB
	 * 
	 * @param GetProfileCRMRequest
	 * @return GetProfileCRMResponse
	 */

	public CommonResponse getProfileCRMStatus(CommonRequest request) throws CommonException {

		long startTime = System.currentTimeMillis();
		logger.info("StartTime of ProfileCRM Service [" + startTime + " ]");
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
				GetProfileCRMRequest getProfileCRMRequest = gson.fromJson(inputString, GetProfileCRMRequest.class);
				String crmId = getProfileCRMRequest.getCrm_id();
				logger.info("leadId= " + crmId);

				if (Strings.isNullOrEmpty(crmId)) {
					_isError = true;
					message = readProperties.getPropertyValue("CRMIDERROR").trim();
				}

				String cacheRes = new CustomSessionServiceImpl().getDyncacheData(sessionKey, deviceId);
				org.json.simple.JSONObject cachejsonObject = CacheServices.parseString(cacheRes);

				if (CustomSessionServiceImpl.validateDetails("", cachejsonObject, "", "", crmId)) {
					_isError = true;
					message = CustomSessionServiceImpl.message;
				}
				if (!_isError) {

					String payload = gson.toJson(getProfileCRMRequest);
					final String getProfileCRMURL = localURL
							+ readProperties.getPropertyValue("GETPROFILECRMURL").trim();
					responseStr = CommonUtility.callRestClient(payload, getProfileCRMURL);
					response = CommonUtility.generateCommonResponse(key, responseStr, isEnc, aesEncDec, readProperties);
					long endTime = System.currentTimeMillis();
					logger.info("EndTime of ProfileCRM Service [" + endTime + " ]");
					logger.info("TotalTime of ProfileCRM Service [" + (endTime - startTime) + " ]");
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
	 * Function used to fetch ContactCRMStatus from ESB
	 * 
	 * @param GetContactCRMRequest
	 * @return GetContactCRMResponse
	 */

	public CommonResponse getContactCRMStatus(CommonRequest request) throws CommonException {

		long startTime = System.currentTimeMillis();
		logger.info("StartTime of ContactCRM Service [" + startTime + " ]");
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
				GetContactCRMRequest serviceRequest = gson.fromJson(inputString, GetContactCRMRequest.class);
				String crmId = serviceRequest.getCrm_id();
				logger.info("crmId = " + crmId);
				if (Strings.isNullOrEmpty(crmId)) {
					_isError = true;
					message = readProperties.getPropertyValue("CRMIDERROR").trim();
				}

				String cacheRes = new CustomSessionServiceImpl().getDyncacheData(sessionKey, deviceId);
				org.json.simple.JSONObject cachejsonObject = CacheServices.parseString(cacheRes);

				if (CustomSessionServiceImpl.validateDetails("", cachejsonObject, "", "", crmId)) {
					_isError = true;
					message = CustomSessionServiceImpl.message;
				}

				if (!_isError) {

					String payload = gson.toJson(serviceRequest);
					final String serviceURL = localURL + readProperties.getPropertyValue("GETCONTACTCRMURL").trim();
					responseStr = CommonUtility.callRestClient(payload, serviceURL);
					response = CommonUtility.generateCommonResponse(key, responseStr, isEnc, aesEncDec, readProperties);
					long endTime = System.currentTimeMillis();
					logger.info("EndTime of ContactCRM Service [" + endTime + " ]");
					logger.info("TotalTime of ContactCRM Service [" + (endTime - startTime) + " ]");
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
	 * Function used to get NotifyCRMStatus from ESB
	 * 
	 * @param NotifyCRMRequest
	 * @return NotifyCRMResponse
	 */

	public CommonResponse getNotifyCRMStatus(CommonRequest request) throws CommonException {

		long startTime = System.currentTimeMillis();
		logger.info("StartTime of NotifyCRM Service [" + startTime + " ]");
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
				NotifyCRMRequest serviceRequest = gson.fromJson(inputString, NotifyCRMRequest.class);
				String lead_id = serviceRequest.getLead_id();
				logger.info("leadId= " + lead_id);

				if (Strings.isNullOrEmpty(lead_id)) {
					_isError = true;
					message = readProperties.getPropertyValue("LEADIDERRORMESSAGE").trim();
				}

				String cacheRes = new CustomSessionServiceImpl().getDyncacheData(sessionKey, deviceId);
				org.json.simple.JSONObject cachejsonObject = CacheServices.parseString(cacheRes);

				if (null != lead_id && CustomSessionServiceImpl.validateDetails("", cachejsonObject, "", lead_id, "")) {
					_isError = true;
					message = CustomSessionServiceImpl.message;
				}

				if (!_isError) {

					String payload = gson.toJson(serviceRequest);
					final String serviceURL = localURL+readProperties.getPropertyValue("GETNOTIFYCRMURL").trim();
					responseStr = CommonUtility.callRestClient(inputString, serviceURL);
					response = CommonUtility.generateCommonResponse(key, responseStr, isEnc, aesEncDec, readProperties);
					long endTime = System.currentTimeMillis();
					logger.info("EndTime of NotifyCRM Service [" + endTime + " ]");
					logger.info("TotalTime of NotifyCRM Service [" + (endTime - startTime) + " ]");
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
	 * Function used to get saveComplaintCRMStatus from ESB
	 * 
	 * @param SaveComplaintCRMRequest
	 * @return SaveComplaintCRMResponse
	 */

	public CommonResponse saveComplaintCRMStatus(CommonRequest request) throws CommonException {

		long startTime = System.currentTimeMillis();
		logger.info("StartTime of saveComplaintCRM Service [" + startTime + " ]");
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
				SaveComplaintCRMRequest serviceRequest = gson.fromJson(inputString, SaveComplaintCRMRequest.class);
				String crm_id = serviceRequest.getCrm_id();
				logger.info("crm_id = " + crm_id);

				if (Strings.isNullOrEmpty(crm_id)) {
					_isError = true;
					message = readProperties.getPropertyValue("CRMIDERROR").trim();
				}

				String cacheRes = new CustomSessionServiceImpl().getDyncacheData(sessionKey, deviceId);
				org.json.simple.JSONObject cachejsonObject = CacheServices.parseString(cacheRes);

				if (CustomSessionServiceImpl.validateDetails("", cachejsonObject, "", "", crm_id)) {
					_isError = true;
					message = CustomSessionServiceImpl.message;
				}

				if (!_isError) {

					String payload = gson.toJson(serviceRequest);
					final String serviceURL = localURL + readProperties.getPropertyValue("SAVECOMPLAINTCRMURL").trim();
					responseStr = CommonUtility.callRestClient(payload, serviceURL);
					response = CommonUtility.generateCommonResponse(key, responseStr, isEnc, aesEncDec, readProperties);
					long endTime = System.currentTimeMillis();
					logger.info("EndTime of saveComplaintCRM Service [" + endTime + " ]");
					logger.info("TotalTime of saveComplaintCRM Service [" + (endTime - startTime) + " ]");
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
	 * Function used to get ServiceRequestCRMStatus from ESB
	 * 
	 * @param ServiceRequestCRMRequest
	 * @return ServiceRequestCRMResponse
	 */

	public CommonResponse getServiceRequestCRMStatus(CommonRequest request) throws CommonException {

		long startTime = System.currentTimeMillis();
		logger.info("StartTime of ServiceRequestCRM Service [" + startTime + " ]");
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
				ServiceRequestCRMRequest serviceRequest = gson.fromJson(inputString, ServiceRequestCRMRequest.class);
				String crm_id = serviceRequest.getCrm_id();
				logger.info("crm_id = " + crm_id);

				if (Strings.isNullOrEmpty(crm_id)) {
					_isError = true;
					message = readProperties.getPropertyValue("CRMIDERROR").trim();
				}

				String currentTime = new SimpleDateFormat("ddMMyyyy").format(new Date());
				String transaction_id = currentTime + NetbankingPayload.generateRandom(8);
				logger.info("transaction_id:" + transaction_id);
				serviceRequest.setTransaction_id(transaction_id);

				if (!_isError) {

					String payload = gson.toJson(serviceRequest);
					JSONObject jsonObject = JSONObject.parse(payload);
					final String serviceURL = localURL + readProperties.getPropertyValue("SERVICEREQUESTCRMURL").trim();
					responseStr = CommonUtility.callRestClient(payload, serviceURL);
					logger.info("ResponseStr =" + responseStr);
					ServiceRequestCRMResponse crmResponse = gson.fromJson(responseStr, ServiceRequestCRMResponse.class);
					currentTime = new SimpleDateFormat("HH:mm").format(new Date());
					crmResponse.setCurrentTime(currentTime);
					logger.info("currentTime =" + currentTime);

					jsonObject.put("status", crmResponse.getStatus().trim());
					jsonObject.put("currentTime", currentTime);
					jsonObject.put("error_message", crmResponse.getError_message());
					jsonObject.put("case_number", crmResponse.getCase_number());
					jsonObject.put("success", crmResponse.getSuccess());
					jsonObject.put("case_creation_date", crmResponse.getCase_creation_date());
					responseStr = jsonObject.toString();
					response = CommonUtility.generateCommonResponse(key, responseStr, isEnc, aesEncDec, readProperties);
					long endTime = System.currentTimeMillis();
					logger.info("EndTime of ServiceRequestCRM Service [" + endTime + " ]");
					logger.info("TotalTime of ServiceRequestCRM Service [" + (endTime - startTime) + " ]");
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
	 * Function used to get eMandateResultStatus from ESB
	 * 
	 * @param EMandateResultRequest
	 * @return EMandateResultResponse
	 */
	public CommonResponse eMandateResultStatus(CommonRequest request) throws CommonException {

		long startTime = System.currentTimeMillis();
		logger.info("StartTime of eMandateResult Service [" + startTime + " ]");
		logger.info("eMandateResult CommonRequest:" + request.toString());
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
				
				logger.error("IBMANDATE  ::  EMandateResult  request Enc Token:: " + request.getInputString());
				logger.error("IBMANDATE  ::  EMandateResult  request Dec Token:: " + inputString);

				Gson gson = new GsonBuilder().setPrettyPrinting().create();
				EMandateResultRequest serviceRequest = gson.fromJson(inputString, EMandateResultRequest.class);
				String lead_id = serviceRequest.getLead_id();
				logger.info("leadId= " + lead_id);
				if (Strings.isNullOrEmpty(lead_id)) {
					_isError = true;
					message = readProperties.getPropertyValue("LEADIDERRORMESSAGE").trim();
				}
				// String cacheRes = new CustomSessionServiceImpl().getDyncacheData(sessionKey,
				// deviceId);
				// org.json.simple.JSONObject cachejsonObject =
				// CacheServices.parseString(cacheRes);

				/*
				 * if (CustomSessionServiceImpl.validateDetails("", cachejsonObject, "",
				 * lead_id, "")) { _isError = true; message = CustomSessionServiceImpl.message;
				 * }
				 */

				if (!_isError) {

					String payload = gson.toJson(serviceRequest);
					logger.info("payload = " + payload);
					final String serviceURL = localURL + readProperties.getPropertyValue("EMANDATERESULTURL").trim();
					responseStr = CommonUtility.callRestClient(payload, serviceURL);
					logger.info("ESB responseStr = " + responseStr);
					response = CommonUtility.generateCommonResponse(key, responseStr, isEnc, aesEncDec, readProperties);
					long endTime = System.currentTimeMillis();
					
					logger.error("IBMANDATE  ::  EMandateResult  Dec response  :: " +responseStr );
					logger.error("IBMANDATE  ::  EMandateResult  Enc response:: " + response.toString());
					
					logger.info("EndTime of eMandateResult Service [" + endTime + " ]");
					logger.info("TotalTime of eMandateResult Service [" + (endTime - startTime) + " ]");
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

	public CommonResponse eSignVarificationStatus(CommonRequest request) throws CommonException {

		long startTime = System.currentTimeMillis();
		logger.info("StartTime of eSignVarification Service [" + startTime + " ]");
		String isEsignUp = readProperties.getPropertyValue("ISESIGNUP").trim();
		AESEncDec aesEncDec = new AESEncDec();
		CommonResponse response = new CommonResponse();
		ConsentResponse consentResponse = new ConsentResponse();
		String responseStr = null, txnId_clnt = "", message = null, deviceId = "", sessionKey = "";
		boolean _isError = false, isConsent = false;
		try {

			deviceId = request.getDevice_id();
			sessionKey = request.getSession_key();
			boolean isValidRequest = CommonUtility.validateRequest(isEnc, request, aesEncDec);
			String key = AESEncDec.generateRandomKey(deviceId, sessionKey, isEnc);

			if (isEsignUp.equalsIgnoreCase("Y")) {
				if (isValidRequest) {

					String inputString = aesEncDec.decrypt(key, request.getInputString(), isEnc);
					logger.info("key= " + key + " inputString= " + inputString);

					Gson gson = new GsonBuilder().setPrettyPrinting().create();
					ESignVarificationRequest serviceRequest = gson.fromJson(inputString,
							ESignVarificationRequest.class);
					String lead_id = serviceRequest.getLead_id();
					String doc_type = serviceRequest.getDocument_type();
					String aadhar_number = serviceRequest.getAadhaar_number();
					logger.info("leadId= " + lead_id + " doc_type :" + doc_type + " aadhar_number :" + aadhar_number);

					if (Strings.isNullOrEmpty(lead_id)) {
						_isError = true;
						message = readProperties.getPropertyValue("LEADIDERRORMESSAGE").trim();
					}
					if (Strings.isNullOrEmpty(aadhar_number)) {
						_isError = true;
						message = readProperties.getPropertyValue("AADHARNUMBERERROR").trim();
					}

					if (!Strings.isNullOrEmpty(doc_type) && doc_type.equals("Loan_Doc")) {
						txnId_clnt = lead_id + "_" + new SimpleDateFormat("ddMMyyyyHHmmss").format(new Date()) + "_"
								+ "LD";

					}
					if (!Strings.isNullOrEmpty(doc_type) && doc_type.equals("Aadhaar_Doc")) {
						txnId_clnt = lead_id + "_" + new SimpleDateFormat("ddMMyyyyHHmmss").format(new Date()) + "_"
								+ "AD";
					}
					if (!Strings.isNullOrEmpty(doc_type) && doc_type.equals("enach")) {
						txnId_clnt = lead_id + "_" + new SimpleDateFormat("ddMMyyyyHHmmss").format(new Date()) + "_"
								+ "NM";
					}
					logger.info("txnId_clnt= " + txnId_clnt);
					try {

						String cacheRes = new CustomSessionServiceImpl().getDyncacheData(sessionKey, deviceId);
						org.json.simple.JSONObject cachejsonObject = CacheServices.parseString(cacheRes);

						if (CustomSessionServiceImpl.validateDetails("", cachejsonObject, "", lead_id, "")) {
							_isError = true;
							message = CustomSessionServiceImpl.message;
						}

						String ipaddress = serviceRequest.getCust_IP();
						String consentRes = new IVLCommonServiceImpl().callCustomerConsentService("esign", lead_id,
								ipaddress, "");
						consentResponse = gson.fromJson(consentRes, ConsentResponse.class);
						ipaddress = serviceRequest.getCust_IP() + "~" + deviceId;
						serviceRequest.setTxnId_clnt(txnId_clnt);
						serviceRequest.setCust_IP(ipaddress);
						serviceRequest.setAadhaar_number(aadhar_number);
						String consentText = readProperties.getPropertyValue("ESIGNCONSENTTEXT");
						logger.info("consentText::= " + consentText);
						serviceRequest.setConsent_text(consentText);
						isConsent = consentResponse.isSuccess();
						logger.info("consentResponse::= " + consentResponse);

					} catch (Exception e) {
						e.printStackTrace();
					}
					if (isConsent) {

						if (!_isError) {

							String payload = gson.toJson(serviceRequest);
							logger.info("payload= " + payload);
							final String serviceURL = localURL
									+ readProperties.getPropertyValue("ESIGNVARIFICATIONURL").trim();
							responseStr = CommonUtility.callRestClient(payload, serviceURL);
							response = CommonUtility.generateCommonResponse(key, responseStr, isEnc, aesEncDec,
									readProperties);
							long endTime = System.currentTimeMillis();
							logger.info("EndTime of eSignVarification Service [" + endTime + " ]");
							logger.info("TotalTime of eSignVarification Service [" + (endTime - startTime) + " ]");
						}
					} else {

						message = consentResponse.getError_message();
						response = CommonUtility.setCommonResponse(message);
					}
				} else {
					message = readProperties.getPropertyValue("COMMONINPUTERROR").trim();
					response = CommonUtility.setCommonResponse(message);
				}
			} else {
				message = readProperties.getPropertyValue("ESIGNDOWNTIMEMSG").trim();
				response = CommonUtility.setCommonResponse(response, aesEncDec, key, isEnc, message);
			}

		} catch (Exception ex) {

			ex.printStackTrace();
			response = CommonUtility.setCommonResponseForException(response, readProperties);
		}

		return response;
	}

	/**
	 * Function used to getEMandateDataStatus from ESB
	 * 
	 * @param GetEMandateDataRequest
	 * @return GetEMandateDataResponse
	 */

	public CommonResponse getEMandateDataStatus(CommonRequest request) throws CommonException {

		long startTime = System.currentTimeMillis();
		logger.info("StartTime of EMandateData Service [" + startTime + " ]");
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
				GetEMandateDataRequest serviceRequest = gson.fromJson(inputString, GetEMandateDataRequest.class);
				String lead_id = serviceRequest.getLead_id();
				logger.info("lead_id = " + lead_id);
				if (Strings.isNullOrEmpty(lead_id)) {
					_isError = true;
					message = readProperties.getPropertyValue("LEADIDERRORMESSAGE").trim();
				}
				// String cacheRes = new CustomSessionServiceImpl().getDyncacheData(sessionKey,
				// deviceId);
				// org.json.simple.JSONObject cachejsonObject =
				// CacheServices.parseString(cacheRes);

				/*
				 * if (CustomSessionServiceImpl.validateDetails("", cachejsonObject, "",
				 * lead_id, "")) { _isError = true; message = CustomSessionServiceImpl.message;
				 * }
				 */

				if (!_isError) {

					String payload = gson.toJson(serviceRequest);
					final String serviceURL = localURL+readProperties.getPropertyValue("GETEMANDATEDATAURL").trim();
					responseStr = CommonUtility.callRestClient(payload, serviceURL);
					response = CommonUtility.generateCommonResponse(key, responseStr, isEnc, aesEncDec, readProperties);
					long endTime = System.currentTimeMillis();
					logger.info("EndTime of EMandateData Service [" + endTime + " ]");
					logger.info("TotalTime of EMandateData Service [" + (endTime - startTime) + " ]");
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
	 * Function used to redirect HDFCNetbanking URL from ESB
	 * 
	 * @param HDFCUrlRedirectionRequest
	 * @return HDFCUrlRedirectionResponse
	 */

	public HDFCUrlRedirectionResponse getHDFCUrlRedirectStatus(CommonRequest request1)
			throws CommonException {

		long startTime = System.currentTimeMillis();
		logger.info("StartTime of HDFCUrlRedirection:" + startTime);
		AESEncDec aesEncDec = new AESEncDec();
		HDFCUrlRedirectionResponse response = new HDFCUrlRedirectionResponse();
		boolean _isError = false;
		String message = null, cardNumber = "", expMonth = "", expYear = "", cvvCode = "";
		String deviceId = "", sessionKey = "";

		try {
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
			HDFCUrlRedirectionRequest request = gson.fromJson(inputString, HDFCUrlRedirectionRequest.class);
			
			String merchantId = readProperties.getPropertyValue("MERCHANTIDKEY").trim();
			String transactionId = NetbankingPayload.generateRandom(13);
			String totalAmount = readProperties.getPropertyValue("BILLPAYAMTKEY").trim();
			String accountNo = request.getAccountNo();
			String consumerId = request.getConsumerId();
			logger.info("consumerId:::::" + consumerId);
			String consumerMobileNo = request.getConsumerMobileNo();
			String consumerEmailId = request.getConsumerEmailId();
			String debitStartDate = request.getDebitStartDate();
			String debitEndDate = request.getDebitEndDate();
			String maxAmount = request.getMaxAmount();
			String amountType = request.getAmountType();
			String frequency = request.getFrequency().trim().toUpperCase();
			frequency = readProperties.getPropertyValue(frequency);
			String salt = readProperties.getPropertyValue("SALTKEY").trim();

			if (Strings.isNullOrEmpty(consumerId)) {
				_isError = true;
				message = readProperties.getPropertyValue("CONSUMERIDERROR").trim();
			}
			if (!_isError) {

				String token = merchantId + "|" + transactionId + "|" + totalAmount + "|" + accountNo + "|" + consumerId
						+ "|" + consumerMobileNo + "|" + consumerEmailId + "|" + debitStartDate + "|" + debitEndDate
						+ "|" + maxAmount + "|" + amountType + "|" + frequency + "|" + cardNumber + "|" + expMonth + "|"
						+ expYear + "|" + cvvCode + "|" + salt;

				logger.info("Token without hashing:" + token);
				String hexToken = AES_Encrption.checkSum(token);
				logger.info("Token after hashing:" + hexToken);

				if (!Strings.isNullOrEmpty(hexToken)) {
					logger.info("hex Token:\t" + hexToken);
					String payload = gson.toJson(request);
					JSONObject jsonObject = JSONObject.parse(payload);
					logger.info("jsonObject:\t" + jsonObject.toString());
					response.setToken(hexToken);
					response.setTxnId(transactionId);
					response.setSuccess(true);
					response.setError_message(null);
				
				} else {

					response.setSuccess(false);
					response.setTxnId(null);
					response.setToken(null);
					response.setError_message(readProperties.getPropertyValue("DATAHEXERROR").trim());
				}
			} else {

				response.setToken(null);
				response.setTxnId(null);
				response.setError_message(message);
				response.setSuccess(false);
			}
			
			}
		} catch (Exception e) {
			e.printStackTrace();

		}

		return response;
	}

	/**
	 * Function used to validate hash value
	 * 
	 * @param HDFCRedirectResParamRequest
	 * @return HDFCRedirectResParamResponse
	 */

	public HDFCRedirectResParamResponse getHDFCUrlRedirectHashValidation(CommonRequest request1)
			throws CommonException {

		long startTime = System.currentTimeMillis();
		logger.info("StartTime of HDFCUrlRedirectResponse:" + startTime);
		AESEncDec aesEncDec = new AESEncDec();
		HDFCRedirectResParamResponse response = new HDFCRedirectResParamResponse();
		StringBuffer buffer = null;
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		boolean _isError = false;
		String message = null;
		String deviceId = "", sessionKey = "";
		try {
			deviceId = request1.getDevice_id();
			sessionKey = request1.getSession_key();
			String key = AESEncDec.generateRandomKey(deviceId, sessionKey, isEnc);
			logger.info("key= " + key);
			String inputString = aesEncDec.decrypt(key, request1.getInputString(), isEnc);
			logger.info("decrypted inputString= " + inputString);
			
			HDFCRedirectResParamRequest request = gson.fromJson(inputString, HDFCRedirectResParamRequest.class);
			

			String redirectRes = request.getRedirestResponseParam();
			logger.info("redirectResParam:" + redirectRes);

			String[] result = redirectRes.split("\\|");
			String[] reqParams = new String[result.length];

			for (int i = 0; i < result.length; i++) {
				reqParams[i] = result[i].trim();
				logger.info("split response:" + reqParams[i]);
			}

			String txn_status = reqParams[0].trim();
			String txn_msg = reqParams[1].trim();
			String txn_err_msg = reqParams[2].trim();
			String clnt_txn_ref = reqParams[3].trim();
			String tpsl_txn_id = reqParams[5].trim();
			String txn_amt = reqParams[6].trim();
			String tpsl_txn_time = reqParams[8].trim();
			String BankTransactionID = reqParams[12].trim();
			String mandate_reg_no = reqParams[13].trim();

			logger.info("split response:" + result);
			String hash = result[result.length - 1];
			logger.info("hash in response:" + hash);

			StringBuilder builder = new StringBuilder();

			for (int i = 0; i < result.length - 1; i++) {
				builder = builder.append(result[i] + "|");
			}

			String subString = builder.toString();
			logger.info("subString:" + subString);
			String newResponse = subString + readProperties.getPropertyValue("SALTKEY").trim();
			logger.info("newResponse:" + newResponse);
			String newHash = AES_Encrption.checkSum(newResponse);
			logger.info("newHash:" + newHash);

			if (Strings.isNullOrEmpty(redirectRes)) {

				_isError = true;
				message = readProperties.getPropertyValue("REDIRECTRESPONSEERROR").trim();
			}

			String cacheRes = new CustomSessionServiceImpl().getDyncacheData(sessionKey, deviceId);
			org.json.simple.JSONObject cachejsonObject = CacheServices.parseString(cacheRes);
			if (CustomSessionServiceImpl.validateDetails("", cachejsonObject, clnt_txn_ref, "", "")) {
				_isError = true;
				message = CustomSessionServiceImpl.message;
			}

			if (!_isError) {
				String payload = gson.toJson(request);
				JSONObject jsonObject = JSONObject.parse(payload);
				logger.info("Value of jsonObject Before Checking Null Or Empty: \t" + jsonObject.toString());

				if (!Strings.isNullOrEmpty(hash)) {
					logger.info("Value of hash Is Not NULL ");

					if (hash.equals(newHash)) {
						response.setTransaction_ref_no(clnt_txn_ref);
						response.setTransaction_id(tpsl_txn_id);
						response.setStatus(txn_status);
						response.setStatus_msg(txn_msg);
						response.setEmandate_date(
								new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime()));
						response.setEmandate_time(tpsl_txn_time);
						response.setPayment_gateway_ref_id(BankTransactionID);
						response.setMandate_status(txn_status);
						response.setPayment_amount(txn_amt);
						response.setMandate_registration_num(mandate_reg_no);
						response.setAdditional_1("");
						response.setAdditional_2("");
						response.setAdditional_3("");
						response.setAdditional_4("");
						response.setAdditional_5("");
						response.setAdditional_6("");
						response.setAdditional_7("");
						response.setAdditional_8("");
						response.setError_message(txn_err_msg);
						response.setSuccess(true);
					} else {
						logger.info("NewHash and OldHash are not equal");
					}

					buffer = new StringBuffer();
					buffer.append(response);
					logger.info("Value of appended buffer :  " + buffer);
					response = gson.fromJson(buffer.toString(), HDFCRedirectResParamResponse.class);
					logger.info("response :: " + response);

					long endTime = System.currentTimeMillis();
					logger.info("EndTime of HDFCHashValidation Service [" + endTime + " ]");
					logger.info("TotalTime of HDFCHashValidation Service [" + (endTime - startTime) + " ]");

				} else {

					response.setSuccess(false);
					response.setError_message(null);
				}
			} else {

				logger.info("Value of hash Is NULL ");
				response.setError_message(message);
				response.setSuccess(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	/**
	 * Function used to get ENachDetails from ESB
	 * 
	 * @param ENachDetailsRequest
	 * @return ENachDetailsResponse
	 */

	public CommonResponse getENachDetailsStatus(CommonRequest request) throws CommonException {

		long startTime = System.currentTimeMillis();
		logger.info("StartTime of ENachDetails Service [" + startTime + " ]");
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
				ENachDetailsRequest serviceRequest = gson.fromJson(inputString, ENachDetailsRequest.class);
				String leadId = serviceRequest.getLead_id();
				logger.info("leadId= " + leadId);

				String cacheRes = new CustomSessionServiceImpl().getDyncacheData(sessionKey, deviceId);
				org.json.simple.JSONObject cachejsonObject = CacheServices.parseString(cacheRes);
				if (CustomSessionServiceImpl.validateDetails("", cachejsonObject, "", leadId, "")) {
					_isError = true;
					message = CustomSessionServiceImpl.message;
				}

				if (!_isError) {

					String payload = gson.toJson(serviceRequest);
					final String serviceURL = localURL + readProperties.getPropertyValue("ENACHDETAILSURL").trim();
					responseStr = CommonUtility.callRestClient(payload, serviceURL);
					response = CommonUtility.generateCommonResponse(key, responseStr, isEnc, aesEncDec, readProperties);
					long endTime = System.currentTimeMillis();
					logger.info("EndTime of ENachDetails Service [" + endTime + " ]");
					logger.info("TotalTime of ENachDetails Service [" + (endTime - startTime) + " ]");
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
	 * Function used to get ENachValidationStatus from ESB
	 * 
	 * @param ENachValidationRequest
	 * @return ENachValidationResponse
	 */

	public CommonResponse getENachValidationStatus(CommonRequest request) throws CommonException {

		long startTime = System.currentTimeMillis();
		logger.info("StartTime of getENachValidation Service [" + startTime + " ]");
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
				ENachValidationRequest serviceRequest = gson.fromJson(inputString, ENachValidationRequest.class);
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
					final String serviceURL = localURL + readProperties.getPropertyValue("ENACHVALIDATIONURL").trim();
					responseStr = CommonUtility.callRestClient(payload, serviceURL);
					response = CommonUtility.generateCommonResponse(key, responseStr, isEnc, aesEncDec, readProperties);
					long endTime = System.currentTimeMillis();
					logger.info("EndTime of getENachValidation Service [" + endTime + " ]");
					logger.info("TotalTime of getENachValidation Service [" + (endTime - startTime) + " ]");
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
	 * Function used to get LoanSummary from ESB
	 * 
	 * @param LoanSummaryRequest
	 * @return LoanSummaryResponse
	 */

	public CommonResponse getLoanSummaryStatus(CommonRequest request) throws CommonException {

		long startTime = System.currentTimeMillis();
		logger.info("StartTime of LoanSummary Service [" + startTime + " ]");
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
				LoanSummaryRequest serviceRequest = gson.fromJson(inputString, LoanSummaryRequest.class);
				logger.info(
						"StartTime of LoanSummary :: " + startTime + " ::with crmId:: " + serviceRequest.getCrm_id());
				String crm_id = serviceRequest.getCrm_id();
				logger.info("crm_id = " + crm_id);

				if (Strings.isNullOrEmpty(crm_id)) {
					_isError = true;
					message = readProperties.getPropertyValue("CRMIDERROR").trim();
				}

				String cacheRes = new CustomSessionServiceImpl().getDyncacheData(sessionKey, deviceId);
				org.json.simple.JSONObject cachejsonObject = CacheServices.parseString(cacheRes);
				if (CustomSessionServiceImpl.validateDetails("", cachejsonObject, "", "", crm_id)) {
					_isError = true;
					message = CustomSessionServiceImpl.message;
				}

				if (!_isError) {
					String currentTime = new SimpleDateFormat("ddMMyyyy").format(new Date());
					String transaction_id = currentTime + NetbankingPayload.generateRandom(8);
					logger.info("transaction_id:" + transaction_id);
					serviceRequest.setTransaction_id(transaction_id);

					String payload = gson.toJson(serviceRequest);
					final String serviceURL = localURL + readProperties.getPropertyValue("LOANSUMMARYURL").trim();
					responseStr = CommonUtility.callRestClient(payload, serviceURL);
					responseStr = new CustomSessionServiceImpl().loanSummeryResManipulation(responseStr);
					response = CommonUtility.generateCommonResponse(key, responseStr, isEnc, aesEncDec, readProperties);
					long endTime = System.currentTimeMillis();
					logger.info("EndTime of LoanSummary Service [" + endTime + " ]");
					logger.info("TotalTime of LoanSummary Service [" + (endTime - startTime) + " ]");
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
	 * Function used to get LoanRepaymentDetails from ESB
	 * 
	 * @param LoanRepaymentDetailsRequest
	 * @return LoanRepaymentDetailsResponse
	 */

	public CommonResponse getLoanRepaymentDetails(CommonRequest request) throws CommonException {

		long startTime = System.currentTimeMillis();
		logger.info("StartTime of LoanRepayment Service [" + startTime + " ]");

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
				LoanRepaymentDetailsRequest serviceRequest = gson.fromJson(inputString,
						LoanRepaymentDetailsRequest.class);
				String cas_id = serviceRequest.getCas_id();
				logger.info("casId= " + cas_id);

				if (Strings.isNullOrEmpty(cas_id)) {
					_isError = true;
					message = readProperties.getPropertyValue("CASIDERROR").trim();
				}
				if (!_isError) {

					String currentTime = new SimpleDateFormat("ddMMyyyy").format(new Date());
					String transaction_id = currentTime + NetbankingPayload.generateRandom(8);
					logger.info("transaction_id:" + transaction_id);
					serviceRequest.setTransaction_id(transaction_id);
					String payload = gson.toJson(serviceRequest);
					final String serviceURL = localURL
							+ readProperties.getPropertyValue("LOANREPAYMENTDETAILSURL").trim();
					responseStr = CommonUtility.callRestClient(payload, serviceURL);
					responseStr = new CustomSessionServiceImpl().loanRepaymentResManipulation(responseStr);
					response = CommonUtility.generateCommonResponse(key, responseStr, isEnc, aesEncDec, readProperties);
					long endTime = System.currentTimeMillis();
					logger.info("EndTime of LoanRepayment Service [" + endTime + " ]");
					logger.info("TotalTime of LoanRepayment Service [" + (endTime - startTime) + " ]");
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
	 * Function used to get EMIPaymentDetails from ESB
	 * 
	 * @param EMIPaymentDetailsRequest
	 * @return EMIPaymentDetailsResponse
	 */

	public CommonResponse getEMIPaymentDetailsStatus(CommonRequest request) throws CommonException {

		long startTime = System.currentTimeMillis();
		logger.info("StartTime of EMIPaymentDetails Service [" + startTime + " ]");
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
				EMIPaymentDetailsRequest serviceRequest = gson.fromJson(inputString, EMIPaymentDetailsRequest.class);
				String cas_id = serviceRequest.getCas_id();
				logger.info("cas_id = " + cas_id);
				if (Strings.isNullOrEmpty(cas_id)) {
					_isError = true;
					message = readProperties.getPropertyValue("CASIDERROR").trim();
				}
				if (!_isError) {

					String currentTime = new SimpleDateFormat("ddMMyyyy").format(new Date());
					String transaction_id = currentTime + NetbankingPayload.generateRandom(8);
					logger.info("transaction_id:" + transaction_id);
					serviceRequest.setTransaction_id(transaction_id);

					String payload = gson.toJson(serviceRequest);
					final String serviceURL = localURL + readProperties.getPropertyValue("EMIPAYMENTDETAILSURL").trim();
					responseStr = CommonUtility.callRestClient(payload, serviceURL);
					response = CommonUtility.generateCommonResponse(key, responseStr, isEnc, aesEncDec, readProperties);
					long endTime = System.currentTimeMillis();
					logger.info("EndTime of EMIPaymentDetails Service [" + endTime + " ]");
					logger.info("TotalTime of EMIPaymentDetails Service [" + (endTime - startTime) + " ]");
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
	 * Function used to Validate PerfiosForENach from ESB
	 * 
	 * @param ValidatePerfiosForENachRequest
	 * @return ValidatePerfiosForENachResponse
	 */

	public CommonResponse getValidatePerfiosForENach(CommonRequest request) throws CommonException {

		long startTime = System.currentTimeMillis();
		logger.info("StartTime of ValidatePerfiosForENach Service [" + startTime + " ]");
		AESEncDec aesEncDec = new AESEncDec();
		CommonResponse response = new CommonResponse();
		String responseStr = null;
		boolean _isError = false;
		String message = null;
		String deviceId = "", sessionKey = "", txnId = "";
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
				ValidatePerfiosForENachRequest forENachRequest = gson.fromJson(inputString,
						ValidatePerfiosForENachRequest.class);
				String lead_id = forENachRequest.getLead_id();
				logger.info("leadId= " + lead_id);
				txnId = forENachRequest.getTxn_id();

				if (Strings.isNullOrEmpty(lead_id)) {
					_isError = true;
					message = readProperties.getPropertyValue("LEADIDERRORMESSAGE").trim();
				}
				String cacheRes = new CustomSessionServiceImpl().getDyncacheData(sessionKey, deviceId);
				org.json.simple.JSONObject cachejsonObject = CacheServices.parseString(cacheRes);

				if (CustomSessionServiceImpl.validateDetails("", cachejsonObject, txnId, lead_id, "")) {
					_isError = true;
					message = CustomSessionServiceImpl.message;
				}

				if (!_isError) {

					String payload = gson.toJson(forENachRequest);
					final String serviceURL = localURL + readProperties.getPropertyValue("PERFFORENACHURL").trim();
					responseStr = CommonUtility.callRestClient(payload, serviceURL);
					response = CommonUtility.generateCommonResponse(key, responseStr, isEnc, aesEncDec, readProperties);
					long endTime = System.currentTimeMillis();
					logger.info("EndTime of ValidatePerfiosForENach Service [" + endTime + " ]");
					logger.info("TotalTime of ValidatePerfiosForENach Service [" + (endTime - startTime) + " ]");
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
	 * Function used for bill payment-tech process integration
	 * 
	 * @param BillPaymentRequest
	 * @return BillPaymentResponse
	 */
	public BillPaymentResponse getBillPaymentStatus(BillPaymentRequest request) throws CommonException {

		long startTime = System.currentTimeMillis();
		logger.info("StartTime of BillPayment Service [" + startTime + " ]");

		BillPaymentResponse response = new BillPaymentResponse();
		boolean _isError = false;
		String message = "", cardNumber = "", expMonth = "", expYear = "", cvvCode = "";
		try {

			String merchantId = readProperties.getPropertyValue("BILLPAYMERCHANTIDKEY").trim();
			String salt = readProperties.getPropertyValue("BILLPAYSALTKEY").trim();
			String totalAmount = request.getTotalAmount().trim(); // readProperties.getPropertyValue("BILLPAYAMTKEY").trim();
			String transactionId = NetbankingPayload.generateRandom(12).trim();
			String accountNo = "";
			String consumerId = request.getConsumerId().trim();
			String consumerMobileNo = request.getConsumerMobileNo().trim();
			String consumerEmailId = request.getConsumerEmailId().trim();
			String debitStartDate = "";
			String debitEndDate = "";
			String maxAmount = "";
			String amountType = "";
			String frequency = "";

			if (Strings.isNullOrEmpty(merchantId)) {
				_isError = true;
				message = readProperties.getPropertyValue("CONSUMERIDERROR").trim();
			}
			if (!_isError) {

				String token = merchantId + "|" + transactionId + "|" + totalAmount + "|" + accountNo + "|" + consumerId
						+ "|" + consumerMobileNo + "|" + consumerEmailId + "|" + debitStartDate + "|" + debitEndDate
						+ "|" + maxAmount + "|" + amountType + "|" + frequency + "|" + cardNumber + "|" + expMonth + "|"
						+ expYear + "|" + cvvCode + "|" + salt;

				logger.info("BillPay Token before hashing:" + token);
				String hexToken = AES_Encrption.checkSum(token);
				logger.info("BillPay Token after hashing:" + hexToken);

				if (!Strings.isNullOrEmpty(hexToken)) {

					logger.info("BillPay hex Token:\t" + hexToken);
					/*
					 * Gson gson = new GsonBuilder().setPrettyPrinting().create(); String payload =
					 * gson.toJson(request); JSONObject jsonObject = JSONObject.parse(payload);
					 * logger.info("jsonObject:\t" + jsonObject.toString());
					 */

					response.setToken(hexToken);
					response.setTxnId(transactionId);
					response.setSuccess(true);
					response.setError_message(null);
					logger.info("response :: " + response.toString());
					long endTime = System.currentTimeMillis();
					logger.info("EndTime of BillPayment Service [" + endTime + " ]");
					logger.info("TotalTime of BillPayment Service [" + (endTime - startTime) + " ]");
				} else {
					response.setSuccess(false);
					response.setTxnId(null);
					response.setToken(null);
					response.setError_message(readProperties.getPropertyValue("DATAHEXERROR").trim());
				}
			} else {

				response.setToken(null);
				response.setTxnId(null);
				response.setError_message(message);
				response.setSuccess(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return response;
	}

	/**
	 * Function used to initiate disbursement process
	 * 
	 * @param InitiateDisbursementRequest
	 * @return InitiateDisbursementResponse
	 */

	public CommonResponse getInitiateDisbursementStatus(CommonRequest request) throws CommonException {

		long startTime = System.currentTimeMillis();
		logger.info("StartTime of InitiateDisbursement Service [" + startTime + " ]");
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
				InitiateDisbursementRequest initiateDisRequest = gson.fromJson(inputString,
						InitiateDisbursementRequest.class);
				String lead_id = initiateDisRequest.getLead_id();
				logger.info("leadId= " + lead_id);

				if (Strings.isNullOrEmpty(lead_id)) {
					_isError = true;
					message = readProperties.getPropertyValue("LEADIDERRORMESSAGE").trim();
				}
				// String cacheRes = new CustomSessionServiceImpl().getDyncacheData(sessionKey,
				// deviceId);
				// org.json.simple.JSONObject cachejsonObject =
				// CacheServices.parseString(cacheRes);

				/*
				 * if (CustomSessionServiceImpl.validateDetails("", cachejsonObject, "",
				 * lead_id, "")) { _isError = true; message = CustomSessionServiceImpl.message;
				 * }
				 */

				if (!_isError) {

					String payload = gson.toJson(initiateDisRequest);
					final String initialDisURL = localURL
							+ readProperties.getPropertyValue("INITIALDISBURSMENTURL").trim();
					responseStr = CommonUtility.callRestClient(payload, initialDisURL);
					response = CommonUtility.generateCommonResponse(key, responseStr, isEnc, aesEncDec, readProperties);
					long endTime = System.currentTimeMillis();
					logger.info("EndTime of InitiateDisbursement Service [" + endTime + " ]");
					logger.info("TotalTime of InitiateDisbursement Service [" + (endTime - startTime) + " ]");
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

	public CommonResponse getPANsubmitForDaasStatus(CommonRequest request) throws CommonException {

		long startTime = System.currentTimeMillis();
		logger.info("StartTime of PANsubmitForDaas Service [" + startTime + " ]");
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
				PANSubmissionForDASSRequest dassRequest = gson.fromJson(inputString, PANSubmissionForDASSRequest.class);
				String leadId = dassRequest.getLead_id();
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

					String payload = gson.toJson(dassRequest);
					final String panSubForDassURL = localURL
							+ readProperties.getPropertyValue("PANSUBMISSIONFORDASSURL").trim();
					responseStr = CommonUtility.callRestClient(payload, panSubForDassURL);
					response = CommonUtility.generateCommonResponse(key, responseStr, isEnc, aesEncDec, readProperties);
					long endTime = System.currentTimeMillis();
					logger.info("EndTime of PANsubmitForDaas Service [" + endTime + " ]");
					logger.info("TotalTime of PANsubmitForDaas Service [" + (endTime - startTime) + " ]");
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

	public CommonResponse getEMIPaymentUpdateStatus(CommonRequest request) throws CommonException {

		long startTime = System.currentTimeMillis();
		logger.info("StartTime of EMIPaymentUpdate Service:" + startTime);
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
				EMIPaymentUpdateRequest serviceRequest = gson.fromJson(inputString, EMIPaymentUpdateRequest.class);

				String leadId = serviceRequest.getTransaction_id();
				logger.info("leadId= " + leadId);

				if (!Strings.isNullOrEmpty(leadId)) {

					String currentTime = new SimpleDateFormat("ddMMyyyy").format(new Date());
					String transaction_id = currentTime + NetbankingPayload.generateRandom(8);
					logger.info("transaction_id:" + transaction_id);
					serviceRequest.setTransaction_id(transaction_id);
					String payload = gson.toJson(serviceRequest);
					final String panSubForDassURL = localURL
							+ readProperties.getPropertyValue("EMIPAYMENTUPDATEURL").trim();
					responseStr = CommonUtility.callRestClient(payload, panSubForDassURL);
					response = CommonUtility.generateCommonResponse(key, responseStr, isEnc, aesEncDec, readProperties);
					long endTime = System.currentTimeMillis();
					logger.info("EndTime of EMIPaymentUpdate Service: " + endTime + "\t"
							+ "  TotalTime of EMIPaymentUpdate Service: " + (endTime - startTime));
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
	 * Function used to validate hash value
	 * 
	 * @param HDFCRedirectResParamRequest
	 * @return HDFCRedirectResParamResponse
	 */

	public HDFCRedirectResParamResponse getHDFCBillPaymentHashValidation(HDFCRedirectResParamRequest request)
			throws CommonException {

		long startTime = System.currentTimeMillis();
		logger.info("StartTime of HDFCUrlRedirectResponse:: " + startTime);

		HDFCRedirectResParamResponse response = new HDFCRedirectResParamResponse();
		StringBuffer buffer = null;
		boolean _isError = false;
		String message = null;

		try {

			String redirectRes = request.getRedirestResponseParam();
			logger.info("redirectResParam:" + redirectRes);

			String[] result = redirectRes.split("\\|");
			String[] reqParams = new String[result.length];

			for (int i = 0; i < result.length; i++) {
				reqParams[i] = result[i].trim();
				logger.info("split response:" + reqParams[i]);
			}

			String txn_status = reqParams[0].trim();
			String txn_msg = reqParams[1].trim();
			String txn_err_msg = reqParams[2].trim();
			String clnt_txn_ref = reqParams[3].trim();
			String tpsl_txn_id = reqParams[5].trim();
			String txn_amt = reqParams[6].trim();
			String tpsl_txn_time = reqParams[8].trim();
			String BankTransactionID = reqParams[12].trim();
			String mandate_reg_no = reqParams[13].trim();

			logger.info("split response:" + result);
			String hash = result[result.length - 1];
			logger.info("hash in response:" + hash);

			StringBuilder builder = new StringBuilder();

			for (int i = 0; i < result.length - 1; i++) {
				builder = builder.append(result[i] + "|");
			}

			String subString = builder.toString();
			logger.info("subString:" + subString);
			String newResponse = subString + readProperties.getPropertyValue("BILLPAYSALTKEY").trim();
			logger.info("newResponse:" + newResponse);
			String newHash = AES_Encrption.checkSum(newResponse);
			logger.info("newHash:" + newHash);

			if (Strings.isNullOrEmpty(redirectRes)) {

				_isError = true;
				message = readProperties.getPropertyValue("REDIRECTRESPONSEERROR").trim();
			}
			if (!_isError) {

				Gson gson = new GsonBuilder().setPrettyPrinting().create();
				String payload = gson.toJson(request);
				JSONObject jsonObject = JSONObject.parse(payload);
				logger.info("Value of jsonObject Before Checking Null Or Empty: \t" + jsonObject.toString());

				if (!Strings.isNullOrEmpty(hash)) {
					logger.info("Value of hash Is Not NULL ");

					if (hash.equals(newHash)) {
						response.setTransaction_ref_no(clnt_txn_ref);
						response.setTransaction_id(tpsl_txn_id);
						response.setStatus(txn_status);
						response.setStatus_msg(txn_msg);
						response.setEmandate_date(
								new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime()));
						response.setEmandate_time(tpsl_txn_time);
						response.setPayment_gateway_ref_id(BankTransactionID);
						response.setMandate_status(txn_status);
						response.setPayment_amount(txn_amt);
						response.setMandate_registration_num(mandate_reg_no);
						response.setAdditional_1("");
						response.setAdditional_2("");
						response.setAdditional_3("");
						response.setAdditional_4("");
						response.setAdditional_5("");
						response.setAdditional_6("");
						response.setAdditional_7("");
						response.setAdditional_8("");
						response.setError_message(txn_err_msg);
						response.setSuccess(true);
					} else {
						logger.info("NewHash and OldHash are not equal");
					}

					buffer = new StringBuffer();
					buffer.append(response);

					logger.info("Value of appended buffer :  " + buffer);
					response = gson.fromJson(buffer.toString(), HDFCRedirectResParamResponse.class);
					logger.info("response :: " + response);

					long endTime = System.currentTimeMillis();
					logger.info("EndTime of HDFCUrlRedirectResponse:: " + endTime + "\t"
							+ "  TotalTime of HDFCUrlRedirectResponse:: " + (endTime - startTime));
				} else {

					response.setSuccess(false);
					response.setError_message(null);
				}
			} else {

				logger.info("Value of hash Is NULL ");
				response.setError_message(message);
				response.setSuccess(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return response;
	}

	public CommonResponse getBranchStatus(CommonRequest request) throws CommonException {

		long startTime = System.currentTimeMillis();
		logger.info("StartTime of getBranch Service:: " + startTime);
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
				BranchRequest branchRequest = gson.fromJson(inputString, BranchRequest.class);
				String leadId = branchRequest.getLead_id();
				logger.info("leadId= " + leadId);

				if (!Strings.isNullOrEmpty(leadId)) {

					String payload = gson.toJson(branchRequest);
					final String panSubForDassURL = localURL + readProperties.getPropertyValue("GETUPDATEURL").trim();
					responseStr = CommonUtility.callRestClient(payload, panSubForDassURL);
					response = CommonUtility.generateCommonResponse(key, responseStr, isEnc, aesEncDec, readProperties);
					long endTime = System.currentTimeMillis();
					logger.info("EndTime of getBranch Service:: " + endTime + "\t"
							+ "  TotalTime of getBranch Service:: " + (endTime - startTime));
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

	public AESKeyResponse servletd(AESKeyRequest request) throws CommonException {

		long startTime = System.currentTimeMillis();
		logger.info("StartTime of servletd Service [" + startTime + " ]");
		logger.info("servletd [" + request.toString() + " ]");
		AESKeyResponse response = new AESKeyResponse();
		double sessionTimeout = Double.valueOf(readProperties.getPropertyValue("SESSION_TIME_OUT"));
		response.setSessionTimeout(sessionTimeout);
		String sessionKey = "";
		String cacheUrl = "InitializeServlet?deviceid=";
		String deviceId;
		boolean _isError = false;
		String message = "";

		try {
			deviceId = request.getDevice_id();

			if (Strings.isNullOrEmpty(deviceId)) {
				_isError = true;
				message = readProperties.getPropertyValue("LEADIDERRORMESSAGE").trim();
				logger.info("error_message: " + message);
			}

			if (!Strings.isNullOrEmpty(isEnc)) {
				if (isEnc.equals("Y")) {
					response.setEnc(true);
					logger.info("is Enc equals Y" + response.isEnc());
				} else {
					response.setEnc(false);
					logger.info("is Enc not equals Y" + response.isEnc());
				}
			}
			if (!_isError) {

				cacheUrl = cacheUrl + deviceId;
				String cacheResponse = CacheServices.processCacheService(cacheUrl, "");
				logger.info("cacheResponse: " + cacheResponse);
				String enableocr=readProperties.getPropertyValue("ENABLEOCR").trim();
				Boolean enableocrrslt=false;
				if(enableocr.equalsIgnoreCase("true")){
					 enableocrrslt=true;
				}
				if (!Strings.isNullOrEmpty(cacheResponse)) {

					org.json.simple.JSONObject jsonObject = CacheServices.parseString(cacheResponse);
					if (null != jsonObject) {
						String encKey = jsonObject.get("enckey") == null ? "" : jsonObject.get("enckey").toString();
						sessionKey = jsonObject.get("sessionkey") == null ? ""
								: jsonObject.get("sessionkey").toString();
						response.setKey(encKey);
						response.setSessionKey(sessionKey);
						response.setEnable_ocr(enableocrrslt);
						logger.info("ServletD Response:" + response.toString());
					}
				}
				long endTime = System.currentTimeMillis();
				logger.info("EndTime of servletd Service [" + endTime + " ]");
				logger.info("TotalTime of servletd Service [" + (endTime - startTime) + " ]");
			}

		} catch (Exception ex) {
			ex.printStackTrace();

		}
		return response;
	}

	public CommonResponse getNewUserSignUPDetails(CommonRequest request) throws CommonException {

		long startTime = System.currentTimeMillis();
		logger.info("StartTime of NewUserSignUP Service [" + startTime + " ]");
		AESEncDec aesEncDec = new AESEncDec();
		CommonResponse response = new CommonResponse();
		SessionData data = new SessionData();
		String responseStr = null;
		boolean _isError = false;
		String message = null;
		String deviceId = "";
		String sessionKey = "";
		String crm_id = "", leadid = "", mpin = "";
		int count1 = 0, count2 = 0;
		try {

			deviceId = request.getDevice_id();
			sessionKey = request.getSession_key();
			boolean isValidRequest = CommonUtility.validateRequest(isEnc, request, aesEncDec);
			String key = AESEncDec.generateRandomKey(deviceId, sessionKey, isEnc);
			logger.info("key= " + key);
			String userSignupRequest = aesEncDec.decrypt(key, request.getInputString(), isEnc);
			logger.info("decrypted inputString= " + userSignupRequest);

			if (isDhaniWebUp.equalsIgnoreCase("Y")) {

				if (isValidRequest) {

					Gson gson = new GsonBuilder().setPrettyPrinting().create();
					NewUserSignUPRequest userSignUPRequest = gson.fromJson(userSignupRequest,
							NewUserSignUPRequest.class);
					logger.info("StartTime of NewUserSignUP::: " + startTime + " ::for mobile:: "
							+ userSignUPRequest.getMobile_number());
					String mobileNumber = userSignUPRequest.getMobile_number();
					logger.info("mobileNumber= " + mobileNumber);
					if (Strings.isNullOrEmpty(mobileNumber)) {
						_isError = true;
						message = readProperties.getPropertyValue("MOBILEERRORMESSAGE").trim();
					}

					String cacheRes = new CustomSessionServiceImpl().getDyncacheData(sessionKey, deviceId);
					org.json.simple.JSONObject cachejsonObject = CacheServices.parseString(cacheRes);

					String serviceCompletionTime = (String) cachejsonObject.get("serviceCompletionTime");
					String serviceCompletionTime2 = (String) cachejsonObject.get("documents");
					String mobileNo = (String) cachejsonObject.get("mobileno");
					String sessionCount1 = (String) cachejsonObject.get("crmid");
					String sessionCount2 = (String) cachejsonObject.get("checkSum");
					String flag1 = (String) cachejsonObject.get("flag1");
					String flag2 = (String) cachejsonObject.get("flag2");
					String mobileFirst = (String) cachejsonObject.get("mobileFirst");
					String mobileSecond = (String) cachejsonObject.get("mobileSecond");
					logger.info("_isError= " + _isError);
					
					if (!_isError) {

						String otp = OTPGeneration.generateOTP(mobileNumber);
						userSignUPRequest.setOTP(otp);
						String payload = gson.toJson(userSignUPRequest);
						final String userSignUpURL = localURL + readProperties.getPropertyValue("USERSIGNUPURL").trim();
						responseStr = CommonUtility.callRestClient(payload, userSignUpURL);
						logger.info("responseStr= " + responseStr);
						NewUserSignUPResponse response2 = gson.fromJson(responseStr, NewUserSignUPResponse.class);
						
						
						List_loans_detail[] list_loans_details = response2.getList_loans_detail();
						List<List_loans_detail> list = Arrays.asList(list_loans_details);
						
						
						leadid = list.get(0).getLead_id();
						logger.info("leadid::= " + leadid);
						crm_id = response2.getCrm_id();
						mpin = response2.getMpin();
						if (mpin != null && !mpin.equals("null") && !mpin.equals("")) {
							response2.setMpin("XXXX");
						}

						responseStr = gson.toJson(response2);
						response = CommonUtility.generateCommonResponse(key, responseStr, isEnc, aesEncDec,
								readProperties);

						if (mpin == null || mpin.equals("") || mpin.equals("null")) {

							if (serviceCompletionTime.equals("null") || serviceCompletionTime==null
									|| serviceCompletionTime.equals("")) {

								++count1;
								logger.info("serviceCompletionTime: " + serviceCompletionTime);
								serviceCompletionTime = "0";
								serviceCompletionTime2 = "0";
								sessionCount2 = "0";
								logger.info("serviceCompletionTime2: " + serviceCompletionTime2);
								logger.info("count1: " + count1);

								data.setMobilenumber(mobileNumber);
								data.setMobileFirst(mobileNumber);
								data.setFlag1("false");
								data.setDeviceid(deviceId);
								data.setLeadid(leadid);
								data.setEncKey(key);
								data.setServiceCompletionTime("" + System.currentTimeMillis());
								data.setDocuments(serviceCompletionTime2);
								data.setOtp(otp);
								data.setMpin(mpin);
								data.setCrmid(String.valueOf(count1));
								data.setCheckSum(sessionCount2);
								new CustomSessionServiceImpl().saveDyncacheData(sessionKey, deviceId, data);
							}

							if (!serviceCompletionTime.equals("0") && mobileNumber.equals(mobileNo)) {

								count1 = Integer.valueOf(sessionCount1);
								count2 = Integer.valueOf(sessionCount2);
								logger.info("serviceCompletionTime: " + serviceCompletionTime + " count1 " + count1
										+ " count2 " + count2);

								if (count1 >= 1 && flag1.equals("false") && mobileNumber.equals(mobileFirst)) {

									logger.info("mobileNumber same::::::::::::::::count1>=1: ");
									long timeDifference = CalculateTime.getTimeInSeconds(
											Long.parseLong(serviceCompletionTime), System.currentTimeMillis());
									long otpGenTime = Integer
											.valueOf(readProperties.getPropertyValue("OTPGENTIME").trim());
									logger.info("otpGenTime If: " + otpGenTime);
									++count1;
									if (count1 > 3) {
										_isError = true;
										message = readProperties.getPropertyValue("ACCOUNTLOCKEDERROR").trim();
									} else {
										message = readProperties.getPropertyValue("OTPGENERROR").trim();
									}
									if (timeDifference <= otpGenTime) {

										_isError = true;
										logger.info("timeDifference: " + timeDifference);
										logger.info("message: " + message);
									}

									logger.info("count1 : " + count1);

									data.setMobilenumber(mobileNumber);
									data.setDeviceid(deviceId);
									data.setMobileFirst(mobileFirst);
									data.setMobileSecond(mobileSecond);
									data.setFlag1("false");
									data.setFlag2("true");
									data.setLeadid(leadid);
									data.setEncKey(key);
									data.setServiceCompletionTime(serviceCompletionTime);
									data.setDocuments(serviceCompletionTime2);
									data.setOtp(otp);
									data.setMpin(mpin);
									data.setCrmid(String.valueOf(count1));
									data.setCheckSum(String.valueOf(count2));
									new CustomSessionServiceImpl().saveDyncacheData(sessionKey, deviceId, data);

								}

								if (count2 >= 1 && flag2.equals("true") && mobileNumber.equals(mobileSecond)) {

									logger.info("mobileNumber same::::::::::::::::count2>=1: ");
									long previousTime1 = Long.parseLong(serviceCompletionTime2);
									long timeDifference1 = CalculateTime.getTimeInSeconds(previousTime1,
											System.currentTimeMillis());

									long otpGenTime1 = Integer
											.valueOf(readProperties.getPropertyValue("OTPGENTIME").trim());
									++count2;
									if (count2 > 3) {
										_isError = true;
										message = readProperties.getPropertyValue("ACCOUNTLOCKEDERROR").trim();
									} else {
										message = readProperties.getPropertyValue("OTPGENERROR").trim();
									}
									logger.info("timeDifference..1: " + timeDifference1);
									if (timeDifference1 <= otpGenTime1) {
										_isError = true;
										logger.info("message: " + message);
									}

									logger.info("count2: " + count2);
									data.setMobilenumber(mobileNumber);
									data.setDeviceid(deviceId);
									data.setMobileFirst(mobileFirst);
									data.setMobileSecond(mobileSecond);
									data.setFlag2("true");
									data.setFlag1("false");
									data.setLeadid(leadid);
									data.setCrmid(crm_id);
									data.setEncKey(key);
									data.setDocuments(serviceCompletionTime2);
									data.setServiceCompletionTime(serviceCompletionTime);
									data.setOtp(otp);
									data.setMpin(mpin);
									data.setCrmid(String.valueOf(count1));
									data.setCheckSum(String.valueOf(count2));
									new CustomSessionServiceImpl().saveDyncacheData(sessionKey, deviceId, data);

								}
							} else if (!serviceCompletionTime.equals("0") && !mobileNumber.equals(mobileNo)) {

								count2 = Integer.valueOf(sessionCount2);
								count1 = Integer.valueOf(sessionCount1);
								if (count2 == 0) {
									// logger.info("mobileNumber different::::::::::::::::count2==0: ");
									++count2;
									logger.info("count2: " + count2);

									data.setMobilenumber(mobileNumber);
									data.setDeviceid(deviceId);
									data.setLeadid(leadid);
									data.setEncKey(key);
									data.setServiceCompletionTime(serviceCompletionTime);
									data.setDocuments("" + System.currentTimeMillis());
									data.setMobileSecond(mobileNumber);
									data.setMobileFirst(mobileFirst);
									data.setFlag2("true");
									data.setFlag1("false");
									data.setOtp(otp);
									data.setMpin(mpin);
									data.setCrmid(sessionCount1);
									data.setCheckSum(String.valueOf(count2));
									new CustomSessionServiceImpl().saveDyncacheData(sessionKey, deviceId, data);
								}

								if (count1 >= 1 && flag1.equals("false") && mobileNumber.equals(mobileFirst)) {

									logger.info("mobileNumber different::::::::::::::::count1>=1: ");

									long otpGenTime = Integer
											.valueOf(readProperties.getPropertyValue("OTPGENTIME").trim());
									long previousTime = Long.parseLong(serviceCompletionTime2);
									long timeDifference = CalculateTime.getTimeInSeconds(previousTime,
											System.currentTimeMillis());

									++count1;
									logger.info("count1: " + count1);

									if (count1 > 3) {
										_isError = true;
										message = readProperties.getPropertyValue("ACCOUNTLOCKEDERROR").trim();
									} else {
										message = readProperties.getPropertyValue("OTPGENERROR").trim();
									}
									logger.info("timeDifference: " + timeDifference);
									if (timeDifference <= otpGenTime) {
										_isError = true;
										logger.info("message: " + message);
									}
									data.setMobilenumber(mobileNumber);
									data.setDeviceid(deviceId);
									data.setMobileFirst(mobileFirst);
									data.setFlag1("false");
									data.setMobileSecond(mobileSecond);
									data.setFlag2("true");
									data.setLeadid(leadid);
									data.setEncKey(key);
									data.setServiceCompletionTime(serviceCompletionTime);
									data.setDocuments(serviceCompletionTime2);
									data.setIsAllowed("false");
									data.setOtp(otp);
									data.setMpin(mpin);
									data.setCrmid(String.valueOf(count1));
									data.setCheckSum(sessionCount2);
									new CustomSessionServiceImpl().saveDyncacheData(sessionKey, deviceId, data);
								}

								if (count2 >= 1 && flag2.equals("true") && mobileNumber.equals(mobileSecond)) {
									logger.info("mobileNumber different::::::::::::::::count2>=1: ");

									long otpGenTime = Integer
											.valueOf(readProperties.getPropertyValue("OTPGENTIME").trim());
									long previousTime = Long.parseLong(serviceCompletionTime2);
									long timeDifference = CalculateTime.getTimeInSeconds(previousTime,
											System.currentTimeMillis());

									++count2;
									logger.info("count2: " + count2);

									if (count2 > 3) {
										_isError = true;
										message = readProperties.getPropertyValue("ACCOUNTLOCKEDERROR").trim();
									} else {
										message = readProperties.getPropertyValue("OTPGENERROR").trim();
									}
									logger.info("timeDifference: " + timeDifference);
									if (timeDifference <= otpGenTime) {
										_isError = true;
										logger.info("message: " + message);
									}

									data.setMobilenumber(mobileNumber);
									data.setMobileSecond(mobileSecond);
									data.setMobileFirst(mobileFirst);
									data.setFlag2("true");
									data.setFlag1("false");
									data.setDeviceid(deviceId);
									data.setLeadid(leadid);
									data.setEncKey(key);
									data.setServiceCompletionTime(serviceCompletionTime);
									data.setDocuments(serviceCompletionTime2);
									data.setOtp(otp);
									data.setMpin(mpin);
									data.setCrmid(sessionCount1);
									data.setCheckSum(String.valueOf(count2));
									new CustomSessionServiceImpl().saveDyncacheData(sessionKey, deviceId, data);
								}
								if (!mobileNumber.equals(mobileSecond) && !mobileNumber.equals(mobileFirst)
										&& !mobileSecond.equals("null")) {

									logger.info("mobileNumber different::::::::::::::::");

									data.setMobilenumber(mobileNumber);
									data.setMobileSecond(mobileSecond);
									data.setMobileFirst(mobileFirst);
									data.setFlag2("true");
									data.setFlag1("false");
									data.setDeviceid(deviceId);
									data.setLeadid(leadid);
									data.setEncKey(key);
									data.setServiceCompletionTime(serviceCompletionTime);
									data.setDocuments(serviceCompletionTime2);
									data.setOtp(otp);
									data.setMpin(mpin);
									data.setCrmid(String.valueOf(count1));
									data.setCheckSum(String.valueOf(count2));
									new CustomSessionServiceImpl().saveDyncacheData(sessionKey, deviceId, data);
								}
							}
							if (_isError) {

								/*
								 * message = "{\"error_message\":\"" + message + "\",\"success\":false}";
								 * logger.info("response json::"+message); String encResponse =
								 * aesEncDec.encrypt(key, message, isEnc); String encCheckSum =
								 * AESEncDec.createChecksum(encResponse, isEnc);
								 * response.setOutputString(encResponse); response.setChkSum(encCheckSum);
								 * response.setSuccess(false);
								 */
								response = CommonUtility.setCommonResponse(response, aesEncDec, key, isEnc, message);
								// response = CommonUtility.setCommonResponse(message);;
							}
						} else {

							data.setMobilenumber(mobileNumber);
							data.setMobileSecond(mobileSecond);
							data.setMobileFirst(mobileFirst);
							data.setFlag2("true");
							data.setFlag1("false");
							data.setDeviceid(deviceId);
							data.setLeadid(leadid);
							data.setEncKey(key);
							data.setServiceCompletionTime(serviceCompletionTime);
							data.setDocuments(serviceCompletionTime2);
							data.setOtp(otp);
							data.setMpin(mpin);
							data.setCrmid(String.valueOf(count1));
							data.setCheckSum(String.valueOf(count2));
							new CustomSessionServiceImpl().saveDyncacheData(sessionKey, deviceId, data);
						}

					} else {
						/*
						 * message = "{\"error_message\":\"" + message + "\",\"success\":false}";
						 * logger.info("response json in else ::"+message); String encResponse =
						 * aesEncDec.encrypt(key, message, isEnc); String encCheckSum =
						 * AESEncDec.createChecksum(encResponse, isEnc);
						 * response.setOutputString(encResponse); response.setChkSum(encCheckSum);
						 * response.setSuccess(false);
						 */
						response = CommonUtility.setCommonResponse(response, aesEncDec, key, isEnc, message);
					}
					long endTime = System.currentTimeMillis();
					logger.info("EndTime of NewUserSignUP Service [" + endTime + " ]");
					logger.info("TotalTime of NewUserSignUP Service [" + (endTime - startTime) + " ]");
				}

				else {
					message = readProperties.getPropertyValue("COMMONINPUTERROR").trim();
					response = CommonUtility.setCommonResponse(message);
				}

			} else {
				message = readProperties.getPropertyValue("SERVICESDOWNERRORMSG").trim();
				response = CommonUtility.setCommonResponse(response, aesEncDec, key, isEnc, message);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		logger.info("CommonResponse:::::" + response);
		return response;
	}

	public static LoginAttemptsResponse getLoginAttemptsStatus(LoginAttemptsRequest request) throws CommonException {

		long startTime = System.currentTimeMillis();
		logger.info("StartTime of LoginAttempts Service [" + startTime + " ]");
		final String localURL = readProperties.getPropertyValue("UATENVURL") == null ? ""
				: readProperties.getPropertyValue("UATENVURL");
		LoginAttemptsResponse response = new LoginAttemptsResponse();
		String responseStr = null;
		boolean _isError = false;
		String message = null;

		try {

			if (!_isError) {

				Gson gson = new GsonBuilder().setPrettyPrinting().create();
				String payload = gson.toJson(request);
				logger.info("payload= " + payload);
				JSONObject jsonObject = JSONObject.parse(payload);
				logger.info("jsonObject= " + jsonObject.toString());
				logger.info("getLoginAttemptsStatus localURL = " + localURL);

				final String loginAttemptURL =  localURL+readProperties.getPropertyValue("LOGINATTEMPTSURL");
				responseStr = CommonUtility.callRestClient(payload, loginAttemptURL);
				if (!Strings.isNullOrEmpty(responseStr)) {

					logger.info("LoginAttempts ResponseStr =" + responseStr);
					response = gson.fromJson(responseStr, LoginAttemptsResponse.class);
					logger.info("LoginAttempts ResponseJson=" + response);
					long endTime = System.currentTimeMillis();
					logger.info("EndTime of LoginAttempts Service [" + endTime + " ]");
					logger.info("TotalTime of LoginAttempts Service [" + (endTime - startTime) + " ]");

				} else {
					logger.info("LoginAttempts ResponseStr is either null or empty");
					message = readProperties.getPropertyValue("REQUESTERROR").trim();
					response.setError_message(message);
					response.setSuccess(false);
					response.setRecord_status(null);
					response.setAdditional_fld1(null);
					response.setAdditional_fld2(null);
					response.setAdditional_fld3(null);
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return response;
	}

	public String callCustomerConsentService(String serviceType, String leadId, String cleintIp, String filler)
			throws Exception {

		long startTime = System.currentTimeMillis();
		logger.info("StartTime of CustomerConsent Service [" + startTime + " ]");
		JSONObject jsonObject = new JSONObject();
		String responseStr = "";
		try {

			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String timestamp = format.format(new Date());
			jsonObject.put("lead_id", leadId);
			jsonObject.put("consent_type", serviceType);
			jsonObject.put("consent_timestamp", timestamp);
			jsonObject.put("deviceIP", cleintIp);
			jsonObject.put("filler", filler);
			String userConsentURL = readProperties.getPropertyValue("USERCONSENTURL") == null
					? "/esb/ivl/0/process/ivlfin/a_customerconsent/v1"
					: readProperties.getPropertyValue("USERCONSENTURL");
			String consentURL = localURL+userConsentURL;
			responseStr = CommonUtility.callRestClient(jsonObject.toString(), consentURL);
			long endTime = System.currentTimeMillis();
			logger.info("EndTime of CustomerConsent Service [" + endTime + " ]");
			logger.info("TotalTime of CustomerConsent Service [" + (endTime - startTime) + " ]");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return responseStr;
	}

	public CommonResponse putAadharDocumentToIB(CommonRequest request) throws CommonException {

		long startTime = System.currentTimeMillis();
		logger.info("StartTime of putAadharDocumentToIB Service:: " + startTime);
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
				PutAadharDocToIBRequest putAadharDocToIBRequest = gson.fromJson(inputString,
						PutAadharDocToIBRequest.class);
				String leadId = putAadharDocToIBRequest.getLeadID();
				logger.info("leadId= " + leadId);

				if (!Strings.isNullOrEmpty(leadId)) {

					String payload = gson.toJson(putAadharDocToIBRequest);
					final String putAadharDocToIBURL = localURL
							+ readProperties.getPropertyValue("PUTAADHARDOCTOIBURL").trim();
					responseStr = CommonUtility.callRestClient(payload, putAadharDocToIBURL);
					response = CommonUtility.generateCommonResponse(key, responseStr, isEnc, aesEncDec, readProperties);
					long endTime = System.currentTimeMillis();
					logger.info("EndTime of putAadharDocumentToIB Service:: " + endTime + "\t"
							+ "  TotalTime of putAadharDocumentToIB Service:: " + (endTime - startTime));
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

	public CommonResponse putSignedDocument(CommonRequest request) throws CommonException {

		long startTime = System.currentTimeMillis();
		logger.info("StartTime of putSignedDocument Service [" + startTime + " ]");
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
				PushSignedDocRequest pushSignedDocRequest = gson.fromJson(inputString, PushSignedDocRequest.class);
				String leadId = pushSignedDocRequest.getLead_id();
				logger.info("leadId= " + leadId);

				if (!Strings.isNullOrEmpty(leadId)) {

					String payload = gson.toJson(pushSignedDocRequest);
					final String pushSignedDocURL = localURL
							+ readProperties.getPropertyValue("PUSHSIGNEDDOCURL").trim();
					responseStr = CommonUtility.callRestClient(payload, pushSignedDocURL);
					response = CommonUtility.generateCommonResponse(key, responseStr, isEnc, aesEncDec, readProperties);
					long endTime = System.currentTimeMillis();
					logger.info("EndTime of putSignedDocument Service [" + endTime + " ]");
					logger.info("TotalTime of putSignedDocument Service [" + (endTime - startTime) + " ]");
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

	public CommonResponse companyLookUp(CommonRequest request) throws CommonException {

		long startTime = System.currentTimeMillis();
		logger.info("StartTime of companyLookUp Service [" + startTime + " ]");
		AESEncDec aesEncDec = new AESEncDec();
		CommonResponse response = new CommonResponse();
		String responseStr = null;
		String message = null, deviceId = "", sessionKey = "";

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
				CompanyLookUpRequest companyLookUpRequest = gson.fromJson(inputString, CompanyLookUpRequest.class);
				String name = companyLookUpRequest.getName();
				logger.info("name= " + name);

				if (!Strings.isNullOrEmpty(name)) {

					String payload = gson.toJson(companyLookUpRequest);
					final String companyLookupURL = localURL
							+ readProperties.getPropertyValue("COMPANYLOOKUPURL").trim();
					responseStr = CommonUtility.callRestClient(payload, companyLookupURL);
					response = CommonUtility.generateCommonResponse(key, responseStr, isEnc, aesEncDec, readProperties);
					long endTime = System.currentTimeMillis();
					logger.info("EndTime of companyLookUp Service [" + endTime + " ]");
					logger.info("TotalTime of companyLookUp Service [" + (endTime - startTime) + " ]");
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

	public CommonResponse creditVidyaEmailFraud(CommonRequest request) throws CommonException {

		long startTime = System.currentTimeMillis();
		logger.info("StartTime of creditVidyaEmailFraud Service [" + startTime + " ]");
		AESEncDec aesEncDec = new AESEncDec();
		CommonResponse response = new CommonResponse();
		String responseStr = null, message = null, deviceId = "", sessionKey = "";

		try {
			deviceId = request.getDevice_id();
			sessionKey = request.getSession_key();
			boolean isValidRequest = CommonUtility.validateRequest(isEnc, request, aesEncDec);

			if (isValidRequest) {

				String key = AESEncDec.generateRandomKey(deviceId, sessionKey, isEnc);
				logger.info("key= " + key);
				String inputString = aesEncDec.decrypt(key, request.getInputString(), isEnc);
				logger.info("creditVidyaEmailFraud request= " + inputString);

				Gson gson = new GsonBuilder().setPrettyPrinting().create();
				CreditVidyaEmailFraudRequest creditVidyaEmailFraudReq = gson.fromJson(inputString,
						CreditVidyaEmailFraudRequest.class);
				String uniqueId = creditVidyaEmailFraudReq.getUniqueId();

				if (!Strings.isNullOrEmpty(uniqueId)) {

					String payload = gson.toJson(creditVidyaEmailFraudReq);
					logger.info("payload= " + payload);
					final String creditVidyaEmailFraudReqURL = localURL
							+ readProperties.getPropertyValue("CREDITVIDYAEMAILFRAUDURL").trim();
					responseStr = CommonUtility.callRestClient(payload, creditVidyaEmailFraudReqURL);
					response = CommonUtility.generateCommonResponse(key, responseStr, isEnc, aesEncDec, readProperties);
					long endTime = System.currentTimeMillis();
					logger.info("EndTime of creditVidyaEmailFraud Service [" + endTime + " ]");
					logger.info("TotalTime of creditVidyaEmailFraud Service [" + (endTime - startTime) + " ]");
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

	public CommonResponse getLoanInsuranceStatus(CommonRequest request) throws CommonException {

		long startTime = System.currentTimeMillis();
		logger.info("StartTime of LoanInsuranceStatus Service [" + startTime + " ]");
		AESEncDec aesEncDec = new AESEncDec();
		CommonResponse response = new CommonResponse();
		String responseStr = null, message = null, deviceId = "", sessionKey = "";

		try {

			deviceId = request.getDevice_id();
			sessionKey = request.getSession_key();

			boolean isValidRequest = CommonUtility.validateRequest(isEnc, request, aesEncDec);
			if (isValidRequest) {

				String key = AESEncDec.generateRandomKey(deviceId, sessionKey, isEnc);
				logger.info("key= " + key);
				String inputString = aesEncDec.decrypt(key, request.getInputString(), isEnc);
				logger.info("LoanInsurance Request:= " + inputString);

				Gson gson = new GsonBuilder().setPrettyPrinting().create();
				LoanInsuranceRequest serviceRequest = gson.fromJson(inputString, LoanInsuranceRequest.class);
				String corelationId = UUID.randomUUID().toString();
				serviceRequest.setCorrelation_id(corelationId);
				logger.info("LoanInsuranceRequest= " + serviceRequest.toString());

				if (!Strings.isNullOrEmpty(corelationId)) {

					String payload = gson.toJson(serviceRequest);
					final String loanInsuranceURL = localURL+readProperties.getPropertyValue("LOANINSURANCEURL").trim();
					responseStr = CommonUtility.callRestClient(payload, loanInsuranceURL);

					LoanInsuranceResponse insuranceResponse = gson.fromJson(responseStr, LoanInsuranceResponse.class);
					boolean status = insuranceResponse.isStatus();
					//	String total_premium = insuranceResponse.getTotal_premium();
					//insuranceResponse.setTotal_premium_80d(total_premium);

					if (status) {
						insuranceResponse.setSuccess(true);
						logger.info("success:" + insuranceResponse.isSuccess());

					} else {
						insuranceResponse.setSuccess(false);
						logger.info("success:" + insuranceResponse.isSuccess());
					}
					
					responseStr = gson.toJson(insuranceResponse);
					response = CommonUtility.generateCommonResponse(key, responseStr, isEnc, aesEncDec, readProperties);
					long endTime = System.currentTimeMillis();
					logger.info("EndTime of LoanInsuranceStatus Service [" + endTime + " ]");
					logger.info("TotalTime of LoanInsuranceStatus Service [" + (endTime - startTime) + " ]");
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

	public CommonResponse getEAgreementOTPAuthStatus(CommonRequest request) throws CommonException {

		long startTime = System.currentTimeMillis();
		logger.info("StartTime of EAgreementOTPAuth Service [" + startTime + " ]");
		AESEncDec aesEncDec = new AESEncDec();
		CommonResponse response = new CommonResponse();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String responseStr = null, message = null, deviceId = "", sessionKey = "", filler = "", ipaddress = "";
		boolean _isError = false;
		try {

			deviceId = request.getDevice_id();
			sessionKey = request.getSession_key();

			boolean isValidRequest = CommonUtility.validateRequest(isEnc, request, aesEncDec);
			if (isValidRequest) {

				String key = AESEncDec.generateRandomKey(deviceId, sessionKey, isEnc);
				logger.info("key= " + key);
				String eAgreementOTPAuthRequest = aesEncDec.decrypt(key, request.getInputString(), isEnc);
				logger.info("EAgreementOTPAuthentication Request= " + eAgreementOTPAuthRequest);

				EAgreementOTPAuthRequest agreementOTPAuthRequestBean = gson.fromJson(eAgreementOTPAuthRequest,
						EAgreementOTPAuthRequest.class);
				String leadId = agreementOTPAuthRequestBean.getLead_id();
				String mobileNumber = agreementOTPAuthRequestBean.getMobile_number();
				String otp = OTPGeneration.generateOTP(mobileNumber);
				logger.info("otp:::= " + otp);
				agreementOTPAuthRequestBean.setOtp(otp);
				filler = agreementOTPAuthRequestBean.getFiller();
				ipaddress = agreementOTPAuthRequestBean.getIpaddress();
				String consentRes = new IVLCommonServiceImpl().callCustomerConsentService("esign", leadId, ipaddress,
						filler);
				ConsentResponse consentResponse = gson.fromJson(consentRes, ConsentResponse.class);
				boolean isConsent = consentResponse.isSuccess();
				logger.info("isConsent:::= " + isConsent);

				if (agreementOTPAuthRequestBean.getFiller().equalsIgnoreCase("Declined")) {
					_isError = true;
					message = "Declined";
				}
				if (!_isError && !Strings.isNullOrEmpty(leadId)) {

					eAgreementOTPAuthRequest = gson.toJson(agreementOTPAuthRequestBean);
					final String eAgreementOTPAuthURL = localURL
							+ readProperties.getPropertyValue("EAGREEMENTOTPAUTHURL").trim();
					responseStr = CommonUtility.callRestClient(eAgreementOTPAuthRequest, eAgreementOTPAuthURL);
					response = CommonUtility.generateCommonResponse(key, responseStr, isEnc, aesEncDec, readProperties);
					SessionData data = new SessionData();
					data.setMobilenumber(mobileNumber);
					data.setDeviceid(deviceId);
					data.setLeadid(leadId);
					data.setEncKey(key);
					data.setOtp(otp);
					new CustomSessionServiceImpl().saveDyncacheData(sessionKey, deviceId, data);
					long endTime = System.currentTimeMillis();
					logger.info("EndTime of EAgreementOTPAuth Service [" + endTime + " ]");
					logger.info("TotalTime of EAgreementOTPAuth Service [" + (endTime - startTime) + " ]");
				} else {
					message = "{\"error_message\":\"" + message + "\",\"success\":true}";
					String encResponse = aesEncDec.encrypt(key, message, isEnc);
					String encCheckSum = AESEncDec.createChecksum(encResponse, isEnc);
					response.setOutputString(encResponse);
					response.setChkSum(encCheckSum);
					response.setSuccess(false);
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

	public CommonResponse getEAgreementSignStatus(CommonRequest request) throws CommonException {

		long startTime = System.currentTimeMillis();
		logger.info("StartTime of EAgreementSign Service [" + startTime + " ]");
		AESEncDec aesEncDec = new AESEncDec();
		CommonResponse response = new CommonResponse();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String responseStr = null, message = null, deviceId = "", sessionKey = "";
		boolean _isError = false;
		try {

			deviceId = request.getDevice_id();
			sessionKey = request.getSession_key();

			boolean isValidRequest = CommonUtility.validateRequest(isEnc, request, aesEncDec);
			if (isValidRequest) {

				String key = AESEncDec.generateRandomKey(deviceId, sessionKey, isEnc);
				logger.info("key= " + key);
				String eAgreementSignRequest = aesEncDec.decrypt(key, request.getInputString(), isEnc);
				logger.info("eAgreementSign Request= " + eAgreementSignRequest);

				EAgreementSignRequest agreementsignRequestBean = gson.fromJson(eAgreementSignRequest,
						EAgreementSignRequest.class);
				String otp = agreementsignRequestBean.getOtp();
				String cacheRes = new CustomSessionServiceImpl().getDyncacheData(sessionKey, deviceId);
				org.json.simple.JSONObject cachejsonObject = CacheServices.parseString(cacheRes);
				boolean isInCurrectOTP = CustomSessionServiceImpl.validateOtp(otp, cachejsonObject);
				if (isInCurrectOTP) {
					_isError = true;
					message = "Please enter valid OTP";
				}
				logger.info("_isError= " + _isError);
				if (!_isError) {

					final String eAgreementSignURL = localURL
							+ readProperties.getPropertyValue("EAGREEMENTSIGNURL").trim();
					responseStr = CommonUtility.callRestClient(eAgreementSignRequest, eAgreementSignURL);
					response = CommonUtility.generateCommonResponse(key, responseStr, isEnc, aesEncDec, readProperties);
					long endTime = System.currentTimeMillis();
					logger.info("EndTime of EAgreementSign Service [" + endTime + " ]");
					logger.info("TotalTime of EAgreementSign Service [" + (endTime - startTime) + " ]");
				} else {
					message = "{\"error_message\":\"" + message + "\",\"success\":false}";
					String encResponse = aesEncDec.encrypt(key, message, isEnc);
					String encCheckSum = AESEncDec.createChecksum(encResponse, isEnc);
					response.setOutputString(encResponse);
					response.setChkSum(encCheckSum);
					response.setSuccess(false);
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

	public CommonResponse createProductStatus(CommonRequest request) throws CommonException {

		long startTime = System.currentTimeMillis();
		logger.info("StartTime of createProduct Service [" + startTime + " ]");
		AESEncDec aesEncDec = new AESEncDec();
		CommonResponse response = new CommonResponse();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String responseStr = null, message = null, deviceId = "", sessionKey = "";

		try {

			deviceId = request.getDevice_id();
			sessionKey = request.getSession_key();

			boolean isValidRequest = CommonUtility.validateRequest(isEnc, request, aesEncDec);
			if (isValidRequest) {

				String key = AESEncDec.generateRandomKey(deviceId, sessionKey, isEnc);
				logger.info("key= " + key);
				String cretaeProductRequest = aesEncDec.decrypt(key, request.getInputString(), isEnc);
				logger.info("createProductRequest = " + cretaeProductRequest);

				CreateProductRequest createProductRequestBean = gson.fromJson(cretaeProductRequest,
						CreateProductRequest.class);
				logger.info("StartTime of createProductStatus:: " + startTime + " :: with mobile:: "
						+ createProductRequestBean.getMobile_number());
				String mobile_number = createProductRequestBean.getMobile_number();

				if (!Strings.isNullOrEmpty(mobile_number)) {

					final String createProductURL =localURL+ readProperties.getPropertyValue("CREATEPRODUCTURL").trim();

					responseStr = CommonUtility.callRestClient(cretaeProductRequest, createProductURL);
					CreateProductResponse createProductResponse = gson.fromJson(responseStr,
							CreateProductResponse.class);
					response = CommonUtility.generateCommonResponse(key, responseStr, isEnc, aesEncDec, readProperties);
					String leadId = "";
					String lextid = "";
					String conid = "";
					String oppid = "";

					if (createProductResponse.isSuccess()) {

						SessionData data = new SessionData();
						data.setDeviceid(deviceId);
						data.setLeadid(leadId);
						data.setEncKey(key);
						data.setSessionKey(sessionKey);

					new CustomSessionServiceImpl().saveDyncacheData(sessionKey, deviceId, data);
					long endTime = System.currentTimeMillis();
					logger.info("EndTime of createProduct Service [" + endTime + " ]");
					logger.info("TotalTime of createProduct Service [" + (endTime - startTime) + " ]");

				}
			}

			else {
				message = readProperties.getPropertyValue("COMMONINPUTERROR").trim();
				response = CommonUtility.setCommonResponse(message);
			}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			response = CommonUtility.setCommonResponseForException(response, readProperties);

		}
		return response;
	}

	public CommonResponse captureValidateData(CommonRequest request) throws CommonException {

		long startTime = System.currentTimeMillis();
		logger.info("StartTime of captureValidateData Service [" + startTime + " ]");
		AESEncDec aesEncDec = new AESEncDec();
		CommonResponse response = new CommonResponse();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String responseStr = null, message = null, deviceId = "", sessionKey = "";

		try {

			deviceId = request.getDevice_id();
			sessionKey = request.getSession_key();

			boolean isValidRequest = CommonUtility.validateRequest(isEnc, request, aesEncDec);
			if (isValidRequest) {

				String key = AESEncDec.generateRandomKey(deviceId, sessionKey, isEnc);
				logger.info("key= " + key);
				String captureValidateDataRequestJson = aesEncDec.decrypt(key, request.getInputString(), isEnc);
				logger.info("captureValidateDataRequest= " + captureValidateDataRequestJson);

				CaptureValidateDataRequest captureValidateDataRequest = gson.fromJson(captureValidateDataRequestJson,
						CaptureValidateDataRequest.class);
				String leadId = captureValidateDataRequest.getLead_id();

				if (!Strings.isNullOrEmpty(leadId)) {

					final String createProductURL = localURL
							+ readProperties.getPropertyValue("CAPTUREVALIDATEDATAURL").trim();
					responseStr = CommonUtility.callRestClient(captureValidateDataRequestJson, createProductURL);
					response = CommonUtility.generateCommonResponse(key, responseStr, isEnc, aesEncDec, readProperties);
					long endTime = System.currentTimeMillis();
					logger.info("EndTime of captureValidateData Service [" + endTime + " ]");
					logger.info("TotalTime of captureValidateData Service [" + (endTime - startTime) + " ]");
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

	public CommonResponse getTopUpDetails(CommonRequest request) throws CommonException {

		long startTime = System.currentTimeMillis();
		logger.info("StartTime of getTopUpDetails Service [" + startTime + " ]");
		AESEncDec aesEncDec = new AESEncDec();
		CommonResponse response = new CommonResponse();
		String responseStr = null, message = null, deviceId = "", sessionKey = "";

		try {

			deviceId = request.getDevice_id();
			sessionKey = request.getSession_key();
			boolean isValidRequest = CommonUtility.validateRequest(isEnc, request, aesEncDec);

			if (isValidRequest) {

				String key = AESEncDec.generateRandomKey(deviceId, sessionKey, isEnc);
				logger.info("key= " + key);
				String topUpRequest = aesEncDec.decrypt(key, request.getInputString(), isEnc);
				logger.info("TopUpDetails Request:= " + topUpRequest);

				Gson gson = new GsonBuilder().setPrettyPrinting().create();
				TopUpDetailsRequest serviceRequest = gson.fromJson(topUpRequest, TopUpDetailsRequest.class);
				String leadId = serviceRequest.getLead_id();

				if (!Strings.isNullOrEmpty(leadId)) {

					final String topUpURL = localURL + readProperties.getPropertyValue("TOPUPDEATAILURL").trim();
					responseStr = CommonUtility.callRestClient(topUpRequest, topUpURL);
					response = CommonUtility.generateCommonResponse(key, responseStr, isEnc, aesEncDec, readProperties);
					long endTime = System.currentTimeMillis();
					logger.info("EndTime of getTopUpDetails Service [" + endTime + " ]");
					logger.info("TotalTime of getTopUpDetails Service [" + (endTime - startTime) + " ]");
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

	public CommonResponse updateAddressStatus(CommonRequest request) throws CommonException {

		long startTime = System.currentTimeMillis();
		logger.info("StartTime of updateAddress Service:: " + startTime);
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
				logger.info("UpdateAddressRequest= " + inputString);

				Gson gson = new GsonBuilder().setPrettyPrinting().create();
				UpdateAddressRequest serviceRequest = gson.fromJson(inputString, UpdateAddressRequest.class);
				String leadId = serviceRequest.getLead_id();
				if (!Strings.isNullOrEmpty(leadId)) {

					String payload = gson.toJson(serviceRequest);
					final String UpdateAddressURL = localURL
							+ readProperties.getPropertyValue("UPDATEADDRESSURL").trim();
					responseStr = CommonUtility.callRestClient(payload, UpdateAddressURL);
					response = CommonUtility.generateCommonResponse(key, responseStr, isEnc, aesEncDec, readProperties);
					long endTime = System.currentTimeMillis();
					logger.info("EndTime of updateAddress Service [" + endTime + " ]");
					logger.info("TotalTime of updateAddress Service [" + (endTime - startTime) + " ]");
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

	public CommonResponse acceptTopUpStatus(CommonRequest request) throws CommonException {

		long startTime = System.currentTimeMillis();
		logger.info("StartTime of acceptTopUp Service [" + startTime + " ]");
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
				AcceptTopUpRequest serviceRequest = gson.fromJson(inputString, AcceptTopUpRequest.class);
				String LeadId = serviceRequest.getLead_id();
				logger.info("AcceptTopUpRequest= " + serviceRequest.toString());

				if (!Strings.isNullOrEmpty(LeadId)) {

					String payload = gson.toJson(serviceRequest);
					final String loaninSuranceURL = localURL + readProperties.getPropertyValue("ACCEPTTOPUPURL").trim();
					responseStr = CommonUtility.callRestClient(payload, loaninSuranceURL);
					response = CommonUtility.generateCommonResponse(key, responseStr, isEnc, aesEncDec, readProperties);
					long endTime = System.currentTimeMillis();
					logger.info("EndTime of acceptTopUp Service [" + endTime + " ]");
					logger.info("TotalTime of acceptTopUp Service [" + (endTime - startTime) + " ]");
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

	/*
	 * New method added for performance testing.
	 */
	// @Override
	public CommonResponse getNewUserSignupService(CommonRequest request) throws CommonException {

		long startTime = System.currentTimeMillis();
		logger.info("StartTime of getNewUserSignupService:" + startTime);
		AESEncDec aesEncDec = new AESEncDec();
		CommonResponse response = new CommonResponse();
		SessionData data = new SessionData();
		String responseStr = null;
		boolean _isError = false;
		String message = null;
		String deviceId = "";
		String sessionKey = "";
		String crm_id = "", leadid = "", mpin = "";
		int count1 = 0, count2 = 0;
		boolean isExist = false;
		try {

			deviceId = request.getDevice_id();
			sessionKey = request.getSession_key();
			
			logger.info("request.getDevice_id()= " + request.getDevice_id());
			logger.info("request.getSession_key()= " +request.getSession_key());
			boolean isValidRequest = CommonUtility.validateRequest(isEnc, request, aesEncDec);
			String key = AESEncDec.generateRandomKey(deviceId, sessionKey, isEnc);
			logger.info("key= " + key);
			String userSignupRequest = aesEncDec.decrypt(key, request.getInputString(), isEnc);
			logger.info("decrypted inputString= " + userSignupRequest);

			if (isDhaniWebUp.equalsIgnoreCase("Y")) {

				if (isValidRequest) {

					Gson gson = new GsonBuilder().setPrettyPrinting().create();
					NewUserSignUPRequest userSignUPRequest = gson.fromJson(userSignupRequest,
							NewUserSignUPRequest.class);
					String mobileNumber = userSignUPRequest.getMobile_number();

					logger.info("mobileNumber= " + mobileNumber);
					if (Strings.isNullOrEmpty(mobileNumber)) {
						_isError = true;
						message = readProperties.getPropertyValue("MOBILEERRORMESSAGE").trim();
					}

					String cacheRes = new CustomSessionServiceImpl().getDyncacheData(sessionKey, deviceId);
					org.json.simple.JSONObject cachejsonObject = CacheServices.parseString(cacheRes);

					String serviceCompletionTime = (String) cachejsonObject.get("serviceCompletionTime");
					String serviceCompletionTime2 = (String) cachejsonObject.get("documents");
					String mobileNo = (String) cachejsonObject.get("mobileno");
					String sessionCount1 = (String) cachejsonObject.get("crmid");
					String sessionCount2 = (String) cachejsonObject.get("checkSum");
					String flag1 = (String) cachejsonObject.get("flag1");
					String flag2 = (String) cachejsonObject.get("flag2");
					String mobileFirst = (String) cachejsonObject.get("mobileFirst");
					String mobileSecond = (String) cachejsonObject.get("mobileSecond");

					if (!_isError) {

						userSignUPRequest.setCon_id("");
						userSignUPRequest.setLead_id("");
						userSignUPRequest.setOpp_id("");
						userSignUPRequest.setLoanExt_id("");
						userSignUPRequest.setAcc_id("");
						userSignUPRequest.setLead_record_id("");
						userSignUPRequest.setFail_status("NFIC");

						String otp = OTPGeneration.generateOTP(mobileNumber);
						userSignUPRequest.setOTP(otp);
						String payload = gson.toJson(userSignUPRequest);
						final String userSignUpURL = localURL+readProperties.getPropertyValue("USERSIGNUPURL").trim();
						logger.info("new usersingup payload :" + payload);
						responseStr = CommonUtility.callRestClient(payload, userSignUpURL);
						logger.info("responseStr= " + responseStr);
						NewUserSignUPResponse response2 = gson.fromJson(responseStr, NewUserSignUPResponse.class);
						List_loans_detail[] list_loans_details = response2.getList_loans_detail();
						List<List_loans_detail> list = Arrays.asList(list_loans_details);
						leadid = list.get(0).getLead_id();
						logger.info("leadid::= " + leadid);
						crm_id = response2.getCrm_id();
						mpin = response2.getMpin();
						if (mpin != null && !mpin.equals("null") && !mpin.equals("")) {
							response2.setMpin("XXXX");
						}

						isExist = false;
						if (!isExist) {
							logger.info("line number 3544");
							String opp_id = response2.getOpp_id() == null ? "" : response2.getOpp_id();
							String lead_id = response2.getLead_id() == null ? "" : response2.getLead_id();
							String loanExt_id = response2.getLoanExt_id() == null ? "" : response2.getLoanExt_id();
							String con_id = response2.getCon_id() == null ? "" : response2.getCon_id();
							String acc_id = response2.getAcc_id() == null ? "" : response2.getAcc_id();
							
						} else {
							response2.setLead_id(userSignUPRequest.getLead_id());
							response2.setLoanExt_id(userSignUPRequest.getLoanExt_id());
							response2.setOpp_id(userSignUPRequest.getOpp_id());

						}
						responseStr = gson.toJson(response2);
						response = CommonUtility.generateCommonResponse(key, responseStr, isEnc, aesEncDec,
								readProperties);

						if (mpin == null || mpin.equals("") || mpin.equals("null")) {

							if (serviceCompletionTime.equals("null") || serviceCompletionTime.equals(null)
									|| serviceCompletionTime.equals("")) {

								++count1;
								logger.info("serviceCompletionTime: " + serviceCompletionTime);
								serviceCompletionTime = "0";
								serviceCompletionTime2 = "0";
								sessionCount2 = "0";
								logger.info("serviceCompletionTime2: " + serviceCompletionTime2);
								logger.info("count1: " + count1);

								data.setMobilenumber(mobileNumber);
								data.setMobileFirst(mobileNumber);
								data.setFlag1("false");
								data.setDeviceid(deviceId);
								data.setLeadid(leadid);
								data.setEncKey(key);
								data.setServiceCompletionTime("" + System.currentTimeMillis());
								data.setDocuments(serviceCompletionTime2);
								data.setOtp(otp);
								data.setMpin(mpin);
								data.setCrmid(String.valueOf(count1));
								data.setCheckSum(sessionCount2);
								new CustomSessionServiceImpl().saveDyncacheData(sessionKey, deviceId, data);
							}

							if (!serviceCompletionTime.equals("0") && mobileNumber.equals(mobileNo)) {

								count1 = Integer.valueOf(sessionCount1);
								count2 = Integer.valueOf(sessionCount2);
								logger.info("serviceCompletionTime: " + serviceCompletionTime + " count1 " + count1
										+ " count2 " + count2);

								if (count1 >= 1 && flag1.equals("false") && mobileNumber.equals(mobileFirst)) {

									logger.info("mobileNumber same::::::::::::::::count1>=1: ");
									long timeDifference = CalculateTime.getTimeInSeconds(
											Long.parseLong(serviceCompletionTime), System.currentTimeMillis());
									long otpGenTime = Integer
											.valueOf(readProperties.getPropertyValue("OTPGENTIME").trim());
									logger.info("otpGenTime If: " + otpGenTime);
									++count1;
									if (count1 > 3) {
										_isError = true;
										message = readProperties.getPropertyValue("ACCOUNTLOCKEDERROR").trim();
									} else {
										message = readProperties.getPropertyValue("OTPGENERROR").trim();
									}
									if (timeDifference <= otpGenTime) {

										_isError = true;
										logger.info("timeDifference: " + timeDifference);
										logger.info("message: " + message);
									}

									logger.info("count1 : " + count1);

									data.setMobilenumber(mobileNumber);
									data.setDeviceid(deviceId);
									data.setMobileFirst(mobileFirst);
									data.setMobileSecond(mobileSecond);
									data.setFlag1("false");
									data.setFlag2("true");
									data.setLeadid(leadid);
									data.setEncKey(key);
									data.setServiceCompletionTime(serviceCompletionTime);
									data.setDocuments(serviceCompletionTime2);
									data.setOtp(otp);
									data.setMpin(mpin);
									data.setCrmid(String.valueOf(count1));
									data.setCheckSum(String.valueOf(count2));
									new CustomSessionServiceImpl().saveDyncacheData(sessionKey, deviceId, data);

								}

								if (count2 >= 1 && flag2.equals("true") && mobileNumber.equals(mobileSecond)) {

									logger.info("mobileNumber same::::::::::::::::count2>=1: ");
									long previousTime1 = Long.parseLong(serviceCompletionTime2);
									long timeDifference1 = CalculateTime.getTimeInSeconds(previousTime1,
											System.currentTimeMillis());

									long otpGenTime1 = Integer
											.valueOf(readProperties.getPropertyValue("OTPGENTIME").trim());
									++count2;
									if (count2 > 3) {
										_isError = true;
										message = readProperties.getPropertyValue("ACCOUNTLOCKEDERROR").trim();
									} else {
										message = readProperties.getPropertyValue("OTPGENERROR").trim();
									}
									logger.info("timeDifference..1: " + timeDifference1);
									if (timeDifference1 <= otpGenTime1) {
										_isError = true;
										logger.info("message: " + message);
									}

									logger.info("count2: " + count2);
									data.setMobilenumber(mobileNumber);
									data.setDeviceid(deviceId);
									data.setMobileFirst(mobileFirst);
									data.setMobileSecond(mobileSecond);
									data.setFlag2("true");
									data.setFlag1("false");
									data.setLeadid(leadid);
									data.setCrmid(crm_id);
									data.setEncKey(key);
									data.setDocuments(serviceCompletionTime2);
									data.setServiceCompletionTime(serviceCompletionTime);
									data.setOtp(otp);
									data.setMpin(mpin);
									data.setCrmid(String.valueOf(count1));
									data.setCheckSum(String.valueOf(count2));
									new CustomSessionServiceImpl().saveDyncacheData(sessionKey, deviceId, data);

								}
							} else if (!serviceCompletionTime.equals("0") && !mobileNumber.equals(mobileNo)) {

								count2 = Integer.valueOf(sessionCount2);
								count1 = Integer.valueOf(sessionCount1);
								if (count2 == 0) {
									logger.info("mobileNumber different::::::::::::::::count2==0: ");
									++count2;
									logger.info("count2: " + count2);

									data.setMobilenumber(mobileNumber);
									data.setDeviceid(deviceId);
									data.setLeadid(leadid);
									data.setEncKey(key);
									data.setServiceCompletionTime(serviceCompletionTime);
									data.setDocuments("" + System.currentTimeMillis());
									data.setMobileSecond(mobileNumber);
									data.setMobileFirst(mobileFirst);
									data.setFlag2("true");
									data.setFlag1("false");
									data.setOtp(otp);
									data.setMpin(mpin);
									data.setCrmid(sessionCount1);
									data.setCheckSum(String.valueOf(count2));
									new CustomSessionServiceImpl().saveDyncacheData(sessionKey, deviceId, data);
								}

								if (count1 >= 1 && flag1.equals("false") && mobileNumber.equals(mobileFirst)) {

									logger.info("mobileNumber different::::::::::::::::count1>=1: ");

									long otpGenTime = Integer
											.valueOf(readProperties.getPropertyValue("OTPGENTIME").trim());
									long previousTime = Long.parseLong(serviceCompletionTime2);
									long timeDifference = CalculateTime.getTimeInSeconds(previousTime,
											System.currentTimeMillis());

									++count1;
									logger.info("count1: " + count1);

									if (count1 > 3) {
										_isError = true;
										message = readProperties.getPropertyValue("ACCOUNTLOCKEDERROR").trim();
									} else {
										message = readProperties.getPropertyValue("OTPGENERROR").trim();
									}
									logger.info("timeDifference: " + timeDifference);
									if (timeDifference <= otpGenTime) {
										_isError = true;
										logger.info("message: " + message);
									}
									data.setMobilenumber(mobileNumber);
									data.setDeviceid(deviceId);
									data.setMobileFirst(mobileFirst);
									data.setFlag1("false");
									data.setMobileSecond(mobileSecond);
									data.setFlag2("true");
									data.setLeadid(leadid);
									data.setEncKey(key);
									data.setServiceCompletionTime(serviceCompletionTime);
									data.setDocuments(serviceCompletionTime2);
									data.setIsAllowed("false");
									data.setOtp(otp);
									data.setMpin(mpin);
									data.setCrmid(String.valueOf(count1));
									data.setCheckSum(sessionCount2);
									new CustomSessionServiceImpl().saveDyncacheData(sessionKey, deviceId, data);
								}

								if (count2 >= 1 && flag2.equals("true") && mobileNumber.equals(mobileSecond)) {
									logger.info("mobileNumber different::::::::::::::::count2>=1: ");

									long otpGenTime = Integer
											.valueOf(readProperties.getPropertyValue("OTPGENTIME").trim());
									long previousTime = Long.parseLong(serviceCompletionTime2);
									long timeDifference = CalculateTime.getTimeInSeconds(previousTime,
											System.currentTimeMillis());

									++count2;
									logger.info("count2: " + count2);

									if (count2 > 3) {
										_isError = true;
										message = readProperties.getPropertyValue("ACCOUNTLOCKEDERROR").trim();
									} else {
										message = readProperties.getPropertyValue("OTPGENERROR").trim();
									}
									logger.info("timeDifference: " + timeDifference);
									if (timeDifference <= otpGenTime) {
										_isError = true;
										logger.info("message: " + message);
									}

									data.setMobilenumber(mobileNumber);
									data.setMobileSecond(mobileSecond);
									data.setMobileFirst(mobileFirst);
									data.setFlag2("true");
									data.setFlag1("false");
									data.setDeviceid(deviceId);
									data.setLeadid(leadid);
									data.setEncKey(key);
									data.setServiceCompletionTime(serviceCompletionTime);
									data.setDocuments(serviceCompletionTime2);
									data.setOtp(otp);
									data.setMpin(mpin);
									data.setCrmid(sessionCount1);
									data.setCheckSum(String.valueOf(count2));
									new CustomSessionServiceImpl().saveDyncacheData(sessionKey, deviceId, data);
								}
								if (!mobileNumber.equals(mobileSecond) && !mobileNumber.equals(mobileFirst)
										&& !mobileSecond.equals("null")) {

									logger.info("mobileNumber different::::::::::::::::");

									data.setMobilenumber(mobileNumber);
									data.setMobileSecond(mobileSecond);
									data.setMobileFirst(mobileFirst);
									data.setFlag2("true");
									data.setFlag1("false");
									data.setDeviceid(deviceId);
									data.setLeadid(leadid);
									data.setEncKey(key);
									data.setServiceCompletionTime(serviceCompletionTime);
									data.setDocuments(serviceCompletionTime2);
									data.setOtp(otp);
									data.setMpin(mpin);
									data.setCrmid(String.valueOf(count1));
									data.setCheckSum(String.valueOf(count2));
									new CustomSessionServiceImpl().saveDyncacheData(sessionKey, deviceId, data);
								}
							}
							if (_isError) {

								response = CommonUtility.setCommonResponse(response, aesEncDec, key, isEnc, message);

							}
						} else {

							data.setMobilenumber(mobileNumber);
							data.setMobileSecond(mobileSecond);
							data.setMobileFirst(mobileFirst);
							data.setFlag2("true");
							data.setFlag1("false");
							data.setDeviceid(deviceId);
							data.setLeadid(leadid);
							data.setEncKey(key);
							data.setServiceCompletionTime(serviceCompletionTime);
							data.setDocuments(serviceCompletionTime2);
							data.setOtp(otp);
							data.setMpin(mpin);
							data.setCrmid(String.valueOf(count1));
							data.setCheckSum(String.valueOf(count2));
							new CustomSessionServiceImpl().saveDyncacheData(sessionKey, deviceId, data);
						}

					} else {

						response = CommonUtility.setCommonResponse(response, aesEncDec, key, isEnc, message);
					}
				}

				else {
					message = readProperties.getPropertyValue("COMMONINPUTERROR").trim();
					response = CommonUtility.setCommonResponse(message);
				}

			} else {
				message = readProperties.getPropertyValue("SERVICESDOWNERRORMSG").trim();
				response = CommonUtility.setCommonResponse(response, aesEncDec, key, isEnc, message);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		logger.info("CommonResponse:::::" + response);
		return response;
	}

	// @Override
	public CommonResponse getMasterDataService(CommonRequest request) throws CommonException {

		long startTime = System.currentTimeMillis();
		logger.info("StartTime of getMasterDataService Service:" + startTime);
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
				//String isEnc="N";

				String key = AESEncDec.generateRandomKey(deviceId, sessionKey, isEnc);
				//String key = "";
				logger.info("key= " + key);
				logger.info(" decrypted inputString= " + request.getInputString());
				String inputString = aesEncDec.decrypt(key, request.getInputString(), isEnc);
				logger.info("decrypted inputString= " + inputString);

				Gson gson = new GsonBuilder().setPrettyPrinting().create();
				MasterDataRequest serviceRequest = gson.fromJson(inputString, MasterDataRequest.class);
				String type = serviceRequest.getType();
				logger.info("request type : " + type);

				if (!Strings.isNullOrEmpty(type)) {

					// String payload = gson.toJson(serviceRequest);
					// final String serviceURL = localURL +
					// readProperties.getPropertyValue("MASTERDATAURL").trim();
					// responseStr = CommonUtility.callRestClient(payload, serviceURL);
					// XtremeClient client = new XteremeClientUtilImpl();
					String xtremeKey = "";
					String input = serviceRequest.getInput();
					if (type.equals("state")) {
						xtremeKey = "STATE_LIST";
					} else if (type.equals("city")) {
						xtremeKey = "STATE_" + input;
					} else if (type.equals("pin")) {
						xtremeKey = "CITY_" + input;
					} else if (type.equals("pin_city")) {
						xtremeKey = "PIN_" + input;
					} else if (type.equals("allbank")) {
						xtremeKey = "BANKLIST";
					} else if (type.equals("bank")) {
						xtremeKey = "MANDATE_BANKLIST";
					}
					responseStr = readProperties.getStateCityKeyValue(xtremeKey);
					org.json.simple.JSONObject jsonObject = new org.json.simple.JSONObject();
					// CacheServices.parseString(responseStr);
					// responseStr = jsonObject.get(xtremeKey) ==
					// null?"":jsonObject.get(xtremeKey).toString();

					try {
						jsonObject = CacheServices.parseString(responseStr);
						if (null != jsonObject) {
							jsonObject.put("success", true);
						} else {
							jsonObject = new org.json.simple.JSONObject();
							jsonObject.put("success", false);
						}
						responseStr = jsonObject.toString();
					} catch (Exception e) {
						// TODO: handle exception
					}

					/*
					 * org.json.simple.JSONObject jsonObject =new org.json.simple.JSONObject();
					 * jsonObject.put("success", false);
					 * 
					 * responseStr = jsonObject.toString();
					 */
					

					response = CommonUtility.generateCommonResponse(key, responseStr, isEnc, aesEncDec, readProperties);
					long endTime = System.currentTimeMillis();
					logger.info("EndTime of getMasterDataService Service:" + endTime + "\t"
							+ "TotalTime of getMasterDataService Service:" + (endTime - startTime));
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

	public CommonResponse getInitiateCibil(CommonRequest request) throws CommonException {

		long startTime = System.currentTimeMillis();
		logger.info("StartTime of InitiateCibil Service:" + startTime);
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
				InitiateCibilRequest serviceRequest = gson.fromJson(inputString, InitiateCibilRequest.class);
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
					// IF current city pincode is there from request then
					// we have to check from cache based on pincode PIN_"pincode" u will get json
					// amd u have to parse and get active flag. then if its true u have to pass
					// lcation check as pass or elsefail, if u wont get data from cache check addar
					// pincode if u get data from cache then parse and check actvie falg.
					// if both are not present in cache then pass "NA"
					/*
					 * int x=10, y=10; if(x==`) {
					 * 
					 * }
					 */

					String currentPincode = serviceRequest.getCurrent_pincode();
					String aadharPincode = serviceRequest.getAadhaar_pincode();

					boolean islocCheck = false;
					// XteremeClientUtilImpl client = new XteremeClientUtilImpl();

					/*
					 * if(!currentPincode.equals("")) { responseStr =
					 * client.getValue("PIN_"+currentPincode, "g"); if(!responseStr.equals("")) {
					 * islocCheck = client.ispinCodeValid(responseStr, "PIN_"+currentPincode);
					 * if(islocCheck) { serviceRequest.setLocation_check("PASS");
					 * 
					 * }else{ serviceRequest.setLocation_check("FAIL"); }
					 * serviceRequest.setCurrent_pincode_id(client.getPinCodeid()); }else{
					 * serviceRequest.setLocation_check("NA");
					 * serviceRequest.setLocation_check("NA"); } }else if(!aadharPincode.equals(""))
					 * { responseStr = client.getValue("PIN_"+aadharPincode, "g");
					 * if(!responseStr.equals("")) { islocCheck = client.ispinCodeValid(responseStr,
					 * "PIN_"+aadharPincode); if(islocCheck) {
					 * serviceRequest.setLocation_check("PASS"); }else{
					 * serviceRequest.setLocation_check("FAIL"); }
					 * serviceRequest.setCurrent_pincode_id(client.getPinCodeid()); }else{
					 * serviceRequest.setLocation_check("NA");
					 * serviceRequest.setLocation_check("NA"); } }else {
					 * serviceRequest.setLocation_check("NA");
					 * serviceRequest.setLocation_check("NA"); }
					 */

					serviceRequest.setLocation_check("");
					serviceRequest.setCurrent_pincode_id("NA");
					String payload = gson.toJson(serviceRequest);
					final String initiateCibilURL = localURL
							+ readProperties.getPropertyValue("GETINITIATECIBILURL").trim();
					responseStr = CommonUtility.callRestClient(payload, initiateCibilURL);
					response = CommonUtility.generateCommonResponse(key, responseStr, isEnc, aesEncDec, readProperties);
					long endTime = System.currentTimeMillis();
					logger.info("EndTime of EmandateDropOut Service:" + endTime + "\t"
							+ "TotalTime of EmandateDropOut Service:" + (endTime - startTime));
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

	public CommonResponse getInitiateDaasCall1(CommonRequest request) throws CommonException {

		long startTime = System.currentTimeMillis();
		logger.info("StartTime of InitiateDaasCall1 Service:" + startTime);
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
				InitiateDaasCall1Request serviceRequest = gson.fromJson(inputString, InitiateDaasCall1Request.class);
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
					final String initiateDassCall1URL = localURL
							+ readProperties.getPropertyValue("GETINITIATEDAASCALL1").trim();
					responseStr = CommonUtility.callRestClient(payload, initiateDassCall1URL);

					response = CommonUtility.generateCommonResponse(key, responseStr, isEnc, aesEncDec, readProperties);
					long endTime = System.currentTimeMillis();
					logger.info("EndTime of InitiateDaasCall1 Service:" + endTime + "\t"
							+ "TotalTime of InitiateDaasCall1 Service:" + (endTime - startTime));
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

	public CommonResponse getHerokuAPI(CommonRequest request) throws CommonException {

		long startTime = System.currentTimeMillis();
		logger.info("StartTime of getHerokuAPI Service:" + startTime);
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

				if (!_isError) {

					Gson gson = new GsonBuilder().setPrettyPrinting().create();

					HerokuRequest herokuRequest = gson.fromJson(inputString, HerokuRequest.class);

					String mobile = herokuRequest.getMobileNumber();

					responseStr = "";
					// ConnectionUtility.processHerokuClient(mobile);
					response = CommonUtility.generateCommonResponse(key, responseStr, isEnc, aesEncDec, readProperties);
					long endTime = System.currentTimeMillis();
					logger.info("EndTime of getHerokuAPI Service:" + endTime + "\t"
							+ "TotalTime of getHerokuAPI Service:" + (endTime - startTime));
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

	public CommonResponse getPincodes(FAQRequest request) throws CommonException {

		long startTime = System.currentTimeMillis();
		logger.info("StartTime of getPincodes Service:" + startTime);
		AESEncDec aesEncDec = new AESEncDec();
		CommonResponse response = new CommonResponse();
		String responseStr = null;
		String message = null;
		String deviceId = "", sessionKey = "";

		try {
			deviceId = request.getDevice_id();
			sessionKey = request.getSession_key();
			//boolean isValidRequest = CommonUtility.validateRequest(isEnc, request, aesEncDec);

				
			StringBuffer stringBuffer=new StringBuffer();
				File file = new File(readProperties.getKeyValue("getPinCodePath").trim()); 
				  
				  BufferedReader br = new BufferedReader(new FileReader(file)); 
				  
				  String st; 
				  while ((st = br.readLine()) != null) {
				    stringBuffer.append(st);
				    
				  } 
				  response.setOutputString(stringBuffer.toString().replace(";", ""));
				  response.setSuccess(true);
				  response.setError_message("");
				  
				  logger.info(response.getOutputString());
			
		} catch (Exception ex) {
			response.setSuccess(false);
			ex.printStackTrace();
		}
		
		return response;

	}
/*public static void main(String[] args) {
	
	FAQRequest FAQRequest=new FAQRequest();
	IVLCommonServiceImpl iVLCommonServiceImpl=new IVLCommonServiceImpl();
	try {
		iVLCommonServiceImpl.getPincodes(FAQRequest);
	} catch (CommonException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}*/

	@Override
	public CommonResponse getActivatePrepaidCard(CommonRequest request) throws CommonException {

		long startTime = System.currentTimeMillis();
		logger.info("StartTime of getActivatePrepaidCard Service:" + startTime);
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
				ActivatePrepaidCardRequest serviceRequest = gson.fromJson(inputString, ActivatePrepaidCardRequest.class);
				String leadId = serviceRequest.getLead_id();
				logger.info("leadId= " + leadId);
				if (Strings.isNullOrEmpty(leadId)) {
					_isError = true;
					message = readProperties.getPropertyValue("LEADIDERRORMESSAGE").trim();
				}
				if (!_isError) {

					String payload = gson.toJson(serviceRequest);
					final String restURL = readProperties.getPropertyValue("ACTIVEPREPAIDCARD").trim();
					responseStr = CommonUtility.callRestClient(payload, restURL);

					response = CommonUtility.generateCommonResponse(key, responseStr, isEnc, aesEncDec, readProperties);
					long endTime = System.currentTimeMillis();
					logger.info("EndTime of getActivatePrepaidCard Service:" + endTime + "\t"
							+ "TotalTime of getActivatePrepaidCard Service:" + (endTime - startTime));
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
	@Override
	public CommonResponse getGenerateOTPforPINset(CommonRequest request) throws CommonException {

		long startTime = System.currentTimeMillis();
		logger.info("StartTime of getGenerateOTPforPINset Service:" + startTime);
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
				ActivatePrepaidCardRequest serviceRequest = gson.fromJson(inputString, ActivatePrepaidCardRequest.class);
				String leadId = serviceRequest.getLead_id();
				logger.info("leadId= " + leadId);
				if (Strings.isNullOrEmpty(leadId)) {
					_isError = true;
					message = readProperties.getPropertyValue("LEADIDERRORMESSAGE").trim();
				}
				if (!_isError) {

					String payload = gson.toJson(serviceRequest);
					final String restURL = readProperties.getPropertyValue("GENERATEOTPFORPINSET").trim();
					responseStr = CommonUtility.callRestClient(payload, restURL);

					response = CommonUtility.generateCommonResponse(key, responseStr, isEnc, aesEncDec, readProperties);
					long endTime = System.currentTimeMillis();
					logger.info("EndTime of getGenerateOTPforPINset Service:" + endTime + "\t"
							+ "TotalTime of getGenerateOTPforPINset Service:" + (endTime - startTime));
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
	@Override
	public CommonResponse getSetPIN(CommonRequest request) throws CommonException {

		long startTime = System.currentTimeMillis();
		logger.info("StartTime of getSetPIN Service:" + startTime);
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
				ActivatePrepaidCardRequest serviceRequest = gson.fromJson(inputString, ActivatePrepaidCardRequest.class);
				String leadId = serviceRequest.getLead_id();
				logger.info("leadId= " + leadId);
				if (Strings.isNullOrEmpty(leadId)) {
					_isError = true;
					message = readProperties.getPropertyValue("LEADIDERRORMESSAGE").trim();
				}
				if (!_isError) {

					String payload = gson.toJson(serviceRequest);
					final String restURL = readProperties.getPropertyValue("SETPIN").trim();
					responseStr = CommonUtility.callRestClient(payload, restURL);

					response = CommonUtility.generateCommonResponse(key, responseStr, isEnc, aesEncDec, readProperties);
					long endTime = System.currentTimeMillis();
					logger.info("EndTime of getSetPIN Service:" + endTime + "\t"
							+ "TotalTime of getSetPIN Service:" + (endTime - startTime));
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
	
	public CommonResponse loanOutstanding(CommonRequest request) throws CommonException {

		long startTime = System.currentTimeMillis();
		logger.info("StartTime of loanOutstanding Service:" + startTime);
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
				LoanOutstandingRequest serviceRequest = gson.fromJson(inputString, LoanOutstandingRequest.class);
				logger.info("serviceRequest>>>>" + serviceRequest);
				/*String leadId = serviceRequest.getLead_id();
				logger.info("leadId= " + leadId);
				if (Strings.isNullOrEmpty(leadId)) {
					_isError = true;
					message = readProperties.getPropertyValue("LEADIDERRORMESSAGE").trim();
				}*/
				if (!_isError) {
					String currentTime = new SimpleDateFormat("ddMMyyyy").format(new Date());
					String transaction_id = currentTime + NetbankingPayload.generateRandom(8);
					logger.info("transaction_id:" + transaction_id);
					serviceRequest.setTransaction_id(transaction_id);
					String payload = gson.toJson(serviceRequest);
					final String restURL = localURL + readProperties.getPropertyValue("LOANOUTSTANDINGURL").trim();
					responseStr = CommonUtility.callRestClient(payload, restURL);

					response = CommonUtility.generateCommonResponse(key, responseStr, isEnc, aesEncDec, readProperties);
					long endTime = System.currentTimeMillis();
					logger.info("EndTime of loanOutstanding Service:" + endTime + "\t"
							+ "TotalTime of loanOutstanding Service:" + (endTime - startTime));
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
	
	public static void main(String[] args) {
		
		GetApplicationInformationResponse getAppInfoResponse=new GetApplicationInformationResponse();
		System.out.println(getAppInfoResponse.getPrepaid_card_status());
		System.out.println("null".equalsIgnoreCase(getAppInfoResponse.getPrepaid_card_status()));
		
		System.out.println(Strings.isNullOrEmpty(getAppInfoResponse.getPrepaid_card_status()));
		if(Strings.isNullOrEmpty(getAppInfoResponse.getPrepaid_card_status()))
		{
			getAppInfoResponse.setPrepaid_card_status("XX");
		}
		
		System.out.println(getAppInfoResponse.getPrepaid_card_status());
	}

	@Override
	public CommonResponse getDocFromCRMStatus(CommonRequest request) throws CommonException {
		// TODO Auto-generated method stub
		return null;
	}

		
}
