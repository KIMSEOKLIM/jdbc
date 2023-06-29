package kr.co.mz.tutorial.http.parser;

import java.util.HashMap;
import java.util.Map;

public class QueryParam {

    private final String requestLine;
    private final Map<String, String> params = new HashMap<>();

    public QueryParam(String requestLine) {
        this.requestLine = requestLine;
    }

    public String getRequestLine() {
        return requestLine;
    }

    public Map<String, String> getQueryParams(String requestLine) {
        int questionIndex = requestLine.indexOf('?');
        if (questionIndex != -1) {
            String queryString = requestLine.substring(questionIndex + 1);
            String[] pairs = queryString.split("&");
            for (String pair : pairs) {
                String[] keyValue = pair.split("=");
                if (keyValue.length == 2) {
                    String key = keyValue[0];
                    String value = keyValue[1].split(" ")[0];
                    System.out.println(key + " " + value);
                    params.put(key, value);
                }
            }
        }
        return params;
    }
}
