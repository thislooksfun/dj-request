//define the table search as an object, which can implement both functions and properties
window.tableSearch = {};

//initialize the search, setup the current object
tableSearch.init = function() {
	//define the properties I want on the tableSearch object
	this.Rows = document.getElementById('searchSongs').getElementsByTagName('TR');
	this.RowsLength = tableSearch.Rows.length;
	this.RowsText = [];

	//loop through the table and add the data to
	for (var i = 0; i < tableSearch.RowsLength; i++) {
		this.RowsText[i] = removeDiacritics((tableSearch.Rows[i].innerText) ? tableSearch.Rows[i].innerText.toUpperCase() : tableSearch.Rows[i].textContent.toUpperCase());
	}
};

//only shows the relevant rows as determined by the search string
tableSearch.runSearch = function() {
	//get the search term
	this.Term = removeDiacritics(document.getElementById('textBoxSearch').value.toUpperCase());
	
	var count = 0;
	
	//loop through the rows and hide rows that do not match the search query
	for (var i = 0, row; row = this.Rows[i], rowText = this.RowsText[i]; i++) {
		row.style.display = ((rowText.indexOf(this.Term) != -1) || this.Term === '') ? '': 'none';
		if (row.style.display == '') {
			count++;
		}
	}
	
	colorTable();
	
	var searchResult = document.getElementById("searchResult");
	
	if (count == this.Rows.length) {
		searchResult.textContent = "";
	} else {
		searchResult.textContent = (count == 0 ? "No" : count) + " results found";
	}
};