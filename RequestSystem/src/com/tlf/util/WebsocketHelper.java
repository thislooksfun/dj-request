package com.tlf.util;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.websocket.CloseReason;
import javax.websocket.EncodeException;
import javax.websocket.Session;

import com.tlf.itunes.Song;
import com.tlf.itunes.SongSystem;

public class WebsocketHelper
{
	private static Map<Session, HttpSession> sessions = Collections.synchronizedMap(new HashMap<Session, HttpSession>());
	
	public static void sendRequestUpdate(int trackID, int newCount, boolean manual)
	{
		Iterator<Session> iterator = sessions.keySet().iterator();
		
		while (iterator.hasNext()) {
			Session session = iterator.next();
			
			try {
				if (sessions.get(session) != null && LoginHelper.instance.isSessionLoggedIn(sessions.get(session))) {
					session.getBasicRemote().sendText(String.format("REQUESTUPDATE:id='%s', newCount='%s', manual='%s'", trackID, newCount, (manual ? "true" : "false")));
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void sendManualRequest(Song song)
	{
		Iterator<Session> iterator = sessions.keySet().iterator();
		while (iterator.hasNext()) {
			Session session = iterator.next();
			
			try {
				if (sessions.get(session) != null && LoginHelper.instance.isSessionLoggedIn(sessions.get(session))) {
					session.getBasicRemote().sendObject(song);
				}
			} catch (IOException | EncodeException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void sendFullUpdate()
	{
		Iterator<Session> clients = sessions.keySet().iterator();
		
		while (clients.hasNext()) {
			Session session = clients.next();
			
			try {
				if (sessions.get(session) != null && LoginHelper.instance.isSessionLoggedIn(sessions.get(session))) {
					session.getBasicRemote().sendText("FULLUPDATE");
					Iterator<Integer> iterator;
					
					if (SongSystem.instance.allowExplicit) {
						iterator = SongSystem.instance.songs.keySet().iterator();
						session.getBasicRemote().sendText("SONGCOUNT:" + SongSystem.instance.songs.size());
					} else {
						iterator = SongSystem.instance.notExplicit.keySet().iterator();
						session.getBasicRemote().sendText("SONGCOUNT:" + SongSystem.instance.notExplicit.size());
					}
					
					while (iterator.hasNext()) {
						Song song = SongSystem.instance.songs.get(iterator.next());
						session.getBasicRemote().sendObject(song);
					}
				}
			} catch (IOException | EncodeException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void openSession(Session session, HttpSession httpSession)
	{
		sessions.put(session, httpSession);
		System.out.println("Opened session " + session.getId());
		
		try {
			session.getBasicRemote().sendText("Welcome!");
			Iterator<Integer> iterator;
			
			if (SongSystem.instance.allowExplicit) {
				iterator = SongSystem.instance.songs.keySet().iterator();
				session.getBasicRemote().sendText("SONGCOUNT:" + SongSystem.instance.songs.size());
			} else {
				iterator = SongSystem.instance.notExplicit.keySet().iterator();
				session.getBasicRemote().sendText("SONGCOUNT:" + SongSystem.instance.notExplicit.size());
			}
			
			while (iterator.hasNext()) {
				Song song = SongSystem.instance.songs.get(iterator.next());
				session.getBasicRemote().sendObject(song);
			}
		} catch (IOException | EncodeException e) {
			e.printStackTrace();
		}
	}
	
	public static void closeSession(Session session, CloseReason reason)
	{
		sessions.remove(session);
		System.out.println("Closed session " + session.getId());
	}
}
