package snap.ib.personelLoan.adapter.common.request;

public class CommonRequest {

	private String inputString;
	private String chkSum;
	private String device_id;
	private String session_key;
	private String geo_location_check;
	private String deviceType;
	private String operationParam;
	private String requestToken;
	private String deviceOS;
	
	public String getDeviceOS() {
		return deviceOS;
	}

	public void setDeviceOS(String deviceOS) {
		this.deviceOS = deviceOS;
	}
	
	public String getRequestToken() {
		return requestToken;
	}

	public void setRequestToken(String requestToken) {
		this.requestToken = requestToken;
	}

	public String getOperationParam() {
		return operationParam;
	}

	public void setOperationParam(String operationParam) {
		this.operationParam = operationParam;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getGeo_location_check() {
		return geo_location_check;
	}

	public void setGeo_location_check(String geo_location_check) {
		this.geo_location_check = geo_location_check;
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

	public String getInputString() {
		return inputString;
	}

	public void setInputString(String inputString) {
		this.inputString = inputString;
	}

	public String getChkSum() {
		return chkSum;
	}

	public void setChkSum(String chkSum) {
		this.chkSum = chkSum;
	}

	@Override
	public String toString() {
		return "CommonRequest [inputString=" + inputString + ", chkSum=" + chkSum + ", device_id=" + device_id
				+ ", session_key=" + session_key + "]";
	}
	
	
}
