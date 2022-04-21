package com.servlet.actions;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.ArrayUtils;

import com.database.mysql.actions.ActionUtilities;

public class LoginServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static final String COOKIENAME = "todoListUser";
	static final String USER = "user";

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");

		String name = request.getParameter("username");
		String pass = request.getParameter("userpass");

		String destPage = "login.jsp";
		
		if (ActionUtilities.validateUser(name, pass)) {
			request.getSession().setAttribute(USER, name);
//			cookie for session
			Cookie cookie = new Cookie(COOKIENAME, name);
//			72 hrs 
			cookie.setMaxAge(72 * 60 * 60);
			response.addCookie(cookie);
			destPage = "todolist.html";
		} else {
			request.setAttribute("message", "Invalid email/password");
		}

		RequestDispatcher rd = request.getRequestDispatcher(destPage);
		rd.forward(request, response);

	}
}
