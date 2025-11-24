package servletproject;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class SearchExp extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String salParam = request.getParameter("sal");

        try {
            int sal = Integer.parseInt(salParam);

            // Load Oracle driver
            Class.forName("oracle.jdbc.driver.OracleDriver");

            // Connect to Oracle
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/orclpdb", "system", "jimin");

            // Prepare SQL query
            PreparedStatement ps = con.prepareStatement("SELECT * FROM emp WHERE sal = ?");
            ps.setInt(1, sal);

            ResultSet rs = ps.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int totalCols = rsmd.getColumnCount();

            out.println("<html><body>");
            out.println("<table border='1' width='80%' style='border-collapse:collapse;text-align:center;'>");
            out.println("<caption><h3>Employee Details</h3></caption>");
            out.println("<tr>");

            // Print column names
            for (int i = 1; i <= totalCols; i++) {
                out.println("<th>" + rsmd.getColumnName(i) + "</th>");
            }
            out.println("</tr>");

            boolean hasData = false;

            // Print all matching rows
            while (rs.next()) {
                hasData = true;
                out.println("<tr>");
                for (int i = 1; i <= totalCols; i++) {
                    out.println("<td>" + rs.getString(i) + "</td>");
                }
                out.println("</tr>");
            }

            if (!hasData) {
                out.println("<tr><td colspan='" + totalCols + "'>No Employee Found with SAL: " + sal + "</td></tr>");
            }

            out.println("</table>");
            out.println("</body></html>");

            con.close();

        } catch (NumberFormatException nfe) {
            out.println("<h3>Error: Please enter a valid number for salary.</h3>");
        } catch (SQLException sqle) {
            out.println("<h3>SQL Error: " + sqle.getMessage() + "</h3>");
            sqle.printStackTrace();
        } catch (Exception e) {
            out.println("<h3>Error: " + e.getMessage() + "</h3>");
            e.printStackTrace();
        } finally {
            out.close();
        }
    }
}

