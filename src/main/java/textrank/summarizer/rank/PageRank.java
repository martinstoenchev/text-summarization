package textrank.summarizer.rank;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class PageRank implements Rankable {

    @Override
    public Map<Integer, Double> pageRank(double[][] matrix) {
        int n = matrix.length;
        double dampingFactor = 0.85;
        double epsilon = 1e-6;
        double[] scores = new double[n];
        Arrays.fill(scores, 1.0 / n);

        double delta;
        do {
            double[] newScores = new double[n];
            delta = 0;

            for (int i = 0; i < n; i++) {
                double sum = 0;
                for (int j = 0; j < n; j++) {
                    sum += matrix[j][i] * scores[j];
                }
                newScores[i] = (1 - dampingFactor) / n + dampingFactor * sum;
                delta += Math.abs(newScores[i] - scores[i]);
            }
            scores = newScores;
        } while (delta > epsilon);

        Map<Integer, Double> scoreMap = new HashMap<>();
        for (int i = 0; i < n; i++) {
            scoreMap.put(i, scores[i]);
        }
        return scoreMap;
    }

}
