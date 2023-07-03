package kr.co.mz.tutorial.http.server;

import java.io.IOException;
import java.sql.SQLException;

public class ServerMain {
    private final static int PORT = 8092;

    public static void main(String[] args) {
        try (Server server = new Server(PORT)) {
            server.initialize();
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
