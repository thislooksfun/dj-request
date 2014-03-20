package com.tlf.itunes;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

@Singleton
@Startup
public class LibraryDecoder
{
	public static Map<Integer, Song> songs = Collections.synchronizedMap(new HashMap<Integer, Song>());
	
	private String home = System.getProperty("user.home");
	private String osName = System.getProperty("os.name");
	private String path = home;
	
	@PostConstruct
	public void onStartup() {
		this.path = this.home;
		
		this.getPath();
		
		System.out.println("Parsing library");
		System.out.println(this.osName);
		System.out.println(this.home);
		System.out.println(this.path);
		
		this.parseLibrary();
	}
	
	private void getPath()
	{
		switch (osName) {
		case "Mac OS X":
			this.path += "/Music/iTunes/";
			break;
		case "Windows XP":
			this.path += "\\My Documents\\My Music\\iTunes\\";
		case "Windows Vista":
		case "Windows 7":
		case "Windows 8":
			this.path += "\\Music\\iTunes\\";
			break;
		default:
			System.out.println("OS Not recognized");
			return;
		}
		
		if (new File(this.path + "iTunes Library.xml").exists()) {
			this.path += "iTunes Library.xml";
		} else if (new File(this.path + "iTunes Music Library.xml").exists()) {
			this.path += "iTunes Music Library.xml";
		} else {
			System.out.println("Library not found");
			return;
		}
	}
	
	private void parseLibrary()
	{
		System.out.println("Parsing xml");
		
		try {
			File fXmlFile = new File(this.path);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder;
			dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			
			doc.getDocumentElement().normalize();
			
			NodeList nList = doc.getElementsByTagName("dict");
			
			System.out.println("----------------------------");
			
			for (int temp = 0; temp < nList.getLength(); temp++) {
				
				Node nNode = nList.item(temp);
				
				if (nNode.getNodeName().equals("dict") && ((Element)nNode).getElementsByTagName("key").item(0) != null) {
					if (((Element)nNode).getElementsByTagName("key").item(0).getTextContent().equals("Name")) {
						break;
					} else if (((Element)nNode).getElementsByTagName("key").item(0).getTextContent().equals("Track ID")) {
						Song song = new Song((Element)nNode);
						if (song.isSong) {
							System.out.println("Adding song #" + song.trackID());
							songs.put(song.trackID(), song);
						}
					}
				}
			}
		} catch (ParserConfigurationException | SAXException | IOException e) {
			System.out.println("Error parsing xml");
			e.printStackTrace();
		}
		
		System.out.println(String.format("Loaded %s songs", songs.size()));
		
		String[] keys = Song.unrecognizedKeys.toArray(new String[0]);
		
		for (int i = 0; i < keys.length; i++) {
			System.out.println("Unrecognized key '" + keys[i] + "'");
		}
	}
}
