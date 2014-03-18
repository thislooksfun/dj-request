var wsUri = "ws://" + document.location.host + "/RequestSystem/websocket/chat";
var ws;

var websocket = 'WebSocket' in window;

var chatlog = document.getElementById("chatlog");

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
			chatlog.textContent += "CONNECTING TO " + wsUri + "\n";
		}

		ws.onopen = function(evt) {
			chatlog.textContent += "CONNECTED\n";
			//ws.send("Testing");
		};
		ws.onclose = function(evt) {
			chatlog.textContent += "DISCONNECTED\n";
		};
		ws.onmessage = function(message) {
			chatlog.textContent += message.data + "\n";
		};
		ws.onerror = function(evt) {
			chatlog.textContent += "An error occurred... :(\n";
		};
	}
}

function postToServer() {
	var tosend = document.getElementById("msg").value;
	chatlog.textContent += "SENT: " + tosend + "\n";
	ws.send(tosend);
	document.getElementById("msg").value = "";
}

function chatKeyPress(e) {
	if (typeof e == 'undefined' && window.event) {
		e = window.event;
	}
	if (e.keyCode == 13) {
		postToServer();
	}
}

function decodeSong(song) {
	
}

connect();