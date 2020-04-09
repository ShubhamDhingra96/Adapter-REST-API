package snap.ib.personelLoan.adapter.common.response;

public class UpdateDisbursementDetailsCRMResponse {

	private boolean success;
	private String error_message;
	private String status;

	public boolean isSuccess() {
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
