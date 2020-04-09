package snap.ib.personelLoan.adapter.common.service;

import snap.ib.personelLoan.adapter.common.exception.CommonException;
import snap.ib.personelLoan.adapter.common.request.AESKeyRequest;
import snap.ib.personelLoan.adapter.common.request.BillPaymentRequest;
import snap.ib.personelLoan.adapter.common.request.CommonRequest;
import snap.ib.personelLoan.adapter.common.request.FAQRequest;
import snap.ib.personelLoan.adapter.common.request.HDFCRedirectResParamRequest;
import snap.ib.personelLoan.adapter.common.request.HDFCUrlRedirectionRequest;
import snap.ib.personelLoan.adapter.common.response.AESKeyResponse;
import snap.ib.personelLoan.adapter.common.response.BillPaymentResponse;
import snap.ib.personelLoan.adapter.common.response.CommonResponse;
import snap.ib.personelLoan.adapter.common.response.HDFCRedirectResParamResponse;
import snap.ib.personelLoan.adapter.common.response.HDFCUrlRedirectionResponse;

public interface IVLCommonService {

	public CommonResponse getDocFromCRMStatus(CommonRequest request) throws CommonException;

	public CommonResponse generateAgreementForms(CommonRequest request) throws CommonException;

	public CommonResponse getPerfiosCheckStatus(CommonRequest request) throws CommonException;

	public CommonResponse getExpAltData(CommonRequest request) throws CommonException;

	public CommonResponse getApplicationInfo(CommonRequest request) throws CommonException;

	public CommonResponse getEmandateDropOutStatus(CommonRequest request) throws CommonException;

	public CommonResponse getProfileCRMStatus(CommonRequest request) throws CommonException;

	public CommonResponse getContactCRMStatus(CommonRequest request) throws CommonException;

	public CommonResponse getServiceRequestCRMStatus(CommonRequest request) throws CommonException;

	public CommonResponse getNotifyCRMStatus(CommonRequest request) throws CommonException;

	public CommonResponse saveComplaintCRMStatus(CommonRequest request) throws CommonException;

	public CommonResponse eMandateResultStatus(CommonRequest request) throws CommonException;

	public CommonResponse eSignVarificationStatus(CommonRequest request) throws CommonException;

	public CommonResponse getEMandateDataStatus(CommonRequest request) throws CommonException;

	public HDFCUrlRedirectionResponse getHDFCUrlRedirectStatus(CommonRequest request)
			throws CommonException;

	public HDFCRedirectResParamResponse getHDFCUrlRedirectHashValidation(CommonRequest request)
			throws CommonException;

	public HDFCRedirectResParamResponse getHDFCBillPaymentHashValidation(HDFCRedirectResParamRequest request)
			throws CommonException;

	public CommonResponse getENachDetailsStatus(CommonRequest request) throws CommonException;

	public CommonResponse getENachValidationStatus(CommonRequest request) throws CommonException;

	public CommonResponse getLoanSummaryStatus(CommonRequest request) throws CommonException;

	public CommonResponse getLoanRepaymentDetails(CommonRequest request) throws CommonException;

	public CommonResponse getEMIPaymentDetailsStatus(CommonRequest request) throws CommonException;

	public CommonResponse getValidatePerfiosForENach(CommonRequest request) throws CommonException;

	public BillPaymentResponse getBillPaymentStatus(BillPaymentRequest request) throws CommonException;

	public CommonResponse getInitiateDisbursementStatus(CommonRequest request) throws CommonException;

	public CommonResponse getPANsubmitForDaasStatus(CommonRequest request) throws CommonException;

	public CommonResponse getEMIPaymentUpdateStatus(CommonRequest request) throws CommonException;

	public CommonResponse getBranchStatus(CommonRequest request) throws CommonException;

	public AESKeyResponse servletd(AESKeyRequest req) throws CommonException;

	public CommonResponse getNewUserSignUPDetails(CommonRequest request) throws CommonException;

	public CommonResponse putAadharDocumentToIB(CommonRequest request) throws CommonException;

	public CommonResponse putSignedDocument(CommonRequest request) throws CommonException;

	public CommonResponse companyLookUp(CommonRequest request) throws CommonException;

	public CommonResponse creditVidyaEmailFraud(CommonRequest request) throws CommonException;
	
	public CommonResponse getLoanInsuranceStatus(CommonRequest request) throws CommonException;
	
	public CommonResponse getEAgreementOTPAuthStatus(CommonRequest request) throws CommonException;
	
	public CommonResponse createProductStatus(CommonRequest request) throws CommonException;
	
	public CommonResponse captureValidateData(CommonRequest request) throws CommonException;
		
    public CommonResponse getTopUpDetails(CommonRequest request) throws CommonException;
	
	public CommonResponse updateAddressStatus(CommonRequest request) throws CommonException;

	public CommonResponse acceptTopUpStatus(CommonRequest request) throws CommonException;
	
	public CommonResponse getEAgreementSignStatus(CommonRequest request) throws CommonException;
	
	public CommonResponse getNewUserSignupService(CommonRequest request) throws CommonException;
	
	public CommonResponse getMasterDataService(CommonRequest request) throws CommonException;
	
	public CommonResponse getInitiateCibil(CommonRequest request) throws CommonException;
	
	public CommonResponse getInitiateDaasCall1(CommonRequest request) throws CommonException;
	
	public CommonResponse getHerokuAPI(CommonRequest request) throws CommonException;

	public CommonResponse getPincodes(FAQRequest request)throws CommonException;

	public CommonResponse getActivatePrepaidCard(CommonRequest request)throws CommonException;

	public CommonResponse getGenerateOTPforPINset(CommonRequest request)throws CommonException;

	public CommonResponse getSetPIN(CommonRequest request)throws CommonException;
	
	public CommonResponse loanOutstanding(CommonRequest request) throws CommonException;
	
} 
