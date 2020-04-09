package snap.ib.personelLoan.adapter.common.request;

public class PerfiosUrlRedirectRequest {
	
	private String lead_id;
	private String emailId;
	private String loanAmount;
	private String loanDuration;
	private String loanType;
	private String institutionId;
	private String form26ASDOB;
	private String returnUrl;
	private String yearMonthFrom;
	private String yearMonthTo;
	private String acceptancePolicy;
	private String device_id;
	private String session_key;
	
	public String getDevice_id() {
		return device_id;
	}
	public void setDevice_id(String device_id) {
		this.device_id = device_id;
	}
	public String getSession_key() {
		return session_key;
	}
	
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getLoanAmount() {
		return loanAmount;
	}
	public void setLoanAmount(String loanAmount) {
		this.loanAmount = loanAmount;
	}
	public String getLoanDuration() {
		return loanDuration;
	}
	public void setLoanDuration(String loanDuration) {
		this.loanDuration = loanDuration;
	}
	public String getLoanType() {
		return loanType;
	}
	public void setLoanType(String loanType) {
		this.loanType = loanType;
	}
	public String getInstitutionId() {
		return institutionId;
	}
	public void setInstitutionId(String institutionId) {
		this.institutionId = institutionId;
	}
	public String getForm26ASDOB() {
		return form26ASDOB;
	}
	public void setForm26ASDOB(String form26asdob) {
		form26ASDOB = form26asdob;
	}
	public String getReturnUrl() {
		return returnUrl;
	}
	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}
	public String getLead_id() {
		return lead_id;
	}
	public void setLead_id(String lead_id) {
		this.lead_id = lead_id;
	}
	public String getYearMonthFrom() {
		return yearMonthFrom;
	}
	public void setYearMonthFrom(String yearMonthFrom) {
		this.yearMonthFrom = yearMonthFrom;
	}
	public String getYearMonthTo() {
		return yearMonthTo;
	}
	public void setYearMonthTo(String yearMonthTo) {
		this.yearMonthTo = yearMonthTo;
	}
	public String getAcceptancePolicy() {
		return acceptancePolicy;
	}
	public void setAcceptancePolicy(String acceptancePolicy) {
		this.acceptancePolicy = acceptancePolicy;
	}
}
