package snap.ib.personelLoan.adapter.common.request;

public class LoanInsuranceRequest {

	private String policy_type;
	private String occupation;
	private String  insured_dob;
	private String insured_name;
	private double sum_insured;
	private String loan_tenure;
	private String loan_sanction_amount;
	private String loan_disbursal_amount;
	private String plan_code;
	private boolean availability_80d;
	private String party_state_name;
	private String correlation_id;
	
	public String getPolicy_type() {
		return policy_type;
	}
	public void setPolicy_type(String policy_type) {
		this.policy_type = policy_type;
	}
	public String getOccupation() {
		return occupation;
	}
	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}
	public String getInsured_dob() {
		return insured_dob;
	}
	public void setInsured_dob(String insured_dob) {
		this.insured_dob = insured_dob;
	}
	public String getInsured_name() {
		return insured_name;
	}
	public void setInsured_name(String insured_name) {
		this.insured_name = insured_name;
	}
	public double getSum_insured() {
		return sum_insured;
	}
	public void setSum_insured(double sum_insured) {
		this.sum_insured = sum_insured;
	}
	public String getLoan_tenure() {
		return loan_tenure;
	}
	public void setLoan_tenure(String loan_tenure) {
		this.loan_tenure = loan_tenure;
	}
	public String getLoan_sanction_amount() {
		return loan_sanction_amount;
	}
	public void setLoan_sanction_amount(String loan_sanction_amount) {
		this.loan_sanction_amount = loan_sanction_amount;
	}
	public String getLoan_disbursal_amount() {
		return loan_disbursal_amount;
	}
	public void setLoan_disbursal_amount(String loan_disbursal_amount) {
		this.loan_disbursal_amount = loan_disbursal_amount;
	}
	public String getPlan_code() {
		return plan_code;
	}
	public void setPlan_code(String plan_code) {
		this.plan_code = plan_code;
	}
	public boolean isAvailability_80d() {
		return availability_80d;
	}
	public void setAvailability_80d(boolean availability_80d) {
		this.availability_80d = availability_80d;
	}
	public String getParty_state_name() {
		return party_state_name;
	}
	public void setParty_state_name(String party_state_name) {
		this.party_state_name = party_state_name;
	}
	public String getCorrelation_id() {
		return correlation_id;
	}
	public void setCorrelation_id(String correlation_id) {
		this.correlation_id = correlation_id;
	}
	@Override
	public String toString() {
		return "LoanInsuranceRequest [policy_type=" + policy_type + ", occupation=" + occupation + ", insured_dob="
				+ insured_dob + ", insured_name=" + insured_name + ", sum_insured=" + sum_insured + ", loan_tenure="
				+ loan_tenure + ", loan_sanction_amount=" + loan_sanction_amount + ", loan_disbursal_amount="
				+ loan_disbursal_amount + ", plan_code=" + plan_code + ", availability_80d=" + availability_80d
				+ ", party_state_name=" + party_state_name + ", correlation_id=" + correlation_id + "]";
	}

}
