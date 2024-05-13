package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static java.sql.DriverManager.getConnection;



public class UserDaoJDBCImpl implements UserDao {
    private Connection connection = Util.getConnection();

    public UserDaoJDBCImpl()  {

    }


    public void createUsersTable() {
        try(Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS users(id INT AUTO_INCREMENT PRIMARY KEY,\n" +
                    "    name VARCHAR(255),\n" +
                    "    lastName VARCHAR(255),\n" +
                    "    age TINYINT)");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void dropUsersTable() {
        try (Statement statement = connection.createStatement()){

            statement.execute("DROP TABLE IF EXISTS users;");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO users (name, lastName, age) VALUES (?, ?, ?)")) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public void removeUserById(long id) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM users WHERE id = ?")) {
            statement.setLong(1, id);
            statement.execute();
        }

    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM users")) {
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
            throw new RuntimeException(e);
        }

        return userList;
    }

    public void cleanUsersTable() {
        try (Statement statement = connection.createStatement()){
            statement.execute("TRUNCATE TABLE users;");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
