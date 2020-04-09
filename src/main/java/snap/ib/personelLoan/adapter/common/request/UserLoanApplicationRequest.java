package snap.ib.personelLoan.adapter.common.request;

public class UserLoanApplicationRequest {

	private String lead_id;
	private String occupation;
	private String sub_occupation;
	private int tenure_in_months;
	private String stage;
	private double loan_amount;
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
	public double getLoan_amount() {
		return loan_amount;
	}

	public void setLoan_amount(double loan_amount) {
		this.loan_amount = loan_amount;
	}

	public int getTenure_in_months() {
		return tenure_in_months;
	}

	public void setTenure_in_months(int tenure_in_months) {
		this.tenure_in_months = tenure_in_months;
	}

	public String getLead_id() {
		return lead_id;
	}

	public void setLead_id(String lead_id) {
		this.lead_id = lead_id;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public String getSub_occupation() {
		return sub_occupation;
	}

	public void setSub_occupation(String sub_occupation) {
		this.sub_occupation = sub_occupation;
	}

	public String getStage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}

}
