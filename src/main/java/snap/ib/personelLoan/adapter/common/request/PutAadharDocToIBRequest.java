package snap.ib.personelLoan.adapter.common.request;

import snap.ib.personelLoan.adapter.common.beans.DocsList;

public class PutAadharDocToIBRequest {
	
	private DocsList[] DocsList;
	private String LeadID;
	private String ReqFiller1;
	private String ReqFiller3;
	private String ReqFiller2;
	private String DocCount;

	public DocsList[] getDocsList() {
		return DocsList;
	}

	public void setDocsList(DocsList[] DocsList) {
		this.DocsList = DocsList;
	}

	public String getLeadID() {
		return LeadID;
	}

	public void setLeadID(String LeadID) {
		this.LeadID = LeadID;
	}

	public String getReqFiller1() {
		return ReqFiller1;
	}

	public void setReqFiller1(String ReqFiller1) {
		this.ReqFiller1 = ReqFiller1;
	}

	public String getReqFiller3() {
		return ReqFiller3;
	}

	public void setReqFiller3(String ReqFiller3) {
		this.ReqFiller3 = ReqFiller3;
	}

	public String getReqFiller2() {
		return ReqFiller2;
	}

	public void setReqFiller2(String ReqFiller2) {
		this.ReqFiller2 = ReqFiller2;
	}

	public String getDocCount() {
		return DocCount;
	}

	public void setDocCount(String DocCount) {
		this.DocCount = DocCount;
	}

	@Override
	public String toString() {
		return "ClassPojo [DocsList = " + DocsList + ", LeadID = " + LeadID + ", ReqFiller1 = " + ReqFiller1
				+ ", ReqFiller3 = " + ReqFiller3 + ", ReqFiller2 = " + ReqFiller2 + ", DocCount = " + DocCount + "]";
	}
}