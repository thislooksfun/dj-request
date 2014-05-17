<%@ page import="com.tlf.util.LoginHelper"%>
<%@ page language="java" contentType="text/html; charset=US-ASCII" pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Login</title>
<script type="text/javascript" src="http://code.jquery.com/jquery-latest.min.js"></script>
<link rel="stylesheet" href="/request.css">
<style type="text/css">
* {
	font-size: 25px;
}

label {
	display: inline-block;
}

div.container_body {
	position: absolute;
	width: 100%;
	height: 100%;
	top: 0px;
	left: 0px;
}
div.container_login {
	background-color: #333;
	position: relative;
	width: 450px;
	padding: 10px;
	top: 150px;
	margin: 0 auto;
	box-shadow: 5px 5px 10px 10px #111;
	border-radius: 10px;
}
div.container_header {
	text-align: right;
	padding: 5px;
}
</style>
</head>
<body>
	<div id="bodyContainer" class="container_body">
		<div id="headerContainer" class="container_header">
			<a href="/"><font size="40px">Main site</font></a>
		</div>
		<div id="loginContainer" class="container_login">
			<h2>Please Login</h2>
			
			<%
				int attempt = LoginHelper.instance.getLoginAttempt(session);
				int maxAttemps = LoginHelper.maxAttemps;
				int remaining = maxAttemps - attempt;
				
				if (attempt > -1) {
					out.println("<font color=\"red\">Username or password is incorrect</font><br>");
					
					if (remaining == 1) {
						out.println("<font color=\"red\">This is your final attempt</font><br>");
					} else {
						out.println(String.format("<font color=\"red\">You have %s attempts left</font><br>", remaining));
					}
				}
			%>

			<form action="LoginServlet" method="post">
				<label>Username</label><input type="text" name="user"><br>
				<label>Password</label><input type="password" name="pwd"><br>
				<input type="submit" value="Log in">
			</form>
		</div>
	</div>
	<script type="text/javascript">
		$(document).ready(function() {
			var max = 0;
			$("label").each(function() {
				if ($(this).width() > max) {
					max = $(this).width();
				}
			});
			$("label").width((max+10) + "px");
		});
	</script>
</body>
</html>