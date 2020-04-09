package snap.ib.personelLoan.adapter.common.request;

public class MPINValidationRequest {

	private String mobile_number;
	private String device_id;
	private String session_key;
	private String mpin;
	private String touchId;
	
	public String getMobile_number() {
		return mobile_number;
	}
	public void setMobile_number(String mobile_number) {
		this.mobile_number = mobile_number;
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
	public String getMpin() {
		return mpin;
	}
	public void setMpin(String mpin) {
		this.mpin = mpin;
	}
	public String getTouchId() {
		return touchId;
	}
	public void setTouchId(String touchId) {
		this.touchId = touchId;
	}
}
