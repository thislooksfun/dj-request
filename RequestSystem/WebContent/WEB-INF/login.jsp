<%@ page import="com.tlf.util.LoginHelper"%>
<%@ page language="java" contentType="text/html; charset=US-ASCII" pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Login</title>
<script type="text/javascript" src="/jQuery.min.js"></script>
<link rel="stylesheet" href="/request.css">
<style type="text/css">
* {
	font-size: 30px;
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
	width: 390px;
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
div.spacer {
	width: 100%;
	height: 5px;
}

.big-btn {
	background-color: #888;
	border-color: #aaa;
	color: #000;
    width: 100px;
    height: 45px;
    border-radius: 8px;
    -moz-border-radius: 8px;
    -webkit-border-radius: 8px;
    padding: 0px;
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

			<form action="login" method="post">
				<input type="text" name="user" placeholder="Username"><br>
				<input type="password" name="pwd" placeholder="Password"><br>
				<div class="spacer"></div>
				<div class="center"><input class="big-btn" type="submit" value="Log in"></div>
			</form>
		</div>
	</div>
</body>
</html>