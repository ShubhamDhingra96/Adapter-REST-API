package snap.ib.personelLoan.adapter.common.request;

public class LoanOutstandingRequest {
	
	private String transaction_id;
	private String cas_id;
	public String getTransaction_id() {
		return transaction_id;
	}
	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}
	public String getCas_id() {
		return cas_id;
	}
	public void setCas_id(String cas_id) {
		this.cas_id = cas_id;
	}
	
}
