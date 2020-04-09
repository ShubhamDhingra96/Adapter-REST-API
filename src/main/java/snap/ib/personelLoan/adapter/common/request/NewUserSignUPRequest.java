package snap.ib.personelLoan.adapter.common.request;

public class NewUserSignUPRequest {

	private String mobile_number;
	private String Device_Number;
	private String OTP;
	private String device_id;
	private String session_key;
	
	// 4 id's are added
	private String lead_id;
	private String opp_id;
	private String con_id;
	private String loanExt_id;
	
	private String acc_id;
	private String lead_record_id;
	private String fail_status;
	 
	
	public String getFail_status() {
		return fail_status;
	}
	public void setFail_status(String fail_status) {
		this.fail_status = fail_status;
	}
	public String getAcc_id() {
		return acc_id;
	}
	public void setAcc_id(String acc_id) {
		this.acc_id = acc_id;
	}
	public String getLead_record_id() {
		return lead_record_id;
	}
	public void setLead_record_id(String lead_record_id) {
		this.lead_record_id = lead_record_id;
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
	public String getDevice_id() {
		return device_id;
	}
	public void setDevice_id(String device_id) {
		this.device_id = device_id;
	}
	public String getSession_key() {
		return session_key;
	}
	public void setSession_key(String session_key) {
		this.session_key = session_key;
	}
	public String getMobile_number() {
		return mobile_number;
	}
	public void setMobile_number(String mobile_number) {
		this.mobile_number = mobile_number;
	}
	public String getDevice_Number() {
		return Device_Number;
	}
	public void setDevice_Number(String deviceNumber) {
		Device_Number = deviceNumber;
	}
	public String getOTP() {
		return OTP;
	}
	public void setOTP(String oTP) {
		OTP = oTP;
	}
}
