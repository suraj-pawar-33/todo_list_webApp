package com.servlet.actions;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.database.mysql.actions.ActionUtilities;

/*
 * Delete todo
 */
public class DeleteNoteServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		int set = ActionUtilities.deleteNote(request.getParameter("group"), request.getParameter("note"),
				(String) request.getSession().getAttribute(LoginServlet.USER));

		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(String.valueOf(set));

	}
}
