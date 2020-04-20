package Classes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login extends HttpServlet {
    private static DBConnector connector = new DBConnector();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        System.out.println("login");
        if (connector == null || !connector.isOpen()) connector = new DBConnector();
        if (req.getParameter("act") != null) {
            switch (req.getParameter("act")) {
                case "reg":
                    try {
                        ResultSet rs = connector.executeQuery("SELECT iduser FROM user WHERE iduser='" + req.getParameter("iduser") + "'");
                        if (rs.next()) {
                            writer.print(rs.getString("iduser"));
                            System.out.println("user login");
                        } else {
                            long c = connector.executeUpdate("INSERT user VALUE('" +
                                    req.getParameter("iduser")
                                    + "','" + req.getParameter("username")
                                    + "','" + req.getParameter("madecounter")
                                    + "','" + req.getParameter("checkcounter") + "')");
                            writer.print(c);
                            System.out.println("user create");
                            System.out.println(c);
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;

                case "update": {
                    long c = connector.executeUpdate("UPDATE user SET madecounter='" + req.getParameter("madecounter")
                            + "'," + "checkcounter='" + req.getParameter("checkcounter")
                            + "' WHERE iduser='" + req.getParameter("iduser") + "'");
                    writer.print(c);
                    System.out.println("user info update");
                    System.out.println(c);
                }
                break;
            }
        }
    }
}
