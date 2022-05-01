package com.servlet.actions;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.database.mysql.actions.ActionUtilities;
import com.database.mysql.actions.SqlConstants;
/*
 * Finds and sends group notes from database
 */
public class TodoServelet extends HttpServlet{
	/** Logger */
	private static final Log LOGGER = LogFactory.getLog(TodoServelet.class);

	/**
	 *  Returns the Todo notes
	 */
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)  
	        throws ServletException, IOException {  
	  
		String groupName = request.getParameter("group");
		JSONArray jarray = new JSONArray();
		ResultSet set = ActionUtilities.getGroupNotes(groupName, (String) request.getSession().getAttribute(LoginServlet.USER));
		try {
			while (set.next()) {
				JSONObject jo = new JSONObject();
				jo.put("note", set.getString(1));
				jo.put("isChecked", set.getString(2));
				jarray.put(jo);
			}
		} catch (SQLException | JSONException e) {
			System.out.println(e + " ERROR in TodoServelet");
			LOGGER.error(e.getLocalizedMessage());
		}  finally {
			SqlConstants.closeConnection();
		}		
		
		String json = jarray.toString();

	    response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");
	    response.getWriter().write(json);
  
	    }
}
