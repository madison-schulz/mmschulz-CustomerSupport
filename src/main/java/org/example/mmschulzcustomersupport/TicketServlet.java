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
        response.setContentType("text/html");

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
            response.setContentType("text/html");

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
        PrintWriter out = response.getWriter();

        out.println("<html><body><h2>Tickets</h2>");
        out.println("<a href=\"ticket?action=createTicket\">Create Ticket</a><br><br>");

        if (ticketDB.size() == 0) {
            out.println("There are no tickets yet. . .");
        } else {
            for (int id : ticketDB.keySet()) {
                Ticket ticket = ticketDB.get(id);
                out.println(": <a href=\"ticket?action=view&ticketId=" + id + "\">");
                out.println(ticket.getCustomerName() + "</a><br>");
            }
        }
        out.println("</body></html>");
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

            PrintWriter out = response.getWriter();
            out.println("<html><body><h2>Ticket Form</h2>");
            out.println("<h3>" + ticket.getCustomerName() + "/h3>");
            out.println("<p>Subject: " + ticket.getSubject() + "</p>");
            out.println("<p>" + ticket.getBodyOfTheTicket() + "</p>");
            if (ticket.hasFile()) {
                out.println("<a href=\"ticket?action=download&ticketId=" +
                        idString + "&attachments=\" + ticket.getAttachments().getName() " +
                        "+ \"> + ticket.getAttachments().getName()</a><br><br>");
            }
            out.println("<a href=\"ticket\">Return to Ticket list</a>");
            out.println("</body></html>");
    }

    private void showTicketForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();

        out.println("<html><body><h2>Create a Ticket</h2>");
        out.println("<form method=\"POST\" action=\"ticket\" enctype=\"multipart/form-data\">");
        out.println("<input type=\"hidden\" name=\"action\" value=\"createTicket\">");
        out.println("Name:<br>");
        out.println("<input type=\"text\"name=\"customerName\"><br><br>");
        out.println("Subject:<br>");
        out.println("<input type=\"text\"name=\"subject\"><br><br>");
        out.println("Body:<br>");
        out.println("<textarea name=\"body\" rows=\"25\"cols=\"100\"></textarea><br><br>");
        out.println("<br>Attachments</br>");
        out.println("<input type=\"file\"name=\"file\"><br><br>");
        out.println("<input type=\"submit\" value=\"Submit\">");
        out.println("</form></body></html>");
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
