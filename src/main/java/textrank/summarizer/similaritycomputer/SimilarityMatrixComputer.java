package textrank.summarizer.similaritycomputer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimilarityMatrixComputer implements Computable {

    @Override
    public double[][] computeSimilarityMatrix(List<String> sentences) {
        Map<String, Integer> articleFrequencies = new HashMap<>();
        List<Map<String, Double>> tfidfVectors = new ArrayList<>();

        computeFrequencies(sentences, articleFrequencies, tfidfVectors);
        computeTFValues(articleFrequencies, tfidfVectors);

        return computeSimilarityMatrixUsingCosineSimilarity(tfidfVectors);
    }

    protected void computeFrequencies(List<String> sentences, Map<String, Integer> articleFrequencies, List<Map<String, Double>> tfidfVectors) {
        for (String sentence : sentences) {
            Map<String, Double> sentenceFrequencies = getFrequenciesForASentence(sentence);
            tfidfVectors.add(sentenceFrequencies);

            for (String word : sentenceFrequencies.keySet()) {
                articleFrequencies.put(word, articleFrequencies.getOrDefault(word, 0) + 1);
            }
        }
    }

    protected Map<String, Double> getFrequenciesForASentence(String sentence) {
        Map<String, Double> sentenceFrequencies = new HashMap<>();
        String[] words = sentence.split("\\s+");

        for (String word : words) {
            sentenceFrequencies.put(word, sentenceFrequencies.getOrDefault(word, 0.0) + 1);
        }

        return sentenceFrequencies;
    }

    protected void computeTFValues(Map<String, Integer> articleFrequencies, List<Map<String, Double>> tfidfVectors) {
        int totalSentences = tfidfVectors.size();
        for (Map<String, Double> tfMap : tfidfVectors) {
            for (String word : tfMap.keySet()) {
                double idf = Math.log((double) totalSentences / (articleFrequencies.get(word) + 1));
                tfMap.put(word, tfMap.get(word) * idf);
            }
        }
    }

    protected double[][] computeSimilarityMatrixUsingCosineSimilarity(List<Map<String, Double>> tfidfVectors) {
        int n = tfidfVectors.size();
        double[][] matrix = new double[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i != j) {
                    matrix[i][j] = cosineSimilarity(tfidfVectors.get(i), tfidfVectors.get(j));
                }
            }
        }

        return matrix;
    }

    private double cosineSimilarity(Map<String, Double> vec1, Map<String, Double> vec2) {
        double dotProduct = 0.0, norm1 = 0.0, norm2 = 0.0;

        for (String key : vec1.keySet()) {
            dotProduct += vec1.getOrDefault(key, 0.0) * vec2.getOrDefault(key, 0.0);
            norm1 += Math.pow(vec1.getOrDefault(key, 0.0), 2);
        }

        for (double value : vec2.values()) {
            norm2 += Math.pow(value, 2);
        }

        return (norm1 == 0 || norm2 == 0) ? 0.0 : dotProduct / (Math.sqrt(norm1) * Math.sqrt(norm2));
    }
}
