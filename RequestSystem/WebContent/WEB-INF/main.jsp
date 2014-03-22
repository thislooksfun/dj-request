<%@ page language="java" contentType="text/html; charset=US-ASCII" pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Request</title>
<script type="text/javascript" src="sorttable.js"></script>
<script type="text/javascript" src="requesttable.js"></script>
<script type="text/javascript" src="websocket.js"></script>
<script type="text/javascript" src="tablesearch.js"></script>
<script type="text/javascript" src="removediacritics.js"></script>
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
<body bgcolor="black" onload="connect(); tableSearch.init();">
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
		<br> <font size="6" id="tableHeader">Please select a song</font><br> <font size="4" id="tableHeader">Select a song with the buttons on the left, then click request. You can also click the headers to sort.</font> <br>
		<label>Search: </label><input type="text" size="50" id="textBoxSearch" onkeyup="tableSearch.runSearch();">
	</center>

	<form action="javascript:requestSong()" id="requestForm">
		<input type="submit" value="Request">
		<table class="sortable" id="songList" width="100%">
			<thead>
				<tr>
					<th class="sorttable_nosort" width="1.2%"></th>
					<th width="3.9%">Requests</th>
					<th width="23.3%">Name</th>
					<th width="3.2%">Time</th>
					<th width="12.4%">Artist</th>
					<th width="17.9%">Album</th>
					<th width="6.6%">Album Artist</th>
					<th width="30.9%">Composer</th>
				</tr>
			</thead>
			<tbody id="searchSongs">
				<tr>
					<td>temp</td>
				</tr>
			</tbody>
			<tfoot></tfoot>
		</table>
	</form>
</body>
</html>