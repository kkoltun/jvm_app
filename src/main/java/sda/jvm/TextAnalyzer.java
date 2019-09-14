package sda.jvm;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class TextAnalyzer {

  private static final Pattern lettersAndSpaces = Pattern.compile("[^\\w ]", Pattern.UNICODE_CHARACTER_CLASS);

  public AnalysisResults analyze(InputStream inputStream) {
    AnalysisResults analysisResults = new AnalysisResults();

    Stream<String> lineStream = new BufferedReader(new InputStreamReader(inputStream)).lines();

    lineStream.forEach(line -> {
      String sanitized = sanitize(line);

      if (!sanitized.equals("")) {
        String lowerCase = sanitized.toLowerCase();
        String[] split = lowerCase.split("\\s+");

        for (String s : split) {
          analysisResults.addWord(s);
        }
      }
    });

    return analysisResults;
  }

  private static String sanitize(String string) {
    return lettersAndSpaces.matcher(string).replaceAll("");
  }


}
