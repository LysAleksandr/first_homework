package com.mipt.lysaleksandr.dataServiceWithDecorators;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class LoggingDecoratorTest {

  @Test
  void testFindDataByKeyAndLogsAction() {
    SimpleDataService simpleService = new SimpleDataService();
    LoggingDecorator loggingService = new LoggingDecorator(simpleService);

    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    PrintStream originalOut = System.out;
    System.setOut(new PrintStream(outputStream));

    try {
      loggingService.findDataByKey("key");

      String output = outputStream.toString();
      assertTrue(output.contains("Search data by key: key"));
      assertTrue(output.contains("Search result: not found"));
    } finally {
      System.setOut(originalOut);
    }
  }

  @Test
  void testSaveDataAndLogsAction() {
    SimpleDataService simpleService = new SimpleDataService();
    LoggingDecorator loggingService = new LoggingDecorator(simpleService);

    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    PrintStream originalOut = System.out;
    System.setOut(new PrintStream(outputStream));

    try {
      loggingService.saveData("key", "data");

      String output = outputStream.toString();
      assertTrue(output.contains("Saving data by key: key"));
      assertTrue(output.contains("Data saved successfully"));
    } finally {
      System.setOut(originalOut);
    }
  }

  @Test
  void testDeleteDataAndLogsAction() {
    SimpleDataService simpleService = new SimpleDataService();
    LoggingDecorator loggingService = new LoggingDecorator(simpleService);

    simpleService.saveData("key", "data");

    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    PrintStream originalOut = System.out;
    System.setOut(new PrintStream(outputStream));

    try {
      loggingService.deleteData("key");

      String output = outputStream.toString();
      assertTrue(output.contains("Deleting data by key: key"));
      assertTrue(output.contains("Result of deletion: successfully"));
    } finally {
      System.setOut(originalOut);
    }
  }
}
