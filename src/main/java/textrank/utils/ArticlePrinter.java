package textrank.utils;

public class ArticlePrinter {

    public static void printArticle(String text) {
        String[] sentences = text.split("\\.");

        for (String sentence : sentences) {
            System.out.println(sentence);
        }
    }

}
