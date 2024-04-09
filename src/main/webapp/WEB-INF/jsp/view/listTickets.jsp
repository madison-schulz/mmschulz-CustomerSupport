<%@ page import="java.util.Map" %>
<%
    @SuppressWarnings("unchecked")
    Map<Integer, Ticket> db = (Map<Integer, Ticket>)request.getAttribute("ticketDatabase");
%>

<html>
<head>
    <title>Tickets</title>
</head>
<body>
    <h2>Tickets</h2>
    <a href="ticket?action=createTicket">Create Ticket</a><br><br>
    <%
        if (db.size() == 0) {%>
            <%="There are no tickets here yet..."%>
    <%}
        else {
            for (int id : db.keySet()) {
                Ticket ticket = db.get(id);%>
                <%="Ticket # " + id%>
                    <a href="ticket?action=view&ticketId=<%=id%>"><%=ticket.getCustomerName()%></a><br>
            <%}
    }%>

</body>
</html>
