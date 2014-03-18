package com.tlf.servlets.websocket;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

import javax.websocket.CloseReason;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.tlf.itunes.LibraryDecoder;
import com.tlf.itunes.Song;

@ServerEndpoint(value = "/websocket/chat",
encoders = {SongEncoder.class})
public class ChatEndpoint
{
	public static Set<Session> sessions = Collections.synchronizedSet(new HashSet<Session>());
	
	@OnOpen
	public void onOpen(Session session) {
		System.out.println("Session " + session.getId() + " opened!");
		sessions.add(session);
		
		try {
			int temp = 0;
			session.getBasicRemote().sendText("Welcome!");
			Iterator<Song> iterator = LibraryDecoder.songs.iterator();
			while (iterator.hasNext()) {
				Song song = iterator.next();
				session.getBasicRemote().sendObject(song);
				temp++;
			}
			System.out.println(String.format("Sent %s songs", temp));
		} catch (IOException | EncodeException e) {
			e.printStackTrace();
		}
	}
	
	@OnClose
	public void onClose(Session session, CloseReason reason) {
		System.out.println("Session " + session.getId() + " closed: " + reason.getReasonPhrase() + " code " + reason.getCloseCode().getCode());
		sessions.remove(session);
	}
	
	@OnMessage
	public void onMessage(Session session, String msg)
	{
		System.out.println("String message!");
		try {
			session.getBasicRemote().sendText(msg);
		} catch (IOException e) {
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