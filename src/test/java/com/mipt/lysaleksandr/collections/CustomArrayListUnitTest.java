package com.mipt.lysaleksandr.collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomArrayListUnitTest {

  private CustomList<String> strList;
  private CustomList<Integer> intList;

  @BeforeEach
  void setUp() {
    strList = new CustomArrayList<>();
    intList = new CustomArrayList<>();
  }

  @Test
  void testAddAndGet() {
    strList.add("Hello");
    intList.add(5);

    assertEquals("Hello", strList.get(0));
    assertEquals(5, intList.get(0));

    assertThrows(IllegalArgumentException.class, () -> intList.add(null));
    assertThrows(IllegalArgumentException.class, () -> strList.add(null));

    strList.add("test");
    intList.add(8);

    assertThrows(IndexOutOfBoundsException.class, () -> strList.get(-1));
    assertThrows(IndexOutOfBoundsException.class, () -> strList.get(2));
  }

  @Test
  void testRemove() {
    strList.add("Hello");
    strList.add("World");
    strList.add("!");

    strList.remove(1);

    assertEquals(2, strList.size());
    assertEquals("Hello", strList.get(0));
    assertEquals("!", strList.get(1));

    assertThrows(IndexOutOfBoundsException.class, () -> strList.remove(-1));
    assertThrows(IndexOutOfBoundsException.class, () -> strList.remove(4));

    strList.remove(0);
    strList.remove(0);
    assertThrows(IndexOutOfBoundsException.class, () -> strList.remove(0));
  }

  @Test
  void testSize() {
    assertEquals(0, intList.size());

    intList.add(1);
    assertEquals(1, intList.size());

    intList.add(2);
    assertEquals(2, intList.size());

    intList.add(3);
    assertEquals(3, intList.size());

    intList.remove(0);
    assertEquals(2, intList.size());
  }

  @Test
  void testIsEmpty() {
    assertTrue(intList.isEmpty());

    intList.add(1);
    assertFalse(intList.isEmpty());

    intList.remove(0);
    assertTrue(intList.isEmpty());
  }
}