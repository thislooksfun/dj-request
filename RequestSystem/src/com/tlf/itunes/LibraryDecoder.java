package com.tlf.itunes;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class LibraryDecoder
{
	public Map<Integer, Song> songs = Collections.synchronizedMap(new HashMap<Integer, Song>());
	public Map<Integer, Song> notExplicit = Collections.synchronizedMap(new HashMap<Integer, Song>());
	
	public boolean allowExplicit = true;
	
	public static LibraryDecoder instance = new LibraryDecoder();
	
	public void parseLibrary(InputStream is) {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			this.parseLibrary(dBuilder.parse(is));
		} catch (ParserConfigurationException | SAXException | IOException e) {
			System.out.println("Error parsing xml");
			e.printStackTrace();
		}
	}
	
	private void parseLibrary(Document doc)
	{
		System.out.println("Parsing xml");
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
					if (song.isSong && !songs.containsKey(song.name())) {
						this.songs.put(song.UUID, song);
						if (!song.explicit()) {
							this.notExplicit.put(song.UUID, song);
						}
					}
				}
			}
		}
		
		System.out.println(String.format("Loaded %s songs", this.songs.size()));
		
		String[] keys = Song.unrecognizedKeys.toArray(new String[0]);
		
		for (int i = 0; i < keys.length; i++) {
			System.out.println("Unrecognized key '" + keys[i] + "'");
		}
	}
	
	public Song getSong(int UUID) {
		return this.allowExplicit ? this.songs.get(UUID) : this.notExplicit.get(UUID);
	}
}
