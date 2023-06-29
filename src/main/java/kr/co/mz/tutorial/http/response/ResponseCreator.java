package kr.co.mz.tutorial.http.response;

import kr.co.mz.tutorial.http.cache.Cache;
import kr.co.mz.tutorial.http.parser.QueryParam;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ResponseCreator {
    private static final String PATH = "/Users/mz01-megalim/Downloads/Web/resources/templates";
    private final Map<String, String> userCredentials;
    private final String url;
    private final QueryParam queryParam;
    private final Cache cache;

    public ResponseCreator(String url, QueryParam queryParam, Map<String, String> userCredentials, Cache cache) {
        this.url = url;
        this.queryParam = queryParam;
        this.userCredentials = userCredentials;
        this.cache = cache;
    }

    public String create() {
        String response;
        if (url.equals("/login")) {
            var pw = queryParam.getQueryParams(queryParam.getRequestLine()).get("password");
            var id = queryParam.getQueryParams(queryParam.getRequestLine()).get("username");
            userCredentials.put(id, pw);
            response = generateHTMLResponse(PATH + "/loginhtml" + url + ".html");
        } else if (url.equals("/viewImage")) {
            var pw = queryParam.getQueryParams(queryParam.getRequestLine()).get("password");
            var id = queryParam.getQueryParams(queryParam.getRequestLine()).get("username");
            Set<String> userSet = userCredentials.keySet();
            Iterator<String> keyIterator = userSet.iterator();
            var k = keyIterator.next();
            var v = userCredentials.get(k);
            if (k.equals(id) && v.equals(pw)) {
                response = generateHTMLResponse(PATH + "/viewImage" + url + ".html");
            } else {
                response = generateHTMLResponse(PATH + "/viewImage/404.html");
            }
        } else if (url.equals("/cash/list")) {
            response = cashListResponse();
        } else {
            response = generateHTMLResponse(PATH + "/starthtml" + url + ".html");
        }
        return response;
    }

    private String cashListResponse() {
        List<String> responseKey = cache.getKeyList();
        StringBuilder sb = new StringBuilder();
        for (String element : responseKey) {
            sb.append(element).append(" ");
        }
        String result = sb.toString().trim();

        return "HTTP/1.1 200 OK\r\n" +
                "Content-Type: text/html; charset=UTF-8\r\n" +
                "\r\n" +
                result;
    }

    private String generateHTMLResponse(String filePath) {
        String fileContent = readFileContent(filePath);
        if (fileContent != null) {
            return "HTTP/1.1 200 OK\r\n" +
                    "Content-Type: text/html; charset=UTF-8\r\n" +
                    "\r\n" +
                    fileContent;
        } else {
            return generateNotFoundResponse();
        }
    }

    private String readFileContent(String filePath) {
        StringBuilder contentBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                contentBuilder.append(line).append("\n");
            }
        } catch (FileNotFoundException e) {
            return null; // 파일을 찾을 수 없는 경우 null 반환
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contentBuilder.toString();
    }

    private String generateNotFoundResponse() {
        return "HTTP/1.1 404 Not Found\r\n" +
                "Content-Type: text/plain; charset=UTF-8\r\n" +
                "\r\n" +
                "Page not found.";
    }
}
