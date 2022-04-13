package com.servlet.actions;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.database.mysql.actions.ActionUtilities;

public class DeleteListServlet extends HttpServlet {

	/**
	 * Delete list of todo
	 */
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String group = request.getParameter("group");
		int set = ActionUtilities.deleteGroup(group);

		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(String.valueOf(set));

	}
}
