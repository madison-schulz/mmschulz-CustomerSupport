<html>
<head>
    <title>Ticket #<c:out value="${ticketId}"/></title>
</head>
<body>
<a href="<c:url value='/login'>
        <c:param name='logout'/>
    </c:url>">Logout</a>
<h2>Ticket</h2>
<h3>Ticket #<c:out value="${ticketId}"/>: <c:out value="${ticket.customerName}"/></h3>
<p>Subject: <c:out value="${ticket.subject}"/></p>
<p><c:out value="${ticket.bodyOfTheTicket}"/></p>
<c:if test="${ticket.hasFile()}">
    <a href="<c:url value='/ticket' >
            <c:param name='action' value='download' />
            <c:param name='ticketId' value='${ticketId}' />
            <c:param name='attachment' value='${ticket.attachments.name}'/>
        </c:url>"><c:out value="${ticket.attachments.name}"/></a>
</c:if>
<br><a href="ticket">Return to ticket list</a>

</body>
</html>
