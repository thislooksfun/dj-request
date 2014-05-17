<%@ page language="java" contentType="text/html; charset=US-ASCII" pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Request</title>
<script type="text/javascript" src="http://code.jquery.com/jquery-latest.min.js"></script>
<script type="text/javascript" language="javascript" src="//cdn.datatables.net/1.10.0/js/jquery.dataTables.js"></script>
<script type="text/javascript" src="websocket.js"></script>
<script type="text/javascript" src="requesttable.js"></script>
<script type="text/javascript" src="removediacritics.js"></script>
<script type="text/javascript" src="util.js"></script>

<link rel="stylesheet" type="text/css" href="//cdn.datatables.net/1.10.0-beta.1/css/jquery.dataTables.css">
<link rel="stylesheet" href="/request.css">
</head>
<body onload="onLoad()">
	<noscript>
		<div class="center">
			<font color="red" size="6">This site requires javascript in order to function, please turn it on then try again</font>
		</div>
	</noscript>
	<p align="right">
		<a href="/admin"><font size="5">Admin site</font></a>
	</p>
	<div class="center">
		<font size="7">Welcome!</font>
		<br>
		<br>
		<br>
		<font size="6" id="tableHeader">Please select a song</font><br> <font size="4" id="tableHeader">To request a song, simply click "request" on the song you want. If your song isn't listed, and you have it on your device, click the "manual request" button. You can also click the headers to sort.</font>
	</div>

	<table class="sortable" id="manualRequests" width="100%">
		<thead>
			<tr>
				<th width="64px" class="nosort"></th>
				<th width="64px" id="manRequestColumn" class="reverse">Requests</th>
				<th width="calc((100% - 64px - 64px - 55px)/3)">Requested by</th>
				<th width="calc((100% - 64px - 64px - 55px)/3)">Name</th>
				<th width="55px">Time</th>
				<th width="calc((100% - 64px - 64px - 55px)/3)">Artist</th>
				<th class="hidden" style="display: none">ID</th>
			</tr>
		</thead>
		<tbody id="manSongBody">
			<tr>
				<td>empty</td>
				<td>empty</td>
				<td>empty</td>
				<td>empty</td>
				<td>empty</td>
				<td>empty</td>
				<td>empty</td>
			</tr>
		</tbody>
		<tfoot></tfoot>
	</table>
	<div id="noManualRequestBar" style="display: none; width: 100%">
		<div class="center">
			<h2>No manual requests found.</h2>
		</div>
	</div>
	<div id="spacerBar" style="display: none; width: 100%; height: 50px">
	</div>
	
	<table class="sortable" id="songList" width="100%">
		<thead>
			<tr>
				<th width="64px" class="nosort"></th>
				<th width="64px" id="requestColumn" class="reverse">Requests</th>
				<th width="">Name</th>
				<th width="55px">Time</th>
				<th width="">Artist</th>
				<th width="">Album</th>
				<th width="">Album Artist</th>
				<th width="">Composer</th>
				<th width="125px">Genre</th>
				<th class="hidden" style="display: none">ID</th>
			</tr>
		</thead>
		<tbody id="songBody">
			<tr>
				<td>empty</td>
				<td>empty</td>
				<td>empty</td>
				<td>empty</td>
				<td>empty</td>
				<td>empty</td>
				<td>empty</td>
				<td>empty</td>
				<td>empty</td>
				<td>empty</td>
			</tr>
		</tbody>
		<tfoot></tfoot>
	</table>
	<div id="noResultBar" style="display: none; width: 100%">
		<div class="center">
			<h2>
				No results found! <a href="/request" id="requestLink">Click here</a> if you have the song on your device
			</h2>
		</div>
	</div>
	<div id="noItemsBar" style="display: none; width: 100%">
		<div class="center">
			<h2>No items found. Try reloading the page.</h2>
		</div>
	</div>
</body>
</html>