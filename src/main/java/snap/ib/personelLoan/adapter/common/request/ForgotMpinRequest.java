package snap.ib.personelLoan.adapter.common.request;

public class ForgotMpinRequest {

	private String mobile_number;
	private String device_number;
	private String OTP;
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

	public String getDevicenumber() {
		return device_number;
	}

	public void setDevicenumber(String devicenumber) {
		this.device_number = devicenumber;
	}

	public String getOTP() {
		return OTP;
	}

	public void setOTP(String otp) {
		this.OTP = otp;
	}


}
