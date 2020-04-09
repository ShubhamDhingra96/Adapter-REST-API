package snap.ib.personelLoan.adapter.common.request;

public class AndhraBankResDataRequest {

	private String encData;
	private String opp_id;
	private String loanExt_id;
	private String con_id;

	public String getEncData() {
		return encData;
	}

	public void setEncData(String encData) {
		this.encData = encData;
	}

	public String getOpp_id() {
		return opp_id;
	}

	public void setOpp_id(String opp_id) {
		this.opp_id = opp_id;
	}

	public String getLoanExt_id() {
		return loanExt_id;
	}

	public void setLoanExt_id(String loanExt_id) {
		this.loanExt_id = loanExt_id;
	}

	public String getCon_id() {
		return con_id;
	}

	public void setCon_id(String con_id) {
		this.con_id = con_id;
	}

	@Override
	public String toString() {
		return "AndhraBankResDataRequest [encData=" + encData + ", opp_id=" + opp_id + ", loanExt_id=" + loanExt_id
				+ ", con_id=" + con_id + "]";
	}

	
	
	
	
}
