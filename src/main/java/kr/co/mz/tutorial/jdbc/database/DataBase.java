package kr.co.mz.tutorial.jdbc.database;

import kr.co.mz.tutorial.jdbc.connection.JdbcConnection;

import java.sql.Connection;
import java.sql.SQLException;

public class DataBase {

    private final Connection connection;

    public DataBase(JdbcConnection jdbcConnection) {
        try {
            connection = jdbcConnection.connect();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void put(String url) throws SQLException {
        var preparedStatement = connection.prepareStatement("insert into cache (url, response) values (?, 'test')");
        preparedStatement.setString(1, url);
        preparedStatement.execute();
    }
}
