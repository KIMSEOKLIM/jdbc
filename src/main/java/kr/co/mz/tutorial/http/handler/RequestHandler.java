package kr.co.mz.tutorial.http.handler;

import kr.co.mz.tutorial.http.cache.Cache;
import kr.co.mz.tutorial.http.parser.QueryParam;
import kr.co.mz.tutorial.http.parser.RequestParser;
import kr.co.mz.tutorial.http.response.ResponseCreator;
import kr.co.mz.tutorial.jdbc.connection.JdbcConnection;
import kr.co.mz.tutorial.jdbc.database.DataBase;

import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.Map;

public class RequestHandler {

    private final Map<String, String> userCredentials;
    private final Cache cache;
    private final Socket clientSocket;
    private final RequestParser requestParser;
    private final DataBase dataBase;

    public RequestHandler(Socket clientSocket, Map<String, String> userCredentials, Cache cache, JdbcConnection jdbcConnection) throws IOException {
        this.clientSocket = clientSocket;
        requestParser = new RequestParser(clientSocket);
        this.userCredentials = userCredentials;
        this.cache = cache;
        dataBase = new DataBase(jdbcConnection);
    }

    public void handle() throws IOException, SQLException {
        String cachedResponse = cache.get(requestParser.getUrl());
        if (cachedResponse.isEmpty()) {
            ResponseCreator responseCreator = new ResponseCreator(requestParser.getUrl(), new QueryParam(requestParser.getRequestLine()), userCredentials, cache);
            cachedResponse = responseCreator.create();
            cache.put(requestParser.getUrl(), cachedResponse);
            dataBase.put(requestParser.getUrl());
        }
        clientSocket.getOutputStream().write(cachedResponse.getBytes(StandardCharsets.UTF_8)); //sendResponse
    }
}
