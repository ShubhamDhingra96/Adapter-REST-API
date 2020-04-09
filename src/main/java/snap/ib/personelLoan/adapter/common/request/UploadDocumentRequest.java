package snap.ib.personelLoan.adapter.common.request;

public class UploadDocumentRequest {
	
	private String lead_id;
	private String document_name;
	private String document_id;
	private String type;
	private String data;
	private String poa_type;
	private String device_id;
	private String session_key;

	
	public String getPoa_type() {
		return poa_type;
	}
	public void setPoa_type(String poa_type) {
		this.poa_type = poa_type;
	}
	public String getLead_id() {
		return lead_id;
	}
	public void setLead_id(String lead_id) {
		this.lead_id = lead_id;
	
	}
	public String getDocument_name() {
		return document_name;
	}
	public void setDocument_name(String document_name) {
		this.document_name = document_name;
	}
	public String getDocument_id() {
		return document_id;
	}
	public void setDocument_id(String document_id) {
		this.document_id = document_id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
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
	@Override
	public String toString() {
		return "UploadDocumentRequest [lead_id=" + lead_id + ", document_name=" + document_name + ", document_id="
				+ document_id + ", type=" + type + ", data=" + data + ", device_id=" + device_id + ", session_key="
				+ session_key + "]";
	}
	
	
	

}
