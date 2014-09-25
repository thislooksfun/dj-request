package com.tlf.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class Timer implements Runnable
{
	private static final Timer instance = new Timer();
	private static Thread thread;
	private boolean isRunning = true;
	
	private Map<Runnable, Object[]> toCall = new HashMap<Runnable, Object[]>();
	
	private Timer() {} //Makes sure there is only one instance
	
	@Override
	public void run()
	{
		while (isRunning)
		{
			long time = System.currentTimeMillis();
			Iterator<Entry<Runnable, Object[]>> ite = Util.cloneMap(toCall).entrySet().iterator();
			
			while (ite.hasNext())
			{
				Entry<Runnable, Object[]> entry = ite.next();
				Object[] value = entry.getValue();
				if (time-(long)value[1] >= (int)value[0]) {
					if ((boolean)value[2]) {
						new Thread(entry.getKey()).start();
					} else {
						entry.getKey().run();
					}
					
					if ((boolean)value[3]) {
						value[1] = time;
						toCall.put(entry.getKey(), value);
					}
				}
			}
		}
	}
	
	public static void scheduleSeconds(Runnable runnable, double increment, boolean shouldThread, boolean repeat) {
		scheduleMiliseconds(runnable, (int)(increment * 1000), shouldThread, repeat);
	}
	public static void scheduleMiliseconds(Runnable runnable, int increment, boolean shouldThread, boolean repeat) {
		instance.toCall.put(runnable, new Object[]{increment, System.currentTimeMillis(), shouldThread, repeat});
		
		if (thread == null) {
			thread = new Thread(instance);
			thread.start();
		}
	}
	
	public static void stop()
	{
		instance.isRunning = false;
	}
}