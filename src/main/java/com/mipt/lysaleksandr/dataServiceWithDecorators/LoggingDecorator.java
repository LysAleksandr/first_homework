package com.mipt.lysaleksandr.dataServiceWithDecorators;

import java.util.Optional;

public class LoggingDecorator implements DataService {

  private final DataService dataService;

  public LoggingDecorator(DataService dataService) {
    this.dataService = dataService;
  }

  @Override
  public Optional<String> findDataByKey(String key) {
    System.out.println("Search data by key: " + key);
    Optional<String> result = dataService.findDataByKey(key);
    System.out.println("Search result: " + (result.isPresent() ? "found" : "not found"));
    return result;
  }

  @Override
  public void saveData(String key, String data) {
    System.out.println("Saving data by key: " + key);
    dataService.saveData(key, data);
    System.out.println("Data saved successfully");
  }

  @Override
  public boolean deleteData(String key) {
    System.out.println("Deleting data by key: " + key);
    boolean result = dataService.deleteData(key);
    System.out.println("Result of deletion: " + (result ? "successfully" : "key not found"));
    return result;
  }
}