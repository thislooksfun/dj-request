<%@page import="java.util.StringTokenizer"%>
<%@ page language="java" contentType="text/html; charset=US-ASCII" pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Login</title>
<script type="text/javascript">
String.prototype.endsWith = function(suffix) {
	return this.indexOf(suffix, this.length - suffix.length) !== -1;
};
</script>
<link rel="stylesheet" href="/request.css">
<style type="text/css">
* {
	font-size: 30px;
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
	-moz-border-radius: 10px;
	border-radius: 10px;
	padding: 15px;
	position: relative;
	top: 150px;
	display : block;
	text-align: center;
	width: 640px;
	margin: 0 auto;
	text-decoration: none;
	display: block;
	-moz-box-shadow: 5px 5px 8px 7px #111;
	-webkit-box-shadow: 5px 5px 8px 7px #111;
	box-shadow: 5px 5px 8px 7px #111;
}
input {
	display: block;
	padding-top: 15px;
}
</style>
</head>
<body>
	<div id="pageContainer" class="container_page">
		<div id="uploadContainer" class="container_form">
			<h2>
				<%
					String browserDetails = request.getHeader("User-Agent");
					String userAgent = browserDetails;	
					String user = userAgent.toLowerCase();
				
					String message = "Please navigate to %s and upload the file \"iTunes Library.xml\" or \"iTunes Music Library.xml\"";
					String macMessage = "/Music/iTunes/";
					String XPMessage = "\\My Documents\\My Music\\iTunes\\";
					String VistaPlusMessage = "\\Music\\iTunes\\";
					//=================OS=======================
					if (user.indexOf("windows") >= 0) {
						if (user.indexOf("windows nt 5.1") >= 0) {
							out.print(String.format(message, XPMessage));
						} else if (user.indexOf("windows nt 6.") >= 0) {
							out.print(String.format(message, VistaPlusMessage));
						}
					} else if (user.indexOf("mac") >= 0) {
						out.print(String.format(message, macMessage));
					} else {
						response.sendRedirect("/admin"); //Unknown/unsupported OS redirect
					}
				%>
			</h2>
			<form id="uploadForm" action="UploadServlet" method="post" enctype="multipart/form-data">
				<input type="file" id="uploadFile" name="file" onchange="checkUploadedFile()">
			</form>
			<a href="/admin">Click here bypass this screen</a>
		</div>
	</div>
	<script type="text/javascript">
		function checkUploadedFile() {
			if (document.getElementById("uploadFile").value
					.endsWith("iTunes Library.xml")
					|| document.getElementById("uploadFile").value
							.endsWith("iTunes Music Library.xml")) {
				document.getElementById("uploadForm").submit();
			}
		}
	</script>
</body>
</html>