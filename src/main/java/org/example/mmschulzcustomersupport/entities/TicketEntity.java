package org.example.mmschulzcustomersupport.entities;


import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "tickets")
public class TicketEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    private long id;
    private String customerName;
    private String subject;
    private String bodyOfTheTicket;

@Id
@Column(name="id")
@GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    @Basic
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Basic
    public String getBodyOfTheTicket() {
        return bodyOfTheTicket;
    }

    public void setBodyOfTheTicket(String bodyOfTheTicket) {
        this.bodyOfTheTicket = bodyOfTheTicket;
    }

    @Override
    public String toString() {
        return "TicketEntity{" +
                "id='" + id + '\'' +
                ", customerName=" + customerName +
                ", subject='" + subject +
                ", bodyOfTheTicket=" + bodyOfTheTicket +
                '}';
    }
}
