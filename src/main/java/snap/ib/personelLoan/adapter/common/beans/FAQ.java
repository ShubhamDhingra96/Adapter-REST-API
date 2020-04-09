package snap.ib.personelLoan.adapter.common.beans;

import java.util.ArrayList;

public class FAQ {

	private String category;
	private ArrayList<FAQ_List> list;

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public ArrayList<FAQ_List> getList() {
		return list;
	}

	public void setList(ArrayList<FAQ_List> list) {
		this.list = list;
	}
}
