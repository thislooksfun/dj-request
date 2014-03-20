var hasPlaceholder = true;

function addSong(song)
{
	var table = document.getElementById("songList");
	
	var posisions = [song.indexOf("id="), song.indexOf("requests="), song.indexOf("name="), song.indexOf("time="), song.indexOf("artist="), song.indexOf("album="), song.indexOf("albumartist="), song.indexOf("composer=")];
	var info = [];

	for (var i = 0; i < posisions.length; i++) {
		info[i] = song.substring(song.indexOf("'", posisions[i])+1, song.indexOf("'", song.indexOf("'", posisions[i])+1));
	}

	var rowCount = table.rows.length;
	var row = table.insertRow(rowCount);

	var cell0 = row.insertCell(0);
	var button = document.createElement("input");
	button.type = "radio";
	button.value = info[0];
	button.name = "selectedSong";
	cell0.appendChild(button);

	var cell1 = row.insertCell(1);
	cell1.innerHTML = (info[1] == "null" ? "" : info[1]);
	var cell2 = row.insertCell(2);
	cell2.innerHTML = (info[2] == "null" ? "" : info[2]);
	var cell3 = row.insertCell(3);
	cell3.innerHTML = (info[3] == "null" ? "" : info[3]);
	var cell4 = row.insertCell(4);
	cell4.innerHTML = (info[4] == "null" ? "" : info[4]);
	var cell5 = row.insertCell(5);
	cell5.innerHTML = (info[5] == "null" ? "" : info[5]);
	var cell6 = row.insertCell(6);
	cell6.innerHTML = (info[6] == "null" ? "" : info[6]);
	var cell7 = row.insertCell(7);
	cell7.innerHTML = (info[7] == "null" ? "" : info[7]);
	
	if (hasPlaceholder) {
		table.deleteRow(1);
		hasPlaceholder = false;
	}
}

function updateRequestCount(data) {
	var posisions = [data.indexOf("id="), data.indexOf("newCount=")];
	var info = [];

	for (var i = 0; i < posisions.length; i++) {
		info[i] = song.substring(song.indexOf("'", posisions[i])+1, song.indexOf("'", song.indexOf("'", posisions[i])+1));
	}
}

function clearTable() {
	var table = document.getElementById("songList");
	var rowCount = table.rows.length;
	table.insertRow(rowCount).insertCell(0).innerHTML = "Hello";
	
	for (var i = 1; i < table.rows.length - 1; i++) {
		table.deleteRow(1);
	}
	
	hasPlaceholder = true;
}