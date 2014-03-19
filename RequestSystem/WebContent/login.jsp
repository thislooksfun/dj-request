<%@page import="com.tlf.util.LoginHelper"%>
<%@ page language="java" contentType="text/html; charset=US-ASCII" pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Login</title>
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
			int maxAttemps = 5;
			
			if (attempt == -2) {
				out.println("<font color=\"red\">That user is already logged in</font><br>");
			} else if (attempt > 0) {
				out.println("<font color=\"red\">Username or password is incorrect</font><br>");
				out.println(String
						.format("<font color=\"red\">You have %s attempt%s left</font><br>",
								maxAttemps - attempt,
								maxAttemps - attempt == 1 ? "" : "s"));
			}
		%>

		<form method="post" action="LoginServlet">
			<font color="white"> Username: <input type="text" name="user" /> <br> Password: <input type="password" name="pwd" /> <br> <input type="submit" value="Log in">
			</font>
		</form>
	</center>
</body>

</html>