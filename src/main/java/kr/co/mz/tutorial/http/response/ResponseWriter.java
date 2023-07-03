package kr.co.mz.tutorial.http.response;


import java.io.IOException;
import java.io.OutputStream;

public class ResponseWriter {


    public void writeResponse(OutputStream outputStream, byte[] bodyResponse) throws IOException {
        int offset = 0;
        int chunkSize = 4096;
        while (offset < bodyResponse.length) {
            int length = Math.min(chunkSize, bodyResponse.length - offset);
            outputStream.write(bodyResponse, offset, length);
            offset += length;
        }
    }


}