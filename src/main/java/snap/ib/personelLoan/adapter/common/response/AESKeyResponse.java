package snap.ib.personelLoan.adapter.common.response;

public class AESKeyResponse {
 
	private String key;
	private String sessionKey;
    private String outputData;
    private double sessionTimeout;
	private boolean isEnc;
	private String docUploadMaxSize="1024";
	private boolean enable_ocr;
	
	
	public boolean isEnable_ocr() {
		return enable_ocr;
	}

	public void setEnable_ocr(boolean enable_ocr) {
		this.enable_ocr = enable_ocr;
	}

	public String getDocUploadMaxSize() {
		return docUploadMaxSize;
	}

	public void setDocUploadMaxSize(String docUploadMaxSize) {
		this.docUploadMaxSize = docUploadMaxSize;
	}

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
				 + " sessionTimeout=" + sessionTimeout + ", isEnc=" + isEnc + "]";
	}
}
