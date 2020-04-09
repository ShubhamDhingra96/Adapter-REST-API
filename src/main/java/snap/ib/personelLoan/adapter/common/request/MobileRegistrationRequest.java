package snap.ib.personelLoan.adapter.common.request;

public class MobileRegistrationRequest {

	private String mobile_number;
	private String device_number;
	private String mpin;
	private String device_id;
	private String session_key;
	private String otp;
	
	private String lead_id ;
	private String opp_id ;
	private String con_id ;
	private String loanExt_id ;
	
	private String device_ID;

	
	public String getDevice_ID() {
		return device_ID;
	}
	public void setDevice_ID(String device_ID) {
		this.device_ID = device_ID;
	}
	public String getLead_id() {
		return lead_id;
	}
	public void setLead_id(String lead_id) {
		this.lead_id = lead_id;
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
	public String getOtp() {
		return otp;
	}
	public void setOtp(String otp) {
		this.otp = otp;
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
	public String getDevice_number() {
		return device_number;
	}

	public void setDevice_number(String device_number) {
		this.device_number = device_number;
	}

	public String getMobile_number() {
		return mobile_number;
	}

	public void setMobile_number(String mobile_number) {
		this.mobile_number = mobile_number;
	}

	public String getMpin() {
		return mpin;
	}

	public void setMpin(String mpin) {
		this.mpin = mpin;
	}
}
