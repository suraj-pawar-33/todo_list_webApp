package com.servlet.actions;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONException;
import org.json.JSONObject;

import com.database.mysql.actions.ActionUtilities;

public class NewListServlet extends HttpServlet{
	/** Logger */
	private static final Log LOGGER = LogFactory.getLog(NewListServlet.class);

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
			LOGGER.error(e.getLocalizedMessage());
		}
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json);
  
	    }
}