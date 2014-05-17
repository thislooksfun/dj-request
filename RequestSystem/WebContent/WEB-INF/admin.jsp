<%@ page import="com.tlf.util.LoginHelper"%>
<%@ page language="java" contentType="text/html; charset=US-ASCII" pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Admin site</title>
<script type="text/javascript" src="http://code.jquery.com/jquery-latest.min.js"></script>
<script type="text/javascript" language="javascript" src="//cdn.datatables.net/1.10.0/js/jquery.dataTables.js"></script>
<script type="text/javascript" src="adminwebsocket.js"></script>
<script type="text/javascript" src="adminrequesttable.js"></script>
<script type="text/javascript" src="removediacritics.js"></script>
<script type="text/javascript" src="util.js"></script>

<link rel="stylesheet" type="text/css" href="//cdn.datatables.net/1.10.0-beta.1/css/jquery.dataTables.css">
<link rel="stylesheet" href="/request.css">
</head>
<body onload="onLoad();">
	<noscript>
		<div class="center">
			<font color="red" size="6">This site requires javascript in order to function, please turn it on then try again</font>
		</div>
	</noscript>
	<form action="LogoutServlet" method="post" id="logoutForm">
		<input type="hidden" name="sessionKey" value="<%=session.getId() %>">
	</form>
	<div id="header" align="right" style="position: absolute; top: 5px; right: 10px; width">
		<button onclick="document.getElementById('logoutForm').submit();">Logout</button>
		<a href="/"><font size="5">Main site</font></a>
	</div>
	<div class="center">
		<h1>Admin site</h1>
		<h2>
			Logged in as
			<%=LoginHelper.instance.getUserForSession(session)%>
		</h2>
		<br> <font size="6" id="tableHeader">Please select a song</font><br> <font size="4">To clear the requests on a song, simply click "played." You can also click the headers to sort.</font>
	</div>

	<table class="sortable" id="manualRequests" width="100%">
		<thead>
			<tr>
				<th width="57px" class="nosort"></th>
				<th width="64px" id="manRequestColumn" class="reverse">Requests</th>
				<th width="">Requested by</th>
				<th width="">Name</th>
				<th width="55px">Time</th>
				<th width="">Artist</th>
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
				<th width="57px" class="nosort"></th>
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