package servletproject;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class Register extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // ✅ username is already a String, no need to parse
        String ENAME = request.getParameter("name");
        int EID = Integer.parseInt(request.getParameter("eid"));
        int SAL = Integer.parseInt(request.getParameter("sal"));
        long EMP_MNO = Long.parseLong(request.getParameter("mno"));

        try {
            // Load Oracle Driver
        	Class.forName("oracle.jdbc.driver.OracleDriver");
System.out.println("driver loaded");
            // Connect to Oracle
Connection con = DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/orclpdb", "system", "jimin");

            // Prepare SQL statement
            PreparedStatement ps = con.prepareStatement("INSERT INTO emp VALUES (?, ?, ?, ?)");

            // Set values
            ps.setString(1, ENAME);
            ps.setInt(2, EID);
            ps.setInt(3, SAL);
            ps.setLong(4, EMP_MNO);

            // Execute insert
            int i = ps.executeUpdate();

            if (i > 0) {
                out.println("<h3>You are successfully registered!</h3>");
            }

            con.close();
        } catch (Exception e2) {
            out.println("<h3>Error: " + e2.getMessage() + "</h3>");
        }

        out.close();
    }
}
