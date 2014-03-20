<!--<%@ page language="java" contentType="text/html; charset=US-ASCII" pageEncoding="US-ASCII"%>-->
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Request</title>
<script type="text/javascript" src="sorttable.js"></script>
<script type="text/javascript" src="requestTable.js"></script>
<script type="text/javascript" src="websocket.js"></script>
<style type="text/css">
/* Sortable tables */
table.sortable thead {
	background-color: #676767;
	color: #cccccc;
	font-weight: bold;
	cursor: default;
}

table.sortable tbody tr:nth-child(2n) td {
	background: #242424;
}

table.sortable tbody tr:nth-child(2n+1) td {
	background: #424242;
}

table.sortable th:not (.sorttable_sorted ):not (.sorttable_sorted_reverse ):after {
	content: " \25B4\25BE"
}

table.sortable tr {
	color: #FFFFFF
}

A:link {
	color: #FFFFFF
}

A:visited {
	color: #FFFFFF
}

body {
	color: #FFFFFF
}
</style>
</head>
<body bgcolor="black" onload="connect();">
	<p align="right">
		<a href="/admin"><font size="5">Admin site</font></a>
	</p>
	<center>
		<font size="7">Welcome!</font> <br>
		<textarea id="chatlog" readonly style="height: 250px; width: 500px"></textarea>
		<br>
		<button type="button" id="connectButton" onclick="connect()">Connect</button>
		<input type="text" id="msg" onkeydown="chatKeyPress(event)" />
		<button type="button" id="sendButton" onclick="postToServer()">Send!</button>
		<br> <font size="6" id="tableHeader">Please select a song</font><br>
		<font size="4" id="tableHeader">Select a song with the buttons on the left, then click request. You can also click the headers to sort.</font>
	</center>

	<form action="javascript:requestSong()" id="requestForm">
		<input type="submit" value="Request">
		<table class="sortable" id="songList">
			<thead>
				<tr>
					<th class="sorttable_nosort"></th>
					<th>Requests</th>
					<th>Name</th>
					<th>Time</th>
					<th>Artist</th>
					<th>Album</th>
					<th>Album Artist</th>
					<th>Composer</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>temp</td>
				</tr>
			</tbody>
			<tfoot></tfoot>
		</table>
	</form>
</body>
</html>