var wsUri = "ws://" + document.location.host + "/RequestSystem/websocket/admin";
var ws;

var websocket = 'WebSocket' in window;

var reconnecting = false;

function connect() {
	if (!websocket) {
		chatlog += "WebSockets aren't supported in your browser, sorry";
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

function playSong(song) {
	if (song != null) {
		send("PLAYED:"+song);
		resortRequests();
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
			} else {
				tableSearch.init();
				document.getElementById("tableHeader").textContent = "Please select a song";
				document.getElementById("textBoxSearch").disabled = false;
				var buttons = document.getElementsByName("playButtons");
				for (var i = 0; i < buttons.length; i++) {
					buttons[i].disabled = false;
				}
			}
		}
	} else if (data.length > 14 && data.substring(0, 14) == "REQUESTUPDATE:") {
		updateRequestCount(data.substring(14));
	} else if (data.length == 10 && data.substring(0, 10) == "FULLUPDATE") {
		clearTable();
	} else {
		log(data);
	}
}