package entities;


import java.util.Date;

public class SalesOrder {

    private long id;
    private String customer;
    private SalesOrderStatus salesOrderStatus;

    private Date postingDate;
    private  String packedBy;
    private String approvedBy;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public SalesOrderStatus getSalesOrderStatus() {
        return salesOrderStatus;
    }

    public void setSalesOrderStatus(SalesOrderStatus salesOrderStatus) {
        this.salesOrderStatus = salesOrderStatus;
    }

    public Date getPostingDate() {
        return postingDate;
    }

    public void setPostingDate(Date postingDate) {
        this.postingDate = postingDate;
    }

    public String getPackedBy() {
        return packedBy;
    }

    public void setPackedBy(String packedBy) {
        this.packedBy = packedBy;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }
}
