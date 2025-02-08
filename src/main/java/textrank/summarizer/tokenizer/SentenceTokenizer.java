package textrank.summarizer.tokenizer;

import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

public class SentenceTokenizer implements Tokenizable {

    private final String text;

    public SentenceTokenizer(String text) {
        this.text = text;
    }

    @Override
    public List<String> tokenizeTextIntoSentences() throws IOException {
        InputStream modelIn = new FileInputStream("E:\\Programming\\Projects\\TextRankAlgorithmWithTF-IDF\\src\\main\\resources\\en-sent.bin"); // OpenNLP sentence model
        SentenceDetectorME sentenceDetector = new SentenceDetectorME(new SentenceModel(modelIn));
        return Arrays.asList(sentenceDetector.sentDetect(text));
    }

}
