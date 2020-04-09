package snap.ib.personelLoan.adapter.common.response;

public class ICICIUrlRedirectResResponse {

	private boolean success;
	private String error_message;
	private String PAID;
	private String status;
	private String status_msg;
	private String transaction_ref_no;
	private String transaction_id;
	private String emandate_date; 
	private String emandate_time;
	private String payment_gateway_ref_id;
	private String mandate_registration_num;
	private double payment_amount;
	private String additional_1;
	private String additional_2;
	private String additional_3;
	private String additional_4;
	private String additional_5;
	private String additional_6;
	private String additional_7;
	private String additional_8;
	
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean finalSuccess) {
		success = finalSuccess;
	}
	public String getError_message() {
		return error_message;
	}
	public void setError_message(String finalError_message) {
		error_message = finalError_message;
	}
	public String getPAID() {
		return PAID;
	}
	public void setPAID(String finalPAID) {
		PAID = finalPAID;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String finalStatus) {
		status = finalStatus;
	}
	public String getStatus_msg() {
		return status_msg;
	}
	public void setStatus_msg(String finalStatus_msg) {
		status_msg = finalStatus_msg;
	}
	public String getTransaction_ref_no() {
		return transaction_ref_no;
	}
	public void setTransaction_ref_no(String finalTransaction_ref_no) {
		transaction_ref_no = finalTransaction_ref_no;
	}
	public String getTransaction_id() {
		return transaction_id;
	}
	public void setTransaction_id(String finalTransaction_id) {
		transaction_id = finalTransaction_id;
	}
	public String getEmandate_date() {
		return emandate_date;
	}
	public void setEmandate_date(String finalEmandate_date) {
		emandate_date = finalEmandate_date;
	}
	public String getEmandate_time() {
		return emandate_time;
	}
	public void setEmandate_time(String finalEmandate_time) {
		emandate_time = finalEmandate_time;
	}
	public String getPayment_gateway_ref_id() {
		return payment_gateway_ref_id;
	}
	public void setPayment_gateway_ref_id(String finalPayment_gateway_ref_id) {
		payment_gateway_ref_id = finalPayment_gateway_ref_id;
	}
	public String getMandate_registration_num() {
		return mandate_registration_num;
	}
	public void setMandate_registration_num(String finalMandate_registration_num) {
		mandate_registration_num = finalMandate_registration_num;
	}
	public double getPayment_amount() {
		return payment_amount;
	}
	public void setPayment_amount(double finalPayment_amount) {
		payment_amount = finalPayment_amount;
	}
	public String getAdditional_1() {
		return additional_1;
	}
	public void setAdditional_1(String finalAdditional_1) {
		additional_1 = finalAdditional_1;
	}
	public String getAdditional_2() {
		return additional_2;
	}
	public void setAdditional_2(String finalAdditional_2) {
		additional_2 = finalAdditional_2;
	}
	public String getAdditional_3() {
		return additional_3;
	}
	public void setAdditional_3(String finalAdditional_3) {
		additional_3 = finalAdditional_3;
	}
	public String getAdditional_4() {
		return additional_4;
	}
	public void setAdditional_4(String finalAdditional_4) {
		additional_4 = finalAdditional_4;
	}
	public String getAdditional_5() {
		return additional_5;
	}
	public void setAdditional_5(String finalAdditional_5) {
		additional_5 = finalAdditional_5;
	}
	public String getAdditional_6() {
		return additional_6;
	}
	public void setAdditional_6(String finalAdditional_6) {
		additional_6 = finalAdditional_6;
	}
	public String getAdditional_7() {
		return additional_7;
	}
	public void setAdditional_7(String finalAdditional_7) {
		additional_7 = finalAdditional_7;
	}
	public String getAdditional_8() {
		return additional_8;
	}
	public void setAdditional_8(String finalAdditional_8) {
		additional_8 = finalAdditional_8;
	}
	@Override
	public String toString() {
		return "ICICIUrlRedirectResResponse [success=" + success + ", error_message=" + error_message + ", PAID=" + PAID
				+ ", status=" + status + ", status_msg=" + status_msg + ", transaction_ref_no=" + transaction_ref_no
				+ ", transaction_id=" + transaction_id + ", emandate_date=" + emandate_date + ", emandate_time="
				+ emandate_time + ", payment_gateway_ref_id=" + payment_gateway_ref_id + ", mandate_registration_num="
				+ mandate_registration_num + ", payment_amount=" + payment_amount + ", additional_1=" + additional_1
				+ ", additional_2=" + additional_2 + ", additional_3=" + additional_3 + ", additional_4=" + additional_4
				+ ", additional_5=" + additional_5 + ", additional_6=" + additional_6 + ", additional_7=" + additional_7
				+ ", additional_8=" + additional_8 + "]";
	}

}
