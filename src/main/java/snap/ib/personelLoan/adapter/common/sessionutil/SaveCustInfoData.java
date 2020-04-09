package snap.ib.personelLoan.adapter.common.sessionutil;

import java.io.Serializable;

public class SaveCustInfoData implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public String  sessionKey;
	public SessionData data;
	public String getSessionKey() {
		return sessionKey;
	}
	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}
	public SessionData getData() {
		return data;
	}
	public void setData(SessionData data) {
		this.data = data;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
	
	
	
	
	
}
