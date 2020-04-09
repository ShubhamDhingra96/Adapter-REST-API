package snap.ib.personelLoan.adapter.common.request;

public class ESignVarificationRequest {

	private String lead_id;
	private String aadhaar_number;
	private String document_type;
	private String is_consent;
	private String consent_text;
	private String device_id;
	private String session_key;
	private String re_url;
	private String txnId_clnt;
	private String esign_type;
	private String filler1;
	private String filler2;
	private String cust_IP;
	private String cust_name;
	private String pdfDoc;
	
	public String getDocument_type() {
		return document_type;
	}
	public void setDocument_type(String document_type) {
		this.document_type = document_type;
	}
	public String getRe_url() {
		return re_url;
	}
	public void setRe_url(String re_url) {
		this.re_url = re_url;
	}
	public String getTxnId_clnt() {
		return txnId_clnt;
	}
	public void setTxnId_clnt(String txnId_clnt) {
		this.txnId_clnt = txnId_clnt;
	}
	public String getEsign_type() {
		return esign_type;
	}
	public void setEsign_type(String esign_type) {
		this.esign_type = esign_type;
	}
	public String getFiller1() {
		return filler1;
	}
	public void setFiller1(String filler1) {
		this.filler1 = filler1;
	}
	public String getFiller2() {
		return filler2;
	}
	public void setFiller2(String filler2) {
		this.filler2 = filler2;
	}
	public void setSession_key(String session_key) {
		this.session_key = session_key;
	}
	public String getIs_consent() {
		return is_consent;
	}
	public void setIs_consent(String finalIs_consent) {
		is_consent = finalIs_consent;
	}
	public String getConsent_text() {
		return consent_text;
	}
	public void setConsent_text(String finalConsent_text) {
		consent_text = finalConsent_text;
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
	public String getLead_id() {
		return lead_id;
	}
	public void setLead_id(String lead_id) {
		this.lead_id = lead_id;
	}
	public String getAadhaar_number() {
		return aadhaar_number;
	}
	public void setAadhaar_number(String aadhaar_number) {
		this.aadhaar_number = aadhaar_number;
	}
	public String getCust_IP() {
		return cust_IP;
	}
	public void setCust_IP(String cust_IP) {
		this.cust_IP = cust_IP;
	}
	public String getCust_name() {
		return cust_name;
	}
	public void setCust_name(String cust_name) {
		this.cust_name = cust_name;
	}
	public String getPdfDoc() {
		return pdfDoc;
	}
	public void setPdfDoc(String pdfDoc) {
		this.pdfDoc = pdfDoc;
	}
	
}
