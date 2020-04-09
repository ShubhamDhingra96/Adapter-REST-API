package snap.ib.personelLoan.adapter.common.request;

import java.util.List;

import snap.ib.personelLoan.adapter.common.beans.UserData;

public class ExperianGetAlternateDataRequest {

	private String lead_id;
	private List<UserData> userData;
	private List<String> mobile_ref;
	private List<String> reg_email_ref;
	private String device_id;
	private String session_key;
	
	public List<String> getReg_email_ref() {
		return reg_email_ref;
	}

	public void setReg_email_ref(List<String> reg_email_ref) {
		this.reg_email_ref = reg_email_ref;
	}

	public void setSession_key(String session_key) {
		this.session_key = session_key;
	}
	
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

	public List<UserData> getUserData() {
		return userData;
	}

	public void setUserData(List<UserData> userData) {
		this.userData = userData;
	}

	public List<String> getMobile_ref() {
		return mobile_ref;
	}

	public void setMobile_ref(List<String> mobile_ref) {
		this.mobile_ref = mobile_ref;
	}

}
