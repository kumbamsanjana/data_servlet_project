package servletproject;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class Search extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String sname = request.getParameter("sname");

        Connection con = null;
        PreparedStatement ps = null;

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");

            con = DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/orcl","system","jimin");

            // 🎯 Search student details by Name
            String query = "SELECT * FROM techbuzz WHERE SNAME = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, sname);

            ResultSet rs = ps.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int col = rsmd.getColumnCount();

            out.println("<html><body>");
            out.println("<h2>Search Result</h2>");
            out.println("<table border='1'>");

            // Column headers
            out.println("<tr>");
            for (int i = 1; i <= col; i++) {
                out.println("<th>" + rsmd.getColumnName(i) + "</th>");
            }
            out.println("</tr>");

            boolean found = false;

            while (rs.next()) {
                found = true;
                out.println("<tr>");
                for (int i = 1; i <= col; i++) {
                    out.println("<td>" + rs.getString(i) + "</td>");
                }
                out.println("</tr>");
            }

            if (!found) {
                out.println("<tr><td colspan='" + col + "'>No Student Found</td></tr>");
            }

            out.println("</table>");
            out.println("</body></html>");

        } catch (Exception e) {
            out.println("<h3>Error: " + e.getMessage() + "</h3>");
        } finally {
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (con != null) con.close(); } catch (Exception e) {}
            out.close();
        }
    }
}
