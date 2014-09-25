package com.tlf.servlets.websocket;

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

@ServerEndpoint(
        value = "/websocket/request",
        encoders = {SongEncoder.class})
public class RequestEndpoint
{
    @OnOpen
    public void onOpen(Session session)
    {
        WebsocketHelper.openSession(session, null);
    }
    
    @OnClose
    public void onClose(Session session, CloseReason reason)
    {
        WebsocketHelper.closeSession(session, reason);
    }
    
    @OnMessage
    public void onMessage(Session session, String msg)
    {
        try {
            if (msg.indexOf("REQUEST:") == 0) {
                Song song = SongSystem.instance.getSong(Integer.parseInt(msg.substring(8)));
                song.requests++;
                WebsocketHelper.sendRequestUpdate(song.UUID, song.requests, song.manual);
            }
        } catch (NumberFormatException e) {
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