package Classes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Result;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

public class User extends HttpServlet {

    private long c;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        JSONArray objects = new JSONArray();
        System.out.println("login");
        if (req.getParameter("act") != null) {
            switch (req.getParameter("act")) {
                case "reg":
                    try {
                        ResultSet rs = DBConnector.executeQuery("SELECT iduser FROM user WHERE iduser='" + req.getParameter("iduser") + "'");
                        if (rs.next()) {
                            writer.print(rs.getString("iduser"));
                            System.out.println("user login");
                        } else {
                             c = DBConnector.executeUpdate("INSERT user VALUE('" +
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
                     c = DBConnector.executeUpdate("UPDATE user SET madecounter='" + req.getParameter("madecounter")
                            + "'," + "checkcounter='" + req.getParameter("checkcounter")
                            + "' WHERE iduser='" + req.getParameter("iduser") + "'");
                    writer.print(c);
                    System.out.println("user info update");
                    System.out.println(c);
                }
                break;

                case "sync": {
                    try {
                        System.out.println("sync notes");
                        ResultSet rs =  DBConnector.executeQuery("SELECT * FROM notes WHERE iduser=" + req.getParameter("iduser"));
                            while (rs.next()) {
                                JSONObject object = new JSONObject();
                                object.put("idnote", rs.getString("idnote"));
                                object.put("type", rs.getString("type"));
                                object.put("name", rs.getString("name"));
                                object.put("amount", rs.getString("amount"));
                                object.put("checked", rs.getString("checked"));
                                object.put("units", rs.getString("units"));
                                object.put("date", rs.getString("date"));
                                objects.put(object);
                            }
                    } catch (SQLException | JSONException e) {
                        e.printStackTrace();
                    }
                }
                writer.println(objects.toString());
                System.out.println(objects.toString());
                break;
            }
        }
    }
}
