package com.database.mysql.actions;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

public class NewListServlet extends HttpServlet{

	/**
	 * New group of notes
	 */
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)  
	        throws ServletException, IOException {  
	  
		String group = request.getParameter("group");
		int set = ActionUtilities.createNewGroup(group, (String) request.getSession().getAttribute("user"));
		JSONObject jo = new JSONObject();
		String json = "";
		try {
			jo.put("result", set);
			json = jo.toString(1);
		} catch (JSONException e) {
			System.out.println(e + " ERROR in NewGroupServlet");
			e.printStackTrace();
		}
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json);
  
	    }
}