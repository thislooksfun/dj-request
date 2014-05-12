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
	top: 100px;
}
label {
	display: inline-block;
}
</style>
</head>
<body bgcolor="black">
	<center>
		<div id="formContainer">
			<form id="uploadForm" action="ManualServlet" method="post" placeholder="<%=request.getParameter("searchContents")%>">
				<label>Song name</label><input type="text" id="Name"><br>
				<label>Length</label><input type="text" id="Time" placeholder="2:34"><br>
				<label>Artist</label><input type="text" id="Artist"><br>
				<label>Album</label><input type="text" id="Album"><br>
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
	</script>
</body>
</html>