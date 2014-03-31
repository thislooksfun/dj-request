package com.tlf.util;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.websocket.CloseReason;
import javax.websocket.EncodeException;
import javax.websocket.Session;

import com.tlf.itunes.LibraryDecoder;
import com.tlf.itunes.Song;

public class WebsocketHelper
{
	private static Set<Session> sessions = Collections.synchronizedSet(new HashSet<Session>());
	
	public static void sendRequestUpdate(int trackID, int newCount) {
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
	
	public static void sendFullUpdate() {
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
	
	public static void openSession(Session session) {
		System.out.println("Session " + session.getId() + " opened!");
		sessions.add(session);
		
		try {
			session.getBasicRemote().sendText("Welcome!");
			Iterator<Integer> iterator;
			
			if (LibraryDecoder.allowExplicit) {
				iterator = LibraryDecoder.songs.keySet().iterator();
				session.getBasicRemote().sendText("SONGCOUNT:"+LibraryDecoder.songs.size());
			} else {
				iterator = LibraryDecoder.notExplicit.keySet().iterator();
				session.getBasicRemote().sendText("SONGCOUNT:"+LibraryDecoder.notExplicit.size());
			}
			
			while (iterator.hasNext()) {
				Song song = LibraryDecoder.songs.get(iterator.next());
				session.getBasicRemote().sendObject(song);
			}
		} catch (IOException | EncodeException e) {
			e.printStackTrace();
		}
	}
	
	public static void closeSession(Session session, CloseReason reason) {
		System.out.println("Session " + session.getId() + " closed: " + reason.getReasonPhrase() + " code " + reason.getCloseCode().getCode());
		sessions.remove(session);
	}
}
