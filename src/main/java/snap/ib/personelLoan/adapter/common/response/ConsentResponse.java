package snap.ib.personelLoan.adapter.common.response;

public class ConsentResponse {

	private boolean success;
	private String filler;
	private String error_message;
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getFiller() {
		return filler;
	}
	public void setFiller(String filler) {
		this.filler = filler;
	}
	public String getError_message() {
		return error_message;
	}
	public void setError_message(String error_message) {
		this.error_message = error_message;
	}
	@Override
	public String toString() {
		return "ConsentResponse [success=" + success + ", filler=" + filler + ", error_message=" + error_message + "]";
	}
	
	
	
}
