package snap.ib.personelLoan.adapter.common.beans;


public class ClientReference {
	
	private String transactionId;
	private String losId;
	private String leadId;

	
	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getLosId() {
		return losId;
	}

	public void setLosId(String losId) {
		this.losId = losId;
	}

	public String getLeadId() {
		return leadId;
	}

	public void setLeadId(String leadId) {
		this.leadId = leadId;
	}

	@Override
	public String toString() {
		return "ClassPojo [transactionId = " + transactionId + ", losId = " + losId + ", leadId = " + leadId + "]";
	}
}