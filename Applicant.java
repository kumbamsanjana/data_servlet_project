package servletproject;
import java.io.IOException;
import java.sql.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class Applicant extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String sname = request.getParameter("sname");
        int phno = request.getIntHeader("phno");
        String course = request.getParameter("course");
        String batchtime = request.getParameter("batchtime");
        String address = request.getParameter("address");
        Connection con = null;
        PreparedStatement ps = null;

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");

            con = DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/orcl","system", "jimin");

            String query = "INSERT INTO techbuzz (SNAME, PHNO, COURSE, BATCHTIME,ADDRESS) VALUES (?, ?, ?, ?, ?)";

            ps = con.prepareStatement(query);

            ps.setString(1, sname);
            ps.setInt(2,  phno);
            ps.setString(3, course);
            ps.setString(4, batchtime);
            ps.setString(5, address);

            ps.executeUpdate();

            response.getWriter().println("<h2>Fee Details Submitted Successfully!</h2>");

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("<h3>Error: " + e.getMessage() + "</h3>");
        } finally {
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (con != null) con.close(); } catch (Exception e) {}
        }
    }
}


