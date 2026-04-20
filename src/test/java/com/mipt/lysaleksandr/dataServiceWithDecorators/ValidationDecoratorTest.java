package com.mipt.lysaleksandr.dataServiceWithDecorators;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidationDecoratorTest {

  @Test
  void testSaveDataAndValidatesKey() {
    SimpleDataService simpleService = new SimpleDataService();
    ValidationDecorator validationService = new ValidationDecorator(simpleService);

    assertThrows(IllegalArgumentException.class, () -> {
      validationService.saveData("", "data");
    });

    assertThrows(IllegalArgumentException.class, () -> {
      validationService.saveData(null, "data");
    });

    String key = "a".repeat(5000);
    assertThrows(IllegalArgumentException.class, () -> {
      validationService.saveData(key, "data");
    });
  }

  @Test
  void testSaveDataAndValidatesData() {
    SimpleDataService simpleService = new SimpleDataService();
    ValidationDecorator validationService = new ValidationDecorator(simpleService);

    assertThrows(IllegalArgumentException.class, () -> {
      validationService.saveData("key", null);
    });

    String data = "a".repeat(5000);
    assertThrows(IllegalArgumentException.class, () -> {
      validationService.saveData("key", data);
    });
  }

  @Test
  void testFindDataByKeyAndValidatesKey() {
    SimpleDataService simpleService = new SimpleDataService();
    ValidationDecorator validationService = new ValidationDecorator(simpleService);

    assertThrows(IllegalArgumentException.class, () -> {
      validationService.findDataByKey("");
    });
  }

  @Test
  void testDeleteDataAndValidatesKey() {
    SimpleDataService simpleService = new SimpleDataService();
    ValidationDecorator validationService = new ValidationDecorator(simpleService);

    assertThrows(IllegalArgumentException.class, () -> {
      validationService.deleteData("");
    });
  }

  @Test
  void testValidDataAndWorksCorrectly() {
    SimpleDataService simpleService = new SimpleDataService();
    ValidationDecorator validationService = new ValidationDecorator(simpleService);

    assertDoesNotThrow(() -> {
      validationService.saveData("key", "data");
      validationService.findDataByKey("key");
      validationService.deleteData("key");
    });
  }
}
