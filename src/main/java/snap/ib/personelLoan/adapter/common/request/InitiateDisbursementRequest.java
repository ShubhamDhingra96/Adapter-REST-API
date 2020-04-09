package snap.ib.personelLoan.adapter.common.request;

public class InitiateDisbursementRequest {

	private String lead_id;
	private String bank_name;
	private String acct_num;
	private String ifsc_code;
	private String disbursement_amt;
	private String cust_name;
	private String uniq_ref_id;
	private String cas_id;
	private String disbursement_percentage;
	private String email_id;
	private String mobile_number;
	private String device_id;
	private String session_key;
	private String opp_id;
	private String con_id;
	private String loanExt_id;
	
	
	public String getOpp_id() {
		return opp_id;
	}
	public void setOpp_id(String opp_id) {
		this.opp_id = opp_id;
	}
	public String getCon_id() {
		return con_id;
	}
	public void setCon_id(String con_id) {
		this.con_id = con_id;
	}
	public String getLoanExt_id() {
		return loanExt_id;
	}
	public void setLoanExt_id(String loanExt_id) {
		this.loanExt_id = loanExt_id;
	}
	public String getDevice_id() {
		return device_id;
	}
	public void setDevice_id(String device_id) {
		this.device_id = device_id;
	}
	public String getSession_key() {
		return session_key;
	}
	public String getMobile_number() {
		return mobile_number;
	}
	public void setMobile_number(String mobile_number) {
		this.mobile_number = mobile_number;
	}
	public String getLead_id() {
		return lead_id;
	}
	public void setLead_id(String lead_id) {
		this.lead_id = lead_id;
	}
	public String getBank_name() {
		return bank_name;
	}
	public void setBank_name(String bank_name) {
		this.bank_name = bank_name;
	}
	public String getAcct_num() {
		return acct_num;
	}
	public void setAcct_num(String acct_num) {
		this.acct_num = acct_num;
	}
	public String getIfsc_code() {
		return ifsc_code;
	}
	public void setIfsc_code(String ifsc_code) {
		this.ifsc_code = ifsc_code;
	}
	public String getDisbursement_amt() {
		return disbursement_amt;
	}
	public void setDisbursement_amt(String disbursement_amt) {
		this.disbursement_amt = disbursement_amt;
	}
	public String getCust_name() {
		return cust_name;
	}
	public void setCust_name(String cust_name) {
		this.cust_name = cust_name;
	}
	public String getUniq_ref_id() {
		return uniq_ref_id;
	}
	public void setUniq_ref_id(String uniq_ref_id) {
		this.uniq_ref_id = uniq_ref_id;
	}
	
	public String getCas_id() {
		return cas_id;
	}
	public void setCas_id(String cas_id) {
		this.cas_id = cas_id;
	}
	public String getDisbursement_percentage() {
		return disbursement_percentage;
	}
	public void setDisbursement_percentage(String disbursement_percentage) {
		this.disbursement_percentage = disbursement_percentage;
	}
	public String getEmail_id() {
		return email_id;
	}
	public void setEmail_id(String email_id) {
		this.email_id = email_id;
	}
	
}
