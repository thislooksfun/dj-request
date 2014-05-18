<%@page import="com.tlf.itunes.SongSystem"%>
<%@ page import="com.tlf.util.LoginHelper"%>
<%@ page language="java" contentType="text/html; charset=US-ASCII" pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Settings</title>
<script type="text/javascript" src="/jQuery.min.js"></script>
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
div.container_settings {
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
		<div id="loginContainer" class="container_settings">
			<h2>Settings</h2>
			<form action="settings" method="post">
				<label>Allow explicit content</label><input type="checkbox" name="explicit" value="true" <%=(SongSystem.instance.allowExplicit ? "checked" : "") %>>
				<br>
				<br>
				<input type="submit" value="Save">
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