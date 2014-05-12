/** Taken from http://stackoverflow.com/a/17994303/3438793 */
package com.tlf.servlets.websocket.configurators;

import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;

public class GetHttpSessionConfigurator extends ServerEndpointConfig.Configurator
{
	@Override
	public void modifyHandshake(ServerEndpointConfig config, HandshakeRequest request, HandshakeResponse response)
	{
		HttpSession httpSession = (HttpSession)request.getHttpSession();
		config.getUserProperties().put(HttpSession.class.getName(),httpSession);
	}
}