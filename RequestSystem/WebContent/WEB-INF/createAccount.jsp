<%@page import="java.util.StringTokenizer"%>
<%@ page language="java" contentType="text/html; charset=US-ASCII" pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Create Account</title>
<script type="text/javascript" src="/jQuery.min.js"></script>
<link rel="stylesheet" href="/request.css">
<link rel="stylesheet" href="/arrowbox.css">
<style type="text/css">
* {
	font-size: 30px;
}

label {
	display: inline-block;
}

.error {
	border: 2px solid red;
}

div.container_page {
	position: absolute;
	top: 0px;
	left: 0px;
	height: 100%;
	width: 100%;
}
div.container_form {
	background-color: #333;
	font-size: 50px;
	-moz-border-radius: 10px;
	border-radius: 10px;
	padding: 15px;
	position: relative;
	top: 150px;
	display : block;
	text-align: center;
	width: 540px;
	margin: 0 auto;
	text-decoration: none;
	display: block;
	-moz-box-shadow: 5px 5px 8px 7px #111;
	-webkit-box-shadow: 5px 5px 8px 7px #111;
	box-shadow: 5px 5px 8px 7px #111;
}
span.head {
	font-size: 50px;
}
</style>
</head>
<body>
	<div id="pageContainer" class="container_page">
		<div id="formContainer" class="container_form">
			<form id="uploadForm" action="createaccount" method="post">
				<span class="center head">Create Account:</span><br>
				<label>Username</label><input type="text"     id="user"  name="username"><br>
				<label>Password</label><input type="password" id="pass1" name="password"><br>
				<label>Password</label><input type="password" id="pass2" name="pass2"><br>
				<label>Email</label><input    type="email"    id="email" name="email"><br>
				<div class="center">
					<input type="button" onclick="return validateForm();" value="Create">
				</div>
			</form>
		</div>
		<div id="usernameBlank"		class="tip left" style="display: none">This field can't be blank</div>
		<div id="usernameExists"	class="tip left" style="display: none">This username is taken</div>
		<div id="password1Blank"	class="tip left" style="display: none">This field can't be blank</div>
		<div id="password2Blank"	class="tip left" style="display: none">This field can't be blank</div>
		<div id="passwordMismatch"	class="tip left" style="display: none">Passwords must be the same</div>
		<div id="emailBlank"		class="tip left" style="display: none">This field can't be blank</div>
		<div id="emailFormat"		class="tip left" style="display: none">Email must be in proper format (hello@world.com)</div>
	</div>
	<script type="text/javascript">
		$(document).ready(function() {
			var max = 0;
			$("label").each(function() {
				if ($(this).width() > max) {
					max = $(this).width();
				}
			});
			$("label").width(max + "px");
			
			$("#usernameBlank")[0].style.top =		$("#user").offset().top + "px";
			$("#usernameBlank")[0].style.left =		($("#user").offset().left + $("#user").width() + 10) + "px";
			$("#usernameExists")[0].style.top =		$("#user").offset().top + "px";
			$("#usernameExists")[0].style.left =	($("#user").offset().left + $("#user").width() + 10) + "px";
			
			$("#password1Blank")[0].style.top = 	$("#pass1").offset().top + "px";
			$("#password1Blank")[0].style.left = 	($("#pass1").offset().left + $("#pass1").width() + 10) + "px";
			
			$("#password2Blank")[0].style.top = 	$("#pass2").offset().top + "px";
			$("#password2Blank")[0].style.left = 	($("#pass2").offset().left + $("#pass2").width() + 10) + "px";
			
			$("#passwordMismatch")[0].style.top = 	$("#pass1").offset().top + "px";
			$("#passwordMismatch")[0].style.left = 	($("#pass1").offset().left + $("#pass1").width() + 10) + "px";
			
			$("#emailBlank")[0].style.top =			$("#email").offset().top + "px";
			$("#emailBlank")[0].style.left =		($("#email").offset().left + $("#email").width() + 10) + "px";
			$("#emailFormat")[0].style.top =		$("#email").offset().top + "px";
			$("#emailFormat")[0].style.left =		($("#email").offset().left + $("#email").width() + 10) + "px";
		});
		
		function validateForm() {
			var form = document.forms["uploadForm"];
			var user = form["username"].value.trim();
			var pass1 = form["password"].value.trim();
			var pass2 = form["pass2"].value.trim();
			var email = form["email"].value.trim();
			var check = true;
			var pass1Blank = false;
			var pass2Blank = false;
			
			
			$("#user").removeClass("error");
			$("#pass1").removeClass("error");
			$("#pass2").removeClass("error");
			$("#email").removeClass("error");
			
			$("#usernameBlank")[0].style.display = "none";
			$("#usernameExists")[0].style.display = "none";
			$("#password1Blank")[0].style.display = "none";
			$("#password2Blank")[0].style.display = "none";
			$("#passwordMismatch")[0].style.display = "none";
			$("#emailBlank")[0].style.display = "none";
			$("#emailFormat")[0].style.display = "none";
			
			
			if (user == null || user == "") {
				check = false;
				$("#user").addClass("error");
				$("#usernameBlank")[0].style.display = "";
			} else {
				$.post("createaccount", {
					userCheck: true,
					username: user
				}, function(data) {
					console.log(data);
					if (data != null && data != "") {
						console.log("2");
						check = false;
						$("#user").addClass("error");
						$("#usernameExists")[0].style.display = "";
					}
				});
			}
			
			if (pass1 == null || pass1 == "") {
				check = false;
				pass1Blank = true;
				$("#pass1").addClass("error");
				$("#password1Blank")[0].style.display = "";
			}
			
			if (pass2 == null || pass2 == "") {
				check = false;
				pass2Blank = true;
				$("#pass2").addClass("error");
				$("#password2Blank")[0].style.display = "";
			}
			
			if (!pass1Blank && !pass2Blank) {
				if (pass1 != pass2) {
					check = false;
					$("#pass1").addClass("error");
					$("#pass2").addClass("error");
					$("#passwordMismatch")[0].style.display = "";
				}
			}
			
			if (email == null || email == "") {
				check = false;
				$("#email").addClass("error");
				$("#emailBlank")[0].style.display = "";
			} else {
				if (!(/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w+)+$/.test(email))) {
					check = false;
					$("#email").addClass("error");
					$("#emailFormat")[0].style.display = "";
				}
			}
			
			if (check) {
				form.submit();
			}
		}
	</script>
</body>
</html>