<%@ page import="com.tlf.util.LoginHelper"%>
<%@ page language="java" contentType="text/html; charset=US-ASCII" pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Login</title>
<link rel="stylesheet" href="/request.css" id="Stylesheet">
</head>
<body bgcolor="black">
	<p align="right">
		<a href=".."><font color="white" size="5">Main site</font></a>
	</p>
	<center>
		<h2>
			<font color="white">Please Login</font>
		</h2>

		<%
			int attempt = LoginHelper.instance.getLoginAttempt(session);
			int maxAttemps = LoginHelper.maxAttemps;
			int remaining = maxAttemps - attempt;
			
			if (attempt > -1) {
				out.println("<font color=\"red\">Username or password is incorrect</font><br>");
				
				if (remaining == 1) {
					out.println("<font color=\"red\">This is your final attempt</font><br>");
				} else {
					out.println(String
							.format("<font color=\"red\">You have %s attempts left</font><br>",
									remaining));
				}
			}
		%>

		<form action="LoginServlet" method="post">
			Username: <input type="text" name="user" /> <br> Password: <input type="password" name="pwd" /> <br> <input type="submit" value="Log in">
		</form>
	</center>
</body>
</html>