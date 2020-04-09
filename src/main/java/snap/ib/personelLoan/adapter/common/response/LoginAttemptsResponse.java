package snap.ib.personelLoan.adapter.common.response;

public class LoginAttemptsResponse {

	private boolean success;
	private String error_message;
	private String additional_fld1;
	private String additional_fld2;
	private String additional_fld3;
	private String record_status;
	
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
	public String getAdditional_fld1() {
		return additional_fld1;
	}
	public void setAdditional_fld1(String additional_fld1) {
		this.additional_fld1 = additional_fld1;
	}
	public String getAdditional_fld2() {
		return additional_fld2;
	}
	public void setAdditional_fld2(String additional_fld2) {
		this.additional_fld2 = additional_fld2;
	}
	public String getAdditional_fld3() {
		return additional_fld3;
	}
	public void setAdditional_fld3(String additional_fld3) {
		this.additional_fld3 = additional_fld3;
	}
	public String getRecord_status() {
		return record_status;
	}
	public void setRecord_status(String record_status) {
		this.record_status = record_status;
	}
}
