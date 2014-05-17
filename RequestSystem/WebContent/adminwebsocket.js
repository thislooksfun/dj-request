var wsUri = ((document.location.protocol === 'https:') ? "wss://" : "ws://") + document.location.host + "/RequestSystem/websocket/admin";
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
			console.log("CONNECTING TO " + wsUri);
		}
		
		ws.onopen = function(evt) {
			console.log("CONNECTED");
			reconnecting = false;
			clearTable();
		};
		ws.onclose = function(evt) {
			console.log("DISCONNECTED");
			if (!reconnecting) {
				reconnecting = true;
				connect();
			}
		};
		ws.onmessage = function(message) {
			onMessage(message);
		};
		ws.onerror = function(evt) {
			console.log("An error occurred... :(");
		};
	}
};

function send(message) {
	console.log("SENT: " + message);
	ws.send(message);
}

function playSong(song) {
	if (song != null) {
		send("PLAYED:"+song);
	}
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
		console.log("Loading " + totalSongCount + " songs");
	} else if (data.length > 5 && data.substring(0, 5) == "song=") {
		addSong(data);
		currentSong++;
		if (totalSongCount > 0) {
			if (currentSong < totalSongCount) {
				document.getElementById("noItemsBar").style.display = "none";
				document.getElementById("noManualRequestBar").style.display = "none";
				document.getElementById("spacerBar").style.display = "";
				document.getElementById("tableHeader").textContent = Math.round((currentSong/totalSongCount)*100) + "% loaded  (" + currentSong + "/" + totalSongCount + ")";
				document.getElementById("manualRequests_filter").firstChild.lastChild.disabled = true;
				document.getElementById("manualRequests_filter").firstChild.lastChild.style.cursor = "not-allowed";
			} else {
				document.getElementById("manualRequests_filter").firstChild.lastChild.disabled = false;
				document.getElementById("manualRequests_filter").firstChild.lastChild.style.cursor = "";
				document.getElementById("tableHeader").textContent = "Please select a song";
				var buttons = document.getElementsByName("playButtons");
				for (var i = 0; i < buttons.length; i++) {
					if (buttons[i].parent.nextSibling.innerHTML != 0) {
						buttons[i].disabled = false;
						buttons[i].style.cursor = "pointer";
					}
				}
				checkForEmpty();
				console.log("Loaded");
			}
		}
	} else if (data.length > 14 && data.substring(0, 14) == "REQUESTUPDATE:") {
		updateRequestCount(data.substring(14));
	} else if (data.length == 10 && data.substring(0, 10) == "FULLUPDATE") {
		clearTable();
	} else {
		console.log(data);
	}
}

window.onunload = function() {
	ws.close();
};