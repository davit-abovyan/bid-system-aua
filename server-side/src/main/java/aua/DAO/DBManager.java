package aua.DAO;

import java.sql.*;
import java.util.Properties;

public class DBManager {
    private Connection connection;

    private static DBManager dbManager = new DBManager();

    public static DBManager getInstance() {
        return dbManager;
    }

    public Connection getConnection(){
        return connection;
    }

    private DBManager() {
        try {
            //Class.forName("com.mysql.jdbc.Driver");
            Properties properties = new Properties();
            properties.setProperty("user", "root");
            properties.setProperty("password", "root");
            connection = DriverManager.getConnection("jdbc:mysql://localhost/auction", properties);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeConn(Statement st, ResultSet rs) {
        if (st != null)
            try {
                st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        if (rs != null)
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }
}
