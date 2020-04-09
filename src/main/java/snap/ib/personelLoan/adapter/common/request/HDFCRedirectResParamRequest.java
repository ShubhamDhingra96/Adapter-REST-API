package snap.ib.personelLoan.adapter.common.request;

public class HDFCRedirectResParamRequest {

	private String redirestResponseParam;
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
	public String getRedirestResponseParam() {
		return redirestResponseParam;
	}

	public void setRedirestResponseParam(String redirestResponseParam) {
		this.redirestResponseParam = redirestResponseParam;
	}
}
