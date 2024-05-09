<html>
<head>
    <title>Ticket #<c:out value="${ticketId}"/></title>
</head>
<body>
<a href="<c:url value='/logout'/>">Logout</a>
<h2>Ticket</h2>
<h3>Ticket #<c:out value="${ticketId}"/>: <c:out value="${ticket.customerName}"/></h3>
<p>Subject: <c:out value="${ticket.subject}"/></p>
<p><c:out value="${ticket.bodyOfTheTicket}"/></p>
<c:if test="${ticket.hasFile()}">
    <a href="<c:url value='/ticket/${ticketId}/attachment/${ticket.attachment.name}' />">
        <c:out value="${ticket.attachment.name}"/></a>
</c:if>
<br><a href="<c:url value='/ticket/list'/>">Return to ticket list</a>

</body>
</html>
