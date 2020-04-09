package snap.ib.personelLoan.adapter.common.request;

public class KotakUrlRedirectRequest {
 
	private String messageCode;
	private String merchantReferenceNumber;
	private String merchantCode;
	private String amount;
	private String accountNumber;
	private String dateTime;
	private String frequency;
	private String startDate;
	private String endDate;
	private String debitType;
	private String returnUrl;
	
	

	public String getReturnUrl() {
		return returnUrl;
	}
	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}
	public String getMessageCode() {
		return messageCode;
	}
	public void setMessageCode(String messageCode) {
		this.messageCode = messageCode;
	}
	public String getMerchantReferenceNumber() {
		return merchantReferenceNumber;
	}
	public void setMerchantReferenceNumber(String merchantReferenceNumber) {
		this.merchantReferenceNumber = merchantReferenceNumber;
	}
	public String getMerchantCode() {
		return merchantCode;
	}
	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
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
	public String getDateTime() {
		return dateTime;
	}
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
	public String getFrequency() {
		return frequency;
	}
	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getDebitType() {
		return debitType;
	}
	public void setDebitType(String debitType) {
		this.debitType = debitType;
	}
	@Override
	public String toString() {
		return "KotakUrlRedirectRequest [messageCode=" + messageCode + ", merchantReferenceNumber="
				+ merchantReferenceNumber + ", merchantCode=" + merchantCode + ", amount=" + amount + ", accountNumber="
				+ accountNumber + ", dateTime=" + dateTime + ", frequency=" + frequency + ", startDate=" + startDate
				+ ", endDate=" + endDate + ", debitType=" + debitType + "]";
	}
	
	
}
