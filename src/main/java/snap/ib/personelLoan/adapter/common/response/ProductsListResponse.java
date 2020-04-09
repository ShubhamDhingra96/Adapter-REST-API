package snap.ib.personelLoan.adapter.common.response;

import java.util.ArrayList;

import snap.ib.personelLoan.adapter.common.beans.ProductList;

public class ProductsListResponse {

	private boolean success;
	private ArrayList<ProductList> productList;
	private Object error_message;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public ArrayList<ProductList> getList() {
		return productList;
	}

	public void setList(ArrayList<ProductList> list) {
		this.productList = list;
	}

	public Object getError_message() {
		return error_message;
	}

	public void setError_message(Object error_message) {
		this.error_message = error_message;
	}

}
