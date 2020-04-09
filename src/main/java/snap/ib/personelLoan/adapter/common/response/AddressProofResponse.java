package snap.ib.personelLoan.adapter.common.response;

public class AddressProofResponse {

	private boolean success;
	private String idProofList;
	private Object error_message;
	
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getIdProofList() {
		return idProofList;
	}
	public void setIdProofList(String idProofList) {
		this.idProofList = idProofList;
	}
	public Object getError_message() {
		return error_message;
	}
	public void setError_message(Object error_message) {
		this.error_message = error_message;
	}
	
}
