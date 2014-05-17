var manTable;
var songTable;
var lastSearch;

var link = "/request";

function onLoad() {
	initTables();
	checkForEmpty();
	connect();
}

function initTables()
{
	//Initalizing table 1
	$("#manualRequests").dataTable({
		"paging": false,
		"info": false,
		"columnDefs": [{
			"targets": "nosort",
			"searchable": false,
			"orderable": false
		}, {
			"targets": "hidden",
			"visible": false,
            "searchable": false,
            "orderable": false
		}, {
        	"targets": "reverse",
        	"orderSequence": ["desc", "asc"]
        }, {
        	"targets": 4,
        	"sType": "time"
        }]
	}).fnSort([[1, 'desc'], [3, 'asc']]);
	manTable = $("#manualRequests").DataTable();
	
	//Makes column 1 (request count) sort by column 3 (song name) if the data in column 1 is identical
	$('#manualRequests thead th:eq(1)').unbind('click').click(function() {
		var table = $("#manualRequests").dataTable();
		var aaSorting = table.fnSettings().aaSorting;
		if (aaSorting[0][1] == 'desc') {
			table.fnSort([[1, 'asc'], [3, 'asc']]);
		} else {
			table.fnSort([[1, 'desc'], [3, 'asc']]);
		}
	});
	
	
	//Initalizing table 2
	$("#songList").dataTable({
		"paging": false,
		"info": false,
		"dom": "lrtip",
		"columnDefs": [{
			"targets": "nosort",
			"searchable": false,
			"orderable": false
		}, {
			"targets": "hidden",
			"visible": false,
            "searchable": false,
            "orderable": false
		}, {
        	"targets": "reverse",
        	"orderSequence": ["desc", "asc"]
        }, {
        	"targets": 3,
        	"sType": "time"
        }]
	}).fnSort([[1, 'desc'], [2, 'asc']]);
	songTable = $("#songList").DataTable();
	
	//Makes column 1 (request count) sort by column 2 (song name) if the data in column 1 is identical
	$('#songList thead th:eq(1)').unbind('click').click(function() {
		var table = $("#songList").dataTable();
		var aaSorting = table.fnSettings().aaSorting;
		if (aaSorting[0][1] == 'desc') {
			table.fnSort([[1, 'asc'], [2, 'asc']]);
		} else {
			table.fnSort([[1, 'desc'], [2, 'asc']]);
		}
	});
	
	
	//Apply sort from table 1 to table 2
	$('#manualRequests').on('search.dt', function() {
		var term = removeDiacritics(manTable.search());
		if (term != lastSearch) {
			lastSearch = term;
			manTable.search(term).draw();
		    songTable.search(term).draw();
		    updateLink(term);
		    checkNotFound();
		}
	});
}
function updateLink(param) {
	var rLink = document.getElementById('requestLink');
	if (rLink != null) {
		rLink.href = link + "?search=" + formatForURI(param);
	}
}

var URIFormat = [
	{"match":/\x25/g, "replace":"%25"},
	{"match":/\x21/g, "replace":"%21"},
	{"match":/\x23/g, "replace":"%23"},
	{"match":/\x24/g, "replace":"%24"},
	{"match":/\x26/g, "replace":"%26"},
	{"match":/\x27/g, "replace":"%27"},
	{"match":/\x28/g, "replace":"%28"},
	{"match":/\x29/g, "replace":"%29"},
	{"match":/\x2A/g, "replace":"%2A"},
	{"match":/\x2B/g, "replace":"%2B"},
	{"match":/\x2C/g, "replace":"%2C"},
	{"match":/\x2F/g, "replace":"%2F"},
	{"match":/\x3A/g, "replace":"%3A"},
	{"match":/\x3B/g, "replace":"%3B"},
	{"match":/\x3D/g, "replace":"%3D"},
	{"match":/\x3F/g, "replace":"%3F"},
	{"match":/\x40/g, "replace":"%40"},
	{"match":/\x5A/g, "replace":"%5A"},
	{"match":/\x5D/g, "replace":"%5D"}
];
function formatForURI(param) {
	for (var i = 0; i < URIFormat.length; i++) {
		param = param.replace(URIFormat[i]["match"], URIFormat[i]["replace"]);
	}
	return param;
}

function checkForEmpty()
{
	var manRow1 = document.getElementById('manSongBody').getElementsByTagName('TR')[0];
	var row1 = document.getElementById('songBody').getElementsByTagName('TR')[0];
	
	if (hasManualPlaceholder) {
		manRow1.style.display = "none";
		document.getElementById("noManualRequestBar").style.display = "";
		document.getElementById("spacerBar").style.display = "none";
	} else {
		document.getElementById("noManualRequestBar").style.display = "none";
		document.getElementById("spacerBar").style.display = "";
		
	}
	
	if (hasPlaceholder) {
		row1.style.display = "none";
		document.getElementById("noItemsBar").style.display = "";
	} else {
		document.getElementById("noItemsBar").style.display = "none";
	}
}

function checkNotFound()
{
	var $empty1 = $("#manualRequests").find(".dataTables_empty");
	var $empty2 = $("#songList").find(".dataTables_empty");
	
	if ($empty1.length > 0 && $empty2.length > 0) {
		document.getElementById("noResultBar").style.display = "";
	} else {
		document.getElementById("noResultBar").style.display = "none";
	}
}

function scrollToTop() {
	$("html, body").animate({ scrollTop: 0 }, "slow");
	return false;
}

function fixDiv() {
	var $cache = $('#backToTop');
	if ($(window).scrollTop() > 400) {
		$cache[0].style.display = "";
	} else {
		$cache[0].style.display = "none";
	}
};
$(window).scroll(fixDiv);

//Custom sorting
jQuery.fn.dataTableExt.oSort['time-asc'] = function(x,y)
{
	var xTimes = x.split(":");
	var yTimes = y.split(":");
	
	if (xTimes.length < yTimes.length) {
		return -1;
	} else if (xTimes.length > yTimes.length) {
		return 1;
	} else {
		for (var i = 0; i < xTimes.length; i++)
		{
			if (xTimes[i].length < yTimes[i].length) {
				return -1;
			} else if (xTimes[i].length > yTimes[i].length) {
				return 1;
			} else {
				var numX = parseInt(xTimes[i]);
				var numY = parseInt(yTimes[i]);
				if (numX < numY) {
					return -1;
				} else if (numX > numY) {
					return 1;
				}
			}
		}
	}
	
	return 0;
};

jQuery.fn.dataTableExt.oSort['time-desc'] = function(x,y)
{
	var xTimes = x.split(":");
	var yTimes = y.split(":");
	
	if (xTimes.length < yTimes.length) {
		return 1;
	} else if (xTimes.length > yTimes.length) {
		return -1;
	} else {
		for (var i = 0; i < xTimes.length; i++)
		{
			if (xTimes[i].length < yTimes[i].length) {
				return 1;
			} else if (xTimes[i].length > yTimes[i].length) {
				return -1;
			} else {
				var numX = parseInt(xTimes[i]);
				var numY = parseInt(yTimes[i]);
				if (numX < numY) {
					return 1;
				} else if (numX > numY) {
					return -1;
				}
			}
		}
	}
	
	return 0;
};

jQuery.fn.DataTable.ext.type.search.string = function ( data ) {
    return ! data ? '' : (typeof data === 'string' ? removeDiacritics(data) : data);
};

//String prototype methods
String.prototype.replaceAll = function(find, replace) {
	return this.replace(new RegExp(find, 'g'), replace);
};