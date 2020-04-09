package snap.ib.personelLoan.adapter.common.request;

public class YesBankResponse {

	private String token;
	private String error_message;
	private boolean success;

	@Override
	public String toString() {
		return "YesBankResponse [token=" + token + ", error_message=" + error_message + ", success=" + success + "]";
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getError_message() {
		return error_message;
	}
	public void setError_message(String error_message) {
		this.error_message = error_message;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
}
