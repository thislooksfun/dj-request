package com.tlf.itunes;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Song
{
	private static int songNumber = 0;
	
	/**
	 * This song's unique identifier.
	 */
	public final int UUID;
	
	public boolean isSong = true;
	
	// Song information
	private String requestedBy = "";
	private String name = "";
	private String artist = "";
	private String albumArtist = "";
	private String composer = "";
	private String album = "";
	private String genre = "";
	private String time = "0:00";
	private int year = 0;
	private boolean explicit = false;
	
	/** Number of requests this song currently has */
	public int requests = 0;
	
	/** Whether or not this is a manual request **/
	public final boolean manual;
	
	public Song(Element song)
	{
		System.out.println();
		this.UUID = songNumber++;
		this.manual = false;
		NodeList keys = song.getElementsByTagName("key");
		
		for (int i = 0; i < keys.getLength(); i++) {
			this.parseElement(keys.item(i));
		}
	}
	
	public Song(Map<String, String> data)
	{
		this.UUID = songNumber++;
		this.manual = true;
		this.parseMap(data);
	}
	
	private void parseMap(Map<String, String> data)
	{
		Iterator<Entry<String, String>> iterator = data.entrySet().iterator();
		
		while (iterator.hasNext()) {
			Entry<String, String> entry = iterator.next();
			switch (entry.getKey().toLowerCase()) {
			case "rname":
				this.requestedBy = entry.getValue();
				break;
			case "name":
				this.name = entry.getValue();
				break;
			case "artist":
				this.artist = entry.getValue();
				break;
			case "album artist":
				this.albumArtist = entry.getValue();
				break;
			case "composer":
				this.composer = entry.getValue();
				break;
			case "album":
				this.album = entry.getValue();
				break;
			case "explicit":
				this.explicit = Boolean.parseBoolean(entry.getValue());
				break;
			case "genre":
				this.genre = entry.getValue();
				break;
			case "year":
				this.year = Integer.parseInt(entry.getValue());
				break;
			case "time":
				this.time = entry.getValue();
				break;
			}
		}
	}
	
	private void parseElement(Node item)
	{
		Node next = item.getNextSibling();
		boolean temp;
		
		switch (item.getTextContent().toLowerCase()) {
		case "name":
			this.name = next.getTextContent();
			break;
		case "artist":
			this.artist = next.getTextContent();
			break;
		case "album artist":
			this.albumArtist = next.getTextContent();
			break;
		case "composer":
			this.composer = next.getTextContent();
			break;
		case "album":
			this.album = next.getTextContent();
			break;
		case "explicit":
			this.explicit = Boolean.parseBoolean(next.getNodeName());
			break;
		case "genre":
			this.genre = next.getTextContent();
			break;
		case "total time":
			this.parseTime(Integer.parseInt(next.getTextContent()));
			break;
		case "has video":
		case "movie":
			temp = Boolean.parseBoolean(next.getNodeName());
			this.isSong = (temp ? false : this.isSong);
			break;
		case "track type":
			temp = next.getTextContent().equals("Remote") || next.getTextContent().equals("URL");
			this.isSong = (temp ? false : this.isSong);
			break;
		}
	}
	
	private void parseTime(int time)
	{
		float minutes = (float) time / 60000;
		
		int seconds = (int) Math.round((minutes - (int) minutes) * 60);
		
		int hours = 0;
		while ((int) minutes >= 60) {
			hours++;
			minutes -= 60;
		}
		
		this.time = (hours > 0 ? hours + ":" : "") + (hours > 0 && minutes < 10 ? "0" + (int) minutes : (int) minutes) + ":" + (seconds < 10 ? "0" + seconds : seconds);
	}
	
	public String requestedBy()
	{
		return this.requestedBy;
	}
	
	public String name()
	{
		return this.name;
	}
	
	public String artist()
	{
		return this.artist;
	}
	
	public String albumArtist()
	{
		return this.albumArtist;
	}
	
	public String composer()
	{
		return this.composer;
	}
	
	public String album()
	{
		return this.album;
	}
	
	public String genre()
	{
		return this.genre;
	}
	
	public String time()
	{
		return this.time;
	}
	
	public int year()
	{
		return this.year;
	}
	
	public boolean explicit()
	{
		return this.explicit;
	}
}