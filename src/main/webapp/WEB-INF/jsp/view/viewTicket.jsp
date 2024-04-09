<%
    String ticketId = (String)request.getAttribute("ticketID");
    Ticket ticket = (Ticket)request.getAttribute("ticket");
%>

<html>
<head>
    <title>Ticket <%=ticketId%></title>
</head>
<body>
    <h2>Ticket</h2>
    <h3>Ticket: <%=ticket.getCustomerName()%></h3>
    <p>Subject: <%=ticket.getSubject()%></p>
    <p><%=ticket.getBodyOfTheTicket()%></p>
    <%if (ticket.hasFile()) {%>
    <a href="ticket?action=download&ticketId=<%=ticketId%>&attachments=<%=ticket.getAttachments()%>">
        <%=ticket.getAttachments()%></a>
    <%}%>
    <br><a href="ticket">Return to Ticket list</a>
</body>
</html>
