package com.tlf.servlets.redirects;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tlf.itunes.SongSystem;
import com.tlf.util.LoginHelper;
import com.tlf.util.WebsocketHelper;

/**
 * Servlet implementation class UploadRedirect
 */
@WebServlet("/settings")
public class SettingsRedirect extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SettingsRedirect()
	{
		super();
	}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		if (LoginHelper.instance.isSessionLoggedIn(request.getSession())) {
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/WEB-INF/settings.jsp");
			rd.forward(request, response);
		} else {
			response.sendRedirect("/admin");
		}
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		if (LoginHelper.instance.isSessionLoggedIn(request.getSession())) {
			boolean old = SongSystem.instance.allowExplicit;
			SongSystem.instance.allowExplicit = Boolean.parseBoolean(request.getParameter("explicit"));
			boolean different = old != SongSystem.instance.allowExplicit;
			if (different && SongSystem.instance.notExplicit.size() != SongSystem.instance.songs.size()) {
				WebsocketHelper.sendFullUpdate();
			}
		}
		
		response.sendRedirect("/admin");
	}
}