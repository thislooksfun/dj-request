package com.tlf.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tlf.itunes.SongSystem;

/**
 * Servlet implementation class RequestServlet
 */
@WebServlet("/RequestServlet")
public class RequestServlet extends HttpServlet
{
    private static final long serialVersionUID = 1L;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RequestServlet()
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
    }
    
    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        System.out.println("Request received!");
        try {
            SongSystem.instance.getSong(Integer.parseInt(request.getParameter("selectedSong"))).requests++;
        } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        
        response.sendRedirect("/");
    }
}