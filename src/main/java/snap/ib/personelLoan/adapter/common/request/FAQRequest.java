package snap.ib.personelLoan.adapter.common.request;

public class FAQRequest {
    
	private String lead_id;
	private String device_id;
	private String session_key;
	private String lang;
	
	public String getLang() {
		return lang;
	}
	public void setLang(String lang) {
		this.lang = lang;
	}
	public void setSession_key(String session_key) {
		this.session_key = session_key;
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
}
