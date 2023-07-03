package kr.co.mz.tutorial.http.dao;

import kr.co.mz.tutorial.http.dto.CacheDto;
import kr.co.mz.tutorial.jdbc.connection.JdbcConnection;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

public class CacheDao {
    private final Connection connection;

    public CacheDao(JdbcConnection jdbcConnection) {
        try {
            connection = jdbcConnection.getconnect();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void insert(String path, byte[] cache_data) throws SQLException, NullPointerException {
        File file = new File(System.getProperty("user.dir") + "/src/main/resources/templates" + path + ".html");
        if (file.exists()) {
            var preparedStatement = connection.prepareStatement(
                    "INSERT INTO cache (path_data, cache_data) " +
                            "SELECT ?, ? "
            );
            preparedStatement.setString(1, path);
            preparedStatement.setBytes(2, cache_data);
            preparedStatement.execute();
        }
    }

    public Optional<CacheDto> selectOne(String path) throws SQLException {
        var preparedStatement = connection.prepareStatement(
                "select * from cache where path_data = ?"
        );
        preparedStatement.setString(1, path);
        var rs = preparedStatement.executeQuery();
        CacheDto cacheDto = null;
        if (rs.next()) {
            cacheDto = new CacheDto(rs.getString(1), rs.getBytes(2));
        }
        return Optional.ofNullable(cacheDto);
    }


    public void deleteAll() throws SQLException {
        var preparedStatement = connection.prepareStatement("delete from cache");
        preparedStatement.execute();
    }

}
