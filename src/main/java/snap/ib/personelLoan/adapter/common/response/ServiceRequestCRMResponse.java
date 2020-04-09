package snap.ib.personelLoan.adapter.common.response;

public class ServiceRequestCRMResponse {
	
	private boolean success;
	private String error_message;
	private String case_number;
	private String case_creation_date;
	private String status;
	private String currentTime; 

	public String getCurrentTime() {
		return currentTime;
	}
	public void setCurrentTime(String currentTime) {
		this.currentTime = currentTime;
	}
	public boolean getSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getError_message() {
		return error_message;
	}
	public void setError_message(String error_message) {
		this.error_message = error_message;
	}
	public String getCase_number() {
		return case_number;
	}
	public void setCase_number(String case_number) {
		this.case_number = case_number;
	}
	public String getCase_creation_date() {
		return case_creation_date;
	}
	public void setCase_creation_date(String case_creation_date) {
		this.case_creation_date = case_creation_date;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
