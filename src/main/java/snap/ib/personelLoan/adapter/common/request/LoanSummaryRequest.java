package snap.ib.personelLoan.adapter.common.request;

public class LoanSummaryRequest {

	private String crm_id;
	private String transaction_id;
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
	public String getCrm_id() {
		return crm_id;
	}

	public void setCrm_id(String crm_id) {
		this.crm_id = crm_id;
	}

	public String getTransaction_id() {
		return transaction_id;
	}

	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}

}
