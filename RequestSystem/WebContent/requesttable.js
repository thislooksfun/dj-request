var hasPlaceholder = true;

function addSong(song)
{
	var table = document.getElementById("songList");

	var manualIndex = song.indexOf("manual=")
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

	var rowCount = table.rows.length;
	var row = table.insertRow(rowCount);

	var cell0 = row.insertCell(0);
	cell0.innerHTML = "<input type=\"button\" value=\"Request\" name=\"requestButtons\" onclick=\"requestSong('id=\\'"+info[0]+"\\', name=\\'"+info[2]+"\\', artist=\\'"+info[4]+"\\'')\" style=\"cursor:not-allowed\" disabled=\"true\">";
	
	for (i = 1; i < info.length; i++) {
		var cell = row.insertCell(i);
		cell.innerHTML = (info[i] == "null" || info[i] == "Not Documented" ? "" : info[i]);
	}
	
	var cellID = row.insertCell(info.length);
	cellID.innerHTML = info[0];
	cellID.style.display = "none";

	if (hasPlaceholder) {
		table.deleteRow(1);
		hasPlaceholder = false;
	}
}

function addManualRequest(song)
{
	var table = document.getElementById("manualRequests");

	var posisions = [song.indexOf("requestedby="), song.indexOf("name="), song.indexOf("artist="), song.indexOf("time=")];
	var info = [];

	for (var i = 0; i < posisions.length; i++) {
		info[i] = song.substring(song.indexOf("&^&", posisions[i])+3, song.indexOf("&^&", song.indexOf("&^&", posisions[i])+3));
		console.log(info[i]);
	}

	var rowCount = table.rows.length;
	var row = table.insertRow(rowCount);
	
	for (i = 0; i < info.length; i++) {
		var cell = row.insertCell(i);
		cell.innerHTML = (info[i] == "null" || info[i] == "Not Documented" ? "" : info[i]);
	}
	
	var cellID = row.insertCell(info.length);
	cellID.innerHTML = info[0];
	cellID.style.display = "none";

	if (hasPlaceholder) {
		table.deleteRow(1);
		hasPlaceholder = false;
	}
}

function updateRequestCount(data) {
	var posisions = [data.indexOf("id="), data.indexOf("newCount=")];
	var info = [];

	for (var i = 0; i < posisions.length; i++) {
		info[i] = data.substring(data.indexOf("'", posisions[i])+1, data.indexOf("'", data.indexOf("'", posisions[i])+1));
	}
	
	for (i = 0; i < tableSearch.Rows.length; i++) {
		if (tableSearch.Rows[i].cells[9].innerHTML == info[0]) {
			tableSearch.Rows[i].cells[1].innerHTML = info[1];
			
			resortRequests();
			
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
}