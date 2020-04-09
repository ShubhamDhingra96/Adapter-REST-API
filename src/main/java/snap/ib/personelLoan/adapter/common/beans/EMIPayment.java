package snap.ib.personelLoan.adapter.common.beans;

public class EMIPayment {

	private String amount;
	private String charges;
	private String sum_total_of_all_receivable_charges;
	
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getCharges() {
		return charges;
	}
	public void setCharges(String charges) {
		this.charges = charges;
	}
	public String getSum_total_of_all_receivable_charges() {
		return sum_total_of_all_receivable_charges;
	}
	public void setSum_total_of_all_receivable_charges(
			String sum_total_of_all_receivable_charges) {
		this.sum_total_of_all_receivable_charges = sum_total_of_all_receivable_charges;
	}
	 
}
