package Classes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddNote extends HttpServlet {
    private static DBConnector connector = new DBConnector();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        System.out.println("input note");
        if (connector == null || !connector.isOpen()) connector = new DBConnector();
        if (req.getParameter("act").equals("addNote")) {
                    long c = connector.executeUpdate("INSERT notes VALUE('" +
                            req.getParameter("idnote")
                            + "','" + req.getParameter("name")
                            + "','" + req.getParameter("type")
                            +  "','" + req.getParameter("amount")
                            + "','" + req.getParameter("units")
                            + "','" + req.getParameter("date")
                            + "','" + req.getParameter("checked")
                            + "','" + req.getParameter("iduser") + "')");
                    writer.print(c);
                    System.out.println("note add");
                    System.out.println(c);
        }

    }
}
