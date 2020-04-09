package snap.ib.personelLoan.adapter.common.request;

public class CreateProductRequest {

	private String mobile_number;
	private String product_type;
	private String product_category;
	private String add1;
	private String add2;
	private String add3;
	private String lead_id;
	private String opp_id;
	private String con_id;
	private String loanExt_id;
	private String acc_id;
	private String device_id;
	
	public String getDevice_id() {
		return device_id;
	}
	public void setDevice_id(String device_id) {
		this.device_id = device_id;
	}
	public String getProduct_category() {
		return product_category;
	}
	public void setProduct_category(String product_category) {
		this.product_category = product_category;
	}
	public String getLead_id() {
		return lead_id;
	}
	public void setLead_id(String lead_id) {
		this.lead_id = lead_id;
	}
	public String getOpp_id() {
		return opp_id;
	}
	public void setOpp_id(String opp_id) {
		this.opp_id = opp_id;
	}
	public String getCon_id() {
		return con_id;
	}
	public void setCon_id(String con_id) {
		this.con_id = con_id;
	}
	public String getLoanExt_id() {
		return loanExt_id;
	}
	public void setLoanExt_id(String loanExt_id) {
		this.loanExt_id = loanExt_id;
	}
	public String getAcc_id() {
		return acc_id;
	}
	public void setAcc_id(String acc_id) {
		this.acc_id = acc_id;
	}
	public String getMobile_number() {
		return mobile_number;
	}
	public void setMobile_number(String mobile_number) {
		this.mobile_number = mobile_number;
	}
	
	public String getProduct_type() {
		return product_type;
	}
	public void setProduct_type(String product_key) {
		this.product_type = product_key;
	}
	public String getAdd1() {
		return add1;
	}
	public void setAdd1(String add1) {
		this.add1 = add1;
	}
	public String getAdd2() {
		return add2;
	}
	public void setAdd2(String add2) {
		this.add2 = add2;
	}
	public String getAdd3() {
		return add3;
	}
	public void setAdd3(String add3) {
		this.add3 = add3;
	}
}
