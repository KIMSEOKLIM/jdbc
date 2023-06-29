package kr.co.mz.tutorial.http.server;

import kr.co.mz.tutorial.http.cache.Cache;
import kr.co.mz.tutorial.http.handler.RequestHandler;
import kr.co.mz.tutorial.jdbc.connection.JdbcConnection;

import java.io.IOException;
import java.net.ServerSocket;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class Server {

    private final int port;
    private final Map<String, String> userCredentials = new HashMap<>();
    private ServerSocket serverSocket;
    private final Cache cache = new Cache();
    private final JdbcConnection jdbcConnection;

    public Server(int port) {
        this.port = port;
        jdbcConnection = new JdbcConnection();
        System.out.println("서버가 생성되었습니다.");
    }

    public void initialize() throws IOException {
        System.out.println("서버가 초기화 되었습니다.");
        serverSocket = new ServerSocket(port);
    }

    public void start() throws IOException, SQLException {
        while (true) {
            System.out.println("서버가 시작되었습니다.");
            RequestHandler requestHandler = new RequestHandler(serverSocket.accept(), userCredentials, cache, jdbcConnection);
            requestHandler.handle();
        }
    }

    public void close() throws IOException {
        serverSocket.close();
    }
}
