package com.tlf.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Maps;

public class LoginHelper
{
	public static final LoginHelper instance = new LoginHelper();
	
	public static final int maxAttemps = 5;
	
	private BiMap<String, String> users = Maps.synchronizedBiMap(HashBiMap.create(new HashMap<String, String>()));
	private BiMap<String, String> emails = Maps.synchronizedBiMap(HashBiMap.create(new HashMap<String, String>()));
	private BiMap<HttpSession, String> loggedIn = Maps.synchronizedBiMap(HashBiMap.create(new HashMap<HttpSession, String>()));
	private Map<String, Long> lastActionTime = new HashMap<String, Long>();
	private BiMap<HttpSession, Integer> failedAttemps = Maps.synchronizedBiMap(HashBiMap.create(new HashMap<HttpSession, Integer>()));
	
	private LoginHelper()
	{
		this.loadUserData();
		//LoginChecker.init();
	}
	
	private File loadUserData()
	{
		File users = new File("users.txt");
		if (!users.exists())
		{
			try
			{
				users.createNewFile();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		
		try
		{
			FileReader fr = new FileReader(users);
			BufferedReader reader = new BufferedReader(fr);
			
			String line = reader.readLine();
			while (line != null)
			{
				System.out.println("Line = " + line);
				String[] info = line.split("::");
				System.out.println(info[0]);
				System.out.println(info[1]);
				System.out.println(info[2]);
				this.users.put(info[0].toLowerCase(), info[2]);
				this.emails.put(info[1].toLowerCase(), info[0]);
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		
		return users;
	}
	
	public boolean loginWithAuth(HttpSession session, String username, String password)
	{
		if (username != null && !username.equals("")) {
			if (username.matches("^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w+)+$")) {
				username = this.emails.get(username); //Get username if supplied with email
			}
			
			username = username.toLowerCase();
			if (this.users.get(username) != null)
			{
				try
				{
					if (PasswordHash.validatePassword(password, this.users.get(username)))
					{
						this.login(session, username);
						return true;
					}
				} catch (NoSuchAlgorithmException | InvalidKeySpecException e)
				{
					e.printStackTrace();
				}
			}
			
			Object temp = this.failedAttemps.get(session);
			int attempt = (temp != null ? (int)temp + 1 : 0);
			this.failedAttemps.put(session, attempt);
		}
		
		return false;
	}
	
	public void login(HttpSession session, String username)
	{
		if (this.isUserLoggedIn(username))
		{
			this.logout(username);
		}
		this.loggedIn.put(session, username);
		this.updateLastAction(username);
		this.failedAttemps.remove(session);
	}
	
	public void updateLastAction(HttpSession session) {
		if (this.isSessionLoggedIn(session)) {
			this.lastActionTime.put(this.getUserForSession(session), System.currentTimeMillis());
		}
	}
	public void updateLastAction(String username) {
		if (this.isUserLoggedIn(username)) {
			this.lastActionTime.put(username, System.currentTimeMillis());
		}
	}
	
	public Map<String, Long> getActionTimes()
	{
		return Util.cloneMap(this.lastActionTime);
	}
	
	public boolean isEmail(String email)
	{
		return this.emails.containsKey(email);
	}
	
	public boolean isUser(String username)
	{
		return this.users.containsKey(username.toLowerCase());
	}
	
	public boolean isReserved(String username)
	{
		return false; //TODO
	}
	
	public void createUser(String username, String password, String email)
	{
		try
		{
			File users = this.loadUserData();
			
			if (!this.users.containsKey(username) && !this.emails.containsKey(email)) {
				String hash = PasswordHash.createHash(password);
				this.users.put(username, hash);
				FileWriter fw = new FileWriter(users);
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write(username+"::"+email+"::"+hash+"\n");
				bw.close();
			}
		} catch (NoSuchAlgorithmException | InvalidKeySpecException | IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public int getLoginAttempt(HttpSession session)
	{
		Object temp = this.failedAttemps.get(session);
		return (temp != null ? (int)temp : -1);
	}
	
	public boolean isUserLoggedIn(String username)
	{
		return this.loggedIn.containsValue(username.toLowerCase());
	}
	
	public boolean isSessionLoggedIn(HttpSession session)
	{
		return this.loggedIn.containsKey(session);
	}
	
	public String getUserForSession(HttpSession session)
	{
		return this.loggedIn.get(session);
	}
	
	public HttpSession getSessionForUser(HttpSession session)
	{
		return this.loggedIn.inverse().get(session);
	}
	
	public void logout(HttpSession session)
	{
		this.lastActionTime.remove(this.loggedIn.remove(session));
	}
	
	public void logout(String user)
	{
		this.loggedIn.inverse().remove(user.toLowerCase());
		this.lastActionTime.remove(user.toLowerCase());
	}
}