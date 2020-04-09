package snap.ib.personelLoan.adapter.common.response;

public class ServeletEEncResponse {
 
	private String key;
	private String sessionKey;
    private String outputData;
    private double sessionTimeout;
	private boolean isEnc;

	public double getSessionTimeout() {
		return sessionTimeout;
	}

	public void setSessionTimeout(double sessionTimeout) {
		this.sessionTimeout = sessionTimeout;
	}

	public boolean isEnc() {
		return isEnc;
	}

	public void setEnc(boolean isEnc) {
		this.isEnc = isEnc;
	}

	public String getOutputData() {
		return outputData;
	}

	public void setOutputData(String outputData) {
		this.outputData = outputData;
	}

	public String getSessionKey() {
		return sessionKey;
	}

	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	@Override
	public String toString() {
		return "AESKeyResponse [key=" + key + ", sessionKey=" + sessionKey + ", outputData=" + outputData
				+ "sessionTimeout=" + sessionTimeout + ", isEnc=" + isEnc + "]";
	}
}
