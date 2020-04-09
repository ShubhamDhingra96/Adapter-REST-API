package snap.ib.personelLoan.adapter.common.beans;

public class List_loans_detail {

	private Double loan_amount;
	private String cycle_date;
	private String emi;
	private String converted;
	private String product_type;
	private String add5;
	private String add4;
	private String add3;
	private String lead_id;
	private String stage;
	private String add2;
	private String add1;
	private String tenure;
	private String decline_reason;
	private String cas_id;
	private String has_top_up;
	private String product_category;
	private boolean is_prepaid_card_loan;
	
	private String lead_source;
	
	public String getLead_source() {
		return lead_source;
	}

	public void setLead_source(String lead_source) {
		this.lead_source = lead_source;
	}

	public boolean isIs_prepaid_card_loan() {
		return is_prepaid_card_loan;
	}

	public void setIs_prepaid_card_loan(boolean is_prepaid_card_loan) {
		this.is_prepaid_card_loan = is_prepaid_card_loan;
	}

	public String getProduct_category() {
		return product_category;
	}

	public void setProduct_category(String product_category) {
		this.product_category = product_category;
	}

	public Double getLoan_amount() {
		return loan_amount;
	}

	public void setLoan_amount(Double loan_amount) {
		this.loan_amount = loan_amount;
	}

	public String getCycle_date() {
		return cycle_date;
	}

	public void setCycle_date(String cycle_date) {
		this.cycle_date = cycle_date;
	}

	public String getEmi() {
		return emi;
	}

	public void setEmi(String emi) {
		this.emi = emi;
	}

	public String getConverted() {
		return converted;
	}

	public void setConverted(String converted) {
		this.converted = converted;
	}

	public String getProduct_type() {
		return product_type;
	}

	public void setProduct_type(String product_type) {
		this.product_type = product_type;
	}

	public String getAdd5() {
		return add5;
	}

	public void setAdd5(String add5) {
		this.add5 = add5;
	}

	public String getAdd4() {
		return add4;
	}

	public void setAdd4(String add4) {
		this.add4 = add4;
	}

	public String getAdd3() {
		return add3;
	}

	public void setAdd3(String add3) {
		this.add3 = add3;
	}

	public String getLead_id() {
		return lead_id;
	}

	public void setLead_id(String lead_id) {
		this.lead_id = lead_id;
	}

	public String getStage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}

	public String getAdd2() {
		return add2;
	}

	public void setAdd2(String add2) {
		this.add2 = add2;
	}

	public String getAdd1() {
		return add1;
	}

	public void setAdd1(String add1) {
		this.add1 = add1;
	}

	public String getTenure() {
		return tenure;
	}

	public void setTenure(String tenure) {
		this.tenure = tenure;
	}

	public String getDecline_reason() {
		return decline_reason;
	}

	public void setDecline_reason(String decline_reason) {
		this.decline_reason = decline_reason;
	}

	public String getCas_id() {
		return cas_id;
	}

	public void setCas_id(String cas_id) {
		this.cas_id = cas_id;
	}

	public String getHas_top_up() {
		return has_top_up;
	}

	public void setHas_top_up(String has_top_up) {
		this.has_top_up = has_top_up;
	}

	@Override
	public String toString() {
		return "List_loans_detail [loan_amount = " + loan_amount + ", cycle_date = " + cycle_date + ", emi = " + emi
				+ ", converted = " + converted + ", product_type = " + product_type + ", add5 = " + add5 + ", add4 = "
				+ add4 + ", add3 = " + add3 + ", lead_id = " + lead_id + ", stage = " + stage + ", add2 = " + add2
				+ ", add1 = " + add1 + ", tenure = " + tenure + ", decline_reason = " + decline_reason + ", cas_id = "
				+ cas_id + ", has_top_up = " + has_top_up + "]";
	}
}
