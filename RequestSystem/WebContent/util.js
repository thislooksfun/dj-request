var chatlog;

function log(message) {
	if (chatlog == null) {
		chatlog = document.getElementById("chatlog");
	}
	
	if (message == "") {
		message = "%null%";
	}
	chatlog.textContent += message + "\n";
}

function onLoad() {
	connect();
	tableSearch.init();
	checkForEmpty();
	alert(alert);
}

function resortRequests()
{
	var requests = document.getElementById("requestColumn");

	var sortLevel = 0;

	var oldClassname = requests.className;
	if ((requests.className = requests.className.replace('sorttable_sorted_reverse', '')) != oldClassname) {
		sortLevel = requests.className.search(/\bsorttable_reverse\b/) != -1 ? 1 : 2;
	} else if ((requests.className = requests.className.replace('sorttable_sorted', '')) != oldClassname) {
		sortLevel = requests.className.search(/\bsorttable_reverse\b/) != -1 ? 2 : 1;
	}

	for (var j = 0; j < sortLevel; j++) {
		sorttable.innerSortFunction.apply(requests, []);
	}
}

function colorTable()
{
	var color1 = "#242424";
	var color2 = "#424242";

	var colorState = false;

	for (var i = 0, row; row = tableSearch.Rows[i], rowText = tableSearch.RowsText[i]; i++) {
		if (row.style.display != "none") {
			var cells = row.cells;
			for (var j = 0; j < cells.length; j++) {
				cells[j].style.backgroundColor = colorState ? color1 : color2;
			}
			colorState = !colorState;
		}
	}
}

String.prototype.endsWith = function(suffix) {
	return this.indexOf(suffix, this.length - suffix.length) !== -1;
};

window.onunload = function() {
	ws.close();
};

function checkForEmpty()
{
	var row1 = document.getElementById('manualSubmits').getElementsByTagName('TR')[0];
	var row1Text = removeDiacritics((row1.innerText) ? row1.innerText.toUpperCase() : row1.textContent.toUpperCase()).trim();

	if (row1Text == ("empty".toUpperCase())) {
		row1.style.display = "none";
		document.getElementById("noManualRequestBar").style.display = "";
	} else {
		document.getElementById("noManualRequestBar").style.display = "none";
	}
	
	if (tableSearch.RowsText[0] == ("empty".toUpperCase())) {
		tableSearch.Rows[0].style.display = "none";
		document.getElementById("noItemsBar").style.display = "";
	} else {
		document.getElementById("noItemsBar").style.display = "none";
	}
}