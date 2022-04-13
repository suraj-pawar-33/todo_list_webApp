package com.servlet.actions;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.database.mysql.actions.ActionUtilities;

public class LoginServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)  
	        throws ServletException, IOException {  
	  
	    response.setContentType("text/html");  
	          
	    String n=request.getParameter("username");  
	    String p=request.getParameter("userpass");  
	    
	    String destPage = "index.jsp";
	    if(ActionUtilities.validateUser(n, p)){
			HttpSession session = request.getSession();
			session.setAttribute("user", n);
			destPage = "todolist.html";
		} else {
			String message = "Invalid email/password";
            request.setAttribute("message", message);
		}

		RequestDispatcher rd = request.getRequestDispatcher(destPage);
		rd.forward(request, response);
  
	    }
}
