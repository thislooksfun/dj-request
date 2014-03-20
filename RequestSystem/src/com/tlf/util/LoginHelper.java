package com.tlf.util;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Maps;

public class LoginHelper
{
	public static final LoginHelper instance = new LoginHelper();
	
	private BiMap<String, String> users = Maps.synchronizedBiMap(HashBiMap.create(new HashMap<String, String>()));
	private BiMap<HttpSession, String> loggedIn = Maps.synchronizedBiMap(HashBiMap.create(new HashMap<HttpSession, String>()));
	private BiMap<HttpSession, Integer> failedAttemps = Maps.synchronizedBiMap(HashBiMap.create(new HashMap<HttpSession, Integer>()));
	
	private LoginHelper() {
		this.populatePasswords();
	}
	
	private void populatePasswords() {
		this.users.put("Admin", "Admin");
		this.users.put("thislooksfun", "cocorequest");
	}
	
	public boolean login(HttpSession session, String username, String password)
	{
		if (this.users.get(username) != null && this.users.get(username).equals(password)) {
			if (this.isUserLoggedIn(username)) {
				this.logout(username);
			}
			
			this.loggedIn.put(session, username);
			return true;
		}
		
		Object temp = this.failedAttemps.get(session.getId());
		int attempt = (temp != null ? (int)temp+1 : 0);
		this.failedAttemps.put(session, attempt);
		return false;
	}
	
	public int getLoginAttempt(HttpSession session) {
		Object temp = this.failedAttemps.get(session.getId());
		return (temp != null ? (int)temp : -1);
	}
	
	public boolean isUserLoggedIn(String username) {
		return this.loggedIn.containsValue(username);
	}
	public boolean isSessionLoggedIn(HttpSession session) {
		return this.loggedIn.containsKey(session);
	}
	public String getUserForSession(HttpSession session) {
		return this.loggedIn.get(session);
	}
	public HttpSession getSessionForUser(HttpSession session) {
		return this.loggedIn.inverse().get(session);
	}
	public void logout(HttpSession session) {
		this.loggedIn.remove(session);
	}
	public void logout(String user) {
		this.loggedIn.inverse().remove(user);
	}
}