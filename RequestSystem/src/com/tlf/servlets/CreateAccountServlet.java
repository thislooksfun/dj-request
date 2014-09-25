package com.tlf.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tlf.util.LoginChecker;
import com.tlf.util.LoginHelper;

/**
 * Servlet implementation class CreateAccount
 */
@WebServlet("/createaccount")
public class CreateAccountServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CreateAccountServlet()
	{
		super();
	}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/WEB-INF/createAccount.jsp");
        rd.forward(request, response);
        LoginChecker.init();
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		boolean userCheck = Boolean.parseBoolean(request.getParameter("userCheck"));
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String email = request.getParameter("email1");
		
		System.out.println(username);
		if (LoginHelper.instance.isUser(username)) {
			response.getWriter().write("Sup");
		} else if (!userCheck) {
			LoginHelper.instance.createUser(username, password, email);
			LoginHelper.instance.login(request.getSession(), username);
			response.sendRedirect("/admin");
		}
	}
}