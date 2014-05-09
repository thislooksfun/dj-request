package com.tlf.itunes;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class SongSystem
{
	/** Final instance */
	public static final SongSystem instance = new SongSystem();
	
	/** Final Decoder */
	public final LibraryDecoder decoder;
	
	
	/** List of all songs loaded by a DJ */
	public Map<Integer, Song> songs = Collections.synchronizedMap(new HashMap<Integer, Song>());
	/** List of all non-explicit songs loaded by a DJ */
	public Map<Integer, Song> notExplicit = Collections.synchronizedMap(new HashMap<Integer, Song>());
	/** List of all manually requested songs */
	public Map<Integer, Song> manual = Collections.synchronizedMap(new HashMap<Integer, Song>());
	/** List of all manually requested non-explicit songs */
	public Map<Integer, Song> manualNotExplicit = Collections.synchronizedMap(new HashMap<Integer, Song>());
	
	public boolean allowExplicit = true;
	
	public SongSystem()
	{
		this.decoder = new LibraryDecoder(this);
	}
	
	public Song getSong(int UUID)
	{
		boolean manual = (""+UUID).substring(0, 1).equals("1");
		return this.allowExplicit ? (manual ? this.manual.get(UUID) : this.songs.get(UUID)) : (manual ? this.manualNotExplicit.get(UUID) : this.notExplicit.get(UUID));
	}
}