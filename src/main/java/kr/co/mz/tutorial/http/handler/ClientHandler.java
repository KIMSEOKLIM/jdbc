package kr.co.mz.tutorial.http.handler;

import kr.co.mz.tutorial.http.dao.CacheDao;
import kr.co.mz.tutorial.http.dto.CacheDto;
import kr.co.mz.tutorial.http.parser.RequestParser;
import kr.co.mz.tutorial.http.response.ResponseGenerator;
import kr.co.mz.tutorial.http.response.ResponseWriter;
import kr.co.mz.tutorial.jdbc.connection.JdbcConnection;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Optional;

public class ClientHandler {

    private final Socket clientSocket;
    private final CacheDao cacheDao;
    private final RequestParser requestParser;

    public ClientHandler(Socket clientSocket, JdbcConnection jdbcConnection) throws IOException {
        this.clientSocket = clientSocket;
        this.cacheDao = new CacheDao(jdbcConnection);
        this.requestParser = new RequestParser(clientSocket.getInputStream());
    }

    public void handle() throws IOException, SQLException {
        Optional<CacheDto> optionalCacheDto = cacheDao.selectOne(requestParser.getPath());
        OutputStream outputStream = clientSocket.getOutputStream();
        ResponseGenerator responseGenerator = new ResponseGenerator(requestParser.getPath());
        outputStream.write(responseGenerator.generateResponseHeader()); //헤더만 먼저쓰고
        ResponseWriter responseWriter = new ResponseWriter();
        if (optionalCacheDto.isPresent()) {  //캐시존재하면 캐시에서 내용꺼내어 보내주고
            responseWriter.writeResponse(outputStream, optionalCacheDto.get().getCacheData());
        } else { //캐시가 존재하지 않으면 내용을 보내주고 캐시에 넣는다
            byte[] bodyResponse = responseGenerator.generateResponseBody();
            responseWriter.writeResponse(outputStream, bodyResponse);
            cacheDao.insert(requestParser.getPath(), bodyResponse);
        }
    }
}
