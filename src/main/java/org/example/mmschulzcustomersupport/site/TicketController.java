package org.example.mmschulzcustomersupport.site;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

@Controller
@RequestMapping("ticket")
public class TicketController {
    private volatile int TICKET_ID = 1;
    private Map<Integer, Ticket> ticketDB = new LinkedHashMap<>();

    @RequestMapping(value={"list", ""})
    public String listTickets(Model model) {
        model.addAttribute("ticketDatabase", ticketDB);
        return "listTickets";
    }

    @GetMapping("create")
    public ModelAndView createTicket() {
        return new ModelAndView("ticketForm", "ticket", new TicketForm());
    }

    @PostMapping("create")
    public View createPost(@ModelAttribute("ticket")TicketForm form) throws IOException {
        Ticket ticket = new Ticket();
        ticket.setCustomerName(form.getCustomerName());
        ticket.setSubject(form.getSubject());
        ticket.setBodyOfTheTicket(form.getBodyOfTheSubject());

        MultipartFile file = form.getAttachments();
        Attachment attachment = new Attachment();
        attachment.setName(file.getOriginalFilename());
        attachment.setContents(file.getBytes());
        if ((attachment.getName() != null && attachment.getName().length() > 0 ||
                (attachment.getContents() != null && attachment.getContents().length > 0 ))) {
            ticket.setAttachment(attachment);
        }
        int id;
        synchronized (this) {
            id = this.TICKET_ID++;
            ticketDB.put(id, ticket);
        }

        return new RedirectView("view/" +id, true, false);

    }

    @GetMapping("view/{ticketId}")
    public ModelAndView viewPost(Model model, @PathVariable("ticketId")int ticketId) {
        Ticket ticket = ticketDB.get(ticketId);

        if (ticket == null) {
            return new ModelAndView(new RedirectView("ticket/list", true, false));
        }


        model.addAttribute("ticketId", ticketId);
        model.addAttribute("ticket", ticket);

        return new ModelAndView("viewTicket");

    }


    @GetMapping("/{ticketId}/attachment/{attachment:.+}")
    public View downloadAttachment(@PathVariable("ticketId") int ticketId, @PathVariable("attachment")String name) {
        Ticket ticket = ticketDB.get(ticketId);

        if (ticket == null) {
            return new RedirectView("listTickets", true, false);
        }

        Attachment attachment = ticket.getAttachment();
        if(attachment == null) {
            return new RedirectView("listTickets", true, false);
        }
        return new DownloadView(attachment.getName(), attachment.getContents());
    }
    public static class TicketForm {
        private String customerName;
        private String subject;
        private String bodyOfTheSubject;
        private MultipartFile attachments;

        public String getCustomerName() {
            return customerName;
        }

        public String getSubject() {
            return subject;
        }

        public String getBodyOfTheSubject() {
            return bodyOfTheSubject;
        }

        public MultipartFile getAttachments() {
            return attachments;
        }

        public void setCustomerName(String customerName) {
            this.customerName = customerName;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public void setBodyOfTheSubject(String bodyOfTheSubject) {
            this.bodyOfTheSubject = bodyOfTheSubject;
        }

        public void setAttachments(MultipartFile attachments) {
            this.attachments = attachments;
        }
    }
}
