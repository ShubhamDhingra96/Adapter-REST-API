package snap.ib.personelLoan.adapter.common.response;

public class UpdateMpinResponse {

	private boolean success;
	private String lead_id;
	private String error_message;
	private String crm_id;
	private String cas_id;
	
	public boolean getSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getLead_id() {
		return lead_id;
	}
	public void setLead_id(String lead_id) {
		this.lead_id = lead_id;
	}
	public String getError_message() {
		return error_message;
	}
	public void setError_message(String error_message) {
		this.error_message = error_message;
	}
	public String getCrm_id() {
		return crm_id;
	}
	public void setCrm_id(String crm_id) {
		this.crm_id = crm_id;
	}
	public String getCas_id() {
		return cas_id;
	}
	public void setCas_id(String cas_id) {
		this.cas_id = cas_id;
	}
	
	
}
