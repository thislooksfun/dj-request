<%@page import="java.util.StringTokenizer"%>
<%@ page language="java" contentType="text/html; charset=US-ASCII" pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Request</title>
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
			<form id="uploadForm" action="ManualServlet" method="post">
				<span class="center head">Request:</span><br>
				<label>Your name</label><input type="text" id="RName" name="RName"><br>
				<label>Song name</label><input type="text" id="Name" name="Name" value="<%=(request.getParameter("search") == null ? "" : request.getParameter("search"))%>"><br>
				<label>Length</label><input type="text" id="Time" name="Time" placeholder="eg: 1:23"><br>
				<label>Artist</label><input type="text" id="Artist" name="Artist"><br>
				<label>Explicit</label><input type="checkbox" id="Explicit" name="Explicit" value="true"><br>
				<div class="center">
					<input type="button" onclick="return validateForm();" value="Request">
				</div>
			</form>
		</div>
		<div id="RNameError" 		class="tip left" style="display: none">This field can't be blank</div>
		<div id="NameError" 		class="tip left" style="display: none">This field can't be blank</div>
		<div id="TimeErrorBlank" 	class="tip left" style="display: none">This field can't be blank</div>
		<div id="TimeErrorFormat" 	class="tip left" style="display: none">Time must be in a proper format. eg. 1:23</div>
		<div id="ArtistError" 		class="tip left" style="display: none">This field can't be blank</div>
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
			
			$("#RNameError")[0].style.top = $("#RName").offset().top + "px";
			$("#RNameError")[0].style.left = ($("#RName").offset().left + $("#RName").width() + 10) + "px";
			
			$("#NameError")[0].style.top = $("#Name").offset().top + "px";
			$("#NameError")[0].style.left = ($("#Name").offset().left + $("#Name").width() + 10) + "px";
			
			$("#TimeErrorBlank")[0].style.top = $("#Time").offset().top + "px";
			$("#TimeErrorBlank")[0].style.left = ($("#Time").offset().left + $("#Time").width() + 10) + "px";
			$("#TimeErrorFormat")[0].style.top = $("#Time").offset().top + "px";
			$("#TimeErrorFormat")[0].style.left = ($("#Time").offset().left + $("#Time").width() + 10) + "px";
			
			$("#ArtistError")[0].style.top = $("#Artist").offset().top + "px";
			$("#ArtistError")[0].style.left = ($("#Artist").offset().left + $("#Time").width() + 10) + "px";
		});
		
		function validateForm() {
			var form = document.forms["uploadForm"];
			var rName = form["RName"].value.trim();
			var name = form["Name"].value.trim();
			var time = form["Time"].value.trim();
			var artist = form["Artist"].value.trim();
			var check = true;
			
			if (rName == null || rName == "") {
				check = false;
				$("#RName").addClass("error");
				$("#RNameError")[0].style.display = "";
			} else {
				$("#RName").removeClass("error");
				$("#RNameError")[0].style.display = "none";
			}
			
			if (name == null || name == "") {
				check = false;
				$("#Name").addClass("error");
				$("#NameError")[0].style.display = "";
			} else {
				$("#Name").removeClass("error");
				$("#NameError")[0].style.display = "none";
			}
			
			if (time == null || time == "") {
				check = false;
				$("#Time").addClass("error");
				$("#TimeErrorBlank")[0].style.display = "";
			} else {
				$("#Time").removeClass("error");
				$("#TimeErrorBlank")[0].style.display = "none";
				
				if (time != "" && !(/^[\d]+:[0-5][0-9]$/.test(time))) {
					check = false;
					$("#Time").addClass("error");
					$("#TimeErrorFormat")[0].style.display = "";
				} else {
					$("#Time").removeClass("error");
					$("#TimeErrorFormat")[0].style.display = "none";
				}
			}
			
			if (artist == null || artist == "") {
				check = false;
				$("#Artist").addClass("error");
				$("#ArtistError")[0].style.display = "";
			} else {
				$("#Artist").removeClass("error");
				$("#ArtistError")[0].style.display = "none";
			}
			
			if (check) {
				form.submit();
			}
		}
	</script>
</body>
</html>