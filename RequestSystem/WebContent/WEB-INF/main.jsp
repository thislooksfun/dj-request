<%@ page language="java" contentType="text/html; charset=US-ASCII" pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Request</title>
<script type="text/javascript" src="websocket.js"></script>
<script type="text/javascript" src="sorttable.js"></script>
<script type="text/javascript" src="requesttable.js"></script>
<script type="text/javascript" src="tablesearch.js"></script>
<script type="text/javascript" src="removediacritics.js"></script>
<script type="text/javascript" src="util.js"></script>
<style type="text/css">
table thead {
	background-color: #676767;
	color: #cccccc;
	font-weight: bold;
	cursor: default;
}

table tbody tr:nth-child(2n) td {
	background: #242424;
}

table tbody tr:nth-child(2n+1) td {
	background: #424242;
}

table tr {
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
<body bgcolor="black" onload="onLoad()">
	<noscript>
		<center>
			<font color="red" size="6">This site requires javascript in order to function, please turn it on then try again</font>
		</center>
	</noscript>
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
		<br>
		<font size="6" id="tableHeader">Please select a song</font><br> <font size="4" id="tableHeader">To request a song, simply click "request" on the song you want. If your song isn't listed, and you have it on your device, click the "manual request" button. You can also click the headers to sort.</font>
	</center>

	<table id="manualRequests" width="100%">
		<thead>
			<tr>
				<th width="25%">Requested by</th>
				<th width="25%">Name</th>
				<th width="25%">Artist</th>
				<th width="25%">Album</th>
			</tr>
		</thead>
		<tbody id="manualSubmits">
			<tr>
				<td>empty</td>
			</tr>
		</tbody>
		<tfoot></tfoot>
	</table>
	<div id="noManualRequestBar" style="display: none; width: 100%">
		<center>
			<h2>No manual requests found.</h2>
		</center>
	</div>
	
	<center>
		<label>Search: </label> <input type="text" size="50" id="textBoxSearch" onkeyup="tableSearch.runSearch();"> <br> <label id="searchResult" style=""> </label>
	</center>
	<table class="sortable" id="songList" width="100%">
		<thead>
			<tr>
				<th width="4%" class="sorttable_nosort"></th>
				<th width="3.9%" id="requestColumn" class="sorttable_reverse sorttable_stable">Requests</th>
				<th width="23.9%">Name</th>
				<th width="3.2%">Time</th>
				<th width="9.1%">Artist</th>
				<th width="14.7%">Album</th>
				<th width="5.9%">Album Artist</th>
				<th width="27%">Composer</th>
				<th width="7.4%">Genre</th>
				<th style="display: none">ID</th>
			</tr>
		</thead>
		<tbody id="searchSongs">
			<tr>
				<td>empty</td>
			</tr>
		</tbody>
		<tfoot></tfoot>
	</table>
	<div id="noResultBar" style="display: none; width: 100%">
		<center>
			<h2>
				No results found! <a href="/admin">Click here</a> if you have the song on your device
			</h2>
		</center>
	</div>
	<div id="noItemsBar" style="display: none; width: 100%">
		<center>
			<h2>No items found. Try reloading the page, or asking the DJ</h2>
		</center>
	</div>
</body>
</html>