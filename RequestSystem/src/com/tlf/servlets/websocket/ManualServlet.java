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
		String[] parts = request.getParameter("songData").split("\\b&^&\\b");
		for (int i = 0; i < parts.length; i++) {
			String part = parts[i];
			int split = part.indexOf(", ");
			String pre = part.substring(0, split);
			String post = part.substring(split+2);
			data.put(pre, post);
			System.out.println(part);
			System.out.println(pre + ", " + post);
			System.out.println();
		}
		SongSystem.instance.manualRequest(data);
	}
}