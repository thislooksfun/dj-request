package com.tlf.util;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.websocket.CloseReason;
import javax.websocket.EncodeException;
import javax.websocket.Session;

import com.tlf.itunes.Song;
import com.tlf.itunes.SongSystem;

public class WebsocketHelper
{
    private static Set<Session> sessions = Collections.synchronizedSet(new HashSet<Session>());
    
    public static void sendRequestUpdate(int trackID, int newCount)
    {
        Iterator<Session> iterator = sessions.iterator();
        
        while (iterator.hasNext()) {
            Session session = iterator.next();
            
            try {
                session.getBasicRemote().sendText("REQUESTUPDATE:id='" + trackID + "', newCount='" + newCount + "'");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    public static void sendManualRequest(Song song)
    {
        Iterator<Session> iterator = sessions.iterator();
        
        while (iterator.hasNext()) {
            Session session = iterator.next();
            
            try {
                session.getBasicRemote().sendObject(song);
            } catch (IOException | EncodeException e) {
                e.printStackTrace();
            }
        }
    }
    
    public static void sendFullUpdate()
    {
        Iterator<Session> clients = sessions.iterator();
        
        while (clients.hasNext()) {
            Session session = clients.next();
            
            try {
                session.getBasicRemote().sendText("FULLUPDATE");
                Iterator<Integer> iterator;
                Iterator<Integer> manual;
                
                if (SongSystem.instance.allowExplicit) {
                    iterator = SongSystem.instance.songs.keySet().iterator();
                    manual = SongSystem.instance.manual.keySet().iterator();
                    session.getBasicRemote().sendText("SONGCOUNT:" + SongSystem.instance.songs.size());
                } else {
                    iterator = SongSystem.instance.notExplicit.keySet().iterator();
                    manual = SongSystem.instance.manualNotExplicit.keySet().iterator();
                    session.getBasicRemote().sendText("SONGCOUNT:" + SongSystem.instance.notExplicit.size());
                }
                
                while (iterator.hasNext()) {
                    Song song = SongSystem.instance.songs.get(iterator.next());
                    session.getBasicRemote().sendObject(song);
                }
                while (manual.hasNext()) {
                    Song song = SongSystem.instance.manual.get(manual.next());
                    session.getBasicRemote().sendObject(song);
                }
            } catch (IOException | EncodeException e) {
                e.printStackTrace();
            }
        }
    }
    
    public static void openSession(Session session)
    {
        System.out.println("Session " + session.getId() + " opened!");
        sessions.add(session);
        
        try {
            session.getBasicRemote().sendText("Welcome!");
            Iterator<Integer> iterator;
            Iterator<Integer> manual;
            
            if (SongSystem.instance.allowExplicit) {
                iterator = SongSystem.instance.songs.keySet().iterator();
                manual = SongSystem.instance.manual.keySet().iterator();
                session.getBasicRemote().sendText("SONGCOUNT:" + SongSystem.instance.songs.size());
            } else {
                iterator = SongSystem.instance.notExplicit.keySet().iterator();
                manual = SongSystem.instance.manualNotExplicit.keySet().iterator();
                session.getBasicRemote().sendText("SONGCOUNT:" + SongSystem.instance.notExplicit.size());
            }
            
            while (iterator.hasNext()) {
                Song song = SongSystem.instance.songs.get(iterator.next());
                session.getBasicRemote().sendObject(song);
            }
            while (manual.hasNext()) {
                Song song = SongSystem.instance.manual.get(manual.next());
                session.getBasicRemote().sendObject(song);
            }
        } catch (IOException | EncodeException e) {
            e.printStackTrace();
        }
    }
    
    public static void closeSession(Session session, CloseReason reason)
    {
        System.out.println("Session " + session.getId() + " closed: " + reason.getReasonPhrase() + " code " + reason.getCloseCode().getCode());
        sessions.remove(session);
    }
}
