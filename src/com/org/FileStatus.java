package com.org;

import java.io.IOException;
import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class FileStatus
 */
@WebServlet("/status")
public class FileStatus extends HttpServlet {
	private static final long serialVersionUID = 1L;
    protected String message;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public void init() {
        // TODO Initialization
    	message = "Hello!";
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Returns the file status message when a GET message is received
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		out.println("<h1>"+message+"</h1>");
		out.close();
	}
	
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Update the status when a request is received.
		response.setContentType("text/html");
		String newMessage = (String) request.getParameter("message");
		message = newMessage;
		response.getWriter().println("<p>Message Updated. <br>New Message: "+message+"</p>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPut(request, response);
	}

}
