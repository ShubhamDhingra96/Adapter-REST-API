package snap.ib.personelLoan.adapter.common.request;


public class ICICIUrlRedirectRequest {

	private String no_inst;
	private String accountNumber;
	private String prn;
	private String ru;
	private String pmt_dt;
	private String auto_pay_amount;
	
	public String getPrn() {
		return prn;
	}

	public void setPrn(String prn) {
		this.prn = prn;
	}

	public String getRu() {
		return ru;
	}

	public void setRu(String ru) {
		this.ru = ru;
	}

	public String getPmt_dt() {
		return pmt_dt;
	}

	public String getAuto_pay_amount() {
		return auto_pay_amount;
	}

	public void setAuto_pay_amount(String finalAuto_pay_amount) {
		auto_pay_amount = finalAuto_pay_amount;
	}

	public void setPmt_dt(String pmt_dt) {
		this.pmt_dt = pmt_dt;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getNo_inst() {
		return no_inst;
	}

	public void setNo_inst(String no_inst) {
		this.no_inst = no_inst;
	} 
}
