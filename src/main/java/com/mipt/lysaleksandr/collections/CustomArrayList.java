package com.mipt.lysaleksandr.collections;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Interface for CustomArrayList
 * @param <A> type of element for CustomArrayList
 */

public class CustomArrayList<A> implements CustomList<A>{
  private Object[] elements;

  /**
   * @param DEFAULT_CAPACITY basic capacity
   */
  private static int DEFAULT_CAPACITY = 10;

  /**
   * @param COEFFICIENT capacity magnification coefficient
   */
  private static double COEFFICIENT = 1.5;

  /**
   * @param size size of CustomArrayList
   */
  private int size;


  /**
   * @param capacity real cpasity of CustomArrayList
   */
  private int capacity;


  public CustomArrayList() {
    this.elements = new Object[DEFAULT_CAPACITY];
    this.size = 0;
    this.capacity = DEFAULT_CAPACITY;
  }

  /**
   * Adds an element to the end of the list
   * @param element element to add
   * @throws IllegalArgumentException if the element is null
   */
  @Override
  public void add(A element) {
    if (element == null) {
      throw new IllegalArgumentException("Element cannot be null");
    }

    if (size == capacity) {
      capacity = (int) (capacity * COEFFICIENT);
      Object[] new_elements = new Object[capacity];
      System.arraycopy(elements, 0, new_elements, 0, size);
      elements = new_elements;
    }

    elements[size] = element;
    size += 1;
  }

  /**
   * Gets the element at the specified index
   * @param index element's index
   * @throws IndexOutOfBoundsException if the index is out of bounds of the list
   * @return return the element at the specified index
   */
  @Override
  public A get(int index) {
    if (index < 0 || index >= size) {
      throw new IndexOutOfBoundsException("Index must be from 0 to " + (size - 1));
    }
    return (A) elements[index];
  }

  /**
   * Removes the element at the specified index
   * @param index index of the element to remove
   * @throws IndexOutOfBoundsException if the index is out of bounds of the list
   */
  @Override
  public void remove(int index) {
    if (index < 0 || index >= size) {
      throw new IndexOutOfBoundsException("Index must be from 0 to " + (size - 1));
    }
    for (int ind = index; ind < size - 1; ind++) {
      elements[ind] = elements[ind + 1];
    }
    size -= 1;
    elements[size] = null;
  }

  /**
   * Returns the number of elements in a list
   * @return number of elements returned
   */
  @Override
  public int size() {
    return size;
  }

  /**
   * Checks if the list is empty
   * @return outputs true if the list is empty, false otherwise
   */
  @Override
  public boolean isEmpty() {
    return size == 0;
  }

  /**
   * returns an iterator of element in elements
   * @return returns an iterator of element in elements
   */
  @Override
  public Iterator<A> iterator() {
    return new CustomArrayListIterator();
  }

  /**
   * returns an iterator of element in elements
   */
  private class CustomArrayListIterator implements Iterator<A> {
    private int current_index = 0;
    private boolean can_remove = false;

    /**
     * checks if there is a next element
     * @return true if condition is met
     */
    @Override
    public boolean hasNext() {
      return current_index < size;
    }

    /**
     * move to the next element
     * @throws NoSuchElementException if you reached the end
     * @return next element
     */
    @Override
    public A next() {
      if (!hasNext()) {
        throw new NoSuchElementException("No more elements in this list");
      }
      can_remove = true;
      return (A) elements[current_index++];
    }

    /**
     * deleting an element
     * @throws IllegalStateException if there is nothing to delete
     */
    @Override
    public void remove() {
      if (!can_remove) {
        throw new IllegalStateException("Can't remove from this list");
      }
      CustomArrayList.this.remove(--current_index);
      can_remove = false;
    }
  }
}
