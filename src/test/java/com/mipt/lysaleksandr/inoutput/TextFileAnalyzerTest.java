package com.mipt.lysaleksandr.inoutput;

import org.junit.jupiter.api.Test;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class TextFileAnalyzerTest {

  @Test
  void testAnalyzeFile() throws IOException {
    TextFileAnalyzer analyzer = new TextFileAnalyzer();

    Path testFile = Files.createTempFile("test", ".txt");
    Files.write(testFile, Arrays.asList("Hello world!", "This is test."));

    TextFileAnalyzer.AnalysisResult result = analyzer.analyzeFile(testFile.toString());

    assertEquals(2, result.getLineCount());
    assertEquals(5, result.getWordCount());
    assertEquals(26, result.getCharCount());

    Map<Character, Integer> frequency = result.getCharFrequency();
    assertEquals(1, frequency.get('H'));
    assertEquals(3, frequency.get('l'));
    assertEquals(3, frequency.get(' '));
    assertEquals(1, frequency.get('!'));

    Files.deleteIfExists(testFile);
  }

  @Test
  void testSaveAnalysisResult() throws IOException {
    TextFileAnalyzer analyzer = new TextFileAnalyzer();

    Map<Character, Integer> frequency = Map.of('a', 1, 'b', 2, 'c', 3);
    TextFileAnalyzer.AnalysisResult result = new TextFileAnalyzer.AnalysisResult(2, 5, 20, frequency);

    Path outputFile = Files.createTempFile("analysis", ".txt");
    analyzer.saveAnalysisResult(result, outputFile.toString());

    assertTrue(Files.exists(outputFile));
    assertTrue(Files.size(outputFile) > 0);

    List<String> lines = Files.readAllLines(outputFile);
    assertFalse(lines.isEmpty());

    String content = String.join("\n", lines);
    assertTrue(content.contains("Lines: 2"));
    assertTrue(content.contains("Words: 5"));
    assertTrue(content.contains("Chars: 20"));
    assertTrue(content.contains("'a': 1 раз"));
    assertTrue(content.contains("'b': 2 раз"));
    assertTrue(content.contains("'c': 3 раз"));

    Files.deleteIfExists(outputFile);
  }
}