package com.mipt.lysaleksandr.generici;

import java.util.Objects;

public class ArrayUtils {

  public static <T> int findFirst(T[] array, T element) {
    if (array == null) {
      return -1;
    }

    for (int ind = 0; ind < array.length; ind++) {
      if (Objects.equals(element, array[ind])) {
        return ind;
      }
    }

    return -1;
  }

  public static void main(String[] args) {
    // пример использования
    final String[] names = {"Alice", "Bob", "Charlie"};
    final int index1 = ArrayUtils.findFirst(names,
        "Bob");
    System.out.println(index1);

    names[1] = null;
    final int index2 = ArrayUtils.findFirst(names, "Bob");
    System.out.println(index2);

    final int index3 = ArrayUtils.findFirst(names, null);
    System.out.println(index3);

    final int index4 = ArrayUtils.findFirst(names, "Charlie");
    System.out.println(index4);
  }
}
