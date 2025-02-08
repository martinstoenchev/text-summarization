package textrank.summarizer.rank;

import java.util.Map;

public interface Rankable {

    Map<Integer, Double> pageRank(double[][] matrix);

}
