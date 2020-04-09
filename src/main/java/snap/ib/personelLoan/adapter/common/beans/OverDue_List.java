package snap.ib.personelLoan.adapter.common.beans;

public class OverDue_List {

	private String Charge_Date;
	private String Charge_Desc;
	private String Charge_Amt;
	private String Received_Amt;
	private String Pending_Amt;
	
	public String getCharge_Date() {
		return Charge_Date;
	}
	public void setCharge_Date(String charge_Date) {
		Charge_Date = charge_Date;
	}
	public String getCharge_Desc() {
		return Charge_Desc;
	}
	public void setCharge_Desc(String charge_Desc) {
		Charge_Desc = charge_Desc;
	}
	public String getCharge_Amt() {
		return Charge_Amt;
	}
	public void setCharge_Amt(String charge_Amt) {
		Charge_Amt = charge_Amt;
	}
	public String getReceived_Amt() {
		return Received_Amt;
	}
	public void setReceived_Amt(String received_Amt) {
		Received_Amt = received_Amt;
	}
	public String getPending_Amt() {
		return Pending_Amt;
	}
	public void setPending_Amt(String pending_Amt) {
		Pending_Amt = pending_Amt;
	}

}
