package snap.ib.personelLoan.adapter.common.request;

public class NotifyCRMRequest {

	private String lead_id;
	private boolean callback;
	private boolean generate_agreement_forms;
	private boolean applicationidle;
	private String stage;
	private String EnableTouchID;
	private boolean additionalFld3;
	private boolean additionalFld4;
	private boolean additionalFld5;
	private String device_id;
	private String session_key;
	private String opp_id;
	private String  con_id;
	private String loanExt_id;


	public String getOpp_id() {
		return opp_id;
	}

	public void setOpp_id(String opp_id) {
		this.opp_id = opp_id;
	}

	public String getCon_id() {
		return con_id;
	}

	public void setCon_id(String con_id) {
		this.con_id = con_id;
	}

	public String getLoanExt_id() {
		return loanExt_id;
	}

	public void setLoanExt_id(String loanExt_id) {
		this.loanExt_id = loanExt_id;
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

	public void setSession_key(String session_key) {
		this.session_key = session_key;
	}

	

	public String getStage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}

	public String getEnableTouchID() {
		return EnableTouchID;
	}

	public void setEnableTouchID(String enableTouchID) {
		EnableTouchID = enableTouchID;
	}

	public String getLead_id() {
		return lead_id;
	}

	public void setLead_id(String lead_id) {
		this.lead_id = lead_id;
	}

	public boolean getCallback() {
		return callback;
	}

	public void setCallback(boolean callback) {
		this.callback = callback;
	}

	public boolean isGenerate_agreement_forms() {
		return generate_agreement_forms;
	}

	public void setGenerate_agreement_forms(boolean generate_agreement_forms) {
		this.generate_agreement_forms = generate_agreement_forms;
	}

	public boolean isApplicationidle() {
		return applicationidle;
	}

	public void setApplicationidle(boolean applicationidle) {
		this.applicationidle = applicationidle;
	}

	public boolean isAdditionalFld3() {
		return additionalFld3;
	}

	public void setAdditionalFld3(boolean additionalFld3) {
		this.additionalFld3 = additionalFld3;
	}

	public boolean isAdditionalFld4() {
		return additionalFld4;
	}

	public void setAdditionalFld4(boolean additionalFld4) {
		this.additionalFld4 = additionalFld4;
	}

	public boolean isAdditionalFld5() {
		return additionalFld5;
	}

	public void setAdditionalFld5(boolean additionalFld5) {
		this.additionalFld5 = additionalFld5;
	}

}
