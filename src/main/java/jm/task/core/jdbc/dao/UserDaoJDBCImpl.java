package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static java.sql.DriverManager.getConnection;



public class UserDaoJDBCImpl implements UserDao {
    private final Connection connection;

    public UserDaoJDBCImpl() throws SQLException {
        Util util = new Util();
        this.connection = util.getConnection();
    }


    public void createUsersTable() throws SQLException {
        Statement statement = null;
        String sql = "CREATE TABLE IF NOT EXISTS users(id INT AUTO_INCREMENT PRIMARY KEY,\n" +
                "    name VARCHAR(255),\n" +
                "    lastName VARCHAR(255),\n" +
                "    age TINYINT)";
        statement = connection.createStatement();
        statement.execute(sql);
    }

    public void dropUsersTable() throws SQLException {
        Statement statement = null;
        String sql = "DROP TABLE IF EXISTS users;";
        statement = connection.createStatement();
        statement.execute(sql);
    }

    public void saveUser(String name, String lastName, byte age) throws SQLException {
        PreparedStatement statement = null;
        String sql = "INSERT INTO users (name, lastName, age) VALUES (?, ?, ?)";
        statement = connection.prepareStatement(sql);
        statement.setString(1, name);
        statement.setString(2, lastName);
        statement.setByte(3, age);
        statement.executeUpdate();

    }
    public void removeUserById(long id) throws SQLException {
        PreparedStatement statement = null;
        String sql = "DELETE FROM users WHERE id = ?";
        statement = connection.prepareStatement(sql);
        statement.setLong(1, id);
        statement.execute();

    }

    public List<User> getAllUsers() throws SQLException {
        List<User> userList = new ArrayList<>();
        Statement statement = null;
        try {
            String sql = "SELECT * FROM users";
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                System.out.println(user.toString());
                userList.add(user);
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }

        return userList;
    }

    public void cleanUsersTable() throws SQLException {
        Statement statement = null;
        String sql = "TRUNCATE TABLE users;";
        statement = connection.createStatement();
        statement.execute(sql);
    }
}
