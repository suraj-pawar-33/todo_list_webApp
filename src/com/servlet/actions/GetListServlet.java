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
import org.json.JSONException;
import org.json.JSONObject;

import com.database.mysql.actions.ActionUtilities;
import com.database.mysql.actions.SqlConstants;

/*
 * Finds and posts group names
 */
public class GetListServlet extends HttpServlet {
	/** Logger */
	private static final Log LOGGER = LogFactory.getLog(GetListServlet.class);

	
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		JSONObject jo = new JSONObject();
		ResultSet set = ActionUtilities.getGroupNames((String) request.getSession().getAttribute(LoginServlet.USER));
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
				LOGGER.error(e.getLocalizedMessage());
			} finally {
				SqlConstants.closeConnection();
			}
		}
	}

}
