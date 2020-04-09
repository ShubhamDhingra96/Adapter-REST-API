package snap.ib.personelLoan.adapter.common.response;

public class KotakResDataResponse {

	private String messageCode;
	private String dob;
	private String merchantCode;
	private String merchantReferenceNumber;
	private String bankReferenceNumber;
	private String amount;
	private String accountNumber;
	private String status;
	private String customerName;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	private boolean success;
	private String error_message;

	public boolean getSuccess() {
		return success;
	}
	public void setSuccess(boolean b) {
		this.success = b;
	}
	public String getError_message() {
		return error_message;
	}
	public void setError_message(String error_message) {
		this.error_message = error_message;
	}
	
	public String getMessageCode() {
		return messageCode;
	}
	public void setMessageCode(String messageCode) {
		this.messageCode = messageCode;
	}
	
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public String getMerchantCode() {
		return merchantCode;
	}
	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}
	public String getMerchantReferenceNumber() {
		return merchantReferenceNumber;
	}
	public void setMerchantReferenceNumber(String merchantReferenceNumber) {
		this.merchantReferenceNumber = merchantReferenceNumber;
	}
	public String getBankReferenceNumber() {
		return bankReferenceNumber;
	}
	public void setBankReferenceNumber(String bankReferenceNumber) {
		this.bankReferenceNumber = bankReferenceNumber;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	@Override
	public String toString() {
		return "KotakResDataResponse [messageCode=" + messageCode + ", dob=" + dob + ", merchantCode=" + merchantCode
				+ ", merchantReferenceNumber=" + merchantReferenceNumber + ", bankReferenceNumber="
				+ bankReferenceNumber + ", amount=" + amount + ", accountNumber=" + accountNumber + ", status=" + status
				+ ", customerName=" + customerName + ", success=" + success + ", error_message=" + error_message + "]";
	}

}
