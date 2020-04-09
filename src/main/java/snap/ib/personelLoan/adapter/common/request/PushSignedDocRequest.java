package snap.ib.personelLoan.adapter.common.request;

public class PushSignedDocRequest {

	private String  lead_id;
	private String loan_doc;
	private String aadhaar_doc;
	private String device_id;
	private String session_key;
	
	public String getLead_id() {
		return lead_id;
	}
	public void setLead_id(String lead_id) {
		this.lead_id = lead_id;
	}
	public String getLoan_doc() {
		return loan_doc;
	}
	public void setLoan_doc(String loan_doc) {
		this.loan_doc = loan_doc;
	}
	public String getAadhaar_doc() {
		return aadhaar_doc;
	}
	public void setAadhaar_doc(String aadhaar_doc) {
		this.aadhaar_doc = aadhaar_doc;
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
	public void setSession_key(String session_key) {
		this.session_key = session_key;
	}
	
}
