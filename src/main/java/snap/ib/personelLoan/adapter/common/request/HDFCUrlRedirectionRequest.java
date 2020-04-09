package snap.ib.personelLoan.adapter.common.request;

public class HDFCUrlRedirectionRequest {

	private String accountNo;
	private String consumerId;
	private String debitStartDate;
	private String debitEndDate;
	private String consumerMobileNo;
	private String consumerEmailId;
	private String maxAmount;
	private String frequency;
	private String amountType;
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
	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getConsumerId() {
		return consumerId;
	}

	public void setConsumerId(String consumerId) {
		this.consumerId = consumerId;
	}

	public String getDebitStartDate() {
		return debitStartDate;
	}

	public void setDebitStartDate(String debitStartDate) {
		this.debitStartDate = debitStartDate;
	}

	public String getDebitEndDate() {
		return debitEndDate;
	}

	public void setDebitEndDate(String debitEndDate) {
		this.debitEndDate = debitEndDate;
	}

	public String getConsumerMobileNo() {
		return consumerMobileNo;
	}

	public void setConsumerMobileNo(String consumerMobileNo) {
		this.consumerMobileNo = consumerMobileNo;
	}

	public String getConsumerEmailId() {
		return consumerEmailId;
	}

	public void setConsumerEmailId(String consumerEmailId) {
		this.consumerEmailId = consumerEmailId;
	}

	public String getMaxAmount() {
		return maxAmount;
	}

	public void setMaxAmount(String maxAmount) {
		this.maxAmount = maxAmount;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public String getAmountType() {
		return amountType;
	}

	public void setAmountType(String amountType) {
		this.amountType = amountType;
	}

}
