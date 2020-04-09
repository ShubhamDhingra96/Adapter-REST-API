package snap.ib.personelLoan.adapter.common.request;

public class AcceptTopUpRequest {

	private String lead_id;
	private String revised_requested_loan_amount;
	private String revised_requested_loan_tenure;
	private String filler1;
	private String filler2;
	private String orig_lead_id;
	
	public String getLead_id() {
		return lead_id;
	}
	public void setLead_id(String lead_id) {
		this.lead_id = lead_id;
	}
	public String getRevised_requested_loan_amount() {
		return revised_requested_loan_amount;
	}
	public void setRevised_requested_loan_amount(String revised_requested_loan_amount) {
		this.revised_requested_loan_amount = revised_requested_loan_amount;
	}
	public String getRevised_requested_loan_tenure() {
		return revised_requested_loan_tenure;
	}
	public void setRevised_requested_loan_tenure(String revised_requested_loan_tenure) {
		this.revised_requested_loan_tenure = revised_requested_loan_tenure;
	}
	public String getFiller1() {
		return filler1;
	}
	public void setFiller1(String filler1) {
		this.filler1 = filler1;
	}
	public String getFiller2() {
		return filler2;
	}
	public void setFiller2(String filler2) {
		this.filler2 = filler2;
	}
	public String getOrig_lead_id() {
		return orig_lead_id;
	}
	public void setOrig_lead_id(String orig_lead_id) {
		this.orig_lead_id = orig_lead_id;
	}
}
