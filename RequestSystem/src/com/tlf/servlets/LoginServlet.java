package com.tlf.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tlf.util.LoginHelper;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginServlet() {
		super();
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.getRemoteUser();
		
		// get request parameters for userID and password
		String user = request.getParameter("user");
		String pwd = request.getParameter("pwd");
		
		if (LoginHelper.instance.login(request.getSession(), user, pwd))
		{
			Cookie loginCookie = new Cookie("user", user);
			
			//setting cookie to expire in 2 hrs
			loginCookie.setMaxAge(2*60*60);
			
			Cookie[] cookies = request.getCookies();
			
			if (cookies != null) {
				for(Cookie cookie : cookies){
					if (cookie.getName().equals("attempt"))
					{
						cookie.setMaxAge(0);
					}
				}
			}
			
			response.addCookie(loginCookie);
			response.sendRedirect("/admin");
		} else {
			boolean hasFailedAttempt = false;
			
			Cookie[] cookies = request.getCookies();
			
			if (cookies != null) {
				for(Cookie cookie : cookies){
					if (cookie.getName().equals("attempt"))
					{
						hasFailedAttempt = true;
						cookie.setMaxAge(60);
					}
				}
			}
			
			if (!hasFailedAttempt)
			{
				Cookie fac = new Cookie("attempt", "0"); //FailedAttemptCookie
				fac.setMaxAge(60);
				response.addCookie(fac);
			}
			
			response.sendRedirect("/admin");
		}
	}
}