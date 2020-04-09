package snap.ib.personelLoan.adapter.common.request;

public class AESKeyRequest {

	@Override
	public String toString() {
		return "AESKeyRequest [device_id=" + device_id + "]";
	}
	private String device_id;
	
	public String getDevice_id() {
		return device_id;
	}
	public void setDevice_id(String device_id) {
		this.device_id = device_id;
	}
}
