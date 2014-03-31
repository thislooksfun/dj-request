<%@ page import="com.tlf.util.LoginHelper"%>
<%@ page language="java" contentType="text/html; charset=US-ASCII" pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Admin site</title>
<script type="text/javascript" src="adminwebsocket.js"></script>
<script type="text/javascript" src="sorttable.js"></script>
<script type="text/javascript" src="adminrequesttable.js"></script>
<script type="text/javascript" src="tablesearch.js"></script>
<script type="text/javascript" src="removediacritics.js"></script>
<script type="text/javascript" src="util.js"></script>
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
	<noscript><center><font color="red" size="6">This site requires javascript in order to function, please turn it on then try again</font></center></noscript>
	<form action="LogoutServlet" method="get">
		<p align="right">
			<input type="submit" value="Logout">
		</p>
	</form>
	<p align="right">
		<a href=".."><font size="5">Main site</font></a>
	</p>
	<center>
		<h1>Admin site</h1>
		<h2>
			Logged in as
			<%=LoginHelper.instance.getUserForSession(session)%>
		</h2>
		<textarea id="chatlog" readonly style="height: 250px; width: 500px"></textarea>
		<br> <font size="6" id="tableHeader">Please select a song</font><br> <font size="4" id="tableHeader">Select a song with the buttons on the left, then click request. You can also click the headers to sort.</font> <br> <label>Search: </label><input type="text" size="50" id="textBoxSearch" onkeyup="tableSearch.runSearch();">
	</center>

	<table class="sortable" id="songList" width="100%">
		<thead>
			<tr>
				<th width="4%" class="sorttable_nosort"></th>
				<th width="3.9%" id="requestColumn" class="sorttable_reverse">Requests</th>
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
</body>
</html>