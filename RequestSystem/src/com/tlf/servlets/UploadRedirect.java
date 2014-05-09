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
 * Servlet implementation class UploadRedirect
 */
@WebServlet("/upload")
public class UploadRedirect extends HttpServlet
{
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UploadRedirect() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (LoginHelper.instance.isSessionLoggedIn(request.getSession())) {
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/WEB-INF/upload.jsp");
			rd.forward(request, response);
		} else {
			response.sendRedirect("/admin");
		}
	}
}