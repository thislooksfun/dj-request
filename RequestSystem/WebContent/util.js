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