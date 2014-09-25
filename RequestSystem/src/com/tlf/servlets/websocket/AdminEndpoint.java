package com.tlf.servlets.websocket;

import java.io.IOException;

import javax.servlet.http.HttpSession;
import javax.websocket.CloseReason;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.tlf.itunes.Song;
import com.tlf.itunes.SongSystem;
import com.tlf.servlets.websocket.configurators.GetHttpSessionConfigurator;
import com.tlf.servlets.websocket.encoders.SongEncoder;
import com.tlf.util.LoginHelper;
import com.tlf.util.WebsocketHelper;

/**
 * Servlet implementation class AdminEndpoint
 */
@ServerEndpoint(
		value = "/websocket/admin",
		encoders = {SongEncoder.class},
		configurator = GetHttpSessionConfigurator.class)
public class AdminEndpoint
{
	private EndpointConfig config;
	
	@OnOpen
	public void onOpen(Session session, EndpointConfig config)
	{
		this.config = config;
		HttpSession httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
		if (LoginHelper.instance.isSessionLoggedIn(httpSession)) {
			WebsocketHelper.openSession(session, httpSession);
		} else {
			try {
				session.close(new CloseReason(CloseReason.CloseCodes.VIOLATED_POLICY, "User tried to open a restricted connection without being logged in!"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
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
			HttpSession httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
			if (LoginHelper.instance.isSessionLoggedIn(httpSession)) {
				if (msg.indexOf("PLAYED:") == 0) {
	                Song song = SongSystem.instance.getSong(Integer.parseInt(msg.substring(7)));
	                song.requests = 0;
	                if (song.manual) {
	                	SongSystem.instance.remove(song.UUID);
	                }
	                WebsocketHelper.sendRequestUpdate(song.UUID, song.requests, song.manual);
	            }
	            session.getBasicRemote().sendText(msg);
			} else {
				session.close(new CloseReason(CloseReason.CloseCodes.VIOLATED_POLICY, "User isn't logged in!"));
			}
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
