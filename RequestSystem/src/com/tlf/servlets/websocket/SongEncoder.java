package com.tlf.servlets.websocket;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import com.tlf.itunes.Song;

public class SongEncoder implements Encoder.Text<Song>
{
	@Override
	public void destroy() {}
	
	@Override
	public void init(EndpointConfig config) {}
	
	@Override
	public String encode(Song song) throws EncodeException
	{
		return String.format("song={id='%s', time='%s', name='%s', artist='%s', album='%s', albumartist='%s', composer='%s', requests='%s'}", song.trackID(), song.time(), song.name(), song.artist(), song.album(), song.albumArtist(), song.composer(), song.requests);
	}
}