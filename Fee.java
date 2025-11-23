package servletproject;

import java.io.IOException;
import java.sql.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class Fee extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String sname = request.getParameter("sname");
        String total = request.getParameter("total");
        String paid = request.getParameter("paid");
        String balance = request.getParameter("balance");

        Connection con = null;
        PreparedStatement ps = null;

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");

            con = DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/orcl","system", "jimin");

            String query = "INSERT INTO totalfeeee (SNAME, TOTAL, PAID, BALANCE) VALUES (?, ?, ?, ?)";

            ps = con.prepareStatement(query);

            ps.setString(1, sname);
            ps.setInt(2, Integer.parseInt(total));
            ps.setInt(3, Integer.parseInt(paid));
            ps.setInt(4, Integer.parseInt(balance));

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
