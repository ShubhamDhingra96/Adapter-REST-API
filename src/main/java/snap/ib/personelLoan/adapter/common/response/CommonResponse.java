package snap.ib.personelLoan.adapter.common.response;

public class CommonResponse {

	private String outputString;
	private String chkSum;
	private String error_message;
	private boolean success;
	private String responseToken;
	
	
	public String getResponseToken() {
		return responseToken;
	}

	public void setResponseToken(String responseToken) {
		this.responseToken = responseToken;
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

	public String getOutputString() {
		return outputString;
	}

	public void setOutputString(String outputString) {
		this.outputString = outputString;
	}

	public String getChkSum() {
		return chkSum;
	}

	public void setChkSum(String chkSum) {
		this.chkSum = chkSum;
	}

	@Override
	public String toString() {
		return "[outputString=" + outputString + ", chkSum=" + chkSum + ", error_message="
				+ error_message + ", success=" + success + "]";
	}
}
