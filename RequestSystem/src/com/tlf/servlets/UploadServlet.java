package com.tlf.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tlf.itunes.SongSystem;
import com.tlf.util.LoginHelper;

/**
 * Servlet implementation class UploadServlet
 */
@WebServlet("/UploadServlet")
@MultipartConfig
public class UploadServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UploadServlet() {
		super();
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("/admin");
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		if (LoginHelper.instance.isSessionLoggedIn(request.getSession())) {
			String part = request.getPart("file").toString();
			int nameBegin = part.indexOf("File name=");
			String name = part.substring(nameBegin+10, part.indexOf(", ", nameBegin));
			if (name.equals("iTunes Music Library.xml") || name.equals("iTunes Library.xml")) {
				SongSystem.instance.decoder.parseLibrary(request.getPart("file").getInputStream());
			}
		}
		response.sendRedirect("/admin");
	}
}