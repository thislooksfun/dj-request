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
 * Servlet implementation class CreateAccount
 */
@WebServlet("/createaccount")
public class CreateAccountServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		System.out.println(request.getParameter("old"));
		if (Boolean.parseBoolean(request.getParameter("old"))) {
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/WEB-INF/createAccount2.jsp");
	        rd.forward(request, response);
		} else {
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/WEB-INF/createAccount.jsp");
	        rd.forward(request, response);
		}
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		boolean userCheck = Boolean.parseBoolean(request.getParameter("userCheck"));
		String username = request.getParameter("username");
		String pass1 = request.getParameter("pass1");
		String pass2 = request.getParameter("pass2");
		String email = request.getParameter("email");
		
		if (userCheck) {
			if (LoginHelper.instance.isUser(username)) {
				response.getWriter().write("0");
			} else if (LoginHelper.instance.isReserved(username)) {
				response.getWriter().write("1");
			}
		} else if (checkUser(username) && checkPasswords(pass1, pass2) && checkEmail(email)) {
			LoginHelper.instance.createUser(username, pass1, email);
			LoginHelper.instance.login(request.getSession(), username);
			response.sendRedirect("/admin");
		} else {
			response.sendRedirect("/createaccount");
		}
	}
	
	private boolean checkUser(String username) {
		return (stringNotEmpty(username) && !LoginHelper.instance.isUser(username) && !LoginHelper.instance.isReserved(username));
	}
	private boolean checkPasswords(String pass1, String pass2) {
		return (stringNotEmpty(pass1) && stringNotEmpty(pass2) && pass1.equals(pass2));
	}
	private boolean checkEmail(String email) {
		return (stringNotEmpty(email) && email.matches("^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w+)+$"));
	}
	private boolean stringNotEmpty(String s) {
		return (s != null && !s.equals(""));
	}
}