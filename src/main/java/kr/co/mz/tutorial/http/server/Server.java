package kr.co.mz.tutorial.http.server;

import kr.co.mz.tutorial.http.handler.ClientHandler;
import kr.co.mz.tutorial.jdbc.connection.JdbcConnection;

import java.io.IOException;
import java.net.ServerSocket;
import java.sql.SQLException;

public class Server implements AutoCloseable {

    private final int port;
    private ServerSocket serverSocket;
    private final JdbcConnection jdbcConnection;

    public Server(int port) throws SQLException {
        this.port = port;
        jdbcConnection = new JdbcConnection();
        System.out.println("서버가 생성되었습니다.");
    }

    public void initialize() throws IOException {
        System.out.println("서버가 초기화 되었습니다.");
        serverSocket = new ServerSocket(port);
    }

    public void start() throws Exception {
        while (true) {
            System.out.println("서버가 시작되었습니다.");
            ClientHandler clientHandler = new ClientHandler(serverSocket.accept(), jdbcConnection);
            clientHandler.handle();
        }
    }

    @Override
    public void close() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            jdbcConnection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
