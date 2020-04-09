package snap.ib.personelLoan.adapter.common.response;

public class SBIResDataResponse {

	private String sbi_rid;
	private String amount;
	private String rid;
	private String status;
	private String rmk;
	private String tet;
	private String crn;
	private String cny;
	private String typ;
	private String pmd;
	private String error_message;
	private boolean success;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getError_message() {
		return error_message;
	}
	public void setError_message(String error_message) {
		this.error_message = error_message;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getSbi_rid() {
		return sbi_rid;
	}
	public void setSbi_rid(String sbi_rid) {
		this.sbi_rid = sbi_rid;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getRid() {
		return rid;
	}
	public void setRid(String rid) {
		this.rid = rid;
	}
	public String getRmk() {
		return rmk;
	}
	public void setRmk(String rmk) {
		this.rmk = rmk;
	}
	public String getTet() {
		return tet;
	}
	public void setTet(String tet) {
		this.tet = tet;
	}
	public String getCrn() {
		return crn;
	}
	public void setCrn(String crn) {
		this.crn = crn;
	}
	public String getCny() {
		return cny;
	}
	public void setCny(String cny) {
		this.cny = cny;
	}
	public String getTyp() {
		return typ;
	}
	public void setTyp(String typ) {
		this.typ = typ;
	}
	public String getPmd() {
		return pmd;
	}
	public void setPmd(String pmd) {
		this.pmd = pmd;
	}
}
