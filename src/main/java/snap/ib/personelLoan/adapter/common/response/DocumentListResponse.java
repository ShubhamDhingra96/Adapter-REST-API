package snap.ib.personelLoan.adapter.common.response;
import snap.ib.personelLoan.adapter.common.beans.Document_master;

public class DocumentListResponse
{
    private String additional_1;
    private String status;
    private String additional_2;
    private String additional_3;
    private String error_message;
    private Document_master[] document_master;
    private String success;
    private String fileSizeKB;
    private String maxDocLimit;

    public String getFileSizeKB() {
		return fileSizeKB;
	}

	public void setFileSizeKB(String finalFileSizeKB) {
		fileSizeKB = finalFileSizeKB;
	}

	public String getMaxDocLimit() {
		return maxDocLimit;
	}

	public void setMaxDocLimit(String finalMaxDocLimit) {
		maxDocLimit = finalMaxDocLimit;
	}

	public String getAdditional_1 ()
    {
        return additional_1;
    }

    public void setAdditional_1 (String additional_1)
    {
        this.additional_1 = additional_1;
    }

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    public String getAdditional_2 ()
    {
        return additional_2;
    }

    public void setAdditional_2 (String additional_2)
    {
        this.additional_2 = additional_2;
    }

    public String getAdditional_3 ()
    {
        return additional_3;
    }

    public void setAdditional_3 (String additional_3)
    {
        this.additional_3 = additional_3;
    }

    public String getError_message ()
    {
        return error_message;
    }

    public void setError_message (String error_message)
    {
        this.error_message = error_message;
    }

    public Document_master[] getDocument_master ()
    {
        return document_master;
    }

    public void setDocument_master (Document_master[] document_master)
    {
        this.document_master = document_master;
    }

    public String getSuccess ()
    {
        return success;
    }

    public void setSuccess (String success)
    {
        this.success = success;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [additional_1 = "+additional_1+", status = "+status+", additional_2 = "+additional_2+", additional_3 = "+additional_3+", error_message = "+error_message+", document_master = "+document_master+", success = "+success+"]";
    }
}