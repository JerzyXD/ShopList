package Classes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class Login extends HttpServlet {
    private static DBConnector connector = new DBConnector();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        System.out.println("get login");
        if (connector == null || !connector.isOpen()) connector = new DBConnector();
        if (req.getParameter("act").equals("reg")) {
            long c = connector.executeUpdate("INSERT user VALUE('" +
                    req.getParameter("iduser") + "','" + req.getParameter("username") + "','" + req.getParameter("madecounter") +  "','" + req.getParameter("checkcounter") + "')");
            writer.print(Long.toString(c));
            System.out.println(c);
        }
       
    }
}
