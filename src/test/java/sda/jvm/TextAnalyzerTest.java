package sda.jvm;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.*;

public class TextAnalyzerTest {

  @Test
  public void shouldAnalyzeText() throws IOException {
    InputStream example = loadFromResources("example_text.txt");

    TextAnalyzer textAnalyzer = new TextAnalyzer();
    AnalysisResults analysisResults = textAnalyzer.analyze(example);

    assertEquals(7L, analysisResults.getNumberOfWords());

    HashMap<String, Long> wordsWithOccurrences = analysisResults.getWordsWithOccurrences();
    assertEquals(3L, wordsWithOccurrences.get("raz").longValue());
    assertEquals(2L, wordsWithOccurrences.get("dwa").longValue());

    HashMap<Character, Long> lettersWithOccurrences = analysisResults.getLettersWithOccurrences();
    assertEquals(6, lettersWithOccurrences.get('a').longValue());
    assertEquals(5, lettersWithOccurrences.get('r').longValue());
    assertEquals(2, lettersWithOccurrences.get('d').longValue());
    assertEquals(2, lettersWithOccurrences.get('w').longValue());
    assertEquals(3, lettersWithOccurrences.get('z').longValue());
  }

  private InputStream loadFromResources(String filename) throws IOException {
    ClassLoader classLoader = getClass().getClassLoader();
    File file = new File(requireNonNull(classLoader.getResource(filename)).getFile());
    return new FileInputStream(file);
  }

}