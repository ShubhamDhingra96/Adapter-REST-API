package snap.ib.personelLoan.adapter.common.response;

public class InitiateDassCall1Response {
	
	private boolean success;
	private String status;
	private String error_message;
	
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getError_message() {
		return error_message;
	}
	public void setError_message(String error_message) {
		this.error_message = error_message;
	}
	

}
