package snap.ib.personelLoan.adapter.common.request;

public class AndhraTokenRequest {

	
	private String bank_id;
	private String user_lang_id;
	private String app_type;
	private String user_type;
	
	private String cg;
	private String md;
	private String pid;
	private String dhani_id;
	private String dhani_ref;
	private String customer_name;
	private String bank_name;
	private String account_number;
	
	private String loan_amount;
	private String emi_amount;
	private String debit_frequency;
	private String debit_start_date;
	private String debit_end_date;
	private String crn;
	private String ru;
	
	
	
	
	public String getBank_id() {
		return bank_id;
	}
	public void setBank_id(String bank_id) {
		this.bank_id = bank_id;
	}
	public String getUser_lang_id() {
		return user_lang_id;
	}
	public void setUser_lang_id(String user_lang_id) {
		this.user_lang_id = user_lang_id;
	}
	public String getApp_type() {
		return app_type;
	}
	public void setApp_type(String app_type) {
		this.app_type = app_type;
	}
	public String getUser_type() {
		return user_type;
	}
	public void setUser_type(String user_type) {
		this.user_type = user_type;
	}
	public String getCg() {
		return cg;
	}
	public void setCg(String cg) {
		this.cg = cg;
	}
	public String getMd() {
		return md;
	}
	public void setMd(String md) {
		this.md = md;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getDhani_id() {
		return dhani_id;
	}
	public void setDhani_id(String dhani_id) {
		this.dhani_id = dhani_id;
	}
	public String getDhani_ref() {
		return dhani_ref;
	}
	public void setDhani_ref(String dhani_ref) {
		this.dhani_ref = dhani_ref;
	}
	public String getCustomer_name() {
		return customer_name;
	}
	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}
	public String getBank_name() {
		return bank_name;
	}
	public void setBank_name(String bank_name) {
		this.bank_name = bank_name;
	}
	public String getAccount_number() {
		return account_number;
	}
	public void setAccount_number(String account_number) {
		this.account_number = account_number;
	}
	public String getLoan_amount() {
		return loan_amount;
	}
	public void setLoan_amount(String loan_amount) {
		this.loan_amount = loan_amount;
	}
	public String getEmi_amount() {
		return emi_amount;
	}
	public void setEmi_amount(String emi_amount) {
		this.emi_amount = emi_amount;
	}
	public String getDebit_frequency() {
		return debit_frequency;
	}
	public void setDebit_frequency(String debit_frequency) {
		this.debit_frequency = debit_frequency;
	}
	public String getDebit_start_date() {
		return debit_start_date;
	}
	public void setDebit_start_date(String debit_start_date) {
		this.debit_start_date = debit_start_date;
	}
	public String getDebit_end_date() {
		return debit_end_date;
	}
	public void setDebit_end_date(String debit_end_date) {
		this.debit_end_date = debit_end_date;
	}
	public String getCrn() {
		return crn;
	}
	public void setCrn(String crn) {
		this.crn = crn;
	}
	public String getRu() {
		return ru;
	}
	public void setRu(String ru) {
		this.ru = ru;
	}
	@Override
	public String toString() {
		return "AndhraTokenRequest [bank_id=" + bank_id + ", user_lang_id=" + user_lang_id + ", app_type=" + app_type
				+ ", user_type=" + user_type + ", cg=" + cg + ", md=" + md + ", pid=" + pid + ", dhani_id=" + dhani_id
				+ ", dhani_ref=" + dhani_ref + ", customer_name=" + customer_name + ", bank_name=" + bank_name
				+ ", account_number=" + account_number + ", loan_amount=" + loan_amount + ", emi_amount=" + emi_amount
				+ ", debit_frequency=" + debit_frequency + ", debit_start_date=" + debit_start_date
				+ ", debit_end_date=" + debit_end_date + ", crn=" + crn + ", ru=" + ru + "]";
	}
	
}
