<%@ page import="com.tlf.util.LoginHelper"%>
<%@ page language="java" contentType="text/html; charset=US-ASCII" pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Admin site</title>
<script type="text/javascript" src="sorttable.js"></script>
<script type="text/javascript" src="requesttable.js"></script>
<script type="text/javascript" src="websocket.js"></script>
<script type="text/javascript" src="tablesearch.js"></script>
<script type="text/javascript" src="removediacritics.js"></script>
<script type="text/javascript" src="color.js"></script>
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
		<br>
	</center>
	<form target="AdminRequestServlet" action="post">
		<input type="submit" value="Request" id="requestButton">
		<table class="sortable" id="songList" width="100%">
			<thead>
				<tr>
					<th width="1.1%" class="sorttable_nosort"></th>
					<th width="3.9%">Requests</th>
					<th width="24.3%">Name</th>
					<th width="3.2%">Time</th>
					<th width="10.2%">Artist</th>
					<th width="15.7%">Album</th>
					<th width="6.1%">Album Artist</th>
					<th width="28.2%">Composer</th>
					<th width="7.4%">Genre</th>
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