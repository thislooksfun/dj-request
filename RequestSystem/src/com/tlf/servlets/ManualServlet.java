package com.tlf.servlets;

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
public class ManualServlet extends HttpServlet
{
    private static final long serialVersionUID = 1L;
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        response.sendRedirect("/");
    }
    
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
            int seconds = Integer.parseInt(time.substring(time.indexOf(":")+1));
            int hours = 0;
            
            while (seconds > 60) {
                minutes++;
                seconds -= 60;
            }
            
            while (minutes > 60) {
                hours++;
                minutes -= 60;
            }
            time = (hours > 0 ? hours + ":" : "") + (hours > 0 && minutes < 10 ? "0" + (int) minutes : (int) minutes) + time.substring(time.indexOf(":"));
        }
        
        System.out.println(String.format("%s requested %s%s%s", rName, name, (!artist.equals("") ? " by " + artist : ""), (!time.equals("") ? " ("+time+")" : "")));
        
        data.put("rName", rName);
        data.put("Name", name);
        data.put("Time", time);
        data.put("Artist", artist);
        data.put("Explicit", request.getParameter("Explicit"));
        
        SongSystem.instance.manualRequest(data);
        response.sendRedirect("/");
    }
}