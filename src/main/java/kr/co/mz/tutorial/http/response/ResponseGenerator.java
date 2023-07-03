package kr.co.mz.tutorial.http.response;

import java.io.*;

public class ResponseGenerator {
    public static final String TEMPLATES_PATH = System.getProperty("user.dir") + "/src/main/resources/templates";
    private String path;

    private final File file;

    public ResponseGenerator(String path) {
        this.path = path;
        file = new File(TEMPLATES_PATH + path + ".html");
    }

    public byte[] generateResponseHeader() throws IOException {
        StringBuilder headerBuilder = new StringBuilder();
        if (file.exists()) {
            headerBuilder.append("HTTP/1.1 200 OK\r\n");
        } else {
            headerBuilder.append("HTTP/1.1 404 OK\r\n");
        }
        headerBuilder.append("Content-Type: text/html; charset=UTF-8\r\n\r\n");
        return headerBuilder.toString().getBytes();
    }

    public byte[] generateResponseBody() throws IOException {
        if (!file.exists()) {
            path = "/404";
        }
        ByteArrayOutputStream byteArrayOutputStream;
        try (InputStream inputStream = new FileInputStream(TEMPLATES_PATH + path + ".html")) {
            byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[4096];
            int readCount;
            while ((readCount = inputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, buffer.length);
            }
        }
        return byteArrayOutputStream.toByteArray();

    }
}


