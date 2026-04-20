package com.mipt.lysaleksandr.generici;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

public class CollectionUtils {
  public static <T> List<T> mergeLists(List<? extends T> list1,
      List<? extends T> list2) {
    List<T> list = new ArrayList<>();
    if (list1 != null) {
      list.addAll(list1);
    }
    if (list2 != null) {
      list.addAll(list2);
    }
    return list;
  }

  public static <T> void addAll(List<? super T> destination,
      List<? extends T> source) {
    if (destination == null || source == null) {
      return;
    }
    destination.addAll(source);
  }

  public static void main(String[] args) {
    final List<Integer> list1 = Arrays.asList(1, 2, 3);
    final List<Double> list2 = Arrays.asList(4.5, 5.6);
    final List<Number> merged = CollectionUtils.mergeLists(list1, list2);
    for (int ind = 0; ind < merged.size(); ind++) {
      System.out.print(merged.get(ind) + " ");
    }
    System.out.println();
    final List<Object> destination = new ArrayList<>();
    CollectionUtils.addAll(destination, list1);
    for (int ind = 0; ind < destination.size(); ind++) {
      System.out.print(destination.get(ind) + " ");
    }
  }
}
