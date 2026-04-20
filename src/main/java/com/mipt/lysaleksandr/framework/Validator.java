package com.mipt.lysaleksandr.framework;

import java.lang.reflect.Field;

public class Validator {

  private static final String EMAIL_PATTERN = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

  private static void checkNotNull(Field field, Object value, ValidationResult result) {
    if (field.isAnnotationPresent(NotNull.class)) {
      NotNull notNull = field.getAnnotation(NotNull.class);
      if (value == null) {
        result.addError(notNull.message());
      }
    }
  }

  private static void checkSize(Field field, Object value, ValidationResult result) {
    if (field.isAnnotationPresent(Size.class) && value instanceof String) {
      Size size = field.getAnnotation(Size.class);
      String value1 = (String) value;
      long length = value1.length();
      if (length < size.min() || length > size.max()) {
        result.addError(size.message());
      }
    }
  }

  private static void checkRange(Field field, Object value, ValidationResult result) {
    if (field.isAnnotationPresent(Range.class)) {
      Range range = field.getAnnotation(Range.class);
      if (value instanceof Long) {
        Long value1 = (Long) value;
        if (value1 < range.min() || value1 > range.max()) {
          result.addError(range.message());
        }
      } else if (value instanceof Long) {
        Long value1 = (Long) value;
        if (value1 < range.min() || value1 > range.max()) {
          result.addError(range.message());
        }
      }
    }
  }

  private static void checkEmail(Field field, Object value, ValidationResult result) {
    if (field.isAnnotationPresent(Email.class) && value instanceof String) {
      Email email = field.getAnnotation(Email.class);
      String value1 = (String) value;
      if (!isValidEmail(value1)) {
        result.addError(email.message());
      }
    }
  }

  private static boolean isValidEmail(String email) {
    if (email == null || email.trim().isEmpty()) {
      return false;
    }
    return email.contains("@") && email.indexOf("@") < email.lastIndexOf(".");
  }

  public static ValidationResult validate(Object object) {
    ValidationResult result = new ValidationResult();
    if (object == null) {
      result.addError("Validated object can't be null");
      return result;
    }
    Class<?> clazz = object.getClass();
    Field[] fields = clazz.getDeclaredFields();
    for (Field field : fields) {
      field.setAccessible(true);
      try {
        Object value = field.get(object);
        checkNotNull(field, value, result);
        if (value != null) {
          checkSize(field, value, result);
          checkRange(field, value, result);
          checkEmail(field, value, result);
        }
      } catch (IllegalAccessException e) {
        result.addError("Can't access field: " + field.getName());
      }
    }
    return result;
  }
}