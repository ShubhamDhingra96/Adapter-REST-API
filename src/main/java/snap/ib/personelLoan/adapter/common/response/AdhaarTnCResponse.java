package snap.ib.personelLoan.adapter.common.response;

public class AdhaarTnCResponse {
	
	
	
	private boolean success;
	private String text;
	private Object error_message;
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Object getError_message() {
		return error_message;
	}
	public void setError_message(Object error_message) {
		this.error_message = error_message;
	}
	@Override
	public String toString() {
		return "AdhaarTnCResponse [success=" + success + ", text=" + text + ", error_message=" + error_message + "]";
	}
	
	

}
