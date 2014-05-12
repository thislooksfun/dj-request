package com.tlf.servlets.websocket;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tlf.itunes.SongSystem;

/**
 * Servlet implementation class ManualServlet
 */
@WebServlet("/ManualServlet")
public class ManualServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ManualServlet() {
		super();
	}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("/");
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		Map<String, String> data = new HashMap<String, String>();
		String rName = request.getParameter("RName");
		String name = request.getParameter("Name");
		String time = request.getParameter("Time");
		String artist = request.getParameter("Artist");
		
		if (time.length() > 0) {
			int minutes = Integer.parseInt(time.substring(0, time.indexOf(":")));
			int hours = 0;
			while (minutes > 60) {
				hours++;
				minutes -= 60;
			}
			time = (hours > 0 ? hours + ":" : "") + (hours > 0 && minutes < 10 ? "0" + (int)minutes : (int)minutes) + time.substring(time.indexOf(":"));
		}
		
		data.put("rName", rName);
		data.put("Name", name);
		data.put("Time", time);
		data.put("Artist", artist);
		
		SongSystem.instance.manualRequest(data);
		response.sendRedirect("/");
	}
}