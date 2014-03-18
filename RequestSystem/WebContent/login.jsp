<%@ page language="java" contentType="text/html; charset=US-ASCII" pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Login</title>
</head>
<body bgcolor="black">
	<center>
		<h2>
			<font color="white">Please Login</font>
		</h2>

		<%
			Cookie[] cookies = request.getCookies();
			
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("attempt")) {
					out.println("<font color=\"red\">Username or password is incorrect</font><br>");
				}
			}
		%>

		<form method="post" action="LoginServlet">
			<font color="white"> Username: <input type="text" name="user" /> <br> Password: <input type="password" name="pwd" /> <br> <input type="submit" value="Log in">
			</font>
		</form>
	</center>
</body>

</html>