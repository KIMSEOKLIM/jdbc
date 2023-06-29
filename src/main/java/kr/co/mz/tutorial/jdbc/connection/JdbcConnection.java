package kr.co.mz.tutorial.jdbc.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcConnection {
    private final static String URL = "jdbc:mysql://localhost:3306/webchat?serverTimezone=Asia/Seoul&useSSL=false";
    private final static String USERNAME = "webchat";
    private final static String PASSWORD = "webchat!";

    public JdbcConnection() {

    }

    public Connection connect() throws SQLException {

        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }


}

