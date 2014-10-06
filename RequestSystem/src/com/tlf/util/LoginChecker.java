package com.tlf.util;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class LoginChecker implements Runnable
{
	private static boolean initalized = false;
	
	@Override
	public void run()
	{
		//TODO stuff
		System.out.println("Checking!");
		Map<String, Long> actionTimes = LoginHelper.instance.getActionTimes();
		Iterator<Entry<String, Long>> ite = actionTimes.entrySet().iterator();
		long time = System.currentTimeMillis();
		while (ite.hasNext())
		{
			Entry<String, Long> entry = ite.next();
			if (time-entry.getValue() >= (60*60*1000)) {
				LoginHelper.instance.logout(entry.getKey());
				System.out.println("Logged out user: "+entry.getKey());
			}
		}
	}
	
	public static void init()
	{
		if (!initalized) {
			Timer.scheduleSeconds(new LoginChecker(), 10, true, true);
			initalized = true;
		}
	}
}