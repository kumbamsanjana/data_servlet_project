package servletproject;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ValidateServlet extends HttpServlet{
	public void service(HttpServletRequest req, HttpServletResponse res)throws ServletException, IOException
	{
	res.setContentType("text/html");
	PrintWriter pw = res.getWriter();
	
	String str1 = req.getParameter("username");
	String str2 = req.getParameter("password");
	if(str1.equals("swetha")&&str2.equalsIgnoreCase("java")) 
	{
		res.sendRedirect("success.html");
	}
	else
	{
		pw.println("invalid");
		res.sendRedirect("failure.html");
}
	pw.close();
}
	}