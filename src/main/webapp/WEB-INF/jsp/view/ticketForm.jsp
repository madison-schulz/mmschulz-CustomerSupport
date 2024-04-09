<html>
<head>
    <title>Create a new ticket</title>
</head>
<body>
<h2>Create a Ticket</h2>
    <form method="POST" action="ticket" enctype="multipart/form-data">
        <input type="hidden" name="action" value="createTicket">
        Name:<br>
        <input type="text" name="customerName"><br><br>
        Subject:<br>
        <input type="text" name="subject"><br><br>
        Body:<br>
        <textarea name="body" rows="25" cols="100"></textarea><br><br>
        <br>Attachments</br><br>
        <input type="file" name="file"><br><br>
        <input type="submit" value="Submit">
    </form>
</body>
</html>
