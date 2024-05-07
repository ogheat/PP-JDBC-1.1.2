package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    // реализуйте настройку соеденения с БД
    public static Connection connection;
    public static final String URL = "jdbc:mysql://localhost:3306/db";
    public static final String USER = "root";
    public static final String PASSWORD = "root";



    public static Connection getConnection() throws SQLException {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            if (!connection.isClosed())
                System.out.println("Соединение установлено");
        } catch (SQLException ex) {
            System.err.println("Соединение не установлено");
            ex.printStackTrace();
        }
        return connection;
    }

}
