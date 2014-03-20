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

@ServerEndpoint(value = "/websocket/request",
encoders = {SongEncoder.class})
public class RequestEndpoint
{
	public static Set<Session> sessions = Collections.synchronizedSet(new HashSet<Session>());
	
	@OnOpen
	public void onOpen(Session session) {
		System.out.println("Session " + session.getId() + " opened!");
		sessions.add(session);
		
		try {
			session.getBasicRemote().sendText("Welcome!");
			Iterator<Integer> iterator = LibraryDecoder.songs.keySet().iterator();
			session.getBasicRemote().sendText("SONGCOUNT:"+LibraryDecoder.songs.size());
			while (iterator.hasNext()) {
				Song song = LibraryDecoder.songs.get(iterator.next());
				session.getBasicRemote().sendObject(song);
			}
		} catch (IOException | EncodeException e) {
			e.printStackTrace();
		}
	}
	
	public void sendRequestUpdate(int trackID, int newCount) {
		Iterator<Session> iterator = sessions.iterator();
		
		while (iterator.hasNext()) {
			Session session = iterator.next();
			
			try {
				session.getBasicRemote().sendText("REQUESTUPDATE:id='"+trackID+"', newCount='"+newCount+"'");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public void sendFullUpdate() {
		Iterator<Session> iterator = sessions.iterator();
		
		while (iterator.hasNext()) {
			Session session = iterator.next();
			
			try {
				session.getBasicRemote().sendText("FULLUPDATE");
				Iterator<Integer> songIterator = LibraryDecoder.songs.keySet().iterator();
				session.getBasicRemote().sendText("SONGCOUNT:"+LibraryDecoder.songs.size());
				while (songIterator.hasNext()) {
					Song song = LibraryDecoder.songs.get(songIterator.next());
					session.getBasicRemote().sendObject(song);
				}
			} catch (IOException | EncodeException e) {
				e.printStackTrace();
			}
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