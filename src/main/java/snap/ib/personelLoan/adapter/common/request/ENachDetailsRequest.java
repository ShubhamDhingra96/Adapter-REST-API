package snap.ib.personelLoan.adapter.common.request;

public class ENachDetailsRequest {

	private String lead_id;
	private String aadhaar_number;
	private String bank_name;
	private String ifsc_code;
	private String cas_id;
	private String start_date;
	private String end_date;
	private String emi_amount;
	private String phone_no;
	private String name;
	private String account_no;
	private String uniq_ref_id;
	private String loan_security_amount;
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
	public String getLoan_security_amount() {
		return loan_security_amount;
	}
	public void setLoan_security_amount(String loan_security_amount) {
		this.loan_security_amount = loan_security_amount;
	}
	public String getLead_id() {
		return lead_id;
	}
	public void setLead_id(String lead_id) {
		this.lead_id = lead_id;
	}
	public String getAadhaar_number() {
		return aadhaar_number;
	}
	public void setAadhaar_number(String aadhaar_number) {
		this.aadhaar_number = aadhaar_number;
	}
	public String getBank_name() {
		return bank_name;
	}
	public void setBank_name(String bank_name) {
		this.bank_name = bank_name;
	}
	public String getIfsc_code() {
		return ifsc_code;
	}
	public void setIfsc_code(String ifsc_code) {
		this.ifsc_code = ifsc_code;
	}
	public String getCas_id() {
		return cas_id;
	}
	public void setCas_id(String cas_id) {
		this.cas_id = cas_id;
	}
	public String getStart_date() {
		return start_date;
	}
	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}
	public String getEnd_date() {
		return end_date;
	}
	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}
	public String getEmi_amount() {
		return emi_amount;
	}
	public void setEmi_amount(String emi_amount) {
		this.emi_amount = emi_amount;
	}
	public String getPhone_no() {
		return phone_no;
	}
	public void setPhone_no(String phone_no) {
		this.phone_no = phone_no;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAccount_no() {
		return account_no;
	}
	public void setAccount_no(String account_no) {
		this.account_no = account_no;
	}
	public String getUniq_ref_id() {
		return uniq_ref_id;
	}
	public void setUniq_ref_id(String uniq_ref_id) {
		this.uniq_ref_id = uniq_ref_id;
	}

}
