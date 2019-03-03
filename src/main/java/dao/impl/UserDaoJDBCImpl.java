package dao.impl;

import config.DBHelper;
import dao.UserDAO;
import exceptions.UserException;
import model.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("all")
public class UserDaoJDBCImpl implements UserDAO {
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

    private UserDaoJDBCImpl() {
        connection = DBHelper.getConnection();
    }

    public static UserDAO getInstance() {
        return UserDAOHolder.dao;
    }

    public void closeConnection() throws SQLException {
        connection.close();
    }

    @Override
    public void delete(Long id) throws UserException {
        try {
            statement = connection.createStatement();
            connection.setAutoCommit(false);
            int result = statement.executeUpdate("DELETE FROM users WHERE id=" + id);
            if (result == 1) {
                connection.commit();
            } else {
                throw new UserException("No user with provided ID found");
            }
        } catch (SQLException e) {
            System.out.println("User delete failed\n trying rollback...");
            try {
                connection.rollback();
                return;
            } catch (SQLException ex) {
                System.out.println("Rollback failed");
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                statement.close();
            } catch (SQLException ex) {
                System.out.println("Statement closing failed");
                ex.printStackTrace();
            }
        }

    }

    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        try {
            statement = connection.createStatement();
            statement.execute("SELECT * FROM users");
            resultSet = statement.getResultSet();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setLogin(resultSet.getString("login"));
                user.setPassword(resultSet.getString("password"));
                user.setAge(resultSet.getInt("age"));
                user.setSex(resultSet.getBoolean("sex"));
                user.setEmail(resultSet.getString("email"));
                user.setHeight(resultSet.getDouble("height"));
                user.setWeight(resultSet.getDouble("weight"));
                user.setRole(resultSet.getString("role"));
                users.add(user);
            }
        } catch (SQLException e) {
            System.out.println("Failed get all users");
            e.printStackTrace();
        } finally {
            try {
                resultSet.close();
                statement.close();
            } catch (SQLException ex) {
                System.out.println("Statement or resultSet closing failed");
                ex.printStackTrace();
            }
        }
        return users;
    }

    @Override
    public User getByLogin(String login) throws UserException {
        User user = new User();
        user.setLogin(login);
        try {
            statement = connection.createStatement();
            statement.executeQuery("SELECT * from users WHERE login='" + login + "'");
            resultSet = statement.getResultSet();
            if (resultSet.next()) {
                user.setId(resultSet.getLong("id"));
                user.setPassword(resultSet.getString("password"));
                user.setAge(resultSet.getInt("age"));
                user.setSex(resultSet.getInt("sex") == 1 ? true : false);
                user.setEmail(resultSet.getString("email"));
                user.setHeight(resultSet.getDouble("height"));
                user.setWeight(resultSet.getDouble("weight"));
                user.setRole(resultSet.getString("role"));
            } else {
                throw new UserException("No user with provided LOGIN found");
            }
        } catch (SQLException e) {
            System.out.println("Failed get user by login");
            e.printStackTrace();
        } finally {
            try {
                resultSet.close();
                statement.close();
            } catch (SQLException ex) {
                System.out.println("Statement or resultSet closing failed");
                ex.printStackTrace();
            }
        }
        return user;
    }

    @Override
    public User getById(Long id) throws UserException {
        User user = new User();
        user.setId(id);
        try {
            statement = connection.createStatement();
            statement.executeQuery("SELECT * from users WHERE id=" + id);
            resultSet = statement.getResultSet();
            if (resultSet.next()) {
                user.setLogin(resultSet.getString("login"));
                user.setPassword(resultSet.getString("password"));
                user.setAge(resultSet.getInt("age"));
                user.setSex(resultSet.getInt("sex") == 1 ? true : false);
                user.setEmail(resultSet.getString("email"));
                user.setHeight(resultSet.getDouble("height"));
                user.setWeight(resultSet.getDouble("weight"));
                user.setRole(resultSet.getString("role"));
            } else {
                throw new UserException("No user with provided ID found");
            }
        } catch (SQLException e) {
            System.out.println("Failed get user by id");
            e.printStackTrace();
        } finally {
            try {
                resultSet.close();
                statement.close();
            } catch (SQLException ex) {
                System.out.println("Statement or resultSet closing failed");
                ex.printStackTrace();
            }
        }
        return user;
    }

    @Override
    public void updateUser(User user) {

        try {
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            int result = statement.executeUpdate("UPDATE users SET login='" + user.getLogin() + "'," +
                    " email='" + user.getEmail() + "'," +
                    "password='" + user.getPassword() + "'," +
                    "sex=" + user.getSex() + "," +
                    "age=" + user.getAge() + "," +
                    "weight=" + user.getWeight() + "," +
                    "height=" + user.getHeight() + "," +
                    "role='" + user.getRole() + "' WHERE id=" + user.getId());
            if (result == 1) {
                connection.commit();
            }
        } catch (SQLException e) {
            System.out.println("User update failed\n trying rollback...");
            try {
                connection.rollback();
                return;
            } catch (SQLException ex) {
                System.out.println("Rollback failed");
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                statement.close();
            } catch (SQLException ex) {
                System.out.println("Statement closing failed");
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void createUser(User user) throws UserException {

        try {
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            int result = statement.executeUpdate("INSERT INTO users(login, email, password, age, sex, weight, height, role) " +
                    "VALUES ('" + user.getLogin() + "','" + user.getEmail() + "','" + user.getPassword() + "'," + user.getAge() + "," + user.getSex() + ",'" + user.getWeight() + "','" + user.getHeight() + "','" + user.getRole() + "')");
            if (result == 1) {
                connection.commit();
            }
        } catch (SQLException e) {
            System.out.println("User creation failed\n trying rollback...");
            try {
                connection.rollback();
                throw new UserException(e.getMessage());

            } catch (SQLException ex) {
                System.out.println("Rollback failed");
                ex.printStackTrace();
            } finally {
                e.printStackTrace();
                return;
            }
        } finally {
            try {
                statement.close();
            } catch (SQLException ex) {
                System.out.println("Statement closing failed");
                ex.printStackTrace();
            }
        }
    }

    private static class UserDAOHolder {
        private final static UserDAO dao = new UserDaoJDBCImpl();
    }
}
