package snap.ib.personelLoan.adapter.common.request;

public class UpdateLoanReviseDetailsRequest {

	private String lead_id;
	private String revised_requested_loan_amount;
	private String revised_requested_loan_tenure;
	private String emi_cycle;
	private String emi_amount;
	private String is_insurance_opted; 
	private double insurance_premium;
	private String insurance_correlation_id;
	private String standing_offer_accepted;
	private String nominee_name;
	private String nominee_relationship;
	private String device_id;
	private String session_key;
	private String opp_id;
	private String con_id;
	private String loanExt_id;
	
	private boolean simulation_flag;
	private boolean esign_flag;
	private String device_ip;
	private String esign_timestamp;
	
	
	public boolean isSimulation_flag() {
		return simulation_flag;
	}
	public void setSimulation_flag(boolean simulation_flag) {
		this.simulation_flag = simulation_flag;
	}
	public boolean isEsign_flag() {
		return esign_flag;
	}
	public void setEsign_flag(boolean esign_flag) {
		this.esign_flag = esign_flag;
	}
	public String getDevice_ip() {
		return device_ip;
	}
	public void setDevice_ip(String device_ip) {
		this.device_ip = device_ip;
	}
	public String getEsign_timestamp() {
		return esign_timestamp;
	}
	public void setEsign_timestamp(String esign_timestamp) {
		this.esign_timestamp = esign_timestamp;
	}
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
	public String getNominee_name() {
		return nominee_name;
	}
	public void setNominee_name(String nominee_name) {
		this.nominee_name = nominee_name;
	}
	public String getNominee_relationship() {
		return nominee_relationship;
	}
	public void setNominee_relationship(String nominee_relationship) {
		this.nominee_relationship = nominee_relationship;
	}
	public String getIs_insurance_opted() {
		return is_insurance_opted;
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
	public String getLead_id() {
		return lead_id;
	}

	public void setLead_id(String lead_id) {
		this.lead_id = lead_id;
	}

	public String getRevised_requested_loan_amount() {
		return revised_requested_loan_amount;
	}

	public void setRevised_requested_loan_amount(
			String revised_requested_loan_amount) {
		this.revised_requested_loan_amount = revised_requested_loan_amount;
	}

	public String getRevised_requested_loan_tenure() {
		return revised_requested_loan_tenure;
	}

	public void setRevised_requested_loan_tenure(
			String revised_requested_loan_tenure) {
		this.revised_requested_loan_tenure = revised_requested_loan_tenure;
	}

	public String getEmi_cycle() {
		return emi_cycle;
	}

	public void setEmi_cycle(String emi_cycle) {
		this.emi_cycle = emi_cycle;
	}

	public String getEmi_amount() {
		return emi_amount;
	}

	public void setEmi_amount(String emi_amount) {
		this.emi_amount = emi_amount;
	}
	public String isIs_insurance_opted() {
		return is_insurance_opted;
	}
	public void setIs_insurance_opted(String is_insurance_opted) {
		this.is_insurance_opted = is_insurance_opted;
	}

	public double getInsurance_premium() {
		return insurance_premium;
	}

	public void setInsurance_premium(double insurance_premium) {
		this.insurance_premium = insurance_premium;
	}

	public String getInsurance_correlation_id() {
		return insurance_correlation_id;
	}

	public void setInsurance_correlation_id(String insurance_correlation_id) {
		this.insurance_correlation_id = insurance_correlation_id;
	}

	public String getStanding_offer_accepted() {
		return standing_offer_accepted;
	}

	public void setStanding_offer_accepted(String standing_offer_accepted) {
		this.standing_offer_accepted = standing_offer_accepted;
	}

	public void setSession_key(String session_key) {
		this.session_key = session_key;
	}

}
