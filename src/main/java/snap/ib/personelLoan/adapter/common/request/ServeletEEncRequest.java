package snap.ib.personelLoan.adapter.common.request;

public class ServeletEEncRequest {

	private String reqData;
	private String reqToken;
	private String deviceOs;
	
	public String getReqData() {
		return reqData;
	}
	public void setReqData(String reqData) {
		this.reqData = reqData;
	}
	public String getReqToken() {
		return reqToken;
	}
	public void setReqToken(String reqToken) {
		this.reqToken = reqToken;
	}
	public String getDeviceOs() {
		return deviceOs;
	}
	public void setDeviceOs(String deviceOs) {
		this.deviceOs = deviceOs;
	}
	@Override
	public String toString() {
		return "ServeletEEncRequest [reqData=" + reqData + ", reqToken=" + reqToken + ", deviceOs=" + deviceOs + "]";
	}
	
		
	
	
}
