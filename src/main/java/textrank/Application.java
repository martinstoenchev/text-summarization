package textrank;

import textrank.extracter.KeywordExtracter;
import textrank.summarizer.TextSummarizer;
import textrank.utils.ArticlePrinter;
import textrank.utils.ArticleReader;

import java.io.IOException;
import java.util.List;

public class Application {
    private static final int SUMMARY_SENTENCES = 5;

    public static void main(String[] args) throws IOException {
        String text = ArticleReader.readFromFile("E:\\Programming\\Projects\\TextRankAlgorithmWithTF-IDF\\src\\main\\resources\\article.txt");

        TextSummarizer textSummarizer = new TextSummarizer(text, SUMMARY_SENTENCES);
        String summary = textSummarizer.generateSummary();
        System.out.println("Summary:\n");
        ArticlePrinter.printArticle(summary);

        KeywordExtracter keywordExtracter = new KeywordExtracter(text, 10);
        List<String> keywords = keywordExtracter.extractKeywords();
        System.out.println("\nKeywords: " + keywords);
    }
}
