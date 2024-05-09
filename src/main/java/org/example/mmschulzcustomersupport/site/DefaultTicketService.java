package org.example.mmschulzcustomersupport.site;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.example.mmschulzcustomersupport.entities.Attachment;
import org.example.mmschulzcustomersupport.entities.TicketEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DefaultTicketService implements TicketService {

    @Inject TicketRepository ticketRepo;
    @Inject AttachmentRepository attachmentRepo;

    @Override
    @Transactional
    public List<Ticket> getAllTickets() {
        List<Ticket> list = new ArrayList<>();
        ticketRepo.getAll().forEach(e -> list.add(this.convert(e)));
        return list;
    }

    @Override
    public Ticket getTicket(long id) {
        TicketEntity entity = ticketRepo.get(id);
        return entity == null ? null : this.convert(entity);
    }

    private Ticket convert(TicketEntity entity) {
        Ticket ticket = new Ticket();
        ticket.setId(entity.getId());
        ticket.setCustomerName(entity.getCustomerName());
        ticket.setSubject(entity.getSubject());
        ticket.setBodyOfTheTicket(entity.getBodyOfTheTicket());
        ticket.setAttachment(attachmentRepo.getByTicketId(entity.getId()));

        return ticket;

    }
    @Override
    @Transactional
    public void save(Ticket ticket) {
        TicketEntity entity = new TicketEntity();
        entity.setId(ticket.getId());
        entity.setCustomerName(ticket.getCustomerName());
        entity.setSubject(ticket.getSubject());
        entity.setBodyOfTheTicket(ticket.getBodyOfTheTicket());

        if (ticket.hasFile()) {
            // add to the repo
            ticketRepo.add(entity);
            ticket.setId(entity.getId()); // get the id from the entity to use in the Controller to actually view the blog
            ticket.getAttachment().setTicketId(entity.getId());
            System.out.println("TicketEntity: " + entity);
            System.out.println("Ticket: " + ticket);
            System.out.println("Ticket Attachment: " + ticket.getAttachment());
            attachmentRepo.add(ticket.getAttachment());
        }
        else {
            ticketRepo.update(entity);
        }
    }

    @Override
    @Transactional
    public void deleteTicket(long id) {
        ticketRepo.deleteById(id);
    }
}
