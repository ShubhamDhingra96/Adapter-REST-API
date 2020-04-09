package snap.ib.personelLoan.adapter.common.response;

public class DemographicInfoResponse {

	private boolean success;
	private String error_message;
	private String crm_id;
	private String status;
	private String SoftApprovalDecision;
	private String HardApprovalDecision;
	private String PerfiosRequired;
	private String MaxEligibleLoanAmount;
	private String LoanAmountApplied;
	private String RateofInterest;
	private String Tenor;
	private String ProcessingFees;
	private String EMI;
	private String MaxEligibleEMI;
	private boolean PANRequired;
	private String product_type;
	private boolean is_bureau_decline;
	private String pincode_only_stp;
	
	public String getPincode_only_stp() {
		return pincode_only_stp;
	}

	public void setPincode_only_stp(String pincode_only_stp) {
		this.pincode_only_stp = pincode_only_stp;
	}

	public boolean isIs_bureau_decline() {
		return is_bureau_decline;
	}

	public void setIs_bureau_decline(boolean is_bureau_decline) {
		this.is_bureau_decline = is_bureau_decline;
	}

	public String getProduct_type() {
		return product_type;
	}

	public void setProduct_type(String product_type) {
		this.product_type = product_type;
	}
	public boolean isPANRequired() {
		return PANRequired;
	}

	public void setPANRequired(boolean pANRequired) {
		PANRequired = pANRequired;
	}

	public String getMaxEligibleEMI() {
		return MaxEligibleEMI;
	}

	public void setMaxEligibleEMI(String maxEligibleEMI) {
		MaxEligibleEMI = maxEligibleEMI;
	}

	public String getError_message() {
		return error_message;
	}

	public void setError_message(String error_message) {
		this.error_message = error_message;
	}

	public String getSoftApprovalDecision() {
		return SoftApprovalDecision;
	}

	public void setSoftApprovalDecision(String softApprovalDecision) {
		SoftApprovalDecision = softApprovalDecision;
	}

	public String getHardApprovalDecision() {
		return HardApprovalDecision;
	}

	public void setHardApprovalDecision(String hardApprovalDecision) {
		HardApprovalDecision = hardApprovalDecision;
	}

	public String getPerfiosRequired() {
		return PerfiosRequired;
	}

	public void setPerfiosRequired(String perfiosRequired) {
		PerfiosRequired = perfiosRequired;
	}

	public String getMaxEligibleLoanAmount() {
		return MaxEligibleLoanAmount;
	}

	public void setMaxEligibleLoanAmount(String maxEligibleLoanAmount) {
		MaxEligibleLoanAmount = maxEligibleLoanAmount;
	}

	public String getLoanAmountApplied() {
		return LoanAmountApplied;
	}

	public void setLoanAmountApplied(String loanAmountApplied) {
		LoanAmountApplied = loanAmountApplied;
	}

	public String getRateofInterest() {
		return RateofInterest;
	}

	public void setRateofInterest(String rateofInterest) {
		RateofInterest = rateofInterest;
	}

	public String getTenor() {
		return Tenor;
	}

	public void setTenor(String tenor) {
		Tenor = tenor;
	}

	public String getProcessingFees() {
		return ProcessingFees;
	}

	public void setProcessingFees(String processingFees) {
		ProcessingFees = processingFees;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getCrm_id() {
		return crm_id;
	}

	public void setCrm_id(String crm_id) {
		this.crm_id = crm_id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getEMI() {
		return EMI;
	}

	public void setEMI(String eMI) {
		EMI = eMI;
	}

}
