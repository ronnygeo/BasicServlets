package com.org;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.logging.Level;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.sun.istack.internal.logging.Logger;



/**
 * Servlet implementation class UploadFile
 */
@WebServlet(name ="UploadFile", urlPatterns = {"/upload"})
@MultipartConfig
public class UploadFile extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final static Logger LOGGER = Logger.getLogger(UploadFile.class.getClass());
       
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		final File path = new File(System.getProperty("catalina.home"), "/webapps/uploads");
		final Part filePart = request.getPart("file");
		final String fileName = getFileName(filePart);
		
		OutputStream out = null;
		InputStream filecontent = null;
		final PrintWriter writer = response.getWriter();
		
		try {
			out = new FileOutputStream(new File(path, fileName));
			filecontent = filePart.getInputStream();
			
			int read = 0;
			final byte[] bytes = new byte[1024];
			
			while((read = filecontent.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			writer.println("New file created.");
			LOGGER.log(Level.INFO, "New file {0} created.", fileName);
			
		} catch (FileNotFoundException e) {
			writer.println("No file selected.");
			LOGGER.log(Level.SEVERE, "File not found error! {0}", e.getMessage());
		} finally {
			if (out != null) {
				out.close();
			}
			if (filecontent != null) {
				filecontent.close();
			}
			if (writer != null) {
				writer.close();
			}
		}
	}
	
	protected String getFileName(final Part part) {
		final String partHeader = part.getHeader("content-disposition");
		LOGGER.log(Level.INFO, "Part Header: {0}", partHeader);
		for (String content: partHeader.split(";")) {
			if (content.trim().startsWith("filename")) {
				return content.substring(content.indexOf("=")+1).trim().replace("\"", "");
//				String[] substrings =  filename.split("\\");
//				return substrings[substrings.length - 1];
			}
		}
		return null;
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Upload the file to the server on post
		processRequest(request, response);
	}

}