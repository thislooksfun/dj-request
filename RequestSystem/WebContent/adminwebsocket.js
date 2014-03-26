var wsUri = "ws://" + document.location.host + "/RequestSystem/websocket/admin";
var ws;

var websocket = 'WebSocket' in window;

var chatlog;

var reconnecting = false;

function connect() {
	chatlog = document.getElementById("chatlog");
	
	if (!websocket) {
		chatlog += "WebSockets aren't supported in your browser, sorry";
		return;
	} else {

		if (ws != null && ws.readyState == ws.OPEN) {
			ws.close();
		}

		ws = new WebSocket(wsUri);

		if (ws != null) {
			chatlog.textContent += "CONNECTING TO " + wsUri + "\n";
		}

		ws.onopen = function(evt) {
			chatlog.textContent += "CONNECTED\n";
			reconnecting = false;
			clearTable();
		};
		ws.onclose = function(evt) {
			chatlog.textContent += "DISCONNECTED\n";
			if (!reconnecting) {
				reconnecting = true;
				connect();
			}
		};
		ws.onmessage = function(message) {
			onMessage(message);
		};
		ws.onerror = function(evt) {
			chatlog.textContent += "An error occurred... :(\n";
		};
	}
};

function send(message) {
	chatlog.textContent += "SENT: " + message + "\n";
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
		chatlog.textContent += "Loading " + totalSongCount + " songs\n";
	} else if (data.length > 5 && data.substring(0, 5) == "song=") {
		addSong(data);
		currentSong++;
		if (totalSongCount > 0) {
			if (currentSong < totalSongCount) {
				document.getElementById("tableHeader").textContent = "Loading " + (totalSongCount - currentSong) + " items. Please wait.";
			} else {
				tableSearch.init();
				document.getElementById("tableHeader").textContent = "Please select a song";
				
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
		chatlog.textContent += data + "\n";
	}
}