package textrank.summarizer;

import java.io.IOException;

public interface Summarizable {

    String generateSummary() throws IOException;

}
