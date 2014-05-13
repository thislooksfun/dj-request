package com.tlf.itunes;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.tlf.util.WebsocketHelper;

public class LibraryDecoder
{
    private SongSystem songSystem;
    
    private Set<String> songInfo = Collections.synchronizedSet(new HashSet<String>());
    
    public LibraryDecoder(SongSystem songSystem)
    {
        this.songSystem = songSystem;
    }
    
    public void parseLibrary(InputStream is)
    {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
            this.parseLibrary(dBuilder.parse(is));
            WebsocketHelper.sendFullUpdate();
        } catch (ParserConfigurationException | SAXException | IOException e) {
            System.out.println("Error parsing xml");
            e.printStackTrace();
        }
    }
    
    private void parseLibrary(Document doc)
    {
        doc.getDocumentElement().normalize();
        
        NodeList nList = doc.getElementsByTagName("dict");
        
        for (int temp = 0; temp < nList.getLength(); temp++) {
            
            Node nNode = nList.item(temp);
            
            if (nNode.getNodeName().equals("dict") && ((Element) nNode).getElementsByTagName("key").item(0) != null) {
                if (((Element) nNode).getElementsByTagName("key").item(0).getTextContent().equals("Name")) {
                    break;
                } else if (((Element) nNode).getElementsByTagName("key").item(0).getTextContent().equals("Track ID")) {
                    Song song = new Song((Element) nNode);
                    
                    if (song.isSong && !this.songInfo.contains(song.name() + song.album() + song.artist())) {
                        this.songSystem.songs.put(song.UUID, song);
                        this.songInfo.add(song.name() + song.album() + song.artist());
                        if (!song.explicit()) {
                            this.songSystem.notExplicit.put(song.UUID, song);
                        }
                    }
                }
            }
        }
    }
}
