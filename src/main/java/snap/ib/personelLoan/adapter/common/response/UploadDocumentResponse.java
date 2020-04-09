package snap.ib.personelLoan.adapter.common.response;

public class UploadDocumentResponse {
	

	private String success;
	private String error_message;
	private String status;
	private String poa_type;
	
	public String getPoa_type() {
		return poa_type;
	}
	public void setPoa_type(String poa_type) {
		this.poa_type = poa_type;
	}
	public String getSuccess() {
		return success;
	}
	public void setSuccess(String success) {
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
