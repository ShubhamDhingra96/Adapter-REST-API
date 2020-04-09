package snap.ib.personelLoan.adapter.common.request;

public class UpdateDisbursementDetailsCRMRequest {

	private String lead_id;
	private boolean emandate_flag;
	private String customer_unique_number;
	private String corporate_code;
	private String date_of_payment_file_received;
	private String payment_type;
	private String utr_number;
	private String check_number;
	private String disbursment_description_1;
	private String disbursment_description_2;
	private String batch_number;
	private String benificiary_code;
	private String transaction_value_date;
	private String bank_unique_ref_number;
	private long amount_credited;
	private String client_account_number;
	private String ifsc_code;
	private String device_id;
	private String session_key;
	
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
	public boolean isEmandate_flag() {
		return emandate_flag;
	}
	public void setEmandate_flag(boolean emandate_flag) {
		this.emandate_flag = emandate_flag;
	}
	public String getCustomer_unique_number() {
		return customer_unique_number;
	}
	public void setCustomer_unique_number(String customer_unique_number) {
		this.customer_unique_number = customer_unique_number;
	}
	public String getCorporate_code() {
		return corporate_code;
	}
	public void setCorporate_code(String corporate_code) {
		this.corporate_code = corporate_code;
	}
	public String getDate_of_payment_file_received() {
		return date_of_payment_file_received;
	}
	public void setDate_of_payment_file_received(
			String date_of_payment_file_received) {
		this.date_of_payment_file_received = date_of_payment_file_received;
	}
	public String getPayment_type() {
		return payment_type;
	}
	public void setPayment_type(String payment_type) {
		this.payment_type = payment_type;
	}
	public String getUtr_number() {
		return utr_number;
	}
	public void setUtr_number(String utr_number) {
		this.utr_number = utr_number;
	}
	public String getCheck_number() {
		return check_number;
	}
	public void setCheck_number(String check_number) {
		this.check_number = check_number;
	}
	public String getDisbursment_description_1() {
		return disbursment_description_1;
	}
	public void setDisbursment_description_1(String disbursment_description_1) {
		this.disbursment_description_1 = disbursment_description_1;
	}
	public String getDisbursment_description_2() {
		return disbursment_description_2;
	}
	public void setDisbursment_description_2(String disbursment_description_2) {
		this.disbursment_description_2 = disbursment_description_2;
	}
	public String getBatch_number() {
		return batch_number;
	}
	public void setBatch_number(String batch_number) {
		this.batch_number = batch_number;
	}
	public String getBenificiary_code() {
		return benificiary_code;
	}
	public void setBenificiary_code(String benificiary_code) {
		this.benificiary_code = benificiary_code;
	}
	public String getTransaction_value_date() {
		return transaction_value_date;
	}
	public void setTransaction_value_date(String transaction_value_date) {
		this.transaction_value_date = transaction_value_date;
	}
	public String getBank_unique_ref_number() {
		return bank_unique_ref_number;
	}
	public void setBank_unique_ref_number(String bank_unique_ref_number) {
		this.bank_unique_ref_number = bank_unique_ref_number;
	}
	public long getAmount_credited() {
		return amount_credited;
	}
	public void setAmount_credited(long amount_credited) {
		this.amount_credited = amount_credited;
	}
	public String getClient_account_number() {
		return client_account_number;
	}
	public void setClient_account_number(String client_account_number) {
		this.client_account_number = client_account_number;
	}
	public String getIfsc_code() {
		return ifsc_code;
	}
	public void setIfsc_code(String ifsc_code) {
		this.ifsc_code = ifsc_code;
	}
	public String getCredit_debit_indicatior() {
		return credit_debit_indicatior;
	}
	public void setCredit_debit_indicatior(String credit_debit_indicatior) {
		this.credit_debit_indicatior = credit_debit_indicatior;
	}
	public String getBenificiary_account_number() {
		return benificiary_account_number;
	}
	public void setBenificiary_account_number(String benificiary_account_number) {
		this.benificiary_account_number = benificiary_account_number;
	}
	private String credit_debit_indicatior;
	private String benificiary_account_number;
	
}
