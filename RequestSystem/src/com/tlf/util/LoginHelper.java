package com.tlf.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import javax.servlet.http.HttpSession;

public class LoginHelper
{
	public static final LoginHelper instance = new LoginHelper();
	
	private Map<String, String> users = Collections.synchronizedMap(new HashMap<String, String>());
	private Map<String, String> loggedIn = Collections.synchronizedMap(new HashMap<String, String>());
	private Map<String, Integer> failedAttemps = Collections.synchronizedMap(new HashMap<String, Integer>());
	
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
			if (!this.isUserLoggedIn(username)) {
				this.loggedIn.put(session.getId(), username);
				return true;
			} else {
				this.failedAttemps.put(session.getId(), -2);
				return false;
			}
		}
		
		Object temp = this.failedAttemps.get(session.getId());
		int attempt = (temp != null ? (int)temp+1 : 0);
		this.failedAttemps.put(session.getId(), attempt);
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
		return this.loggedIn.containsKey(session.getId());
	}
	
	public String getUserForSession(HttpSession session) {
		return this.loggedIn.get(session.getId());
	}
	
	public void logout(HttpSession session) {
		this.loggedIn.remove(session.getId());
	}
}