package com.tlf.util;

import java.util.HashMap;
import java.util.Map;

public class Util
{
	/** Creates a duplicate of the specified map */
	public static <K,V> Map<K,V> cloneMap(Map<K,V> map)
	{
		Map<K,V> tempUpdates = new HashMap<K,V>();
		tempUpdates.putAll(map);
		
		return tempUpdates;
	}
}