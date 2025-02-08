package textrank.summarizer.tokenizer;

import java.io.IOException;
import java.util.List;

public interface Tokenizable {

    List<String> tokenizeTextIntoSentences() throws IOException;

}
