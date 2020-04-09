package snap.ib.personelLoan.adapter.common.beans;

public class DocsList {
	
	private String DocType;
	private String DocBase64;
	private String DocFiller2;
	private String DocFiller1;

	public String getDocType() {
		return DocType;
	}

	public void setDocType(String DocType) {
		this.DocType = DocType;
	}

	public String getDocBase64() {
		return DocBase64;
	}

	public void setDocBase64(String DocBase64) {
		this.DocBase64 = DocBase64;
	}

	public String getDocFiller2() {
		return DocFiller2;
	}

	public void setDocFiller2(String DocFiller2) {
		this.DocFiller2 = DocFiller2;
	}

	public String getDocFiller1() {
		return DocFiller1;
	}

	public void setDocFiller1(String DocFiller1) {
		this.DocFiller1 = DocFiller1;
	}

	@Override
	public String toString() {
		return "ClassPojo [DocType = " + DocType + ", DocBase64 = " + DocBase64 + ", DocFiller2 = " + DocFiller2
				+ ", DocFiller1 = " + DocFiller1 + "]";
	}
}