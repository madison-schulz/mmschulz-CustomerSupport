package org.example.mmschulzcustomersupport.site;


public class Ticket {
    private String customerName;
    private String subject;
    private String bodyOfTheTicket;
    private Attachment attachment;
    public boolean hasFile() {
        return attachment != null && attachment.getName().length() > 0 && attachment.getContents().length > 0;
    }

    public Ticket() {
        super();
    }

    public Ticket(String customerName, String subject, String bodyOfTheTicket, Attachment attachment) {
        this.customerName = customerName;
        this.subject = subject;
        this.bodyOfTheTicket = bodyOfTheTicket;
        this.attachment = attachment;
    }



    public String getSubject() {
        return subject;
    }

    public String getBodyOfTheTicket() {
        return bodyOfTheTicket;
    }

    public Attachment getAttachment() {
        return attachment;
    }
    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setBodyOfTheTicket(String bodyOfTheTicket) {
        this.bodyOfTheTicket = bodyOfTheTicket;
    }

    public void setAttachment(Attachment attachment) {
        this.attachment = attachment;
    }

    public void addAttachment(Attachment attachments) {
    }
    @Override
    public String toString() {
        return "Ticket{" +
                "customerName='" + customerName + '\'' +
                ", subject=" + subject +
                ", bodyOfTheTicket='" + bodyOfTheTicket + '\'' +
                ", attachment=" + attachment +
                '}';
    }
}
