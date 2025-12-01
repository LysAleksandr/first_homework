package com.mipt.lysaleksandr.dataServiceWithDecorators;

import java.util.Optional;

public class ValidationDecorator implements DataService {

  private final DataService dataService;

  public ValidationDecorator(DataService dataService) {
    this.dataService = dataService;
  }

  private void validateKey(String key) {
    if (key == null || key.trim().isEmpty()) {
      throw new IllegalArgumentException("The key cannot be empty");
    }
    if (key.length() > 1000) {
      throw new IllegalArgumentException("The key is too long");
    }
  }

  private void validateData(String data) {
    if (data == null) {
      throw new IllegalArgumentException("The data cannot be null");
    }
    if (data.length() > 1000) {
      throw new IllegalArgumentException("The data is too big");
    }
  }

  @Override
  public Optional<String> findDataByKey(String key) {
    validateKey(key);
    return dataService.findDataByKey(key);
  }

  @Override
  public void saveData(String key, String data) {
    validateKey(key);
    validateData(data);
    dataService.saveData(key, data);
  }

  @Override
  public boolean deleteData(String key) {
    validateKey(key);
    return dataService.deleteData(key);
  }
}