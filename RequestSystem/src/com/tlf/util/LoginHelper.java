package com.tlf.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpSession;

public class LoginHelper
{
	public static final LoginHelper instance = new LoginHelper();
	
	private HashMap<String, String> users = new HashMap<String, String>();
	private Set<String> sessions = Collections.synchronizedSet(new HashSet<String>());
	
	private LoginHelper()
	{
		this.populatePasswords();
	}
	
	private void populatePasswords()
	{
		this.users.put("Admin", "Admin");
		this.users.put("thislooksfun", "cocorequest");
	}
	
	public boolean login(HttpSession session, String username, String password)
	{
		if (this.users.get(username) != null && this.users.get(username).equals(password)) {
			if (!this.sessions.contains(session.getId())) {
				this.sessions.add(session.getId());
				return true;
			}
		}
		
		return false;
	}
}
