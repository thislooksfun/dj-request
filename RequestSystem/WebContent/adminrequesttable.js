var hasPlaceholder = true;
var hasManualPlaceholder = true;

function addSong(song)
{
	var table = document.getElementById("songList");
	
	var manualIndex = song.indexOf("manual=");
	var isManual = song.substring(song.indexOf("&^&", manualIndex)+3, song.indexOf("&^&", song.indexOf("&^&", manualIndex)+3));
	if (isManual === "true") {
		addManualRequest(song);
		return;
	}
	
	var posisions = [song.indexOf("uuid="), song.indexOf("requests="), song.indexOf("name="), song.indexOf("time="), song.indexOf("artist="), song.indexOf("album="), song.indexOf("albumartist="), song.indexOf("composer="), song.indexOf("genre=")];
	var info = [];

	for (var i = 0; i < posisions.length; i++) {
		info[i] = song.substring(song.indexOf("&^&", posisions[i])+3, song.indexOf("&^&", song.indexOf("&^&", posisions[i])+3));
	}

	for (i = 1; i < info.length; i++) {
		info[i] = (info[i] == "null" || info[i] == "Not Documented" ? "" : info[i]);
	}
	
	var row = songTable.row.add([
	        "<input type=\"button\" value=\"Played\" name=\"playButtons\" onclick=\"playSong('"+info[0]+"')\">",
			info[1], //Requests
	    	info[2], //Name
	    	info[3], //Time
	    	info[4], //Artist
	    	info[5], //Album
	    	info[6], //Album Artist
	    	info[7], //Composer
	    	info[8], //Genre
	    	info[0]  //UUID - hidden
	    ]).draw().node();
	
	var button = row.cells[0].firstChild;
	button.disabled = true;
	button.style.cursor = "not-allowed";

	if (hasPlaceholder) {
		table.deleteRow(1);
		hasPlaceholder = false;
	}
}

function addManualRequest(song)
{
	var table = document.getElementById("manualRequests");

	var posisions = [song.indexOf("uuid="), song.indexOf("requests="), song.indexOf("requestedby="), song.indexOf("name="), song.indexOf("artist="), song.indexOf("time=")];
	var info = [];

	for (var i = 0; i < posisions.length; i++) {
		info[i] = song.substring(song.indexOf("&^&", posisions[i])+3, song.indexOf("&^&", song.indexOf("&^&", posisions[i])+3));
	}
	
	for (i = 1; i < info.length; i++) {
		info[i] = (info[i] == "null" || info[i] == "Not Documented" ? "" : info[i]);
	}
	
	var row = manTable.row.add([
	    "<input type=\"button\" value=\"Played\" name=\"playButtons\" onclick=\"playSong('"+info[0]+"')\">",
	    info[1], //Requests
	    info[2], //Requested by
	    info[3], //Name
	    info[4], //Time
	    info[5], //Artist
	    info[0]  //UUID - hidden
	    ]).draw().node();
	
	var button = row.cells[0].firstChild;
	button.disabled = true;
	button.style.cursor = "not-allowed";

	if (hasManualPlaceholder) {
		table.deleteRow(1);
		hasManualPlaceholder = false;
	}
	
	checkForEmpty();
}

function updateRequestCount(data)
{
	var posisions = [data.indexOf("id="), data.indexOf("newCount="), data.indexOf("manual=")];
	var info = [];

	for (var i = 0; i < posisions.length; i++) {
		info[i] = data.substring(data.indexOf("'", posisions[i])+1, data.indexOf("'", data.indexOf("'", posisions[i])+1));
	}
	
	var rows;
	var table;
	if (info[2] === "true") {
		rows = manTable.rows().nodes();
		table = $('#manualRequests').dataTable();
	} else {
		rows = songTable.rows().nodes();
		table = $('#songList').dataTable();
	}
	
	var lastCell = rows[0].cells.length;
	
	for (i = 0; i < rows.length; i++) {
		if (table.fnGetData(i, lastCell) == info[0]) {
			if (info[2] == "true" && info[1] == 0) {
				table.api().row(i).remove().draw();
			} else {
				table.fnUpdate(info[1], i, 1); //(data, row, column)
				table.api().cell(i, 0).node().firstChild.disabled = (info[1] == 0);
			}
			break;
		}
	}
}

function clearTable() {
	if (!hasPlaceholder)
	{
		var table = document.getElementById("songList");
		var rowCount = table.rows.length;
		table.insertRow(rowCount).insertCell(0).innerHTML = "empty";

		var temp = 0;
		for (var i = 1; i < rowCount; i++) {
			table.deleteRow(1);
			temp++;
		}

		hasPlaceholder = true;
	}
	
	if (!hasManualPlaceholder)
	{
		var table = document.getElementById("manualRequests");
		var rowCount = table.rows.length;
		table.insertRow(rowCount).insertCell(0).innerHTML = "empty";

		var temp = 0;
		for (var i = 1; i < rowCount; i++) {
			table.deleteRow(1);
			temp++;
		}

		hasManualPlaceholder = true;
	}
}