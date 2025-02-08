package textrank.summarizer;

import textrank.processor.TextProcessor;
import textrank.summarizer.rank.PageRank;
import textrank.summarizer.similaritycomputer.SimilarityMatrixComputer;
import textrank.summarizer.tokenizer.SentenceTokenizer;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TextSummarizer implements Summarizable {

    private final int numberOfSentences;
    private final SentenceTokenizer tokenizer;
    private final TextProcessor preprocessor;
    private final SimilarityMatrixComputer computer;
    private final PageRank pageRank;

    public TextSummarizer(String text, int numberOfSentences) {
        this.numberOfSentences = numberOfSentences;
        tokenizer = new SentenceTokenizer(text);
        preprocessor = new TextProcessor();
        computer = new SimilarityMatrixComputer();
        pageRank = new PageRank();
    }

    @Override
    public String generateSummary() throws IOException {
        List<String> sentences = tokenizer.tokenizeTextIntoSentences();
        List<String> processedSentences = sentences.stream()
                .map(preprocessor::removeStopWords)
                .collect(Collectors.toList());

        double[][] similarityMatrix = computer.computeSimilarityMatrix(processedSentences);
        Map<Integer, Double> scores = pageRank.pageRank(similarityMatrix);

        return getSummary(sentences, scores, numberOfSentences);
    }

    private String getSummary(List<String> sentences, Map<Integer, Double> scores, int nSentences) {
        return scores.entrySet().stream()
                .sorted((a, b) -> Double.compare(b.getValue(), a.getValue()))
                .limit(nSentences)
                .map(entry -> sentences.get(entry.getKey()))
                .collect(Collectors.joining(" "));
    }
}
