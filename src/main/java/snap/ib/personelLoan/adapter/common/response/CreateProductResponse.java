package snap.ib.personelLoan.adapter.common.response;

public class CreateProductResponse {
	
	private boolean success;
	private String error_message;
	private String status;
	private String lead_id;
	private String rep1;
	private String rep2;
	private String rep3;
	
	private String opp_id;
	private String lext_id;
	 
	public String getOpp_id() {
		return opp_id;
	}
	public void setOpp_id(String opp_id) {
		this.opp_id = opp_id;
	}
	public String getLext_id() {
		return lext_id;
	}
	public void setLext_id(String lext_id) {
		this.lext_id = lext_id;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getError_message() {
		return error_message;
	}
	public void setError_message(String error_message) {
		this.error_message = error_message;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getLead_id() {
		return lead_id;
	}
	public void setLead_id(String lead_id) {
		this.lead_id = lead_id;
	}
	public String getRep1() {
		return rep1;
	}
	public void setRep1(String rep1) {
		this.rep1 = rep1;
	}
	public String getRep2() {
		return rep2;
	}
	public void setRep2(String rep2) {
		this.rep2 = rep2;
	}
	public String getRep3() {
		return rep3;
	}
	public void setRep3(String rep3) {
		this.rep3 = rep3;
	}
	
}
