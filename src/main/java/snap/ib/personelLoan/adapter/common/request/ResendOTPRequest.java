package snap.ib.personelLoan.adapter.common.request;

public class ResendOTPRequest {

	private String mobile_number;
	private String device_number;
	private String OTP;
	private String attempt_number;
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
	public String getMobile_number() {
		return mobile_number;
	}
	public void setMobile_number(String mobile_number) {
		this.mobile_number = mobile_number;
	}
	public String getDevice_number() {
		return device_number;
	}
	public void setDevice_number(String device_number) {
		this.device_number = device_number;
	}
	public String getOTP() {
		return OTP;
	}
	public void setOTP(String oTP) {
		OTP = oTP;
	}
	public String getAttempt_number() {
		return attempt_number;
	}
	public void setAttempt_number(String attempt_number) {
		this.attempt_number = attempt_number;
	}
	

}
