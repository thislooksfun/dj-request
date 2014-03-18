package com.tlf.itunes;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Song
{
	public static Set<String> unrecognizedKeys = Collections.synchronizedSet(new HashSet<String>());
	
	private int trackID;
	
	private String name;
	private String artist;
	private String albumArtist;
	private String composer;
	private String album;
	private String genre;
	private String time;
	
	private int totalTime;
	private int diskNumber;
	private int diskCount;
	private int trackNumber;
	private int trackCount;
	private int year;
	
	private boolean explicit;
	
	public boolean isSong = true;
	
	public Song(Element song)
	{
		NodeList keys = song.getElementsByTagName("key");
		
		for (int i = 0; i < keys.getLength(); i++)
		{
			this.parseElement(keys.item(i));
		}
	}
	
	private void parseElement(Node item)
	{
		Node next = item.getNextSibling();
		boolean temp;
		
		switch (item.getTextContent()) {
		case "Track ID":
			this.trackID = Integer.parseInt(next.getTextContent());
			break;
		case "Name":
			this.name = next.getTextContent();
			break;
		case "Artist":
			this.artist = next.getTextContent();
			break;
		case "Album Artist":
			this.albumArtist = next.getTextContent();
			break;
		case "Composer":
			this.composer = next.getTextContent();
			break;
		case "Album":
			this.album = next.getTextContent();
			break;
		case "Explicit":
			this.explicit = Boolean.parseBoolean(next.getNodeName());
			break;
		case "Genre":
			this.genre = next.getTextContent();
			break;
		case "Year":
			this.year = Integer.parseInt(next.getTextContent());
			break;
		case "Disk Count":
			this.diskCount = Integer.parseInt(next.getTextContent());
			break;
		case "Disk Number":
			this.diskNumber = Integer.parseInt(next.getTextContent());
			break;
		case "Track Number":
			this.trackNumber = Integer.parseInt(next.getTextContent());
			break;
		case "Track Count":
			this.trackCount = Integer.parseInt(next.getTextContent());
			break;
		case "Total Time":
			this.totalTime = Integer.parseInt(next.getTextContent());
			this.parseTime();
			break;
		case "Has Video":
		case "Movie":
			temp = Boolean.parseBoolean(next.getNodeName());
			this.isSong = (temp ? false : this.isSong);
			break;
		case "Track Type":
			temp = next.getTextContent().equals("Remote");
			this.isSong = (temp ? false : this.isSong);
			break;
		default:
			unrecognizedKeys.add(item.getTextContent());
		}
	}
	
	private void parseTime()
	{
		float minutes = (float)this.totalTime/60000;
		
		int seconds = (int)Math.round((minutes - (int)minutes)*60);
		
		int hours = 0;
		while ((int)minutes >= 60) {
			hours++;
			minutes -= 60;
		}
		
		this.time = (hours > 0 ? hours + ":" : "") + (hours > 0 && minutes < 10 ? "0" + (int)minutes : (int)minutes) + ":" + (seconds < 10 ? "0" + seconds : seconds);
	}
	
	public int trackID() {
		return this.trackID;
	}
	public String name() {
		return this.name;
	}
	public String artist() {
		return this.artist;
	}
	public String albumArtist() {
		return this.albumArtist;
	}
	public String composer() {
		return this.composer;
	}
	public String album() {
		return this.album;
	}
	public String genre() {
		return this.genre;
	}
	public String time() {
		return this.time;
	}
	public int totalTime() {
		return this.totalTime;
	}
	public int diskNumber() {
		return this.diskNumber;
	}
	public int diskCount() {
		return this.diskCount;
	}
	public int trackNumber() {
		return this.trackNumber;
	}
	public int trackCount() {
		return this.trackCount;
	}
	public int year() {
		return this.year;
	}
	public boolean explicit() {
		return this.explicit;
	}
}