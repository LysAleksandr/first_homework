package com.mipt.lysaleksandr.inoutput;

import java.io.*;
import java.util.*;

public class TextFileAnalyzer {

  public static class AnalysisResult {

    private final long lineCount;
    private final long wordCount;
    private final long charCount;
    private final Map<Character, Integer> charFrequency;

    public AnalysisResult(long lineCount, long wordCount, long charCount,
        Map<Character, Integer> charFrequency) {
      this.lineCount = lineCount;
      this.wordCount = wordCount;
      this.charCount = charCount;
      this.charFrequency = charFrequency;
    }

    public long getLineCount() {
      return lineCount;
    }

    public long getWordCount() {
      return wordCount;
    }

    public long getCharCount() {
      return charCount;
    }

    public Map<Character, Integer> getCharFrequency() {
      return charFrequency;
    }

    @Override
    public String toString() {
      return String.format("line=%d, word=%d, char=%d, frequency=%s", lineCount, wordCount,
          charCount, charFrequency);
    }
  }

  public AnalysisResult analyzeFile(String filePath) throws IOException {
    long lineCount = 0;
    long wordCount = 0;
    long charCount = 0;
    Map<Character, Integer> charFrequency = new HashMap<>();

    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
      String line;

      while ((line = reader.readLine()) != null) {
        lineCount += 1;
        charCount += line.length() + 1;

        String[] words = line.trim().split("\\s+");
        if (!line.trim().isEmpty()) {
          wordCount += words.length;
        }

        for (char c : line.toCharArray()) {
          charFrequency.put(c, charFrequency.getOrDefault(c, 0) + 1);
        }
      }

      if (charCount > 0) {
        charCount -= 1;
      }
    }

    return new AnalysisResult(lineCount, wordCount, charCount, charFrequency);
  }

  public void saveAnalysisResult(AnalysisResult result, String outputPath) throws IOException {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath))) {
      writer.write(String.format(
          "Lines: %d\nWords: %d\nChars: %d\nCharacter Frequency:\n",
          result.getLineCount(), result.getWordCount(), result.getCharCount()));

      List<Map.Entry<Character, Integer>> list = new ArrayList<>(
          result.getCharFrequency().entrySet());

      Collections.sort(list, (a, b) -> b.getValue().compareTo(a.getValue()));

      for (Map.Entry<Character, Integer> entry : list) {
        char c = entry.getKey();
        long count = entry.getValue();

        String displayChar;
        if (c == ' ') {
          displayChar = "[space]";
        } else if (c == '\t') {
          displayChar = "[tabulation]";
        } else {
          displayChar = String.valueOf(c);
        }

        writer.write("'" + displayChar + "': " + count + " раз\n");
      }
    }
  }
}
