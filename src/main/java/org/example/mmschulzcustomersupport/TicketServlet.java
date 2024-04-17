package org.example.mmschulzcustomersupport;
import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
@WebServlet(name = "ticket", value="/ticket", loadOnStartup = 1)
@MultipartConfig(fileSizeThreshold = 5_242_880, maxFileSize = 20_971_520L, maxRequestSize = 41_943_040L)
public class TicketServlet extends HttpServlet {
    private volatile int TICKET_ID = 1;
    private Map<Integer, Ticket> ticketDB = new LinkedHashMap<>();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null) {
            action = "list";
        }
        switch (action) {
            case "createTicket" -> this.showTicketForm(request, response);
            case "view" -> this.viewTicket(request, response);
            case "download" -> this.downloadAttachment(request, response);
            default -> this.listTickets(request, response);
        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        {
            String action = request.getParameter("action");

            if (action == null) {
                action = "list";
            }
            switch (action) {
                case "createTicket" -> this.createTicket(request, response);
                default -> response.sendRedirect("ticket");
            }
        }
    }

    private void listTickets(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("ticketDatabase", ticketDB);

        request.getRequestDispatcher("WEB-INF/jsp/view/listTickets.jsp").forward(request, response);
    }

    private void createTicket(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Ticket ticket = new Ticket();
        ticket.setCustomerName(request.getParameter("customerName"));
        ticket.setSubject(request.getParameter("subject"));
        ticket.setBodyOfTheTicket(request.getParameter("body"));

        Part filePart = request.getPart("file");
        if (filePart != null) {
            Attachment attachments = this.processAttachment(filePart);
            if (attachments != null)
                ticket.addAttachment(attachments);
        }
        int id;
        synchronized (this) {
            id = this.TICKET_ID++;
            ticketDB.put(id, ticket);
        }
        response.sendRedirect("ticket?action=view&ticketId=" + id);
    }

    public Attachment processAttachment(Part filePart) throws IOException {
        InputStream inputStream = filePart.getInputStream();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        int read;
        final byte[] bytes = new byte[1024];
        while ((read = inputStream.read(bytes)) != -1) {
            outputStream.write(bytes, 0, read);
        }
        Attachment attachments = new Attachment();

        attachments.setName(filePart.getSubmittedFileName());
        attachments.setContents(outputStream.toByteArray());

        return attachments;
    }

    private void downloadAttachment(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idString = request.getParameter("ticketId");

        Ticket ticket = getTicket(idString, response);

        String name = request.getParameter("attachments");
        if (name == null) {
            response.sendRedirect("ticket?action=view&ticketId=" + idString);
        }
        String attachments = ticket.getAttachments();
        if (attachments == null) {
            response.sendRedirect("blog?action=view&blogId=" + idString);
            return;
        }

        response.setHeader("Content-Disposition", "attachments; filename=" + Attachment.getName());
        response.setContentType("application/octet-stream");

        ServletOutputStream stream = response.getOutputStream();
        stream.write(Attachment.getContents());
    }

    private void viewTicket(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            String idString = request.getParameter("ticketId");
            Ticket ticket = getTicket(idString, response);

            request.setAttribute("ticket", ticket);
            request.setAttribute("ticketId", idString);

            request.getRequestDispatcher("WEB-INF/jsp/view/viewTicket.jsp").forward(request, response);

            /*PrintWriter out = response.getWriter();
            out.println("<html><body><h2>Ticket Form</h2>");
            out.println("<h3>" + ticket.getCustomerName() + "/h3>");
            out.println("<p>Subject: " + ticket.getSubject() + "</p>");
            out.println("<p>" + ticket.getBodyOfTheTicket() + "</p>");
            if (ticket.hasFile()) {
                out.println("<a href=\"ticket?action=download&ticketId=" +
                        idString + "&attachments=\" + ticket.getAttachments().getCustomerName() " +
                        "+ \"> + ticket.getAttachments().getCustomerName()</a><br><br>");
            }
            out.println("<a href=\"ticket\">Return to Ticket list</a>");
            out.println("</body></html>");*/
    }

    private void showTicketForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("WEB-INF/jsp/view/ticketForm.jsp").forward(request, response);
    }

    private Ticket getTicket(String idString, HttpServletResponse response) throws ServletException, IOException {
        if (idString == null || idString.length() == 0) {
            response.sendRedirect("ticket");
            return null;
        }
        try {
            int id = Integer.parseInt(idString);
            Ticket ticket = ticketDB.get(id);
            if (ticket == null) {
                response.sendRedirect("ticket");
                return null;
            }
            return ticket;
        }
        catch (Exception e) {
            response.sendRedirect("ticket");
            return null;
        }
    }
}
