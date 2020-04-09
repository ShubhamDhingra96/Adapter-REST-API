package snap.ib.personelLoan.adapter.common.request;

public class EsignOTPGenRequest {

	private String lead_id;
	private String aadhaar_number;
	private String device_id;
	private String session_key;
	private String is_consent;
	private String consent_text;
	
	public String getIs_consent() {
		return is_consent;
	}
	public void setIs_consent(String finalIs_consent) {
		is_consent = finalIs_consent;
	}
	public String getConsent_text() {
		return consent_text;
	}
	public void setConsent_text(String finalConsent_text) {
		consent_text = finalConsent_text;
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

}
