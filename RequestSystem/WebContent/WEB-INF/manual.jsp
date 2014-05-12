<%@page import="java.util.StringTokenizer"%>
<%@ page language="java" contentType="text/html; charset=US-ASCII" pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Request</title>
<script type="text/javascript" src="http://code.jquery.com/jquery-latest.min.js"></script>
<link rel="stylesheet" href="/request.css" id="Stylesheet">
<style type="text/css">
* {
	font-size: 25px;
}
#formContainer {
	display: inline-block;
	text-align: left;
	position: relative;
	top: 100px;
}
label {
	display: inline-block;
}

.redborder {
	border: 2px solid red;
}
</style>
</head>
<body bgcolor="black">
<center>
	<div id="formContainer">
		<form id="uploadForm" action="ManualServlet" method="post" onsubmit="return validateForm();">
			<label>Your name</label><input type="text" id="RName" name="RName"><br>
			<label>Song name</label><input type="text" id="Name" name="Name" placeholder="<%=(request.getParameter("search") == null ? "" : request.getParameter("search"))%>"><br>
			<label>Length</label><input type="text" id="Time" name="Time" placeholder="eg: 1:23"><br>
			<label>Artist</label><input type="text" id="Artist" name="Artist"><br>
			<label>Explicit</label><input type="checkbox" id="Explicit"><br>
			<center><input type="submit" value="Request"></center>
		</form>
	</div>
	</center>
	<script type="text/javascript">
		$(document).ready(function() {
			var max = 0;
			$("label").each(function() {
				if ($(this).width() > max)
					max = $(this).width();
			});
			$("label").width(max+"px");
		});
		
		function validateForm() {
			var form = document.forms["uploadForm"];
			var rName = form["RName"].value;
			var name = form["Name"].value;
			var time = form["Time"].value;
			var check = true;
			
			if (rName == null || rName == "") {
				check = false;
				$("#RName").addClass("redborder");
			} else {
				$("#RName").removeClass("redborder");
			}
			if (name == null || name == "") {
				check = false;
				$("#Name").addClass("redborder");
			} else {
				$("#Name").removeClass("redborder");
			}
			if (time != "" && !(/^[\d]+:[0-5][0-9]$/.test(time))) {
				check = false;
				$("#Time").addClass("redborder");
			} else {
				$("#Time").removeClass("redborder");
			}
			
			return check;
		}
	</script>
</body>
</html>