package snap.ib.personelLoan.adapter.common.response;

import java.util.ArrayList;

import snap.ib.personelLoan.adapter.common.beans.FAQ;


public class FAQResponse {

	private boolean success;
	private ArrayList<FAQ> faqList;
	private Object error_message;
	
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public ArrayList<FAQ> getFaqList() {
		return faqList;
	}
	public void setFaqList(ArrayList<FAQ> faqList) {
		this.faqList = faqList;
	}
	public Object getError_message() {
		return error_message;
	}
	public void setError_message(Object error_message) {
		this.error_message = error_message;
	}
	
}
