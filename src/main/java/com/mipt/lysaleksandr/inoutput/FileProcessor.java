package com.mipt.lysaleksandr.inoutput;

import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class FileProcessor {

  public List<Path> splitFile(String sourcePath, String outputDir, int partSize)
      throws IOException {
    List<Path> partPaths = new ArrayList<>();
    Path sourceFile = Paths.get(sourcePath);
    String fileName = sourceFile.getFileName().toString();

    try (FileChannel sourceChannel = FileChannel.open(sourceFile, StandardOpenOption.READ)) {
      long fileSize = sourceChannel.size();
      long partNumber = 1;
      long position = 0;

      while (position < fileSize) {
        String partFileName = fileName + ".part" + partNumber;
        Path partPath = Paths.get(outputDir, partFileName);

        try (FileChannel partChannel = FileChannel.open(partPath,
            StandardOpenOption.CREATE, StandardOpenOption.WRITE)) {

          long byteToWrite = Math.min(partSize, fileSize - position);
          long transferred = sourceChannel.transferTo(position, byteToWrite, partChannel);

          if (transferred != byteToWrite) {
            throw new IOException("Failed to write part " + partNumber);
          }

          partPaths.add(partPath);
          position += transferred;
          partNumber += 1;
        }
      }
    }

    return partPaths;
  }

  public void mergeFiles(List<Path> partPaths, String outputPath) throws IOException {
    Path outputFile = Paths.get(outputPath);

    try (FileChannel outputChannel = FileChannel.open(outputFile,
        StandardOpenOption.CREATE, StandardOpenOption.WRITE)) {

      for (Path partPath : partPaths) {
        if (!Files.exists(partPath)) {
          throw new IOException("Part file not found: " + partPath);
        }

        try (FileChannel partChannel = FileChannel.open(partPath, StandardOpenOption.READ)) {
          long size = partChannel.size();
          long position = 0;

          while (position < size) {
            position += partChannel.transferTo(position, size - position, outputChannel);
          }
        }
      }
    }
  }
}