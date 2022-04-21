package com.servlet.actions;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.database.mysql.actions.ActionUtilities;

/**
 * Set the todo to checked
 * 
 * @author spawar
 *
 */
public class CheckNoteServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		int set = ActionUtilities.checkNote(request.getParameter("group"), request.getParameter("note"),
				request.getParameter("checked"));

		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(String.valueOf(set));

	}
}
