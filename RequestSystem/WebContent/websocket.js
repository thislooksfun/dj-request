var wsUri = ((document.location.protocol === 'https:') ? "wss://" : "ws://") + document.location.host + "/RequestSystem/websocket/request";
var ws;

var websocket = 'WebSocket' in window;

var reconnecting = false;

function connect() {
	if (!websocket) {
		alert("WebSockets aren't supported in your browser, sorry");
		return;
	} else {

		if (ws != null && ws.readyState == ws.OPEN) {
			ws.close();
		}

		ws = new WebSocket(wsUri);

		if (ws != null) {
			log("CONNECTING TO " + wsUri);
		}

		ws.onopen = function(evt) {
			log("CONNECTED");
			reconnecting = false;
			clearTable();
		};
		ws.onclose = function(evt) {
			log("DISCONNECTED");
			if (!reconnecting) {
				reconnecting = true;
				connect();
			}
		};
		ws.onmessage = function(message) {
			onMessage(message);
		};
		ws.onerror = function(evt) {
			log("An error occurred... :(");
		};
	}
};

function send(message) {
	log("SENT: " + message);
	ws.send(message);
}

function requestSong(song)
{
	if (song != null) {
		var id = song.substring(song.indexOf("'", song.indexOf("id="))+1, song.indexOf("'", song.indexOf("'", song.indexOf("id="))+1));
		var title = song.substring(song.indexOf("'", song.indexOf("name="))+1, song.indexOf("'", song.indexOf("'", song.indexOf("name="))+1));
		var artist = song.substring(song.indexOf("'", song.indexOf("artist="))+1, song.indexOf("'", song.indexOf("'", song.indexOf("artist="))+1));
		
		var confirmMessage = "Are you sure you want to request '" + title + "'";
		confirmMessage += artist == "null" || artist == "Not Defined" ? "?" :  " by '" + artist +"'?";
		
		if (confirm(confirmMessage)) {
			send("REQUEST:"+id);
			alert("Thank you for your request!");
		}
	}
}

function getRadioButtonValue(rbutton)
{
	for (var i = 0; i < rbutton.length; ++i)
	{ 
		if (rbutton[i].checked) {
			rbutton[i].checked = false;
			return rbutton[i].value;
		}
	}
	
	return null;
}

function postToServer() {
	var tosend = document.getElementById("msg").value;
	document.getElementById("msg").value = "";
	send(tosend);
}

function chatKeyPress(e) {
	if (typeof e == 'undefined' && window.event) {
		e = window.event;
	}
	if (e.keyCode == 13) {
		postToServer();
	}
}

var totalSongCount = 0;
var currentSong = 0;
function onMessage(message)
{
	var data = message.data;
	if (data.length > 10 && data.substring(0, 10) == "SONGCOUNT:") {
		totalSongCount = +data.substring(10);
		currentSong = 0;
		log("Loading " + totalSongCount + " songs");
	} else if (data.length > 5 && data.substring(0, 5) == "song=") {
		addSong(data);
		currentSong++;
		if (totalSongCount > 0) {
			if (currentSong < totalSongCount) {
				document.getElementById("tableHeader").textContent = Math.round((currentSong/totalSongCount)*100) + "% loaded  (" + currentSong + "/" + totalSongCount + ")";
				document.getElementById("textBoxSearch").disabled = true;
				document.getElementById("textBoxSearch").style.cursor = "not-allowed";
			} else {
				tableSearch.init();
				document.getElementById("tableHeader").textContent = "Please select a song";
				document.getElementById("textBoxSearch").disabled = false;
				document.getElementById("textBoxSearch").style.cursor = "";
				var buttons = document.getElementsByName("requestButtons");
				for (var i = 0; i < buttons.length; i++) {
					buttons[i].disabled = false;
					buttons[i].style.cursor = "pointer";
				}
				checkForEmpty();
			}
		}
	} else if (data.length > 14) {
		if (data.substring(0, 14) == "REQUESTUPDATE:") {
			updateRequestCount(data.substring(14));
		} else if (data.substring(0, 14) == "MANUALREQUEST:") {
			addManualRequest(data.substring(14));
		}
	} else if (data.length == 10 && data.substring(0, 10) == "FULLUPDATE") {
		clearTable();
	} else {
		log(data);
	}
}