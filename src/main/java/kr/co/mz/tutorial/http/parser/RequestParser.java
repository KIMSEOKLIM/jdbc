package kr.co.mz.tutorial.http.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class RequestParser {

    private URL url;
    private String version;
    private String method;
    private final String requestLine;

    public RequestParser(InputStream inputStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        requestLine = reader.readLine();
        if (requestLine != null) {
            String[] parts = requestLine.split(" ");
            if (parts.length == 3) {
                url = new URL("http://" + parts[1]);
                version = parts[2];
                method = parts[0];
            }
        }
    }

    public String getPath() {
        if (url == null) {
            return "";
        } else {
            return url.getPath();
        }
    }
}
