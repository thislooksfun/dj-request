<%@ page import="com.tlf.util.LoginHelper"%>
<%@ page language="java" contentType="text/html; charset=US-ASCII" pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Admin site</title>
</head>
<body bgcolor="black">
	<form action="LogoutServlet" method="get">
		<p align="right">
			<input type="submit" value="Logout">
		</p>
	</form>
	<p align="right">
		<a href=".."><font color="white" size="5">Main site</font></a>
	</p>
	<center>
		<h1>
			<font color="white">Admin site</font>
		</h1>
		<h2>
			<font color="white">Logged in as <%
				out.print(LoginHelper.instance.getUserForSession(session));
			%>
			</font>
		</h2>
	</center>
</body>
</html>