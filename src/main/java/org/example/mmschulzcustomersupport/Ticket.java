package org.example.mmschulzcustomersupport;

public class Ticket {
    private String customerName;
    private String subject;
    private String bodyOfTheTicket;
    private String attatchents;

    public Ticket() {
        super();
    }

    public Ticket(String customerName, String subject, String bodyOfTheTicket, String attatchents) {
        this.customerName = customerName;
        this.subject = subject;
        this.bodyOfTheTicket = bodyOfTheTicket;
        this.attatchents = attatchents;
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

    public String getAttatchents() {
        return attatchents;
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
        this.attatchents = attatchents;
    }
}
