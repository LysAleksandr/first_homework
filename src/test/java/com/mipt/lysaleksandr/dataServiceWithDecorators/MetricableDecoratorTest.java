package com.mipt.lysaleksandr.dataServiceWithDecorators;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class MetricalDecoratorTest {

  @Test
  void testFindDataByKeyAndSendsMetric() {
    SimpleDataService simpleService = new SimpleDataService();
    MetricableDecorator metricalService = new MetricableDecorator(simpleService);

    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    PrintStream originalOut = System.out;
    System.setOut(new PrintStream(outputStream));

    try {
      metricalService.findDataByKey("key");

      String output = outputStream.toString();
      assertTrue(output.contains("The method was performed: PT"));
    } finally {
      System.setOut(originalOut);
    }
  }

  @Test
  void testSaveDataAndSendsMetric() {
    SimpleDataService simpleService = new SimpleDataService();
    MetricableDecorator metricalService = new MetricableDecorator(simpleService);

    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    PrintStream originalOut = System.out;
    System.setOut(new PrintStream(outputStream));

    try {
      metricalService.saveData("key", "data");

      String output = outputStream.toString();
      assertTrue(output.contains("The method was performed: PT"));
    } finally {
      System.setOut(originalOut);
    }
  }

  @Test
  void testDeleteDataAndSendsMetric() {
    SimpleDataService simpleService = new SimpleDataService();
    MetricableDecorator metricalService = new MetricableDecorator(simpleService);

    simpleService.saveData("key", "data");

    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    PrintStream originalOut = System.out;
    System.setOut(new PrintStream(outputStream));

    try {
      metricalService.deleteData("key");

      String output = outputStream.toString();
      assertTrue(output.contains("The method was performed: PT"));
    } finally {
      System.setOut(originalOut);
    }
  }
}
