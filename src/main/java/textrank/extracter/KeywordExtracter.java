package textrank.extracter;

import textrank.processor.TextProcessor;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class KeywordExtracter implements Extractable {

    private final int numberOfKeywords;
    private final String text;
    private final TextProcessor textPreprocessor;

    public KeywordExtracter(String text, int numberOfKeywords) {
        this.textPreprocessor = new TextProcessor();
        this.text = text;
        this.numberOfKeywords = numberOfKeywords;
    }

    @Override
    public List<String> extractKeywords() {
        Map<String, Integer> articleFrequencies = new HashMap<>();
        Map<String, Double> tfidfScores = new HashMap<>();

        String[] words = textPreprocessor.removeStopWords(text).split("\\s+");
        Map<String, Integer> wordsFrequencies = new HashMap<>();
        for (String word : words) {
            wordsFrequencies.put(word, wordsFrequencies.getOrDefault(word, 0) + 1);
        }

        for (String word : wordsFrequencies.keySet()) {
            articleFrequencies.put(word, articleFrequencies.getOrDefault(word, 0) + 1);
        }

        int totalDocs = 1;
        for (String word : wordsFrequencies.keySet()) {
            double idf = Math.log((double) totalDocs / (articleFrequencies.get(word) + 1));
            tfidfScores.put(word, wordsFrequencies.get(word) * idf);
        }

        return tfidfScores.entrySet().stream()
                .sorted(Comparator.comparingDouble(Map.Entry::getValue))
                .limit(numberOfKeywords)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

}
