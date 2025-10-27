package com.mipt.lysaleksandr.collections;

import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;

public class CollectionPerformanceTester {

  private static final int COUNT_OF_ELEMENTS = 10000;

  @Test
  public void testCollectionPerformance() {
    System.out.println("Performance comparison ArrayList vs LinkedList");
    System.out.println("Count of elements: " + COUNT_OF_ELEMENTS);
    System.out.println("-----------------------------------------------------------------------------");
    System.out.println("First - ArrayList, Second - LinkedList");
    System.out.println();

    List<Integer> arrayList = new ArrayList<>();
    List<Integer> linkedList = new LinkedList<>();

    long timeArray;
    long timeLinked;
    long start = System.currentTimeMillis();
    for (int ind = 0; ind < COUNT_OF_ELEMENTS; ind++) {
      arrayList.add(ind);
    }
    long end = System.currentTimeMillis();
    timeArray = end - start;
    start = end;
    for (int ind = 0; ind < COUNT_OF_ELEMENTS; ind++) {
      linkedList.add(ind);
    }
    end = System.currentTimeMillis();
    timeLinked = end - start;
    System.out.println("Add at the back: " + timeArray + " " + timeLinked);

    arrayList = new ArrayList<>();
    linkedList = new LinkedList<>();
    start = end;
    for (int ind = 0; ind < COUNT_OF_ELEMENTS; ind++) {
      arrayList.add(0, ind);
    }
    end = System.currentTimeMillis();
    timeArray = end - start;
    start = end;
    for (int ind = 0; ind < COUNT_OF_ELEMENTS; ind++) {
      linkedList.add(0, ind);
    }
    end = System.currentTimeMillis();
    timeLinked = end - start;
    System.out.println("Add at the front: " + timeArray + " " + timeLinked);

    arrayList = new ArrayList<>();
    linkedList = new LinkedList<>();
    start = end;
    for (int ind = 0; ind < COUNT_OF_ELEMENTS; ind++) {
      arrayList.add(arrayList.size() / 2, ind);
    }
    end = System.currentTimeMillis();
    timeArray = end - start;
    start = end;
    for (int ind = 0; ind < COUNT_OF_ELEMENTS; ind++) {
      linkedList.add(linkedList.size() / 2, ind);
    }
    end = System.currentTimeMillis();
    timeLinked = end - start;
    System.out.println("Add at the mid: " + timeArray + " " + timeLinked);

    start = end;
    for (int ind = 0; ind < COUNT_OF_ELEMENTS; ind++) {
      arrayList.get(ind);
    }
    end = System.currentTimeMillis();
    timeArray = end - start;
    start = end;
    for (int ind = 0; ind < COUNT_OF_ELEMENTS; ind++) {
      linkedList.add(ind);
    }
    end = System.currentTimeMillis();
    timeLinked = end - start;
    System.out.println("Access by index: " + timeArray + " " + timeLinked);

    start = end;
    for (int ind = 0; ind < COUNT_OF_ELEMENTS; ind++) {
      arrayList.remove(0);
    }
    end = System.currentTimeMillis();
    timeArray = end - start;
    start = end;
    for (int ind = 0; ind < COUNT_OF_ELEMENTS; ind++) {
      linkedList.remove(0);
    }
    end = System.currentTimeMillis();
    timeLinked = end - start;
    System.out.println("remove front: " + timeArray + " " + timeLinked);

    for (int ind = 0; ind < COUNT_OF_ELEMENTS; ind++) {
      arrayList.add(ind);
    }
    for (int ind = 0; ind < COUNT_OF_ELEMENTS; ind++) {
      linkedList.add(ind);
    }

    start = end;
    for (int ind = 0; ind < COUNT_OF_ELEMENTS; ind++) {
      arrayList.remove(arrayList.size() - 1);
    }
    end = System.currentTimeMillis();
    timeArray = end - start;
    start = end;
    for (int ind = 0; ind < COUNT_OF_ELEMENTS; ind++) {
      linkedList.remove(linkedList.size() - 1);
    }
    end = System.currentTimeMillis();
    timeLinked = end - start;
    System.out.println("remove back: " + timeArray + " " + timeLinked);
  }
}