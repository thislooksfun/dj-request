package com.tlf.servlets.websocket;

import java.io.IOException;

import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.tlf.itunes.Song;
import com.tlf.itunes.SongSystem;
import com.tlf.servlets.websocket.encoders.SongEncoder;
import com.tlf.util.WebsocketHelper;

/**
 * Servlet implementation class AdminEndpoint
 */
@ServerEndpoint(value = "/websocket/admin",
encoders = {SongEncoder.class})
public class AdminEndpoint
{
	@OnOpen
	public void onOpen(Session session) {
		WebsocketHelper.openSession(session);
	}
	
	@OnClose
	public void onClose(Session session, CloseReason reason) {
		WebsocketHelper.closeSession(session, reason);
	}
	
	@OnMessage
	public void onMessage(Session session, String msg)
	{
		try {
			if (msg.indexOf("PLAYED:") == 0) {
				System.out.println("Request!");
				Song song = SongSystem.instance.getSong(Integer.parseInt(msg.substring(7)));
				song.requests = 0;
				WebsocketHelper.sendRequestUpdate(song.UUID, song.requests);
			}
			session.getBasicRemote().sendText(msg);
		} catch (IOException | NumberFormatException e) {
			e.printStackTrace();
		}
	}
	
	@OnError
	public void error(Session session, Throwable throwable)
	{
		System.err.println("ERROR:");
		throwable.printStackTrace();
	}
}
