<%@ page import="com.tlf.util.LoginHelper"%>
<%@ page language="java" contentType="text/html; charset=US-ASCII" pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Login</title>
<script type="text/javascript" src="/jQuery.min.js"></script>
<link rel="stylesheet" href="/request.css">
<link rel="stylesheet" href="/formstyle.css"> <!-- Input style taken with permission from http://git.aaronlumsden.com/strength.js/#demo -->
<style type="text/css">
* {
	font-size: 30px;
}

label {
	display: inline-block;
}

div.container_page {
	position: absolute;
	top: 0px;
	left: 0px;
	height: 100%;
	width: 100%;
}
a {
	position: absolute;
	right: 50px;
	bottom: 20px;
	font-size: 25px;
}
</style>
</head>
<body>
	<div id="pageContainer" class="container_page">
		<div id="formContainer" class="container_form">
			<div id="formContainerInner" class="container_form_inner">
				<form id="uploadForm" action="login" method="post">
					<span class="center head">Login:</span>
			
					<%
						int attempt = LoginHelper.instance.getLoginAttempt(session);
						int maxAttemps = LoginHelper.maxAttemps;
						int remaining = maxAttemps - attempt;
						
						if (attempt > -1) {
							out.println("<br><font color=\"red\">Username or password is incorrect</font><br>");
							
							if (remaining == 1) {
								out.println("<font color=\"red\">This is your final attempt</font><br>");
							} else {
								out.println(String.format("<font color=\"red\">You have %s attempts left</font><br></br>", remaining));
							}
						}
					%>
					<div class="inputDiv">
						<input type="text" class="rounded" id="user" onkeyup="checkUser(event)" name="user" placeholder="Username"><br>
						<div class="errorDiv">
							<div id="usernameBlank" class="errorMsg">Can't be blank</div>
						</div>
					</div>
					<div class="inputDiv">
						<input type="password" class="rounded" id="pass" onkeyup="checkPassword(event)" name="pwd" placeholder="Password"><br>
						<div class="errorDiv">
							<div id="passwordBlank" class="errorMsg">Can't be blank</div>
						</div>
					</div>
					<div class="spacer"></div>
					<div class="center"><input type="button" id="submitButton" class="button" onclick="return validateForm();" value="Log in"></div>
				</form>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#submitButton").addClass("error");
			validateForm();
		});
		
		function checkUser(e)
		{
			if (e != null) {
				var charCode = (typeof e.which === "number") ? e.which : e.keyCode;
				if (charCode === 13) {
					validateForm();
					return;
				}
			}
			
			var form = document.forms["uploadForm"];
			var user = form["user"].value.trim();
			var check = true;
			
			if (user == null || user == "") {
				check = false;
				$("#user").addClass("error");
				$("#usernameBlank").fadeIn();
			} else {
				$("#user").removeClass("error");
				$("#usernameBlank").fadeOut();
			}
			
			if (check) {
				$("#submitButton").removeClass("error1");
			} else {
				$("#submitButton").addClass("error1");
			}
			
			checkButton();
			
			return check;
		}
		
		function checkPassword(e)
		{
			if (e != null) {
				var charCode = (typeof e.which === "number") ? e.which : e.keyCode;
				if (charCode === 13) {
					validateForm();
					return;
				}
			}
			
			var form = document.forms["uploadForm"];
			var pass = form["pwd"].value.trim();
			var check = true;
			
			if (pass == null || pass == "") {
				check = false;
				$("#pass").addClass("error");
				$("#passwordBlank").fadeIn();
			} else {
				$("#pass").removeClass("error");
				$("#passwordBlank").fadeOut();
			}
			
			if (check) {
				$("#submitButton").removeClass("error2");
			} else {
				$("#submitButton").addClass("error2");
			}
			
			checkButton();
			
			return check;
		}
		
		function checkButton() {
			var button = $("#submitButton");
			if (button.hasClass("error1") || button.hasClass("error2")) {
				button.addClass("error");
			} else {
				button.removeClass("error");
			}
		}
		
		function validateForm()
		{
			var check = true;
			if (!checkUser()) {
				check = false;
			}
			
			if (!checkPassword()) {
				check = false;
			}
			
			if (check) {
				document.forms["uploadForm"].submit();
			} else {
				$("#submitButton").addClass("error");
			}
		}
	</script>
</body>
</html>