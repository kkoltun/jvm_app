package sda.jvm;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static java.util.Comparator.reverseOrder;
import static java.util.stream.Collectors.toMap;

class AnalysisResults {
  private long numberOfWords;
  private HashMap<String, Long> wordsWithOccurrences = new HashMap<>();
  private HashMap<Character, Long> lettersWithOccurrences = new HashMap<>();

  public HashMap<String, Long> getWordsWithOccurrences() {
    return wordsWithOccurrences;
  }

  public void setWordsWithOccurrences(HashMap<String, Long> wordsWithOccurrences) {
    this.wordsWithOccurrences = wordsWithOccurrences;
  }

  public HashMap<Character, Long> getLettersWithOccurrences() {
    return lettersWithOccurrences;
  }

  public void setLettersWithOccurrences(HashMap<Character, Long> lettersWithOccurrences) {
    this.lettersWithOccurrences = lettersWithOccurrences;
  }

  public long getNumberOfWords() {
    return numberOfWords;
  }

  public void setNumberOfWords(long numberOfWords) {
    this.numberOfWords = numberOfWords;
  }

  void addWord(String word) {
    ++this.numberOfWords;
    addWordOccurrences(word);
    addLetterOccurrences(word);
  }

  void sortAndLimit(int results) {
    this.wordsWithOccurrences = wordsWithOccurrences.entrySet().stream()
        .sorted(Map.Entry.comparingByValue(reverseOrder()))
        .limit(results)
        .collect(toMap(
            Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

    this.lettersWithOccurrences = lettersWithOccurrences.entrySet().stream()
        .sorted(Map.Entry.comparingByValue(reverseOrder()))
        .collect(toMap(
            Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
  }


  private void addWordOccurrences(String word) {
    Long occurrences = wordsWithOccurrences.getOrDefault(word, 0L);
    wordsWithOccurrences.put(word, occurrences + 1);
  }

  private void addLetterOccurrences(String word) {
    for (Character c : word.toCharArray()) {
      Long occurrences = lettersWithOccurrences.getOrDefault(c, 0L);
      lettersWithOccurrences.put(c, occurrences + 1);
    }
  }


}
