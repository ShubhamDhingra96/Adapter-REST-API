package snap.ib.personelLoan.adapter.common.beans;

public class Document_master
{
    private String document_status;

    private String document_name;

    private String document_add_2;

    private String document_add_1;

    public String getDocument_status ()
    {
        return document_status;
    }

    public void setDocument_status (String document_status)
    {
        this.document_status = document_status;
    }

    public String getDocument_name ()
    {
        return document_name;
    }

    public void setDocument_name (String document_name)
    {
        this.document_name = document_name;
    }

    public String getDocument_add_2 ()
    {
        return document_add_2;
    }

    public void setDocument_add_2 (String document_add_2)
    {
        this.document_add_2 = document_add_2;
    }

    public String getDocument_add_1 ()
    {
        return document_add_1;
    }

    public void setDocument_add_1 (String document_add_1)
    {
        this.document_add_1 = document_add_1;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [document_status = "+document_status+", document_name = "+document_name+", document_add_2 = "+document_add_2+", document_add_1 = "+document_add_1+"]";
    }
}