package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util implements AutoCloseable {

    private static final String USERNAME = "root";
    private static final String PASSWORD = "short";
    private static final String URL = "jdbc:mysql://localhost:3306/pp_1_1_3";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static Connection connection;


    public static Connection activeConnection() {
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Connection is OK");
            //connection.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }

    @Override
    public void close() throws Exception {
        connection.close();
    }
}
