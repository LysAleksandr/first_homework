package com.mipt.lysaleksandr.collections;

/**
 * Interface for CustomList
 * @param <A> type of element for CustomList
 */

public interface CustomList<A> extends Iterable<A> {

  /**
   * Adds an element to the end of the list
   * @param element element to add
   * @throws IllegalArgumentException if the element is null
   */
  void add(A element);

  /**
   * Gets the element at the specified index
   * @param index element's index
   * @throws IndexOutOfBoundsException if the index is out of bounds of the list
   * @return return the element at the specified index
   */
  A get(int index);

  /**
   * Removes the element at the specified index
   * @param index index of the element to remove
   * @throws IndexOutOfBoundsException if the index is out of bounds of the list
   */
  void remove(int index);

  /**
   * Returns the number of elements in a list
   * @return number of elements returned
   */
  int size();

  /**
   * Checks if the list is empty
   * @return outputs true if the list is empty, false otherwise
   */
  boolean isEmpty();
}
