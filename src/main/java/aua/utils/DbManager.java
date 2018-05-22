package aua.utils;

import java.sql.*;
import java.util.Properties;

public class
DbManager {
    private Connection connection;

    private static DbManager dbManager = new DbManager();

    public static DbManager getInstance() {
        return dbManager;
    }

    private DbManager() {
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

    public int isUserExist(String email, String password) {
        String login = email.toLowerCase();
        PreparedStatement preparedStmt = null;  //for SQL stmt
        ResultSet rs = null;
        try {
            String query = "select Password, Id from user where Email= ?";
            preparedStmt = connection.prepareStatement(query);
            preparedStmt.setString(1, login);
            rs = preparedStmt.executeQuery();
            if (rs.next()) {
                String pass = rs.getString("Password");
                int id = rs.getInt("Id");
                return pass.equals(password) ? id : -1;
            } else
                return -1;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new UnknownError();
        } finally {
            closeConn(preparedStmt, rs);
        }
    }

    public boolean isEmailExist(String userEmail) {
        String email = userEmail.toLowerCase();
        PreparedStatement preparedStmt = null;
        ResultSet rs = null;
        try {
            String query = "select * from user where Email= ?";
            preparedStmt = connection.prepareStatement(query);
            preparedStmt.setString(1, email);
            rs = preparedStmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new UnknownError();
        } finally {
            closeConn(preparedStmt, rs);
        }
    }

    public boolean addUser(String emailAddr, String password) {
        String email = emailAddr.toLowerCase();
        PreparedStatement preparedStmt = null;
        try {
            String query = " insert into user (Email, Password)"
                    + " values (?, ?)";
            preparedStmt = connection.prepareStatement(query);
            preparedStmt.setString(1, email);
            preparedStmt.setString(2, password);
            preparedStmt.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new UnknownError();
        } finally {
            closeConn(preparedStmt, null);
        }
    }

    private void closeConn(Statement st, ResultSet rs) {
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
