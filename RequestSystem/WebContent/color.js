var color1 = "#242424";
var color2 = "#424242";

function colorTable()
{
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