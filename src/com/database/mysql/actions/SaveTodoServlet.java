package com.database.mysql.actions;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
/*
 * Saves Todos in the database
 * 
 */
public class SaveTodoServlet extends HttpServlet{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)  
	        throws ServletException, IOException {  
		
		int set = ActionUtilities.saveNote(request.getParameter("group"), request.getParameter("note"));
				
		JSONObject jo = new JSONObject();
		String json = "";
		try {
			jo.put("result", set);
			json = jo.toString(1);
		} catch (JSONException e) {
			System.out.println(e + " ERROR in SaveTodoServlet");
			e.printStackTrace();
		}
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json);
  
  
	    }
}
