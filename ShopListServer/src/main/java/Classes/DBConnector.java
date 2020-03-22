package Classes;

import java.sql.*;
import java.util.Properties;

public class DBConnector {
    private static final String dbClassName = "com.mysql.cj.jdbc.Driver";
    private static final String CONNECTION =
            "jdbc:mysql://localhost:3306/shoplist?useSSL=false&useUnicode=true&characterEncoding=utf8&characterSetResults=utf-8&useJDBCCompliantTimezoneShift=" +
                    "true&useLegacyDatetimeCode=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";

    public Connection con;

    public DBConnector() {
        try {
            System.out.println(dbClassName);
            // Class.forName(xxx) loads the jdbc classes and
            // creates a drivermanager class factory
            Class.forName(dbClassName);

            // Properties for user and password. Here the user and password are both 'craig'
            Properties p = new Properties();
            p.put("user","root");
            p.put("password","299792458");

            // Now try to connect
            con = DriverManager.getConnection(CONNECTION,p);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public boolean isOpen() {
        try {
            return !con.isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ResultSet executeQuery(String q) throws SQLException {
        ResultSet rs;
        Statement st = con.createStatement();
        rs = st.executeQuery(q);
        return rs;
    }

    public long executeUpdate(String q) {
        long count = 0;
        try {
            Statement st = con.createStatement();
            count += st.executeUpdate(q);
        } catch (Exception e) {
            e.printStackTrace();
        };
        return count;
    }
}

