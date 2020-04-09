package snap.ib.personelLoan.adapter.common.request;

public class EmandateDropOutRequest {
	
	private String lead_id;
	private String bank_name;
	private String account_number;
	private String ifsc_code;
	private String frequency;
	private String emi_amount;
	private String debit_start_date;
	private String debit_end_date;
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
	public String getAccount_number() {
		return account_number;
	}
	public void setAccount_number(String account_number) {
		this.account_number = account_number;
	}
	public String getIfsc_code() {
		return ifsc_code;
	}
	public void setIfsc_code(String ifsc_code) {
		this.ifsc_code = ifsc_code;
	}
	public String getFrequency() {
		return frequency;
	}
	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}
	public String getEmi_amount() {
		return emi_amount;
	}
	public void setEmi_amount(String emi_amount) {
		this.emi_amount = emi_amount;
	}
	public String getDebit_start_date() {
		return debit_start_date;
	}
	public void setDebit_start_date(String debit_start_date) {
		this.debit_start_date = debit_start_date;
	}
	public String getDebit_end_date() {
		return debit_end_date;
	}
	public void setDebit_end_date(String debit_end_date) {
		this.debit_end_date = debit_end_date;
	}
	
}
