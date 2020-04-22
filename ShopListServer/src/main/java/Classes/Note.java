package Classes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class Note extends HttpServlet {
    private long c;

    /**
     * В зависимости от значения act параметра
     * выбираются разные сценарии.
     *
     * act = add добавление заметки.
     * <<< idnote, name, type, amount, units, date, checked, iduser
     *
     * act = edit редактирование заметки.
     * <<< idnote, name, type, amount, units, date, checked
     *
     * act = delete удаление заметки
     * <<< idnote
     */

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        if (req.getParameter("act") != null) {
            switch (req.getParameter("act")) {
                case "add": {
                    addNote(req, writer);
                }
                break;

                case "edit": {
                    System.out.println("edit note");
                    c = DBConnector.executeUpdate("UPDATE notes SET name ='"
                            + req.getParameter("name") + "'"
                            + "," + "type='" + req.getParameter("type") + "'"
                            + "," + "amount='" + req.getParameter("amount") + "'" + " WHERE idnote='" + req.getParameter("idnote") + "'");
                    if (c == 0) {
                        addNote(req, writer);
                        System.out.println(req.getParameter("iduser"));
                    }
                    writer.print(c);
                    System.out.println(c);
                }
                break;

                case "delete": {
                    long c = DBConnector.executeUpdate("DELETE FROM notes WHERE idnote='" + req.getParameter("idnote") + "'");
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
        c = DBConnector.executeUpdate("INSERT notes VALUE('" +
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
