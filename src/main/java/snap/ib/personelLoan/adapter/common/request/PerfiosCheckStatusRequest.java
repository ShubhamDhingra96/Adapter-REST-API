package snap.ib.personelLoan.adapter.common.request;

public class PerfiosCheckStatusRequest {

	private String txn_id;
	private String lead_id;
	private String device_id;
	private String session_key;
	private String opp_id;
	private String con_id;
	private String loanExt_id;
	private String acc_id;
	
	
	public String getOpp_id() {
		return opp_id;
	}
	public void setOpp_id(String opp_id) {
		this.opp_id = opp_id;
	}
	public String getCon_id() {
		return con_id;
	}
	public void setCon_id(String con_id) {
		this.con_id = con_id;
	}
	public String getLoanExt_id() {
		return loanExt_id;
	}
	public void setLoanExt_id(String loanExt_id) {
		this.loanExt_id = loanExt_id;
	}
	public String getAcc_id() {
		return acc_id;
	}
	public void setAcc_id(String acc_id) {
		this.acc_id = acc_id;
	}
	public String getDevice_id() {
		return device_id;
	}
	public void setDevice_id(String device_id) {
		this.device_id = device_id;
	}
	public String getSession_key() {
		return session_key;
	}
	public String getTxn_id() {
		return txn_id;
	}

	public void setTxn_id(String txn_id) {
		this.txn_id = txn_id;
	}

	public String getLead_id() {
		return lead_id;
	}

	public void setLead_id(String lead_id) {
		this.lead_id = lead_id;
	}

}
