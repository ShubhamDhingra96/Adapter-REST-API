package snap.ib.personelLoan.adapter.common.request;

public class ENachValidationRequest {

	private String lead_id;
	private String otp;
	private String aadhaar_no;
	private String XML;
	private String XML1;
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
	public String getXML1() {
		return XML1;
	}
	public void setXML1(String xML1) {
		XML1 = xML1;
	}
	public String getLead_id() {
		return lead_id;
	}
	public void setLead_id(String lead_id) {
		this.lead_id = lead_id;
	}
	public String getOtp() {
		return otp;
	}
	public void setOtp(String otp) {
		this.otp = otp;
	}
	public String getAadhaar_no() {
		return aadhaar_no;
	}
	public void setAadhaar_no(String aadhaar_no) {
		this.aadhaar_no = aadhaar_no;
	}
	public String getXML() {
		return XML;
	}
	public void setXML(String xML) {
		XML = xML;
	}
}
