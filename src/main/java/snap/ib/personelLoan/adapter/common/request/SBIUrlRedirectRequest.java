package snap.ib.personelLoan.adapter.common.request;

public class SBIUrlRedirectRequest {
 
	private String amount;
	private String account_number;
	private String exp_date;
	private String schedule_date;
	private String frequency;
	private String return_url;
	private String can_url;
	private String emi_amount;
	private String ifsc_code;
	private String bank_name;
	private String aadhar;
	private String max_amount;
	private String customer_name;
	private String crn;
	private String mandate_reg_charge;
	private String debit_accountno;
	private String Amnt_type;
	
	
	
	public String getAmnt_type() {
		return Amnt_type;
	}
	public void setAmnt_type(String amnt_type) {
		Amnt_type = amnt_type;
	}
	public String getDebit_accountno() {
		return debit_accountno;
	}
	public void setDebit_accountno(String debit_accountno) {
		this.debit_accountno = debit_accountno;
	}
	public String getMandate_reg_charge() {
		return mandate_reg_charge;
	}
	public void setMandate_reg_charge(String mandate_reg_charge) {
		this.mandate_reg_charge = mandate_reg_charge;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getAccount_number() {
		return account_number;
	}
	public void setAccount_number(String account_number) {
		this.account_number = account_number;
	}
	public String getExp_date() {
		return exp_date;
	}
	public void setExp_date(String exp_date) {
		this.exp_date = exp_date;
	}
	public String getSchedule_date() {
		return schedule_date;
	}
	public void setSchedule_date(String schedule_date) {
		this.schedule_date = schedule_date;
	}
	public String getFrequency() {
		return frequency;
	}
	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}
	public String getReturn_url() {
		return return_url;
	}
	public void setReturn_url(String return_url) {
		this.return_url = return_url;
	}
	public String getCan_url() {
		return can_url;
	}
	public void setCan_url(String can_url) {
		this.can_url = can_url;
	}
	public String getEmi_amount() {
		return emi_amount;
	}
	public void setEmi_amount(String emi_amount) {
		this.emi_amount = emi_amount;
	}
	public String getIfsc_code() {
		return ifsc_code;
	}
	public void setIfsc_code(String ifsc_code) {
		this.ifsc_code = ifsc_code;
	}
	public String getBank_name() {
		return bank_name;
	}
	public void setBank_name(String bank_name) {
		this.bank_name = bank_name;
	}
	public String getAadhar() {
		return aadhar;
	}
	public void setAadhar(String aadhar) {
		this.aadhar = aadhar;
	}
	public String getMax_amount() {
		return max_amount;
	}
	public void setMax_amount(String max_amount) {
		this.max_amount = max_amount;
	}
	public String getCustomer_name() {
		return customer_name;
	}
	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}
	public String getCrn() {
		return crn;
	}
	public void setCrn(String crn) {
		this.crn = crn;
	}
}
