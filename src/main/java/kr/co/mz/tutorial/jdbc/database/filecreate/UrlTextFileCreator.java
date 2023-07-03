package kr.co.mz.tutorial.jdbc.database.filecreate;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class UrlTextFileCreator {
    private static final String PATH = "/Users/mz01-megalim/Downloads";
    private final String url;
    private final String response;

    public UrlTextFileCreator(String url, String response) {
        this.url = url;
        this.response = response;
    }

    public void save() throws IOException {
        File file = new File(PATH + url + ".txt");
        if (!file.exists()) {
            Writer writer = new FileWriter(file);
            writer.write(response);
            writer.flush();
            writer.close();
        }
    }
}
