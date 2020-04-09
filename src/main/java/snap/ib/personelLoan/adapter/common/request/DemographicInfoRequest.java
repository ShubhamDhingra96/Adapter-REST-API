package snap.ib.personelLoan.adapter.common.request;

import snap.ib.personelLoan.adapter.common.beans.ObjectRequestWrapper;

public class DemographicInfoRequest {
	
	private ObjectRequestWrapper objRequestWrapper;
	private String device_id;
	private String session_key;
	private String source;
	
	
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
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
	
	public ObjectRequestWrapper getObjRequestWrapper() {
		return objRequestWrapper;
	}

	public void setObjRequestWrapper(ObjectRequestWrapper objRequestWrapper) {
		this.objRequestWrapper = objRequestWrapper;
	}
}
