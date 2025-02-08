package textrank.utils;

import java.io.BufferedReader;
import java.io.IOException;

public class ArticleReader {

    public static String readFromFile(String fileName) throws IOException {
        StringBuilder resultStringBuilder = new StringBuilder();
        try (java.io.FileReader fr = new java.io.FileReader(fileName);
             BufferedReader br = new BufferedReader(fr)) {
            String line;
            while ((line = br.readLine()) != null) {
                resultStringBuilder.append(line).append("\n");
            }
        }
        return resultStringBuilder.toString();
    }

}
