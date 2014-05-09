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
	
	public final int UUID;
	
	private String name;
	private String artist;
	private String albumArtist;
	private String composer;
	private String album;
	private String genre;
	private String time;
	
	private int totalTime;
	private int year;
	
	public int requests = 0;
	
	private boolean explicit;
	
	public boolean isSong = true;
	
	public Song(Element song)
	{
		this.UUID = Integer.parseInt("0"+(songNumber++));
		NodeList keys = song.getElementsByTagName("key");
		
		for (int i = 0; i < keys.getLength(); i++)
		{
			this.parseElement(keys.item(i));
		}
	}
	
	public Song(Map<String, String> data) {
		this.UUID = Integer.parseInt("1"+(songNumber++));
		this.parseMap(data);
	}
	
	private void parseMap(Map<String, String> data)
	{
		Iterator<Entry<String, String>> iterator = data.entrySet().iterator();
		
		while (iterator.hasNext())
		{
			Entry<String, String> entry = iterator.next();
			switch (entry.getKey()) {
			case "Name":
				this.name = entry.getValue();
				break;
			case "Artist":
				this.artist = entry.getValue();
				break;
			case "Album Artist":
				this.albumArtist = entry.getValue();
				break;
			case "Composer":
				this.composer = entry.getValue();
				break;
			case "Album":
				this.album = entry.getValue();
				break;
			case "Explicit":
				this.explicit = Boolean.parseBoolean(entry.getValue());
				break;
			case "Genre":
				this.genre = entry.getValue();
				break;
			case "Year":
				this.year = Integer.parseInt(entry.getValue());
				break;
			case "Total Time":
				this.totalTime = Integer.parseInt(entry.getValue());
				this.parseTime();
				break;
			}
		}
	}
	
	private void parseElement(Node item)
	{
		Node next = item.getNextSibling();
		boolean temp;
		
		switch (item.getTextContent()) {
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
			temp = next.getTextContent().equals("Remote") || next.getTextContent().equals("URL");
			this.isSong = (temp ? false : this.isSong);
			break;
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
	public int year() {
		return this.year;
	}
	public boolean explicit() {
		return this.explicit;
	}
}