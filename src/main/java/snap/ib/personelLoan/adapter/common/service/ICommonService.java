package snap.ib.personelLoan.adapter.common.service;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;

import snap.ib.personelLoan.adapter.common.exception.CommonException;
import snap.ib.personelLoan.adapter.common.request.AxisURLRedirectDecRequest;
import snap.ib.personelLoan.adapter.common.request.CommonRequest;
import snap.ib.personelLoan.adapter.common.request.ICICIUrlRedirectRequest;
import snap.ib.personelLoan.adapter.common.request.ICICIUrlRedirectResRequest;
import snap.ib.personelLoan.adapter.common.request.KotakResDataRequest;
import snap.ib.personelLoan.adapter.common.request.KotakUrlRedirectRequest;
import snap.ib.personelLoan.adapter.common.request.SBIResDataRequest;
import snap.ib.personelLoan.adapter.common.request.SBIUrlRedirectRequest;
import snap.ib.personelLoan.adapter.common.request.URLRedirectRequest;
import snap.ib.personelLoan.adapter.common.response.AxisURLRedirectDecResponse;
import snap.ib.personelLoan.adapter.common.response.CommonResponse;
import snap.ib.personelLoan.adapter.common.response.ICICIUrlRedirectResResponse;
import snap.ib.personelLoan.adapter.common.response.ICICIUrlRedirectResponse;
import snap.ib.personelLoan.adapter.common.response.KotakResDataResponse;
import snap.ib.personelLoan.adapter.common.response.KotakUrlRedirectResponse;
import snap.ib.personelLoan.adapter.common.response.SBIResDataResponse;
import snap.ib.personelLoan.adapter.common.response.SBIUrlRedirectResponse;
import snap.ib.personelLoan.adapter.common.response.URLRedirectResponse;

public interface ICommonService {

	public CommonResponse getMobileRegistrationStatus(CommonRequest request) throws CommonException;

	public CommonResponse getUserLoanDetails(CommonRequest request) throws CommonException;

	public CommonResponse getForgotMpinStatus(CommonRequest request) throws CommonException;

	public CommonResponse getUpdateMpinStatus(CommonRequest request) throws CommonException;
	
	public CommonResponse validateMpin(CommonRequest request) throws CommonException;
	
	public CommonResponse getAadharDropOffStatus(CommonRequest request) throws CommonException;

	public CommonResponse aadharOTPAuthentication(CommonRequest request) throws CommonException;
	
	public CommonResponse esignAadharValidation(CommonRequest request) throws CommonException;

	public CommonResponse aadharOTPGeneration(CommonRequest request, @Context HttpHeaders headers)
			throws CommonException, Exception;

	public CommonResponse getResendOTPStatus(CommonRequest request) throws CommonException;

	public CommonResponse submitDemographicInfo(CommonRequest request) throws CommonException;

	public CommonResponse getMasterData(CommonRequest request) throws CommonException;

	public CommonResponse getPANVarificationStatus(CommonRequest request) throws CommonException;

	public AxisURLRedirectDecResponse getAxisURLRedirectDecStatus(CommonRequest request)
			throws CommonException;

	public CommonResponse getPayloadTransactionStatus(CommonRequest request) throws CommonException;

	public CommonResponse getPerfiosUrlRedirectStatus(CommonRequest request) throws CommonException;

	public CommonResponse getUpdateReviseLoanStatus(CommonRequest request) throws CommonException;

	public CommonResponse getCaptureSelfiStatus(CommonRequest request) throws CommonException;

	public URLRedirectResponse getAxisURLRedirectionStatus(CommonRequest request) throws CommonException;

	public CommonResponse getLoanApplicationStatus(CommonRequest request) throws CommonException;

	public ICICIUrlRedirectResponse getICICIRedirectStatus(CommonRequest request) throws CommonException;

	public ICICIUrlRedirectResResponse getICICIRedirectResDataStatus(CommonRequest request)
			throws CommonException;

	public CommonResponse getEcoAccountNameStatus(CommonRequest request) throws CommonException;

	public CommonResponse getVersionUpgradeStatus(CommonRequest request) throws CommonException;

	public SBIUrlRedirectResponse getSBIRedirectStatus(CommonRequest request) throws CommonException;

	public SBIResDataResponse getSBIRedirectResStatus(CommonRequest request) throws CommonException;

	public CommonResponse getChangeMpin(CommonRequest request) throws CommonException;

	public KotakUrlRedirectResponse getKotakRedirectStatus(CommonRequest request) throws CommonException;

	public KotakResDataResponse getKotakRedirectResStatus(CommonRequest request) throws CommonException;

	public CommonResponse uploadDocument(CommonRequest request) throws CommonException;
	
	public CommonResponse uploadDocumentForDemographic(CommonRequest request) throws CommonException;
	
	public CommonResponse previewDocument(CommonRequest request) throws CommonException;

	public CommonResponse getDocumentList(CommonRequest request) throws CommonException;

	public CommonResponse logout(CommonRequest request) throws CommonException;
	
	public CommonResponse eSignForNonPL(CommonRequest request) throws CommonException;

}
