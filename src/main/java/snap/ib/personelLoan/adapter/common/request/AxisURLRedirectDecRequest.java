package snap.ib.personelLoan.adapter.common.request;

public class AxisURLRedirectDecRequest {
	
	private String encData;
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
	public String getEncData() {
		return encData;
	}

	public void setEncData(String encData) {
		this.encData = encData;
	}
	@Override
	public String toString() {
		return "AxisURLRedirectDecRequest [encData=" + encData + ", device_id=" + device_id + ", session_key="
				+ session_key + "]";
	}
	
	
	
}
