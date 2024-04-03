package org.example.mmschulzcustomersupport;

public class Ticket {
    private String customerName;
    private String subject;
    private String bodyOfTheTicket;
    public String attachments;
    public boolean hasFile() {
        return Attachment.getName().length() > 0 && Attachment.getContents().length > 0;
    }

    public Ticket() {
        super();
    }

    public Ticket(String customerName, String subject, String bodyOfTheTicket, String attatchents) {
        this.customerName = customerName;
        this.subject = subject;
        this.bodyOfTheTicket = bodyOfTheTicket;
        this.attachments = attatchents;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getSubject() {
        return subject;
    }

    public String getBodyOfTheTicket() {
        return bodyOfTheTicket;
    }

    public String getAttachments() {
        return attachments;
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

    public void setAttatchents(String attatchents) {
        this.attachments = attatchents;
    }

    public void addAttachment(Attachment attachments) {
    }
    @Override
    public String toString() {
        return "Ticket{" +
                "customerName='" + customerName + '\'' +
                ", subject=" + subject +
                ", bodyOfTheTicket='" + bodyOfTheTicket + '\'' +
                ", attachments=" + attachments +
                '}';
    }
}
