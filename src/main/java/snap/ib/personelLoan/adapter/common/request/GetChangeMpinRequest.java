package snap.ib.personelLoan.adapter.common.request;

public class GetChangeMpinRequest {

	private String mobile_number;
	private String device_id;
	private String mpin;
	private String oldmpin;
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
	public String getMpin() {
		return mpin;
	}
	public void setMpin(String mpin) {
		this.mpin = mpin;
	}
	public String getOldmpin() {
		return oldmpin;
	}
	public void setOldmpin(String oldmpin) {
		this.oldmpin = oldmpin;
	}
	
}
