package Classes;

import java.sql.*;
import java.util.Properties;

public class DBConnector {
    private static final String dbClassName = "com.mysql.cj.jdbc.Driver";
    private static final String CONNECTION =
            "jdbc:mysql://localhost:3306/shoplist?useSSL=false&useUnicode=true&characterEncoding=utf8&characterSetResults=utf-8&useJDBCCompliantTimezoneShift=" +
                    "true&useLegacyDatetimeCode=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";

    private static Connection con;

    public static void init() {
        try {
            System.out.println(dbClassName);
            Class.forName(dbClassName);

            Properties p = new Properties();
            p.put("user","root");
            p.put("password","299792458");

            con = DriverManager.getConnection(CONNECTION,p);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }



    public static boolean isOpen() {
        try {
            return !con.isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Отправка запроса извлекающего данные.
     * @param q строка запроса.
     * @return result set
     * @throws SQLException
     */
    public static ResultSet executeQuery(String q) throws SQLException {
        if (con == null || !isOpen()) init();
        ResultSet rs;
        Statement st = con.createStatement();
        rs = st.executeQuery(q);
        return rs;
    }

    /**
     * Отправка запроса обновляющего данные.
     * @param q строка запроса.
     * @return Количество измененных записей.
     */
    public static long executeUpdate(String q) {
        if (con == null || !isOpen()) init();
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

