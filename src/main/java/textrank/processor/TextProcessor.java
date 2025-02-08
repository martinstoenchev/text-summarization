package textrank.processor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class TextProcessor implements Processorable {

    static final Set<String> STOP_WORDS = loadStopWords("E:\\Programming\\Projects\\TextRankAlgorithmWithTF-IDF\\src\\main\\resources\\stopwords.txt");

    @Override
    public String removeStopWords(String text) {
        return Arrays.stream(text.toLowerCase().split("\\s+"))
                .filter(word -> !STOP_WORDS.contains(word))
                .collect(Collectors.joining(" "));
    }

    private static Set<String> loadStopWords(String fileName) {
        try {
            return new HashSet<>(Files.readAllLines(new File(fileName).toPath()));
        } catch (IOException e) {
            return Set.of("the", "is", "in", "at", "which", "on", "for", "a", "an", "and", "of"); // Default stop words
        }
    }

}
