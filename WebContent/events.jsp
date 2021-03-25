<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="style.css">
<title>CalendarApp</title>
</head>
<body>
	<div class="container_table">
		<div class="title">
			<h1>Your events</h1>
		</div>

		<jsp:include page="/DisplayEventsServlet" />
		<div class="buttons">
			<a class="button" href="createEvent.jsp">Add new event</a> <a
				class="button" href="signout.jsp">Sign out</a>
		</div>
	</div>
</body>
</html>