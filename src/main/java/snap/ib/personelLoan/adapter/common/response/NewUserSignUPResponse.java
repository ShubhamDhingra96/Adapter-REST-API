package snap.ib.personelLoan.adapter.common.response;

import snap.ib.personelLoan.adapter.common.beans.List_loans_detail;

public class NewUserSignUPResponse {

		private String crm_id;
		private String mpin;
		private String status;
		private String error_message;
		private String stage;
	    private String cas_id;
		private String success;
		private String enableTouchID;
		private List_loans_detail[] list_loans_detail;
	
		// 4 id's are added
		private String lead_id;
		private String opp_id;
		private String con_id;
		private String loanExt_id;
		private String acc_id;
		private String contact_source;
		private boolean has_foreclosed_loan;
		private Foreclosed_loan_details[] foreclosed_loan_details;
		private boolean has_disbursed_top_up;
		private boolean is_prepaid_card_loan;
		
		
		
		public boolean isIs_prepaid_card_loan() {
			return is_prepaid_card_loan;
		}
		public void setIs_prepaid_card_loan(boolean is_prepaid_card_loan) {
			this.is_prepaid_card_loan = is_prepaid_card_loan;
		}
		
		public boolean isHas_disbursed_top_up() {
			return has_disbursed_top_up;
		}
		public void setHas_disbursed_top_up(boolean has_disbursed_top_up) {
			this.has_disbursed_top_up = has_disbursed_top_up;
		}
		
		public boolean isHas_foreclosed_loan() {
			return has_foreclosed_loan;
		}
		public void setHas_foreclosed_loan(boolean has_foreclosed_loan) {
			this.has_foreclosed_loan = has_foreclosed_loan;
		}
		public Foreclosed_loan_details[] getForeclosed_loan_details() {
			return foreclosed_loan_details;
		}
		public void setForeclosed_loan_details(Foreclosed_loan_details[] foreclosed_loan_details) {
			this.foreclosed_loan_details = foreclosed_loan_details;
		}
		
		
		
		public String getContact_source() {
			return contact_source;
		}
		public void setContact_source(String contact_source) {
			this.contact_source = contact_source;
		}
		public String getAcc_id() {
			return acc_id;
		}
		public void setAcc_id(String acc_id) {
			this.acc_id = acc_id;
		}
		public String getLead_id() {
			return lead_id;
		}
		public void setLead_id(String lead_id) {
			this.lead_id = lead_id;
		}
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
		
		
	public String getCas_id() {
		return cas_id;
	}
	public void setCas_id(String cas_id) {
		this.cas_id = cas_id;
	}
	public String getCrm_id() {
		return crm_id;
	}
	public void setCrm_id(String crm_id) {
		this.crm_id = crm_id;
	}
	public String getMpin() {
		return mpin;
	}
	public void setMpin(String mpin) {
		this.mpin = mpin;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getError_message() {
		return error_message;
	}
	public void setError_message(String error_message) {
		this.error_message = error_message;
	}
	public String getStage() {
		return stage;
	}
	public void setStage(String stage) {
		this.stage = stage;
	}
	public String getSuccess() {
		return success;
	}
	public void setSuccess(String success) {
		this.success = success;
	}
	public String getEnableTouchID() {
		return enableTouchID;
	}
	public void setEnableTouchID(String enableTouchID) {
		this.enableTouchID = enableTouchID;
	}
	public List_loans_detail[] getList_loans_detail() {
		return list_loans_detail;
	}
	public void setList_loans_detail(List_loans_detail[] list_loans_detail) {
		this.list_loans_detail = list_loans_detail;
	}
}
