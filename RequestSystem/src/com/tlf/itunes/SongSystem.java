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
	
	/** List of all loaded songs */
	public Map<Integer, Song> songs = Collections.synchronizedMap(new HashMap<Integer, Song>());
	/** List of all loaded non-explicit songs */
	public Map<Integer, Song> notExplicit = Collections.synchronizedMap(new HashMap<Integer, Song>());
	
	public boolean allowExplicit = true;
	
	public SongSystem()
	{
		this.decoder = new LibraryDecoder(this);
	}
	
	public Song getSong(int UUID) {
		return this.allowExplicit ? this.songs.get(UUID) : this.notExplicit.get(UUID);
	}
}