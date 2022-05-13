package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {
    }

    private static Connection connection = Util.activeConnection();

    public void createUsersTable() {
        try (Statement st = connection.createStatement()) {
            connection.setAutoCommit(false);
            st.executeUpdate("create table IF not exists USER" +
                    "(ID bigint UNSIGNED not null auto_increment," +
                    "NAME varchar (255), " +
                    "lastName varchar (255), " +
                    "AGE tinyint, " +
                    "primary key(ID))");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try (Statement st = connection.createStatement()) {
            connection.setAutoCommit(false);
            st.executeUpdate("drop table IF exists USER");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement ps = connection.prepareStatement(
                "insert into USER(name, lastName, age) VALUES(?,?,?)")) {
            connection.setAutoCommit(false);
            ps.setString(1, name);
            ps.setString(2, lastName);
            ps.setByte(3, age);
            ps.executeUpdate();
            //System.out.println("User with name – " + name + " added to database");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try (Statement st = connection.createStatement()) {
            connection.setAutoCommit(false);
            st.executeUpdate("delete from USER where id");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery("select ID, NAME, lastName, AGE from USER")) {
            connection.setAutoCommit(false);
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setName(rs.getString("name"));
                user.setLastName(rs.getString("lastName"));
                user.setAge(rs.getByte("age"));
                users.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    public void cleanUsersTable() {
        try (Statement st = connection.createStatement()) {
            connection.setAutoCommit(false);
            st.executeUpdate("truncate table USER");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
