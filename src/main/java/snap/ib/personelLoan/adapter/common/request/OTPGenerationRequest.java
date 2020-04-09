package snap.ib.personelLoan.adapter.common.request;

public class OTPGenerationRequest {
	
	private String aadhaar_no;
	private String lead_id;
	private String consent_flag;
	private String consent_data;
	private String source_ip;
	private String  req_from;
	private String  token;
	private String terminal_id;
	private String  filler1;
	private String  filler2;
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
	public String getAadhaar_no() {
		return aadhaar_no;
	}

	public void setAadhaar_no(String aadhaar_no) {
		this.aadhaar_no = aadhaar_no;
	}

	public String getLead_id() {
		return lead_id;
	}

	public void setLead_id(String lead_id) {
		this.lead_id = lead_id;
	}
	public String getConsent_flag() {
		return consent_flag;
	}
	public void setConsent_flag(String consent_flag) {
		this.consent_flag = consent_flag;
	}
	public String getConsent_data() {
		return consent_data;
	}
	public void setConsent_data(String consent_data) {
		this.consent_data = consent_data;
	}
	public String getSource_ip() {
		return source_ip;
	}
	public void setSource_ip(String source_ip) {
		this.source_ip = source_ip;
	}
	public String getReq_from() {
		return req_from;
	}
	public void setReq_from(String req_from) {
		this.req_from = req_from;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getTerminal_id() {
		return terminal_id;
	}
	public void setTerminal_id(String terminal_id) {
		this.terminal_id = terminal_id;
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
	public void setSession_key(String session_key) {
		this.session_key = session_key;
	}
	@Override
	public String toString() {
		return "OTPGenerationRequest [aadhaar_no=" + aadhaar_no + ", lead_id=" + lead_id + ", consent_flag="
				+ consent_flag + ", consent_data=" + consent_data + ", source_ip=" + source_ip + ", req_from="
				+ req_from + ", token=" + token + ", terminal_id=" + terminal_id + ", filler1=" + filler1 + ", filler2="
				+ filler2 + ", device_id=" + device_id + ", session_key=" + session_key + "]";
	}

}
