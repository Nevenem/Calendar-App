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
	<div class="container">
		<i> <%
 if (request.getParameter("status") != null) {
 	if (request.getParameter("status").equals("failed")) {
 		out.println("Invalid login, try again!");
 	} else if (request.getParameter("status").equals("activation_success")) {
 		out.println("Account activation successful, please log in.");
 	} else if (request.getParameter("status").equals("activation_failed")) {
 		out.println("Invalid activation code!");
 	}
 }
 %>
		</i>
		<jsp:include page="/LoginServlet" />
	</div>
</body>
</html>