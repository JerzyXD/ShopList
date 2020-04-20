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
    private long c;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        if (connector == null || !connector.isOpen()) connector = new DBConnector();
        if (req.getParameter("act") != null) {
            switch (req.getParameter("act")) {
                case "add": {
                    addNote(req, writer);
                }
                break;

                case "edit": {
                    System.out.println("edit note");
                    c = connector.executeUpdate("UPDATE notes SET name ='"
                            + req.getParameter("name") + "'"
                            + "," + "type='" + req.getParameter("type") + "'"
                            + "," + "amount='" + req.getParameter("amount") + "'" + " WHERE idnote='" + req.getParameter("idnote") + "'");
                    if (c == 0) {
                        addNote(req, writer);
                    }
                    writer.print(c);
                    System.out.println(c);
                }
                break;

                case "delete": {
                    long c = connector.executeUpdate("DELETE FROM notes WHERE idnote='" + req.getParameter("idnote") + "'");
                    writer.print(c);
                    System.out.println("note delete");
                    System.out.println(c);
                }
                break;
            }
        }
    }

    private void addNote(HttpServletRequest req, PrintWriter writer) {
        System.out.println("input note");
        c = connector.executeUpdate("INSERT notes VALUE('" +
                req.getParameter("idnote")
                + "','" + req.getParameter("name")
                + "','" + req.getParameter("type")
                + "','" + req.getParameter("amount")
                + "','" + req.getParameter("units")
                + "','" + req.getParameter("date")
                + "','" + req.getParameter("checked")
                + "','" + req.getParameter("iduser") + "')");
        writer.print(c);
        System.out.println("note add");
        System.out.println(c);
    }


}
