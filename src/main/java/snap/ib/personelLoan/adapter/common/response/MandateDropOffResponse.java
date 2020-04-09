package snap.ib.personelLoan.adapter.common.response;

import java.util.ArrayList;

public class MandateDropOffResponse {
	
	private ArrayList<String> listPoints;
	private boolean success;
	private Object error_message;
	
	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Object getError_message() {
		return error_message;
	}

	public void setError_message(Object error_message) {
		this.error_message = error_message;
	}

	public ArrayList<String> getListPoints() {
		return this.listPoints;
	}

	public void setListPoints(ArrayList<String> listPoints) {
		this.listPoints = listPoints;
	}
}