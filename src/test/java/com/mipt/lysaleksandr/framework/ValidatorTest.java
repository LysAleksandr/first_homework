package com.mipt.lysaleksandr.framework;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ValidatorTest {

  @Test
  void testValidUser() {
    User user = new User("Cars", "cars.pillarman@hamon.com", (long)5000, "supaeija");
    ValidationResult result = Validator.validate(user);
    assertTrue(result.isValid());
    assertEquals(0, result.getErrors().size());
  }

  @Test
  void testNullName() {
    User user = new User(null, "cars.pillarman@hamon.com", (long)5000, "supaeija");
    ValidationResult result = Validator.validate(user);
    assertFalse(result.isValid());
    assertTrue(result.getErrors().contains("The name cannot be null"));
  }

  @Test
  void testShortName() {
    User user = new User("C", "cars.pillarman@hamon.com", (long)5000, "supaeija");
    ValidationResult result = Validator.validate(user);
    assertFalse(result.isValid());
    assertTrue(result.getErrors().contains("The name must be between 2 and 50 characters"));
  }

  @Test
  void testInvalidEmail() {
    User user = new User("Cars", "invalid-email", (long)5000, "supaeija");
    ValidationResult result = Validator.validate(user);
    assertFalse(result.isValid());
    assertTrue(result.getErrors().contains("Incorrect format email"));
  }

  @Test
  void testAgeOutOfRange() {
    User user = new User("John", "test@example.com", (long)7000, "supaeija");
    ValidationResult result = Validator.validate(user);
    assertFalse(result.isValid());
    assertTrue(result.getErrors().contains("Age must be between 0 and 6000"));
  }

  @Test
  void testMultiErrors() {
    User user = new User(null, "invalid-email", (long)7000, "1234");
    ValidationResult result = Validator.validate(user);
    assertFalse(result.isValid());
    assertEquals(4, result.getErrors().size());
  }
}