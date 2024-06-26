package org.example.mmschulzcustomersupport.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Arrays;

@Entity
@Table(name = "attachment")
public class Attachment implements Serializable {
    private static final long serialVersionUID = 1L;

    private long id;
    private long ticketId;

    private String name;
    private byte[] contents;

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
    public long getTicketId() {
        return ticketId;
    }

    public void setTicketId(long ticketId) {
        this.ticketId = ticketId;
    }

    @Basic
    public String getName() {
        return name;
    }

    @Lob
    public byte[] getContents() {
        return contents;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setContents(byte[] contents) {
        this.contents = contents;
    }
    @Override
    public String toString() {
        return "Attachments{" +
                "name='" + name + '\'' +
                ", contents=" + Arrays.toString(contents).substring(0, 100) +
                '}';
    }
}
