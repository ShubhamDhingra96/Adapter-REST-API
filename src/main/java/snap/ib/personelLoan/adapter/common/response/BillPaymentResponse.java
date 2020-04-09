package snap.ib.personelLoan.adapter.common.response;

public class BillPaymentResponse {

	private boolean success;
	private String txnId;
	private String token;
	private String error_message;
	
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

	public String getTxnId() {
		return txnId;
	}

	public void setTxnId(String txnId) {
		this.txnId = txnId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	@Override
	public String toString() {
		return "BillPaymentResponse [success=" + success + ", txnId=" + txnId + ", token=" + token + ", error_message="
				+ error_message + ", isSuccess()=" + isSuccess() + ", getError_message()=" + getError_message()
				+ ", getTxnId()=" + getTxnId() + ", getToken()=" + getToken() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode()+"]";
	}
}
