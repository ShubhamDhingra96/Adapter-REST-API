package snap.ib.personelLoan.adapter.common.response;

public class DocFromCRMResponse {

	private String lead_id;
	private String loan_doc;
	private String document;
	private String aadhaar_doc;
	private boolean success;

	public String getDocument() {
		return document;
	}
	public void setDocument(String document) {
		this.document = document;
	}
	public String getLead_id() {
		return lead_id;
	}
	public void setLead_id(String lead_id) {
		this.lead_id = lead_id;
	}
	public String getLoan_doc() {
		return loan_doc;
	}
	public void setLoan_doc(String loan_doc) {
		this.loan_doc = loan_doc;
	}
	public String getAadhaar_doc() {
		return aadhaar_doc;
	}
	public void setAadhaar_doc(String aadhaar_doc) {
		this.aadhaar_doc = aadhaar_doc;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
}
