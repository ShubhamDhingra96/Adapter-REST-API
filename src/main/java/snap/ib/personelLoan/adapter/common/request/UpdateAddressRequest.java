package snap.ib.personelLoan.adapter.common.request;

public class UpdateAddressRequest {

	private String lead_id;
	private String crm_id;
	private String current_address_line;
	private String current_city;
	private String current_state;
	private String current_pincode;
	private String email_id;
	private String filler1;
	private String filler2;
	public String getLead_id() {
		return lead_id;
	}
	public void setLead_id(String lead_id) {
		this.lead_id = lead_id;
	}
	public String getCrm_id() {
		return crm_id;
	}
	public void setCrm_id(String crm_id) {
		this.crm_id = crm_id;
	}
	public String getCurrent_address_line() {
		return current_address_line;
	}
	public void setCurrent_address_line(String current_address_line) {
		this.current_address_line = current_address_line;
	}
	public String getCurrent_city() {
		return current_city;
	}
	public void setCurrent_city(String current_city) {
		this.current_city = current_city;
	}
	public String getCurrent_state() {
		return current_state;
	}
	public void setCurrent_state(String current_state) {
		this.current_state = current_state;
	}
	public String getCurrent_pincode() {
		return current_pincode;
	}
	public void setCurrent_pincode(String current_pincode) {
		this.current_pincode = current_pincode;
	}
	public String getEmail_id() {
		return email_id;
	}
	public void setEmail_id(String email_id) {
		this.email_id = email_id;
	}
	public String getFiller1() {
		return filler1;
	}
	public void setFiller1(String filler1) {
		this.filler1 = filler1;
	}
	public String getFiller2() {
		return filler2;
	}
	public void setFiller2(String filler2) {
		this.filler2 = filler2;
	}
	@Override
	public String toString() {
		return "UpdateAddressRequest [lead_id=" + lead_id + ", crm_id=" + crm_id + ", current_address_line="
				+ current_address_line + ", current_city=" + current_city + ", current_state=" + current_state
				+ ", current_pincode=" + current_pincode + ", email_id=" + email_id + ", filler1=" + filler1
				+ ", filler2=" + filler2 + "]";
	}

}
