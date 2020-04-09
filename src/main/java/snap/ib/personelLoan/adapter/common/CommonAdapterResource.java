/**
 * Copyright 2016 IBM Corp.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package snap.ib.personelLoan.adapter.common;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.ibm.mfp.adapter.api.OAuthSecurity;
import com.ibm.mfp.server.security.external.resource.AdapterSecurityContext;
import com.snap.ib.personelLoan.common.BaseResource;
import com.snap.ib.personelLoan.common.IOUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import snap.ib.personelLoan.adapter.common.exception.CommonException;
import snap.ib.personelLoan.adapter.common.request.AESKeyRequest;
import snap.ib.personelLoan.adapter.common.request.AddressProofRequest;
import snap.ib.personelLoan.adapter.common.request.AdhaarTnCRequest;
import snap.ib.personelLoan.adapter.common.request.BillPaymentRequest;
import snap.ib.personelLoan.adapter.common.request.CommonRequest;
import snap.ib.personelLoan.adapter.common.request.FAQRequest;
import snap.ib.personelLoan.adapter.common.request.HDFCRedirectResParamRequest;
import snap.ib.personelLoan.adapter.common.request.MandateDropOffRequest;
import snap.ib.personelLoan.adapter.common.request.ProductsListRequest;
import snap.ib.personelLoan.adapter.common.request.RblTokenRequest;
import snap.ib.personelLoan.adapter.common.request.ServeletEEncRequest;
import snap.ib.personelLoan.adapter.common.request.YesBankResponse;
import snap.ib.personelLoan.adapter.common.response.AESKeyResponse;
import snap.ib.personelLoan.adapter.common.response.AddressProofResponse;
import snap.ib.personelLoan.adapter.common.response.AdhaarTnCResponse;
import snap.ib.personelLoan.adapter.common.response.AndhraResponse;
import snap.ib.personelLoan.adapter.common.response.AndhraTokenResponse;
import snap.ib.personelLoan.adapter.common.response.AxisURLRedirectDecResponse;
import snap.ib.personelLoan.adapter.common.response.BillPaymentResponse;
import snap.ib.personelLoan.adapter.common.response.CommonResponse;
import snap.ib.personelLoan.adapter.common.response.FAQResponse;
import snap.ib.personelLoan.adapter.common.response.HDFCRedirectResParamResponse;
import snap.ib.personelLoan.adapter.common.response.HDFCUrlRedirectionResponse;
import snap.ib.personelLoan.adapter.common.response.ICICIUrlRedirectResResponse;
import snap.ib.personelLoan.adapter.common.response.ICICIUrlRedirectResponse;
import snap.ib.personelLoan.adapter.common.response.KotakResDataResponse;
import snap.ib.personelLoan.adapter.common.response.KotakUrlRedirectResponse;
import snap.ib.personelLoan.adapter.common.response.MandateDropOffResponse;
import snap.ib.personelLoan.adapter.common.response.ProductsListResponse;
import snap.ib.personelLoan.adapter.common.response.SBIResDataResponse;
import snap.ib.personelLoan.adapter.common.response.SBIUrlRedirectResponse;
import snap.ib.personelLoan.adapter.common.response.URLRedirectResponse;
import snap.ib.personelLoan.adapter.common.service.BankingIntegrationService;
import snap.ib.personelLoan.adapter.common.service.ICommonService;
import snap.ib.personelLoan.adapter.common.service.IVLCommonService;
import snap.ib.personelLoan.adapter.common.service.IVLCommonServiceStub;
import snap.ib.personelLoan.adapter.common.serviceImpl.BankingIntegrationServiceImpl;
import snap.ib.personelLoan.adapter.common.serviceImpl.CommonServiceImpl;
import snap.ib.personelLoan.adapter.common.serviceImpl.IVLCommonServiceImpl;
import snap.ib.personelLoan.adapter.common.serviceImpl.IVLCommonServiceStubImpl;
import snap.ib.personelLoan.adapter.common.util.CommonUtility;
import snap.ib.personelLoan.adapter.common.util.ReadProperty;
import snap.ib.personelLoan.adapter.common.util.ServletEEncConverterUtils;

@Path("/")
@Api("Common Adapter")
@OAuthSecurity(enabled = false, scope = "mPinAuthRequire")
public class CommonAdapterResource extends BaseResource {

	static Logger logger = LoggerFactory.getLogger(CommonAdapterResource.class.getName());
	private static final ReadProperty readProperties = ReadProperty.getInstance();
	private final String isEnc = readProperties.getPropertyValue("IS_ENC");
	@Context
	AdapterSecurityContext securityContext;
	ICommonService service = CommonServiceImpl.getInstance();
	IVLCommonService ivlService = IVLCommonServiceImpl.getInstance();
	IVLCommonServiceStub ivlStubService = IVLCommonServiceStubImpl.getInstance();
	BankingIntegrationService integrationService = BankingIntegrationServiceImpl.getInstance();
	
	@ApiOperation(value = "Registration", notes = "Registration", response = CommonResponse.class)
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON,MediaType.APPLICATION_FORM_URLENCODED })
	@Path("/getMobileRegistrationStatus")
	public CommonResponse getMobileRegistrationStatus(
			CommonRequest request) {
		CommonResponse response = new CommonResponse();
		try {
			logger.info("Logging info message...inside common -> getMobileRegistrationStatus");
			response = service.getMobileRegistrationStatus(request);
		} catch (CommonException e) {
			logger.error(IOUtils.getPrintStackTrace(e));
		}
		return response;
	}
	
	@ApiOperation(value = "LoanAppStatus", notes = "LoanApplication", response = CommonResponse.class)
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON,
			MediaType.APPLICATION_FORM_URLENCODED })
	@Path("/getUserLoanDetails")
	public CommonResponse getUserLoanDetails(
			CommonRequest request) {
		CommonResponse response = new CommonResponse();
		try {
			logger.info("Logging info message...inside common -> getUserLoanDetails");
			response = service.getUserLoanDetails(request);
		} catch (CommonException e) {
			logger.error(IOUtils.getPrintStackTrace(e));
		}
		return response;
	}
	
	
	@ApiOperation(value = "loanInsurance", notes = "loanInsurance", response = CommonResponse.class)
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON,
			MediaType.APPLICATION_FORM_URLENCODED })
	@Path("/loanInsurance")
	public CommonResponse loanInsurance(CommonRequest request) {
		CommonResponse response = new CommonResponse();
		try {
			logger.info("Logging info message...inside common -> loanInsurance");
			response = ivlService.getLoanInsuranceStatus(request);
		} catch (CommonException e) {
			logger.error(IOUtils.getPrintStackTrace(e));
		}
		return response;
	}
	
	
	@ApiOperation(value = "ForgotMpin", notes = "ForgotMpin", response = CommonResponse.class)
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON,
			MediaType.APPLICATION_FORM_URLENCODED })
	@Path("/getForgotMpinStatus")
	public CommonResponse getForgotMpinStatus(CommonRequest request) {
		CommonResponse response = new CommonResponse();
		try {
			logger.info("Logging info message...inside common -> getForgotMpinStatus");
			response = service.getForgotMpinStatus(request);
		} catch (CommonException e) {
			logger.error(IOUtils.getPrintStackTrace(e));
		}
		return response;
	}

	@ApiOperation(value = "MpinValidation", notes = "MpinValidation", response = CommonResponse.class)
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON,
			MediaType.APPLICATION_FORM_URLENCODED })
	@Path("/validateMpin")
	public CommonResponse validateMpin(CommonRequest request) {
		CommonResponse response = new CommonResponse();
		try {
			logger.info("Logging info message...inside common -> validateMpin");
			response = service.validateMpin(request);
		} catch (CommonException e) {
			logger.error(IOUtils.getPrintStackTrace(e));
		}
		return response;
	}
	
	@ApiOperation(value = "UpdateMpin", notes = "UpdateMpin", response = CommonResponse.class)
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON,
			MediaType.APPLICATION_FORM_URLENCODED })
	@Path("/getUpdateMpinStatus")
	public CommonResponse getUpdateMpinStatus(CommonRequest request) {
		CommonResponse response = new CommonResponse();
		try {
			logger.info("Logging info message...inside common -> getUpdateMpinStatus");
			response = service.getUpdateMpinStatus(request);
		} catch (CommonException e) {
			logger.error(IOUtils.getPrintStackTrace(e));
		}
		return response;
	}

	
	@ApiOperation(value = "ChangeMpin", notes = "ChangeMpin", response = CommonResponse.class)
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON,
			MediaType.APPLICATION_FORM_URLENCODED })
	@Path("/getChangeMpin")
	public CommonResponse getChangeMpin(CommonRequest request) {
		CommonResponse response = new CommonResponse();
		try {
			logger.info("Logging info message...inside common -> getChangeMpin");
			response = service.getChangeMpin(request);
		} catch (CommonException e) {
			logger.error(IOUtils.getPrintStackTrace(e));
		}
		return response;
	}

	
	@ApiOperation(value = "AadharDropOff", notes = "AadharDropOff", response = CommonResponse.class)
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON,
			MediaType.APPLICATION_FORM_URLENCODED })
	@Path("/getAadharDropOffStatus")
	public CommonResponse getAadharDropOffStatus(
			CommonRequest request) {
		CommonResponse response = new CommonResponse();
		try {
			logger.info("Logging info message...inside common -> getAadharDropOffStatus");
			response = service.getAadharDropOffStatus(request);
		} catch (CommonException e) {
			logger.error(IOUtils.getPrintStackTrace(e));
		}

		return response;
	}

	
	@ApiOperation(value = "OTPAuthentication", notes = "OTPAuthentication", response = CommonResponse.class)
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON,
			MediaType.APPLICATION_FORM_URLENCODED })
	@Path("/getOTPAuthenticationStatus")
	public CommonResponse getOTPAuthenticationStatus(
			CommonRequest request) {
		CommonResponse response = new CommonResponse();
		try {
			logger.info("Logging info message...inside common -> aadharOTPAuthentication");
			response = service.aadharOTPAuthentication(request);
		} catch (CommonException e) {
			logger.error(IOUtils.getPrintStackTrace(e));
		}
		return response;
	}

	
	@ApiOperation(value = "OTPGeneration", notes = "OTPGeneration", response = CommonResponse.class)
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON,
			MediaType.APPLICATION_FORM_URLENCODED })
	@Path("/generateOTP")
	public CommonResponse generateOTP(CommonRequest request ,@Context HttpHeaders headers)
			throws Exception {
		CommonResponse response = new CommonResponse();
		try {
			logger.info("Logging info message...inside common -> generateAahdarOTP");
			response = service.aadharOTPGeneration(request,headers);
		} catch (CommonException e) {
			logger.error(IOUtils.getPrintStackTrace(e));
		}
		return response;
	}
	
	@ApiOperation(value = "resendOTP", notes = "resendOTP", response = CommonResponse.class)
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON,
			MediaType.APPLICATION_FORM_URLENCODED })
	@Path("/getResendOTPStatus")
	public CommonResponse getResendOTPStatus(CommonRequest request) {
		CommonResponse response = new CommonResponse();
		try {
			logger.info("Logging info message...inside common -> getResendOTPStatus");
			response = service.getResendOTPStatus(request);
		} catch (CommonException e) {
			logger.error(IOUtils.getPrintStackTrace(e));
		}
		return response;
	}

	
	@ApiOperation(value = "DemographicInformation", notes = "DemographicInformation", response = CommonResponse.class)
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON,
			MediaType.APPLICATION_FORM_URLENCODED })
	@Path("/submitDemographicInfo")
	public CommonResponse submitDemographicInfo(
			CommonRequest request) {
		CommonResponse response = new CommonResponse();
		try {
			logger.info("Logging info message...inside common -> submitDemographicInfo");
			response = service.submitDemographicInfo(request);
		} catch (CommonException e) {
			logger.error(IOUtils.getPrintStackTrace(e));
		}
		return response;
	}

	
	@ApiOperation(value = "MasterData", notes = "MasterData", response = CommonResponse.class)
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON,
			MediaType.APPLICATION_FORM_URLENCODED })
	@Path("/getMasterData")
	public CommonResponse getMasterData(CommonRequest request) {
		CommonResponse response = new CommonResponse();
		try {
			logger.info("Logging info message...inside common -> getMasterData");
			response = service.getMasterData(request);
		} catch (CommonException e) {
			logger.error(IOUtils.getPrintStackTrace(e));
		}
		return response;
	}

	
	@ApiOperation(value = "PANVarification", notes = "PANVarification", response = CommonResponse.class)
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON,
			MediaType.APPLICATION_FORM_URLENCODED })
	@Path("/getPANVarificationStatus")
	public CommonResponse getPANVarificationStatus(
			CommonRequest request) {
		CommonResponse response = new CommonResponse();
		try {
			logger.info("Logging info message...inside common -> getPANVarificationStatus");
			response = service.getPANVarificationStatus(request);
		} catch (CommonException e) {
			logger.error(IOUtils.getPrintStackTrace(e));
		}
		return response;
	}

	
	@ApiOperation(value = "PayloadTransaction", notes = "PayloadTransaction", response = CommonResponse.class)
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON,
			MediaType.APPLICATION_FORM_URLENCODED })
	@Path("/getPayloadTransStatus")
	public CommonResponse getPayloadTransStatus(
			CommonRequest request) {
		CommonResponse response = new CommonResponse();
		try {
			logger.info("Logging info message...inside common -> getPayloadTransStatus");
			response = service.getPayloadTransactionStatus(request);
		} catch (CommonException e) {
			logger.error(IOUtils.getPrintStackTrace(e));
		}
		return response;
	}

	
	@ApiOperation(value = "PerfiosURLRedirection", notes = "PerfiosURLRedirection", response = CommonResponse.class)
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON,
			MediaType.APPLICATION_FORM_URLENCODED })
	@Path("/perfiosUrlRedirection")
	public CommonResponse perfiosUrlRedirection(
			CommonRequest request) {
		CommonResponse response = new CommonResponse();
		
		try {
			logger.info("Logging info message...inside common -> perfiosUrlRedirection");
			response = service.getPerfiosUrlRedirectStatus(request);
		} catch (CommonException e) {
			logger.error(IOUtils.getPrintStackTrace(e));
		}
		return response;
	}

	
	@ApiOperation(value = "UpdateRreviseLoanDetails", notes = "UpdateRreviseLoanDetails", response = CommonResponse.class)
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON,
			MediaType.APPLICATION_FORM_URLENCODED })
	@Path("/getUpdateReviseLoanDetails")
	public CommonResponse getUpdateReviseLoanDetails(
			CommonRequest request) {
		CommonResponse response = new CommonResponse();
		try {
			logger.info("Logging info message...inside common -> UpdateRreviseLoanDetails");
			response = service.getUpdateReviseLoanStatus(request);
		} catch (CommonException e) {
			logger.error(IOUtils.getPrintStackTrace(e));
		}
		return response;
	}

	
	@ApiOperation(value = "getCaptureSelfiStatus", notes = "getCaptureSelfiStatus", response = CommonResponse.class)
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON,
			MediaType.APPLICATION_FORM_URLENCODED })
	@Path("/getCaptureSelfiStatus")
	public CommonResponse getCaptureSelfiStatus(
			CommonRequest request) {
		CommonResponse response = new CommonResponse();
		try {
			logger.info("Logging info message...inside common -> getCaptureSelfiStatus");
			response = service.getCaptureSelfiStatus(request);
		} catch (CommonException e) {
			logger.error(IOUtils.getPrintStackTrace(e));
		}
		return response;
	}
	
	
	@ApiOperation(value = "getDocumentFromCRM", notes = "getDocumentFromCRM", response = CommonResponse.class)
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON,MediaType.APPLICATION_FORM_URLENCODED })
	@Path("/getDocumentFromCRM")
	public CommonResponse getDocumentFromCRM(CommonRequest request) {
		CommonResponse response = new CommonResponse();
		try {
			logger.info("Logging info message...inside common -> getDocumentFromCRM");
			response = ivlService.getDocFromCRMStatus(request);
		} catch (CommonException e) {
			logger.error(IOUtils.getPrintStackTrace(e));
		}
		return response;
	}
	

	@ApiOperation(value = "eSignForNonPL", notes = "eSignForNonPL", response = CommonResponse.class)
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON,MediaType.APPLICATION_FORM_URLENCODED })
	@Path("/eSignForNonPL")
	public CommonResponse eSignForNonPL(CommonRequest request) {
		CommonResponse response = new CommonResponse();
		try {
			logger.info("Logging info message...inside common -> getDocumentFromCRM");
			response = service.eSignForNonPL(request);
		} catch (CommonException e) {
			logger.error(IOUtils.getPrintStackTrace(e));
		}
		return response;
	}
	@ApiOperation(value = "generateAgreementForms", notes = "generateAgreementForms", response = CommonResponse.class)
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON,MediaType.APPLICATION_FORM_URLENCODED })
	@Path("/generateAgreementForms")
	public CommonResponse generateAgreementForms(CommonRequest request) {
		CommonResponse response = new CommonResponse();
		try {
			logger.info("Logging info message...inside common -> generateAgreementForms");
			response = ivlService.generateAgreementForms(request);
		} catch (CommonException e) {
			logger.error(IOUtils.getPrintStackTrace(e));
		}
		return response;
	}
	
	
	@ApiOperation(value = "PerfiosCheckStatus", notes = "PerfiosCheckStatus", response = CommonResponse.class)
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON,MediaType.APPLICATION_FORM_URLENCODED })
	@Path("/getPerfiosCheckStatus")
	public CommonResponse getPerfiosCheckStatus(CommonRequest request) {
		CommonResponse response = new CommonResponse();
		try {
			logger.info("Logging info message...inside common -> getPerfiosCheckStatus");
			response = ivlService.getPerfiosCheckStatus(request);
		} catch (CommonException e) {
			logger.error(IOUtils.getPrintStackTrace(e));
		}
		return response;
	}
	
	
	@ApiOperation(value = "ExperianGetAltData", notes = "ExperianGetAltData", response = CommonResponse.class)
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON,MediaType.APPLICATION_FORM_URLENCODED })
	@Path("/getExpAlternateData")
	public CommonResponse getExpAlternateData(CommonRequest request) {
		CommonResponse response = new CommonResponse();
		try {
			logger.info("Logging info message...inside common -> getExpAlternateData");
			
			response = ivlService.getExpAltData(request);
		} catch (CommonException e) {
			logger.error(IOUtils.getPrintStackTrace(e));
		}
		return response;
	}
	
	
	@ApiOperation(value = "GetApplicationInformation", notes = "GetApplicationInformation", response = CommonResponse.class)
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON,MediaType.APPLICATION_FORM_URLENCODED })
	@Path("/getApplicationInfoStatus")
	public CommonResponse getApplicationInfoStatus(CommonRequest request) {
		CommonResponse response = new CommonResponse();
		
		try {
			logger.info("Logging info message...inside common -> getApplicationInfoStatus");	
			response = ivlService.getApplicationInfo(request);
		} 
		catch (CommonException e) {
			logger.error(IOUtils.getPrintStackTrace(e));
		}
		return response;
	}

	@ApiOperation(value = "EmandateDropOut", notes = "EmandateDropOut", response = CommonResponse.class)
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON,MediaType.APPLICATION_FORM_URLENCODED })
	@Path("/getEmandateDropOutStatus")
	public CommonResponse getEmandateDropOutStatus(CommonRequest request) {
		CommonResponse response = new CommonResponse();
		try {
			logger.info("Logging info message...inside common -> getEmandateDropOutStatus");			
			response = ivlService.getEmandateDropOutStatus(request);
		} catch (CommonException e) {
			logger.error(IOUtils.getPrintStackTrace(e));
		}
		return response;
	}
	
	
	@ApiOperation(value = "GetProfileCRM", notes = "GetProfileCRM", response = CommonResponse.class)
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON,MediaType.APPLICATION_FORM_URLENCODED })
	@Path("/getProfileCRMStatus")
	public CommonResponse getProfileCRMStatus(CommonRequest request) {
		CommonResponse response = new CommonResponse();
		
		try {
			logger.info("Logging info message...inside common -> getProfileCRMStatus");			
			response = ivlService.getProfileCRMStatus(request);
		} catch (CommonException e) {
			logger.error(IOUtils.getPrintStackTrace(e));
		}
		return response;
	}

	
	@ApiOperation(value = "GetContactCRM", notes = "GetContactCRM", response = CommonResponse.class)
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON,MediaType.APPLICATION_FORM_URLENCODED })
	@Path("/getContactCRMStatus")
	public CommonResponse getContactCRMStatus(CommonRequest request) {
		CommonResponse response = new CommonResponse();
		
		try {
			logger.info("Logging info message...inside common -> getContactCRMStatus");			
			response = ivlService.getContactCRMStatus(request);
		} catch (CommonException e) {
			logger.error(IOUtils.getPrintStackTrace(e));
		}
		return response;
	}
	
	
	@ApiOperation(value = "GetNotifyCRM", notes = "GetNotifyCRM", response = CommonResponse.class)
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON,MediaType.APPLICATION_FORM_URLENCODED })
	@Path("/getNotifyCRMStatus")
	public CommonResponse getNotifyCRMStatus(CommonRequest request) {
		CommonResponse response = new CommonResponse();
		
		try {
			logger.info("Logging info message...inside common -> getNotifyCRMStatus");			
			response = ivlService.getNotifyCRMStatus(request);
		} catch (CommonException e) {
			logger.error(IOUtils.getPrintStackTrace(e));
		}
		return response;
	}
	
	
    @ApiOperation(value = "ValidatePerfiosForENach", notes = "ValidatePerfiosForENach", response = CommonResponse.class)
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON,MediaType.APPLICATION_FORM_URLENCODED })
	@Path("/getValidatePerfiosForENach")
	public CommonResponse getValidatePerfiosForENach(CommonRequest request) {
    	CommonResponse response = new CommonResponse();
		
		try {
			logger.info("Logging info message...inside common -> getValidatePerfiosForENach");			
			response = ivlService.getValidatePerfiosForENach(request);
		} catch (CommonException e) {
			logger.error(IOUtils.getPrintStackTrace(e));
		}
		return response;
	}

    @ApiOperation(value = "EsignAadharValidation", notes = "EsignAadharValidation", response = CommonResponse.class)
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON,
			MediaType.APPLICATION_FORM_URLENCODED })
	@Path("/esignAadharValidation")
	public CommonResponse esignAadharValidation(
			CommonRequest request) {
		CommonResponse response = new CommonResponse();
		try {
			logger.info("Logging info message...inside common -> esignAadharValidation");
			response = service.esignAadharValidation(request);
		} catch (CommonException e) {
			logger.error(IOUtils.getPrintStackTrace(e));
		}
		return response;
	}
    
	@ApiOperation(value = "EMIPaymentDetails", notes = "EMIPaymentDetails", response = CommonResponse.class)
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON,MediaType.APPLICATION_FORM_URLENCODED })
	@Path("/getEMIPaymentDetails")
	public CommonResponse getEMIPaymentDetails(CommonRequest request) {
		CommonResponse response = new CommonResponse();
		
		try {
			logger.info("Logging info message...inside common -> getEMIPaymentDetails");			
			response = ivlService.getEMIPaymentDetailsStatus(request);
		} catch (CommonException e) {
			logger.error(IOUtils.getPrintStackTrace(e));
		}
		return response;
	}
	
	
	@ApiOperation(value = "LoanRepaymentDetails", notes = "LoanRepaymentDetails", response = CommonResponse.class)
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON,MediaType.APPLICATION_FORM_URLENCODED })
	@Path("/getLoanRepaymentDetails")
	public CommonResponse getLoanRepaymentDetails(CommonRequest request) {
		CommonResponse response = new CommonResponse();
		
		try {
			logger.info("Logging info message...inside common -> getLoanRepaymentDetails");			
			response = ivlService.getLoanRepaymentDetails(request);
		} catch (CommonException e) {
			logger.error(IOUtils.getPrintStackTrace(e));
		}
		return response;
	}
	

	
	@ApiOperation(value = "LoanSummary", notes = "LoanSummary", response = CommonResponse.class)
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON,MediaType.APPLICATION_FORM_URLENCODED })
	@Path("/getLoanSummaryStatus")
	public CommonResponse getLoanSummaryStatus(CommonRequest request) {
		CommonResponse response = new CommonResponse();
		
		try {
			logger.info("Logging info message...inside common -> getLoanSummaryStatus");			
			response = ivlService.getLoanSummaryStatus(request);
		} catch (CommonException e) {
			logger.error(IOUtils.getPrintStackTrace(e));
		}
		return response;
	}
	
	
	@ApiOperation(value = "ENachValidation", notes = "ENachValidation", response = CommonResponse.class)
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON,MediaType.APPLICATION_FORM_URLENCODED })
	@Path("/getENachValidationStatus")
	public CommonResponse getENachValidationStatus(CommonRequest request) {
		CommonResponse response = new CommonResponse();
		
		try {
			logger.info("Logging info message...inside common -> getENachValidationStatus");			
			response = ivlService.getENachValidationStatus(request);
		} catch (CommonException e) {
			logger.error(IOUtils.getPrintStackTrace(e));
		}
		return response;
	}

	
	@ApiOperation(value = "ENachDetails", notes = "ENachDetails", response = CommonResponse.class)
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON,MediaType.APPLICATION_FORM_URLENCODED })
	@Path("/getENachDetailsStatus")
	public CommonResponse getENachDetailsStatus(CommonRequest request) {
		CommonResponse response = new CommonResponse();
		
		try {
			logger.info("Logging info message...inside common -> getENachDetailsStatus");			
			response = ivlService.getENachDetailsStatus(request);
		} catch (CommonException e) {
			logger.error(IOUtils.getPrintStackTrace(e));
		}
		return response;
	}


	@ApiOperation(value = "HDFCURLRedirectHash", notes = "HDFCURLRedirectHash", response = HDFCRedirectResParamResponse.class)
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON,MediaType.APPLICATION_FORM_URLENCODED })
	@Path("/getHDFCNetBankingHashValidation")
	public HDFCRedirectResParamResponse getHDFCNetBankingHashValidation(CommonRequest request) {
		HDFCRedirectResParamResponse response = new HDFCRedirectResParamResponse();
		
		try {
			logger.info("Logging info message...inside common -> getHDFCUrlRedirectResStatus");			
			response = ivlService.getHDFCUrlRedirectHashValidation(request);
		} catch (CommonException e) {
			logger.error(IOUtils.getPrintStackTrace(e));
		}
		return response;
	}

	@ApiOperation(value = "HDFCURLRedirectHash", notes = "HDFCURLRedirectHash", response = HDFCRedirectResParamResponse.class)
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON,MediaType.APPLICATION_FORM_URLENCODED })
	@Path("/getBillPaymentHashValidation")
	public HDFCRedirectResParamResponse getBillPaymentHashValidation(HDFCRedirectResParamRequest request) {
		HDFCRedirectResParamResponse response = new HDFCRedirectResParamResponse();
		
		try {
			logger.info("Logging info message...inside common -> getHDFCUrlRedirectResStatus");			
			response = ivlService.getHDFCBillPaymentHashValidation(request);
		} catch (CommonException e) {
			logger.error(IOUtils.getPrintStackTrace(e));
		}
		return response;
	}
	
	
	@ApiOperation(value = "HDFCURLRedirection", notes = "HDFCURLRedirection", response = HDFCUrlRedirectionResponse.class)
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON,MediaType.APPLICATION_FORM_URLENCODED })
	@Path("/getHDFCUrlRedirectStatus")
	public HDFCUrlRedirectionResponse getHDFCUrlRedirectStatus(CommonRequest request) {
		HDFCUrlRedirectionResponse response = new HDFCUrlRedirectionResponse();
		
		try {
			logger.info("Logging info message...inside common -> getHDFCUrlRedirectStatus");			
			response = ivlService.getHDFCUrlRedirectStatus(request);
		} catch (CommonException e) {
			logger.error(IOUtils.getPrintStackTrace(e));
		}
		return response;
	}
	
	
	@ApiOperation(value = "SaveComplaintCRM", notes = "SaveComplaintCRM", response = CommonResponse.class)
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON,MediaType.APPLICATION_FORM_URLENCODED })
	@Path("/saveComplaintCRMStatus")
	public CommonResponse saveComplaintCRMStatus(CommonRequest request) {
		CommonResponse response = new CommonResponse();
		
		try {
			logger.info("Logging info message...inside common -> saveComplaintCRMStatus");			
			response = ivlService.saveComplaintCRMStatus(request);
		} catch (CommonException e) {
			logger.error(IOUtils.getPrintStackTrace(e));
		}
		return response;
	}
	
	
	@ApiOperation(value = "ServiceRequestCRM", notes = "ServiceRequestCRM", response = CommonResponse.class)
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON,MediaType.APPLICATION_FORM_URLENCODED })
	@Path("/serviceRequestCRMStatus")
	public CommonResponse serviceRequestCRMStatus(CommonRequest request) {
		CommonResponse response = new CommonResponse();
		
		try {
			logger.info("Logging info message...inside common -> serviceRequestCRMStatus");			
			response = ivlService.getServiceRequestCRMStatus(request);
			
		} catch (CommonException e) {
			logger.error(IOUtils.getPrintStackTrace(e));
		}
		return response;
	}

	
	@ApiOperation(value = "EMandateResult", notes = "EMandateResult", response = CommonResponse.class)
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON,MediaType.APPLICATION_FORM_URLENCODED })
	@Path("/eMandateResultStatus")
	public CommonResponse eMandateResultStatus(CommonRequest request) {
		CommonResponse response = new CommonResponse();
		
		try {
			logger.info("Logging info message...inside common -> eMandateResultStatus");			
			response = ivlService.eMandateResultStatus(request);
		} catch (CommonException e) {
			logger.error(IOUtils.getPrintStackTrace(e));
		}
		return response;
	}

	
	@ApiOperation(value = "ESignVarification", notes = "ESignVarification", response = CommonResponse.class)
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON,MediaType.APPLICATION_FORM_URLENCODED })
	@Path("/eSignVarificationStatus")
	public CommonResponse eSignVarificationStatus(CommonRequest request) {
		CommonResponse response = new CommonResponse();
		
		try {
			logger.info("Logging info message...inside common -> eSignVarificationStatus");			
			response = ivlService.eSignVarificationStatus(request);
		} catch (CommonException e) {
			logger.error(IOUtils.getPrintStackTrace(e));
		}
		return response;
	}

	
	@ApiOperation(value = "LoanApplication", notes = "LoanApplication", response = CommonResponse.class)
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON,MediaType.APPLICATION_FORM_URLENCODED })
	@Path("/getLoanApplicationStatus")
	public CommonResponse getLoanApplicationStatus(CommonRequest request) {
		CommonResponse response = new CommonResponse();
		
		try {
			logger.info("Logging info message...inside common -> getLoanApplicationStatus");			
			response = service.getLoanApplicationStatus(request);
		} catch (CommonException e) {
			logger.error(IOUtils.getPrintStackTrace(e));
		}
		return response;
	}
	
	
	@ApiOperation(value = "GetEmandateData", notes = "GetEmandateData", response = CommonResponse.class)
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON,MediaType.APPLICATION_FORM_URLENCODED })
	@Path("/getEMandateDataStatus")
	public CommonResponse getEMandateDataStatus(CommonRequest request) {
		CommonResponse response = new CommonResponse();
		
		try {
			logger.info("Logging info message...inside common -> getEMandateDataStatus");			
			response = ivlService.getEMandateDataStatus(request);
		} catch (CommonException e) {
			logger.error(IOUtils.getPrintStackTrace(e));
		}
		return response;
	}
		
	
	@ApiOperation(value = "AxisURLRedirectDecData", notes = "AxisURLRedirectDecData", response = AxisURLRedirectDecResponse.class)
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON,
			MediaType.APPLICATION_FORM_URLENCODED })
	@Path("/getAxisURLRedirectDecData")
	public AxisURLRedirectDecResponse getAxisURLRedirectDecData(
			CommonRequest request) {
		AxisURLRedirectDecResponse response = new AxisURLRedirectDecResponse();
		try {
			logger.info("Logging info message...inside common -> getAxisURLRedirectDecData");
			response = service.getAxisURLRedirectDecStatus(request);
		} catch (CommonException e) {
			logger.error(IOUtils.getPrintStackTrace(e));
		}
		return response;
	}	

	
	@ApiOperation(value = "AxisURLRedirection", notes = "AxisURLRedirection", response = URLRedirectResponse.class)
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON,
			MediaType.APPLICATION_FORM_URLENCODED })
	@Path("/getAxisURLRedirection")
	public URLRedirectResponse getAxisURLRedirection(CommonRequest request) {
		URLRedirectResponse response = new URLRedirectResponse();
		logger.info("Logging info message...inside common -> getAxisURLRedirection");
		try {
			response = service.getAxisURLRedirectionStatus(request);
		} catch (CommonException e) {
			logger.error(IOUtils.getPrintStackTrace(e));
		
		}
		return response;
	}

	
	@ApiOperation(value = "BillPayment", notes = "BillPayment", response = BillPaymentResponse.class)
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON,
			MediaType.APPLICATION_FORM_URLENCODED })
	@Path("/getBillPaymentStatus")
	public BillPaymentResponse getBillPaymentStatus(BillPaymentRequest request) {
		BillPaymentResponse response = new BillPaymentResponse();
		logger.info("Logging info message...inside common -> getBillPaymentStatus");
		try {
			response = ivlService.getBillPaymentStatus(request);
		} catch (CommonException e) {
			logger.error(IOUtils.getPrintStackTrace(e));
		}
		return response;
	}
	
	
	@ApiOperation(value = "FAQ", notes = "FAQ", response = FAQResponse.class)
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON,
			MediaType.APPLICATION_FORM_URLENCODED })
	@Path("/getFAQStubStatus")
	public FAQResponse getFAQStubStatus(FAQRequest request) {
		FAQResponse response = new FAQResponse();
		logger.info("Logging info message...inside common -> getFAQStubStatus");
		try {
			response = ivlStubService.getFAQStubStatus(request);
		} catch (CommonException e) {
			logger.error(IOUtils.getPrintStackTrace(e));
		
		}
		return response;
	}

	@ApiOperation(value = "AddressProof", notes = "AddressProof", response = FAQResponse.class)
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON,
			MediaType.APPLICATION_FORM_URLENCODED })
	@Path("/getAddressProofList")
	public AddressProofResponse getAddressProofList(AddressProofRequest request) {
		AddressProofResponse response = new AddressProofResponse();
		logger.info("Logging info message...inside common -> getAddressProofList");
		try {
			response = ivlStubService.getAddressProofList(request);
		} catch (CommonException e) {
			logger.error(IOUtils.getPrintStackTrace(e));
		
		}
		return response;
	}
	
	@ApiOperation(value = "MandateDropOff", notes = "MandateDropOff", response = MandateDropOffResponse.class)
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED })
	@Path("/getMandateDropOffStub")
	public MandateDropOffResponse getMandateDropOffStub(MandateDropOffRequest request) {
		MandateDropOffResponse response = new MandateDropOffResponse();
		logger.info("Logging info message...inside common -> getMandateDropOffStub");
		try {
			response = ivlStubService.getMandateDropOffStub(request);
		} catch (CommonException e) {
			logger.error(IOUtils.getPrintStackTrace(e));
		}
		return response;
	}
	
	@ApiOperation(value = "InitialDisbursement", notes = "InitialDisbursement", response = CommonResponse.class)
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON,
			MediaType.APPLICATION_FORM_URLENCODED })
	@Path("/getInitialDisbursement")
	public CommonResponse getInitialDisbursement(CommonRequest request) {
		CommonResponse response = new CommonResponse();
		logger.info("Logging info message...inside common -> getInitialDisbursement");
		try {
			response = ivlService.getInitiateDisbursementStatus(request);
		} catch (CommonException e) {
			logger.error(IOUtils.getPrintStackTrace(e));
		}
		return response;
	}
	
	
	@ApiOperation(value = "PANSubmissionForDASS", notes = "PANSubmissionForDASS", response = CommonResponse.class)
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON,
			MediaType.APPLICATION_FORM_URLENCODED })
	@Path("/getPANSubmissionForDAAS")
	public CommonResponse getPANSubmissionForDAAS(CommonRequest request) {
		CommonResponse response = new CommonResponse();
		logger.info("Logging info message...inside common -> getPANSubmissionForDAAS");
		try {
			response = ivlService.getPANsubmitForDaasStatus(request);
		} catch (CommonException e) {
			logger.error(IOUtils.getPrintStackTrace(e));
		}
		return response;
	}
	
	
	@ApiOperation(value = "EMIPaymentUpdate", notes = "EMIPaymentUpdate", response = CommonResponse.class)
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON,
			MediaType.APPLICATION_FORM_URLENCODED })
	@Path("/getEMIPaymentUpdate")
	public CommonResponse getEMIPaymentUpdate(CommonRequest request) {
		CommonResponse response = new CommonResponse();
		logger.info("Logging info message...inside common -> getEMIPaymentUpdate");
		try {
			response = ivlService.getEMIPaymentUpdateStatus(request);
		} catch (CommonException e) {
			logger.error(IOUtils.getPrintStackTrace(e));
		}
		return response;
	}
	
	
	@ApiOperation(value = "GetBranch", notes = "GetBranch", response = CommonResponse.class)
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON,
			MediaType.APPLICATION_FORM_URLENCODED })
	@Path("/getBranchStatus")
	public CommonResponse getBranchStatus(CommonRequest request) {
		CommonResponse response = new CommonResponse();
		logger.info("Logging info message...inside common -> GetBranchService");
		try {
			response = ivlService.getBranchStatus(request);
		} catch (CommonException e) {
			logger.error(IOUtils.getPrintStackTrace(e));
		}
		return response;
	}
	
	
	@ApiOperation(value = "servletd", notes = "servletd", response = AESKeyResponse.class)
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON,
			MediaType.APPLICATION_FORM_URLENCODED })
	@Path("/servletd")
	public AESKeyResponse servletd(AESKeyRequest req) {
		AESKeyResponse response = new AESKeyResponse();
		logger.info("Logging info message...inside common -> servletd");
		try {
			
			response = ivlService.servletd(req);
		} catch (CommonException e) {
			logger.error(IOUtils.getPrintStackTrace(e));
		}
		return response;
	}
	
	
	@ApiOperation(value = "servletE", notes = "servletE", response = CommonResponse.class)
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED })
	@Path("/servletE")
	public CommonResponse servletdE(ServeletEEncRequest req) {
		CommonResponse response = new CommonResponse();
		String deviceType="";
		try {
			logger.info("Logging info message...inside common -> servletdE");
			logger.info("Input Request [ "+req.toString()+" isEnc [ " +isEnc +"]");
			AESKeyRequest request = ServletEEncConverterUtils.convert(req,isEnc);
			AESKeyResponse aesKeyResponse = ivlService.servletd(request); 
			deviceType = req.getDeviceOs();
			Gson gson=new Gson();
			String responseStr = gson.toJson(aesKeyResponse);
			logger.info("responseStr [ "+responseStr+" ]");
			//responseStr = "{\"key\":\"426bee0903c544fe|b43d7f347ea2aeeb\",\"sessionKey\":\"HKacsOnm-HB4Xrb5YWQ28UZ\",\"outputData\":null,\"sessionTimeout\":1800000,\"enc\":true}";
		 
			response = CommonUtility.generateCommonResponseForServletE(responseStr,req);
			logger.info("response" + response);
		}catch(Exception e) {
			e.printStackTrace();
			logger.info("servletdE [ "+e+" ]");
		}
		return response;
	}
	
	@ApiOperation(value = "getNewUserSignupService", notes = "getNewUserSignupService", response = CommonResponse.class)
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON,
			MediaType.APPLICATION_FORM_URLENCODED })
	@Path("/getNewUserSignupService")
	public CommonResponse getNewUserSignupService(CommonRequest request) {
		CommonResponse response = new CommonResponse();
		logger.info("Logging info message...inside common -> getNewUserSignupService ::"+request.getChkSum()+":"+request.getDevice_id()+":"+request.getInputString()+":"+request.getSession_key());
		
		try {
			response = ivlService.getNewUserSignupService(request);
		}catch (CommonException e) {
			logger.error(IOUtils.getPrintStackTrace(e));
		}
		return response;
	}
	
	
	/*@ApiOperation(value = "NewUserSignUp", notes = "NewUserSignUp", response = CommonResponse.class)
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON,
			MediaType.APPLICATION_FORM_URLENCODED })
	@Path("/getNewUserSignUPDetails")
	public CommonResponse getNewUserSignUPDetails(CommonRequest request) {
		CommonResponse response = new CommonResponse();
		logger.info("Logging info message...inside common -> getNewUserSignUPDetails");
		try {
			
			response = ivlService.getNewUserSignUPDetails(request);
		} catch (CommonException e) {
			logger.error(IOUtils.getPrintStackTrace(e));
		}
		return response;
	}*/
	
	@ApiOperation(value = "ICICIUrlRedirection", notes = "ICICIUrlRedirection", response = ICICIUrlRedirectResponse.class)
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON,
			MediaType.APPLICATION_FORM_URLENCODED })
	@Path("/getICICIRedirection")
	public ICICIUrlRedirectResponse getICICIRedirection(CommonRequest request) {
		ICICIUrlRedirectResponse response = new ICICIUrlRedirectResponse();
		logger.info("Logging info message...inside common -> getNewUserSignUPDetails");
		try {
			
			response = service.getICICIRedirectStatus(request);
		} catch (CommonException e) {
			logger.error(IOUtils.getPrintStackTrace(e));
		}
		return response;
	}
	
	@ApiOperation(value = "ICICIUrlRedirectResponse", notes = "ICICIUrlRedirectResponse", response = ICICIUrlRedirectResResponse.class)
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON,
			MediaType.APPLICATION_FORM_URLENCODED })
	@Path("/getICICIResponseData")
	public ICICIUrlRedirectResResponse getICICIResponseData(CommonRequest request) {
		ICICIUrlRedirectResResponse response = new ICICIUrlRedirectResResponse();
		logger.info("Logging info message...inside common -> getICICIResponseData");
		try {
			
			response = service.getICICIRedirectResDataStatus(request);
		} catch (CommonException e) {
			logger.error(IOUtils.getPrintStackTrace(e));
		}
		return response;
	}
	
	@ApiOperation(value = "EcoAccountName", notes = "EcoAccountName", response = CommonResponse.class)
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON,
			MediaType.APPLICATION_FORM_URLENCODED })
	@Path("/getEcoAccountNameStatus")
	public CommonResponse getEcoAccountNameStatus(CommonRequest request) {
		CommonResponse response = new CommonResponse();
		logger.info("Logging info message...inside common -> getEcoAccountNameStatus");
		try {
			
			response = service.getEcoAccountNameStatus(request);
		} catch (CommonException e) {
			logger.error(IOUtils.getPrintStackTrace(e));
		}
		return response;
	}

	@ApiOperation(value = "SBIUrlRedirection", notes = "SBIUrlRedirection", response = SBIUrlRedirectResponse.class)
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON,
			MediaType.APPLICATION_FORM_URLENCODED })
	@Path("/getSBIRedirection")
	public SBIUrlRedirectResponse getSBIRedirection(CommonRequest request) {
		SBIUrlRedirectResponse response = new SBIUrlRedirectResponse();
		logger.info("Logging info message...inside common -> getSBIURLRedirectStatus");
		try {
			
			response =service.getSBIRedirectStatus(request);
		} catch (CommonException e) {
			logger.error(IOUtils.getPrintStackTrace(e));
		}
		return response;
	}
	
	@ApiOperation(value = "SBIUrlResponseData", notes = "SBIUrlResponseData", response = SBIResDataResponse.class)
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON,
			MediaType.APPLICATION_FORM_URLENCODED })
	@Path("/getSBIResponseData")
	public SBIResDataResponse getSBIResponseData (CommonRequest request) {
		SBIResDataResponse response = new SBIResDataResponse();
		logger.info("Logging info message...inside common -> getSBIResponseData");
		try {
			
			response =service.getSBIRedirectResStatus(request);
		} catch (CommonException e) {
			logger.error(IOUtils.getPrintStackTrace(e));
		}
		return response;
	}
	
    @ApiOperation(value = "KotakUrlRedirection", notes = "KotakUrlRedirection", response = KotakUrlRedirectResponse.class)
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON,
			MediaType.APPLICATION_FORM_URLENCODED })
	@Path("/getKotakRedirection")
	public KotakUrlRedirectResponse getKotakRedirection(CommonRequest request) {
		KotakUrlRedirectResponse response = new KotakUrlRedirectResponse();
		logger.info("Logging info message...inside common -> getKotakURLRedirectStatus");
		try {
			response =service.getKotakRedirectStatus(request);
		} catch (CommonException e) {
			logger.error(IOUtils.getPrintStackTrace(e));
		}
		return response;
	}
	
	@ApiOperation(value = "KotakResponseData", notes = "KotakResponseData", response = KotakResDataResponse.class)
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON,
			MediaType.APPLICATION_FORM_URLENCODED })
	@Path("/getKotakResData")
	public KotakResDataResponse getKotakResData(CommonRequest request) {
		KotakResDataResponse response = new KotakResDataResponse();
		logger.info("Logging info message...inside common -> getKotakResData");
		try {
			response =service.getKotakRedirectResStatus(request);
		} catch (CommonException e) {
			logger.error(IOUtils.getPrintStackTrace(e));
		}
		return response;
	}

	@ApiOperation(value = "VersionUpgrade", notes = "VersionUpgrade", response = CommonResponse.class)
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON,
			MediaType.APPLICATION_FORM_URLENCODED })
	@Path("/versionUpgradeStatus")
	public CommonResponse versionUpgradeStatus(CommonRequest request) {
		CommonResponse response = new CommonResponse();
		logger.info("Logging info message...inside common -> versionUpgradeStatus");
		try {
			
			response =service.getVersionUpgradeStatus(request);
		} catch (CommonException e) {
			logger.error(IOUtils.getPrintStackTrace(e));
		}
		return response;
	}
	

	@ApiOperation(value = "UploadDocument", notes = "UploadDocument", response = CommonResponse.class)
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON,
			MediaType.APPLICATION_FORM_URLENCODED })
	@Path("/uploadDocument")
	//@OAuthSecurity(enabled = true, scope = "ibsecurity")
	public CommonResponse uploadDocument(CommonRequest request) {
		CommonResponse response = new CommonResponse();
		try {
			logger.info("Logging info message...inside common -> UploadDocument");
			response = service.uploadDocument(request);
		} catch (CommonException e) {
			logger.error(IOUtils.getPrintStackTrace(e));
		}
		return response;
	}
	
	@ApiOperation(value = "UploadDocForDemographic", notes = "UploadDocForDemographic", response = CommonResponse.class)
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON,
			MediaType.APPLICATION_FORM_URLENCODED })
	@Path("/uploadDocumentForDemographic")
	public CommonResponse uploadDocumentForDemographic(CommonRequest request) {
		CommonResponse response = new CommonResponse();
		try {
			logger.info("Logging info message...inside common -> uploadDocumentForDemographic");
			response = service.uploadDocumentForDemographic(request);
		} catch (CommonException e) {
			logger.error(IOUtils.getPrintStackTrace(e));
	}
		return response;
	}
	
	@ApiOperation(value = "PreviewDocument", notes = "UploadDocument", response = CommonResponse.class)
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON,
			MediaType.APPLICATION_FORM_URLENCODED })
	@Path("/previewDocument")
	public CommonResponse previewDocument(CommonRequest request) {
		CommonResponse response = new CommonResponse();
		try {
			logger.info("Logging info message...inside common -> UploadDocument");
			response = service.previewDocument(request);
		} catch (CommonException e) {
			logger.error(IOUtils.getPrintStackTrace(e));
		}
		return response;
	}
	
	
	@ApiOperation(value = "DocumentList", notes = "DocumentList", response = CommonResponse.class)
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON,
			MediaType.APPLICATION_FORM_URLENCODED })
	@Path("/getDocumentList")
	public CommonResponse getDocumentList(CommonRequest request) {
		CommonResponse response = new CommonResponse();
		try {
			logger.info("Logging info message...inside common -> getDocumentList");
			response = service.getDocumentList(request);
		} catch (CommonException e) {
			logger.error(IOUtils.getPrintStackTrace(e));
		}
		return response;
	}
	
	@ApiOperation(value = "putAadharDocumentToIB", notes = "putAadharDocumentToIB", response = CommonResponse.class)
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON,
			MediaType.APPLICATION_FORM_URLENCODED })
	@Path("/putAadharDocumentToIB")
	public CommonResponse putAadharDocumentToIB(CommonRequest request) {
		CommonResponse response = new CommonResponse();
		try {
			logger.info("Logging info message...inside common -> putAadharDocumentToIB");
			response = ivlService.putAadharDocumentToIB(request);
		} catch (CommonException e) {
			logger.error(IOUtils.getPrintStackTrace(e));
		}
		return response;
	}
	
	@ApiOperation(value = "putSignedDocument", notes = "putSignedDocument", response = CommonResponse.class)
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON,
			MediaType.APPLICATION_FORM_URLENCODED })
	@Path("/putSignedDocument")
	public CommonResponse putSignedDocument(CommonRequest request) {
		CommonResponse response = new CommonResponse();
		try {
			logger.info("Logging info message...inside common -> putSignedDocument");
			response = ivlService.putSignedDocument(request);
		} catch (CommonException e) {
			logger.error(IOUtils.getPrintStackTrace(e));
		}
		return response;
	}
	
	@ApiOperation(value = "AdhaarTnC", notes = "TnC", response = AdhaarTnCResponse.class)
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON,
			MediaType.APPLICATION_FORM_URLENCODED })
	@Path("/getAadhaarTnC")
	public AdhaarTnCResponse getAadhaarTnC(AdhaarTnCRequest request) {
		AdhaarTnCResponse response = new AdhaarTnCResponse();
		logger.info("Logging info message...inside common -> getAadhaarTnC");
		try {
			response = ivlStubService.getAadhaarTnC(request);
		} catch (CommonException e) {
			logger.error(IOUtils.getPrintStackTrace(e));
		
		}
		return response;
	}
	
	@ApiOperation(value = "ProductsList", notes = "ProductsList", response = AdhaarTnCResponse.class)
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON,
			MediaType.APPLICATION_FORM_URLENCODED })
	@Path("/getProductsList")
	public ProductsListResponse getProductsList(ProductsListRequest request) {
		ProductsListResponse response = new ProductsListResponse();
		logger.info("Logging info message...inside common -> getProductsList");
		try {
			response = ivlStubService.getProductsList(request);
		} catch (CommonException e) {
			logger.error(IOUtils.getPrintStackTrace(e));
		}
		return response;
	}
	
	@ApiOperation(value = "companyLookUp", notes = "companyLookUp", response = CommonResponse.class)
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON,
			MediaType.APPLICATION_FORM_URLENCODED })
	@Path("/companyLookUp")
	public CommonResponse companyLookUp(CommonRequest request) {
		CommonResponse response = new CommonResponse();
		logger.info("Logging info message...inside common -> companyLookUp");
		try {
			response = ivlService.companyLookUp(request);
		}catch (CommonException e) {
			logger.error(IOUtils.getPrintStackTrace(e));
		}
		return response;
	}

	@ApiOperation(value = "logout", notes = "logout", response = CommonResponse.class)
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON,
			MediaType.APPLICATION_FORM_URLENCODED })
	@Path("/logout")
	public CommonResponse logout(CommonRequest request) {
		CommonResponse response = new CommonResponse();
		try {
			logger.info("Logging info message...inside common -> logout");
			response = service.logout(request);
		} catch (CommonException e) {
			logger.error(IOUtils.getPrintStackTrace(e));
		}
		return response;
	}
	
	@ApiOperation(value = "creditVidyaEmailFraud", notes = "creditVidyaEmailFraud", response = CommonResponse.class)
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON,
			MediaType.APPLICATION_FORM_URLENCODED })
	@Path("/creditVidyaEmailFraud")
	public CommonResponse creditVidyaEmailFraud(CommonRequest request) {
		CommonResponse response = new CommonResponse();
		logger.info("Logging info message...inside common -> creditVidyaEmailFraud");
		try {
			response = ivlService.creditVidyaEmailFraud(request);
		}catch (CommonException e) {
			logger.error(IOUtils.getPrintStackTrace(e));
		}
		return response;
	}
	
	@ApiOperation(value = "EAgreementOTPAuthentication", notes = "EAgreementOTPAuthentication", response = CommonResponse.class)
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON,
			MediaType.APPLICATION_FORM_URLENCODED })
	@Path("/getEAgreementOTPAuthStatus")
	public CommonResponse getEAgreementOTPAuthStatus(CommonRequest request) {
		CommonResponse response = new CommonResponse();
		logger.info("Logging info message...inside common -> getEAgreementOTPAuthstatus");
		try {
			response = ivlService.getEAgreementOTPAuthStatus(request);
		}catch (CommonException e) {
			logger.error(IOUtils.getPrintStackTrace(e));
		}
		return response;
	}
	
	@ApiOperation(value = "EAgreementSign", notes = "EAgreementSign", response = CommonResponse.class)
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON,
			MediaType.APPLICATION_FORM_URLENCODED })
	@Path("/getEAgreementSignStatus")
	public CommonResponse getEAgreementSignStatus(CommonRequest request) {
		
		CommonResponse response = new CommonResponse();
		logger.info("Logging info message...inside common -> getEAgreementSignStatus");
		try {
			response = ivlService.getEAgreementSignStatus(request);
		}catch (CommonException e) {
			logger.error(IOUtils.getPrintStackTrace(e));
		}
		return response;
	}
	
	@ApiOperation(value = "ProductCreation", notes = "ProductCreation", response = CommonResponse.class)
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON,
			MediaType.APPLICATION_FORM_URLENCODED })
	@Path("/createProductStatus")
	public CommonResponse createProductStatus(CommonRequest request) {
		
		CommonResponse response = new CommonResponse();
		logger.info("Logging info message...inside common -> createProductStatus");
		try {
			response = ivlService.createProductStatus(request);
		}catch (CommonException e) {
			logger.error(IOUtils.getPrintStackTrace(e));
		}
		return response;
	}
	
	@ApiOperation(value = "CaptureValidateData", notes = "CaptureValidateData", response = CommonResponse.class)
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON,
			MediaType.APPLICATION_FORM_URLENCODED })
	@Path("/captureValidateData")
	public CommonResponse captureValidateData(CommonRequest request) {
		
		CommonResponse response = new CommonResponse();
		logger.info("Logging info message...inside common -> createProductStatus");
		try {
			response = ivlService.captureValidateData(request);
		}catch (CommonException e) {
			logger.error(IOUtils.getPrintStackTrace(e));
		}
		return response;
	}
	
	@ApiOperation(value = "AcceptTopUp", notes = "AcceptTopUp", response = CommonResponse.class)
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON,
			MediaType.APPLICATION_FORM_URLENCODED })
	@Path("/acceptTopUpStatus")
	public CommonResponse acceptTopUpStatus(
			CommonRequest request) {
		CommonResponse response = new CommonResponse();
		try {
			logger.info("Logging info message...inside common -> acceptTopUpStatus");
			response = ivlService.acceptTopUpStatus(request);
		} catch (CommonException e) {
			logger.error(IOUtils.getPrintStackTrace(e));
		}
		return response;
	}
	
	@ApiOperation(value = "updateAddressStatus", notes = "updateAddressStatus", response = CommonResponse.class)
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON,
			MediaType.APPLICATION_FORM_URLENCODED })
	@Path("/updateAddressStatus")
	public CommonResponse updateAddressStatus(CommonRequest request) {
		CommonResponse response = new CommonResponse();
		logger.info("Logging info message...inside common -> updateAddressStatus");
		try {
			response = ivlService.updateAddressStatus(request);
		}catch (CommonException e) {
			logger.error(IOUtils.getPrintStackTrace(e));
		}
		return response;
	}
	
	@ApiOperation(value = "TopUpDetails", notes = "TopUpDetails", response = CommonResponse.class)
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON,
			MediaType.APPLICATION_FORM_URLENCODED })
	@Path("/getTopUpDetails")
	public CommonResponse getTopUpDetails(CommonRequest request) {
		CommonResponse response = new CommonResponse();
		logger.info("Logging info message...inside common -> getTopUpDetails");
		try {
			response = ivlService.getTopUpDetails(request);
		}catch (CommonException e) {
			logger.error(IOUtils.getPrintStackTrace(e));
		}
		return response;
	}
	

	@ApiOperation(value = "getMasterDataService", notes = "getMasterDataService", response = CommonResponse.class)
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON,
			MediaType.APPLICATION_FORM_URLENCODED })
	@Path("/getMasterDataService")
	public CommonResponse getMasterDataService(CommonRequest request) {
		CommonResponse response = new CommonResponse();
		logger.info("Logging info message...inside common -> getMasterDataService");
		try {
			response = ivlService.getMasterDataService(request);
		}catch (CommonException e) {
			logger.error(IOUtils.getPrintStackTrace(e));
		}
		return response;                                      
	}
	
	@ApiOperation(value = "getInitiateCibil", notes = "InitiateCibil", response = CommonResponse.class)
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON,
			MediaType.APPLICATION_FORM_URLENCODED })
	@Path("/getInitiateCibil")
	public CommonResponse getInitiateCibil(CommonRequest request) {
		CommonResponse response = new CommonResponse();
		logger.info("Logging info message...inside common -> getInitiateCibil");
		try {
			response = ivlService.getInitiateCibil(request);
		}catch (CommonException e) {
			logger.error(IOUtils.getPrintStackTrace(e));
		}
		return response;
	}
	
	@ApiOperation(value = "InitiateDaasCall1", notes = "InitiateDaasCall1", response = CommonResponse.class)
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON,
			MediaType.APPLICATION_FORM_URLENCODED })
	@Path("/getInitiateDaasCall1")
	public CommonResponse getInitiateDaasCall1(CommonRequest request) {
		CommonResponse response = new CommonResponse();
		logger.info("Logging info message...inside common -> getInitiateDaasCall1");
		try {
			response = ivlService.getInitiateDaasCall1(request);
		}catch (CommonException e) {
			logger.error(IOUtils.getPrintStackTrace(e));
		}
		return response;
	}
	
	@ApiOperation(value = "getHerokuAPI", notes = "getHerokuAPI", response = CommonResponse.class)
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON,
			MediaType.APPLICATION_FORM_URLENCODED })
	@Path("/getHerokuAPI")
	public CommonResponse getHerokuAPI(CommonRequest request) {
		CommonResponse response = new CommonResponse();
		logger.info("Logging info message...inside common -> getHerokuAPI");
		try {
			response = ivlService.getHerokuAPI(request);
		}catch (CommonException e) {
			logger.error(IOUtils.getPrintStackTrace(e));
		}
		return response;
	}
	
		
	@ApiOperation(value = "yesBankDualVarification", notes = "yesBankDualVarification", response = YesBankResponse.class)
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON,
			MediaType.APPLICATION_FORM_URLENCODED })
	@Path("/yesBankDualVarification")
	public YesBankResponse yesBankDualVarification(CommonRequest request) {
		YesBankResponse response = new YesBankResponse();
		logger.info("Logging info message...inside common -> yesBankDualVarification");
		try {
			response = integrationService.yesBankDualVarification(request);
		}catch (CommonException e) {
			logger.error(IOUtils.getPrintStackTrace(e));
		}
		return response;
	}
	
	
	@ApiOperation(value = "YesBankRedirection", notes = "YesBankRedirection", response = YesBankResponse.class)
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON,MediaType.APPLICATION_FORM_URLENCODED })
	@Path("/yesBankRedirection")
	public YesBankResponse yesBankRedirection(CommonRequest request) {
		YesBankResponse response = new YesBankResponse();
		logger.info("Logging info message...inside common -> yesBankRedirection");
		try {
			response = integrationService.yesBankRedirection(request);
		}catch (CommonException e) {
			logger.error(IOUtils.getPrintStackTrace(e));
		}
		return response;
	}
	

	@ApiOperation(value = "andhraBankRedirection", notes = "andhraBankRedirection", response = AndhraTokenResponse.class)
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON,
			MediaType.APPLICATION_FORM_URLENCODED })
	@Path("/generateTokenForAndhraBank")
	public AndhraTokenResponse generateTokenForAndhraBank1(CommonRequest request) throws CommonException {
		AndhraTokenResponse response = new AndhraTokenResponse();
		logger.info("Logging info message...inside common -> andhraBankRedirection");
		response = integrationService.generateTokenForAndhraBank(request);
		return response;
	}
	
	@ApiOperation(value = "andhraBankresponseData", notes = "andhraBankresponseData", response = AndhraTokenResponse.class)
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON,
			MediaType.APPLICATION_FORM_URLENCODED })
	@Path("/andhraBankresponseData")
	public AndhraResponse andhraBankresponseData(CommonRequest request) throws CommonException {
		AndhraResponse response = new AndhraResponse();
		logger.info("Logging info message...inside common -> andhraBankresponseData");
		response = integrationService.getandhraBankresponseGen(request);
		return response;
	}
	@ApiOperation(value = "getPincodes", notes = "getPincodes", response = FAQRequest.class)
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON,MediaType.APPLICATION_FORM_URLENCODED })
	@Path("/getPincodes")
	public CommonResponse getPincodes(FAQRequest request) {
		CommonResponse response = new CommonResponse();
		logger.info("Logging info message...inside common -> getPincodes");
		try {
			
			//request.setLang("C:\\logs\\pincode.json");
			//ivlStubService.getFAQStubStatus(request);
			response = ivlService.getPincodes(request);
		} catch (CommonException e) {
			logger.error(IOUtils.getPrintStackTrace(e));
		}
		return response;
	}
	
	@ApiOperation(value = "RBLRedirection", notes = "RBLRedirection", response = RblTokenRequest.class)
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON,
			MediaType.APPLICATION_FORM_URLENCODED })
	@Path("/generateTokenForRBL")
	public CommonResponse generateTokenForRbl(CommonRequest request) throws CommonException {
		CommonResponse response = new CommonResponse();
		logger.info("Logging info message...inside common -> generateTokenForRBL");
		response = integrationService.generateTokenForRbl(request);
		return response;
	}
	
	
	@ApiOperation(value = "getActivatePrepaidCard", notes = "getActivatePrepaidCard", response = CommonResponse.class)
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON,MediaType.APPLICATION_FORM_URLENCODED })
	@Path("/getActivatePrepaidCard")
	public CommonResponse getActivatePrepaidCard(CommonRequest request) {
		CommonResponse response = new CommonResponse();
		logger.info("Logging info message...inside common -> getActivatePrepaidCard");
		try {
			response = ivlService.getActivatePrepaidCard(request);
		}catch (CommonException e) {
			logger.error(IOUtils.getPrintStackTrace(e));
		}
		return response;
	}
	
	@ApiOperation(value = "getGenerateOTPforPINset", notes = "getGenerateOTPforPINset", response = CommonResponse.class)
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON,MediaType.APPLICATION_FORM_URLENCODED })
	@Path("/getGenerateOTPforPINset")
	public CommonResponse getGenerateOTPforPINset(CommonRequest request) {
		CommonResponse response = new CommonResponse();
		logger.info("Logging info message...inside common -> getGenerateOTPforPINset");
		try {
			response = ivlService.getGenerateOTPforPINset(request);
		}catch (CommonException e) {
			logger.error(IOUtils.getPrintStackTrace(e));
		}
		return response;
	}
	@ApiOperation(value = "getSetPIN", notes = "getSetPIN", response = CommonResponse.class)
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON,MediaType.APPLICATION_FORM_URLENCODED })
	@Path("/getSetPIN")
	public CommonResponse getSetPIN(CommonRequest request) {
		CommonResponse response = new CommonResponse();
		logger.info("Logging info message...inside common -> getSetPIN");
		try {
			response = ivlService.getSetPIN(request);
		}catch (CommonException e) {
			logger.error(IOUtils.getPrintStackTrace(e));
		}
		return response;
	}
	
	@ApiOperation(value = "loanOutstanding" , notes= "loanOutstanding", response =CommonResponse.class)
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON,MediaType.APPLICATION_FORM_URLENCODED })
	@Path("/loanOutstanding")
	public CommonResponse loanOutstanding(CommonRequest request) {
		CommonResponse response = new CommonResponse();
		logger.info("Logging info message...inside common -> loanOutstanding");
		try {
			response = ivlService.loanOutstanding(request);
		}catch (CommonException e) {
			logger.error(IOUtils.getPrintStackTrace(e));
		}
		return response;
	}
	
}
