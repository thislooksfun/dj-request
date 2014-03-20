<!--<%@ page language="java" contentType="text/html; charset=US-ASCII" pageEncoding="US-ASCII"%>-->
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Request</title>
</head>
<body bgcolor="black">
	<p align="right">
		<a href="/admin"><font color="white" size="5">Admin site</font></a>
	</p>
	<center>
		<font color="white" size="7">Welcome!</font> <br>
		<textarea id="chatlog" readonly style="height: 250px; width: 500px"></textarea>
		<br>
		<button type="button" id="connectButton" onclick="connect()">Connect</button>
		<input type="text" id="msg" onkeydown="chatKeyPress(event)" />
		<button type="button" id="sendButton" onclick="postToServer()">Send!</button>
	</center>
	<script type="text/javascript" src="/websocket.js"></script>
</body>
</html>