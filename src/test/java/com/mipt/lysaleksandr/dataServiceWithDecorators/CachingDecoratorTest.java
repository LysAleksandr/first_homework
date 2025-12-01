package com.mipt.lysaleksandr.dataServiceWithDecorators;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

class CachingDecoratorTest {

  @Test
  void testDeleteDataAndInvalidatesCache() {
    SimpleDataService simpleService = new SimpleDataService();
    CachingDecorator cachingService = new CachingDecorator(simpleService);

    cachingService.saveData("key", "data");

    assertTrue(simpleService.findDataByKey("key").isPresent());

    boolean deleteResult = cachingService.deleteData("key");
    assertTrue(deleteResult);

    assertFalse(simpleService.findDataByKey("key").isPresent());

    assertFalse(cachingService.findDataByKey("key").isPresent());
  }

  @Test
  void testFindDataByKeyAndCachesResult() {
    SimpleDataService simpleService = new SimpleDataService();
    CachingDecorator cachingService = new CachingDecorator(simpleService);

    simpleService.saveData("key", "data");

    Optional<String> result1 = cachingService.findDataByKey("key");
    assertEquals("data", result1.get());

    simpleService.deleteData("key");

    Optional<String> result2 = cachingService.findDataByKey("key");
    assertTrue(result2.isPresent());
    assertEquals("data", result2.get());
  }

  @Test
  void testSaveData_UpdatesCache() {
    SimpleDataService simpleService = new SimpleDataService();
    CachingDecorator cachingService = new CachingDecorator(simpleService);

    cachingService.saveData("key", "data");

    Optional<String> result = cachingService.findDataByKey("key");
    assertEquals("data", result.get());

    cachingService.saveData("key", "data1");

    Optional<String> updatedResult = cachingService.findDataByKey("key");
    assertTrue(updatedResult.isPresent());
    assertEquals("data1", updatedResult.get());
  }
}
