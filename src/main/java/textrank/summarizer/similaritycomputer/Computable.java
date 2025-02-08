package textrank.summarizer.similaritycomputer;

import java.util.List;

public interface Computable {

    double[][] computeSimilarityMatrix(List<String> sentences);

}
