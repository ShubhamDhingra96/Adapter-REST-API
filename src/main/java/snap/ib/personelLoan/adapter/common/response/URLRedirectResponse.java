package snap.ib.personelLoan.adapter.common.response;

public class URLRedirectResponse {

	private boolean success;
	private String enc_Data;
	private String error_message;
	
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getEnc_Data() {
		return enc_Data;
	}
	public void setEnc_Data(String enc_Data) {
		this.enc_Data = enc_Data;
	}
	public String getError_message() {
		return error_message;
	}
	public void setError_message(String error_message) {
		this.error_message = error_message;
	}
	
}
