package snap.ib.personelLoan.adapter.common.request;

public class BranchRequest {

	private String lead_id;
	private String type;
	private String bank_name;
	private String bank_state;
	private String bank_city;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getBank_name() {
		return bank_name;
	}
	public void setBank_name(String bank_name) {
		this.bank_name = bank_name;
	}
	public String getBank_state() {
		return bank_state;
	}
	public void setBank_state(String bank_state) {
		this.bank_state = bank_state;
	}
	public String getBank_city() {
		return bank_city;
	}
	public void setBank_city(String bank_city) {
		this.bank_city = bank_city;
	}
}
