package kr.co.mz.tutorial.http.server;

import java.io.IOException;
import java.sql.SQLException;

public class ServerMain {
    private final static int PORT = 8092;

    public static void main(String[] args) {
        Server server = null;
        try {
            server = new Server(PORT);
            server.initialize();
            try {
                server.start();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            server.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
