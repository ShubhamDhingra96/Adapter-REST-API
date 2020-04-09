package snap.ib.personelLoan.adapter.common.request;

public class MandateDropOffRequest {

	private String fileName;
	private String revised_req_loan_amt;
	private String device_id;
	private String session_key;
	
	public String getDevice_id() {
		return device_id;
	}
	public void setDevice_id(String device_id) {
		this.device_id = device_id;
	}
	public String getSession_key() {
		return session_key;
	}
	public void setSession_key(String session_key) {
		this.session_key = session_key;
	}
	public String getFileName() {
		return fileName;
	}
	public String getRevised_req_loan_amt() {
		return revised_req_loan_amt;
	}
	public void setRevised_req_loan_amt(String revised_req_loan_amt) {
		this.revised_req_loan_amt = revised_req_loan_amt;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}
