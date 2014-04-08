<%@ page language="java" contentType="text/html; charset=US-ASCII" pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Login</title>
<script type="text/javascript" src="util.js"></script>
<style type="text/css">
body {
	color: #FFFFFF
}
</style>
</head>
<body bgcolor="black">
	<p align="right">
		<a href=".."><font color="white" size="5">Upload library</font></a>
	</p>
	<center>
		<h2>
			Please navigate to your iTunes library and upload the file "iTunes Library.xml" or "iTunes Music Library.xml"<br>Windows XP: \\My Documents\\My Music\\iTunes\\<br> Windows Vista+: \\Music\\iTunes\\<br>OS X: /Music/iTunes/
		</h2>
		<form action="UploadServlet" method="post" enctype="multipart/form-data">
			<input type="file" id="uploadFile" name="file" onchange="checkUploadedFile()"><br> <input type="submit" id="submitFile" name="submit" value="Upload" disabled="disabled">
		</form>
	</center>
	<script type="text/javascript">
		function checkUploadedFile() {
			if (document.getElementById("uploadFile").value.endsWith("iTunes Library.xml")
					|| document.getElementById("uploadFile").value.endsWith("iTunes Music Library.xml")) {
				document.getElementById("submitFile").disabled = false;
			}
		}
	</script>
</body>
</html>