package com.tlf.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tlf.util.LoginHelper;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginServlet()
	{
		super();
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		if (LoginHelper.instance.isSessionLoggedIn(request.getSession())) {
			response.sendRedirect("/admin");
		} else {
			if (LoginHelper.instance.getLoginAttempt(request.getSession()) >= LoginHelper.maxAttemps) {
	            RequestDispatcher rd = getServletContext().getRequestDispatcher("/WEB-INF/maxAttempts.jsp");
	            rd.forward(request, response);
	        } else {
	        	RequestDispatcher rd = getServletContext().getRequestDispatcher("/WEB-INF/login.jsp");
	            rd.forward(request, response);
	        }
		}
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		if (LoginHelper.instance.loginWithAuth(request.getSession(), request.getParameter("user"), request.getParameter("pwd"))) {
			response.sendRedirect("/upload");
		} else {
			response.sendRedirect("/login");
		}
	}
}