package com.mipt.lysaleksandr.inoutput;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class FileProcessorTest {

  @Test
  void testSplitAndMergeFile() throws IOException {
    FileProcessor processor = new FileProcessor();

    Path testFile = Files.createTempFile("test", ".dat");
    byte[] testData = new byte[1500];
    new Random().nextBytes(testData);
    Files.write(testFile, testData);

    Path tempDir = Files.createTempDirectory("parts");
    String outputDir = tempDir.toString();
    List<Path> parts = processor.splitFile(testFile.toString(), outputDir, 500);

    assertEquals(3, parts.size());

    assertEquals(500, Files.size(parts.get(0)));
    assertEquals(500, Files.size(parts.get(1)));
    assertEquals(500, Files.size(parts.get(2)));

    assertTrue(parts.get(0).toString().endsWith(".part1"));
    assertTrue(parts.get(1).toString().endsWith(".part2"));
    assertTrue(parts.get(2).toString().endsWith(".part3"));

    Path mergedFile = Files.createTempFile("merged", ".dat");
    processor.mergeFiles(parts, mergedFile.toString());

    assertArrayEquals(Files.readAllBytes(testFile), Files.readAllBytes(mergedFile));

    Files.deleteIfExists(testFile);
    Files.deleteIfExists(mergedFile);
    for (Path part : parts) {
      Files.deleteIfExists(part);
    }
    Files.deleteIfExists(tempDir);
  }

  @Test
  void testMergeNonExistentParts() throws IOException {
    FileProcessor processor = new FileProcessor();
    Path outputFile = Files.createTempFile("output", ".dat");

    List<Path> nonExistentParts = List.of(Paths.get("nonexistent.part1"));

    assertThrows(IOException.class, () -> {
      processor.mergeFiles(nonExistentParts, outputFile.toString());
    });

    Files.deleteIfExists(outputFile);
  }
}