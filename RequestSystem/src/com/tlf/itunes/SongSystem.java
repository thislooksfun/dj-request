package com.tlf.itunes;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.tlf.util.WebsocketHelper;

public class SongSystem
{
	/** Final instance */
	public static final SongSystem instance = new SongSystem();
	
	/** Final Decoder */
	public final LibraryDecoder decoder;
	
	/** List of all songs */
	public Map<Integer, Song> songs = Collections.synchronizedMap(new HashMap<Integer, Song>());
	/** List of all non-explicit songs */
	public Map<Integer, Song> notExplicit = Collections.synchronizedMap(new HashMap<Integer, Song>());
	
	public boolean allowExplicit = true;
	
	public SongSystem()
	{
		this.decoder = new LibraryDecoder(this);
	}
	
	public Song getSong(int UUID) {
		return this.allowExplicit ? this.songs.get(UUID) : this.notExplicit.get(UUID);
	}
	
	public void manualRequest(Map<String, String> data)
	{
		Song song = new Song(data);
		song.requests = 1;
		if (!this.decoder.checkLoaded(song, true)) {
			this.songs.put(song.UUID, song);
			if (!song.explicit()) {
				this.notExplicit.put(song.UUID, song);
			}
			
			WebsocketHelper.sendManualRequest(song);
		} else {
			//this.getSong().requests++;
		}
	}
	
	public void remove(int UUID) {
		this.songs.remove(UUID);
		this.notExplicit.remove(UUID);
	}
}