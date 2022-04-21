package com.servlet.actions;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.ArrayUtils;

public class PreLoginServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");

		String destPage = "index.jsp";
		
		Cookie[] cookies = request.getCookies();
		Cookie todoListUser = getUserCookie(cookies);
		if (todoListUser != null) {
			request.getSession().setAttribute(LoginServlet.USER, todoListUser.getValue());
			destPage = "todolist.html";
			request.setAttribute("message", "AGE : " + todoListUser.getMaxAge());
		} else {
			request.setAttribute("message", "No saved data found.");
		}

		RequestDispatcher rd = request.getRequestDispatcher(destPage);
		rd.forward(request, response);

	}

	private Cookie getUserCookie(Cookie[] cookies) {
		if (ArrayUtils.isNotEmpty(cookies)) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(LoginServlet.COOKIENAME)) {
					return cookie;
				}
			}
		}
		return null;
	}
}
