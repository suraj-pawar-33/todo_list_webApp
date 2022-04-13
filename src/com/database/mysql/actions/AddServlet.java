package com.database.mysql.actions;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

/*
 * Finds and posts group names
 */
public class AddServlet extends HttpServlet {

	
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {

		JSONObject jo = new JSONObject();
		ResultSet set = ActionUtilities.getGroupNames((String) req.getSession().getAttribute("user"));
		if (set != null) {

			try {
				int i = 0;
				while (set.next()) {
					jo.put(String.valueOf(i++), set.getString(1));
				}

				String json = jo.toString(1);
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(json);
			} catch (SQLException | JSONException e) {
				System.out.println(e + " ERROR in AddServlet");
//				e.printStackTrace();
			} finally {
				SqlConstants.closeConnection();
			}
		}
	}

}
