package snap.ib.personelLoan.adapter.common.request;

public class URLRedirectRequest {

	private String crn;
	private String amt;
	private String rtu;
	private String customer_name;
	private String bank_name;
	private String emi_amount;
	private String ifsc_code;
	private String aadhar;
	private String max_amount;
	private String frequency;
	private String account_number;
	private String schedule_date;
	private String expiry_date;
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
	public String getCrn() {
		return crn;
	}
	public void setCrn(String crn) {
		this.crn = crn;
	}
	public String getAmt() {
		return amt;
	}
	public void setAmt(String amt) {
		this.amt = amt;
	}
	public String getRtu() {
		return rtu;
	}
	public void setRtu(String rtu) {
		this.rtu = rtu;
	}
	public String getCustomer_name() {
		return customer_name;
	}
	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}
	public String getBank_name() {
		return bank_name;
	}
	public void setBank_name(String bank_name) {
		this.bank_name = bank_name;
	}
	public String getEmi_amount() {
		return emi_amount;
	}
	public void setEmi_amount(String emi_amount) {
		this.emi_amount = emi_amount;
	}
	public String getIfsc_code() {
		return ifsc_code;
	}
	public void setIfsc_code(String ifsc_code) {
		this.ifsc_code = ifsc_code;
	}
	public String getAadhar() {
		return aadhar;
	}
	public void setAadhar(String aadhar) {
		this.aadhar = aadhar;
	}
	public String getMax_amount() {
		return max_amount;
	}
	public void setMax_amount(String max_amount) {
		this.max_amount = max_amount;
	}
	public String getFrequency() {
		return frequency;
	}
	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}
	public String getAccount_number() {
		return account_number;
	}
	public void setAccount_number(String account_number) {
		this.account_number = account_number;
	}
	public String getSchedule_date() {
		return schedule_date;
	}
	public void setSchedule_date(String schedule_date) {
		this.schedule_date = schedule_date;
	}
	public String getExpiry_date() {
		return expiry_date;
	}
	public void setExpiry_date(String expiry_date) {
		this.expiry_date = expiry_date;
	}
	@Override
	public String toString() {
		return "URLRedirectRequest [crn=" + crn + ", amt=" + amt + ", rtu=" + rtu + ", customer_name=" + customer_name
				+ ", bank_name=" + bank_name + ", emi_amount=" + emi_amount + ", ifsc_code=" + ifsc_code + ", aadhar="
				+ aadhar + ", max_amount=" + max_amount + ", frequency=" + frequency + ", account_number="
				+ account_number + ", schedule_date=" + schedule_date + ", expiry_date=" + expiry_date + ", device_id="
				+ device_id + ", session_key=" + session_key + "]";
	}
	
	
	
}
