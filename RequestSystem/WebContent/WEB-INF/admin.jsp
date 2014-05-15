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
<link rel="stylesheet" href="/request.css">
</head>
<body onload="onLoad();">
	<noscript>
		<div class="center">
			<font color="red" size="6">This site requires javascript in order to function, please turn it on then try again</font>
		</div>
	</noscript>
	<form action="LogoutServlet" method="get">
		<p align="right">
			<input type="submit" value="Logout">
		</p>
	</form>
	<p align="right">
		<a href=".."><font size="5">Main site</font></a>
	</p>
	<div class="center">
		<h1>Admin site</h1>
		<h2>
			Logged in as
			<%=LoginHelper.instance.getUserForSession(session)%>
		</h2>
		<br> <font size="6" id="tableHeader">Please select a song</font><br> <font size="4">To clear the requests on a song, simply click "played." You can also click the headers to sort.</font>
	</div>

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
		<div class="center">
			<h2>No manual requests found.</h2>
		</div>
	</div>
	
	<div class="center">
		<label>Search: </label> <input type="text" size="50" id="textBoxSearch" onkeyup="tableSearch.runSearch();"> <br> <label id="searchResult" style=""> </label>
	</div>
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
		<div class="center">
			<h2>No results found!</h2>
		</div>
	</div>
	<div id="noItemsBar" style="display: none; width: 100%">
		<div class="center">
			<h2>
				No items found. <a href="/upload">Click here</a> to upload your library
			</h2>
		</div>
	</div>
</body>
</html>