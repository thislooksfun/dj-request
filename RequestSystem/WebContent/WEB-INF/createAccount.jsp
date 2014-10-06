<%@page import="java.util.StringTokenizer"%>
<%@ page language="java" contentType="text/html; charset=US-ASCII" pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Create Account</title>
<script type="text/javascript" src="/jQuery.min.js"></script>
<script type="text/javascript" src="/strength.js"></script>
<link rel="stylesheet" href="/strength.css">
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
				<form id="uploadForm" action="createaccount" method="post">
					<span class="center head">Create Account:</span>
					<div class="inputDiv">
						<input type="text" class="rounded" id="user" name="username" onblur="checkUser()" onkeyup="checkUser(event)" placeholder="Username">
						<div class="errorDiv">
							<div id="usernameBlank"		class="errorMsg">Can't be blank</div>
							<div id="usernameExists"	class="errorMsg">This username is taken</div>
							<div id="usernameReserved"	class="errorMsg">This username is reserved</div>
						</div>
					</div>
					<div class="inputDiv">
						<input type="password" class="rounded" id="pass1" name="pass1" onclick="showMeter()" onkeyup="checkPasswords(event)" onblur="checkPasswords(); hideMeter()" placeholder="Password">
						<div class="strength_container">
							<div id="strength_meter" class="strength_meter"></div>
						</div>
						<div class="errorDiv">
							<div id="password1Blank"	class="errorMsg">Can't be blank</div>
							<div id="passwordMismatch1" class="errorMsg">Passwords must match</div>
						</div>
					</div>
					<div class="inputDiv">
						<input type="password" class="rounded" id="pass2" name="pass2" onkeyup="checkPasswords(event)" onblur="checkPasswords()" placeholder="Re-enter Password">
						<div class="errorDiv">
							<div id="password2Blank"	class="errorMsg">Can't be blank</div>
							<div id="passwordMismatch2" class="errorMsg">Passwords must match</div>
						</div>
					</div>
					<div class="inputDiv">
						<input type="email" class="rounded" id="email" name="email" onkeyup="checkEmail(event)" onblur="checkEmail()" placeholder="Email">
						<div class="errorDiv">
							<div id="emailBlank"	class="errorMsg">Can't be blank</div>
							<div id="emailFormat" 	class="errorMsg">Not a valid email</div>
						</div>
					</div>
					<div class="center">
						<input type="button" id="submitButton" class="button" onclick="return validateForm();" value="Create">
						<div id="buttonBack" class="buttonBack"></div>
						<a href="/login">Login</a>
					</div>
				</form>
			</div>
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
			$("label").width(max + "px");
			
			$("#pass1").strength();
			
			validateForm();
		});
		
		function hideMeter() {
			$('#strength_meter').fadeOut();
		}
		function showMeter() {
			$('#strength_meter').fadeIn();
		}
		
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
			var user = form["username"].value.trim();
			var check = true;
			
			
			$("#user").removeClass("error");
			
			if (user == null || user == "") {
				check = false;
				$("#user").addClass("error");
				$("#usernameBlank").fadeIn();
				$("#usernameExists").fadeOut();
			} else {
				$("#usernameBlank").fadeOut();
				
				$.ajax({
					type: "POST",
					url: "createaccount",
					data: {
						userCheck: true,
						username: user
					},
					success: function(data) {
						if (data == null || data == "") {
							$("#usernameExists").fadeOut();
						} else {
							if (data == "0") {
								check = false;
								$("#user").addClass("error");
								$("#usernameExists").fadeIn();
							} else if (data == "1") {
								check = false;
								$("#user").addClass("error");
								$("#usernameReserved").fadeIn();
							}
						}
					},
					async: false
				});
				
				if ($("#user").hasClass("error")) {
					check = false;
				}
			}
			
			if (check) {
				$("#submitButton").removeClass("error1");
			} else {
				$("#submitButton").addClass("error1");
			}
			
			checkButton();
			
			return check;
		}
		function checkPasswords(e)
		{
			if (e != null) {
				var charCode = (typeof e.which === "number") ? e.which : e.keyCode;
				if (charCode === 13) {
					validateForm();
					return;
				}
			}
			
			var form = document.forms["uploadForm"];
			var pass1 = form["pass1"].value.trim();
			var pass2 = form["pass2"].value.trim();
			var pass1Blank = false;
			var pass2Blank = false;
			var check = true;
			
			
			$("#pass1").removeClass("error");
			$("#pass2").removeClass("error");
			
			if (pass1 == null || pass1 == "") {
				check = false;
				pass1Blank = true;
				$("#pass1").addClass("error");
				$("#password1Blank").fadeIn();
			} else {
				$("#password1Blank").fadeOut();
			}
			
			if (pass2 == null || pass2 == "") {
				check = false;
				pass2Blank = true;
				$("#pass2").addClass("error");
				$("#password2Blank").fadeIn();
			} else {
				$("#password2Blank").fadeOut();
			}
			
			if (!pass1Blank && !pass2Blank && (pass1 != pass2)) {
				check = false;
				$("#pass1").addClass("error");
				$("#pass2").addClass("error");
				$("#passwordMismatch1").fadeIn();
				$("#passwordMismatch2").fadeIn();
			} else {
				$("#passwordMismatch1").fadeOut();
				$("#passwordMismatch2").fadeOut();
			}
			
			if (check) {
				$("#submitButton").removeClass("error2");
			} else {
				$("#submitButton").addClass("error2");
			}
			
			checkButton();
			
			return check;
		}
		function checkEmail(e)
		{
			if (e != null) {
				var charCode = (typeof e.which === "number") ? e.which : e.keyCode;
				if (charCode === 13) {
					validateForm();
					return;
				}
			}
			
			var form = document.forms["uploadForm"];
			var email = form["email"].value.trim();
			var check = true;
			
			
			$("#email").removeClass("error");
			
			if (email == null || email == "") {
				check = false;
				$("#email").addClass("error");
				$("#emailBlank").fadeIn();
				$("#emailFormat").fadeOut();
			} else {
				$("#emailBlank").fadeOut();
				if (!(/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w+)+$/.test(email))) {
					check = false;
					$("#email").addClass("error");
					$("#emailFormat").fadeIn();
				} else {
					$("#emailFormat").fadeOut();
				}
			}
			
			if (check) {
				$("#submitButton").removeClass("error3");
			} else {
				$("#submitButton").addClass("error3");
			}
			
			checkButton();
			
			return check;
		}
		
		function checkButton() {
			var button = $("#submitButton");
			if (button.hasClass("error1") || button.hasClass("error2") || button.hasClass("error3")) {
				button.addClass("error");
			} else {
				button.removeClass("error");
			}
		}
		
		function validateForm() {
			var check = true;
			if (!checkUser()) {
				check = false;
			}
			
			if (!checkPasswords()) {
				check = false;
			}
			
			if (!checkEmail()) {
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