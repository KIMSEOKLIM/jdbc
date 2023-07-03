package kr.co.mz.tutorial.jdbc.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcConnection {
    private final static String URL = "jdbc:mysql://localhost:3306/webchat?serverTimezone=Asia/Seoul&useSSL=false";
    private final static String USERNAME = "webchat";
    private final static String PASSWORD = "webchat!";

    Connection connection;

    public JdbcConnection() throws SQLException {
        connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }


    public Connection getconnect() throws SQLException {
        return connection;
    }

    //closer != creator
    public void close() throws SQLException {
        connection.close();
    }

}

