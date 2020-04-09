package snap.ib.personelLoan.adapter.common.request;

public class SaveComplaintCRMRequest {
	
	private String crm_id;
	private String reason_type;
	private String subject;
	private String body;
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
	public String getCrm_id() {
		return crm_id;
	}
	public void setCrm_id(String crm_id) {
		this.crm_id = crm_id;
	}
	public String getReason_type() {
		return reason_type;
	}
	public void setReason_type(String reason_type) {
		this.reason_type = reason_type;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	
}
