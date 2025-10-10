package com.mipt.lysaleksandr.generici;


public class Pair<K, V> {

  private K key;
  private V value;

  public Pair(K key, V value) {
    this.key = key;
    this.value = value;
  }

  public K getKey() {
    return key;
  }

  public void setKey(K key) {
    this.key = key;
  }

  public V getValue() {
    return value;
  }

  public void setValue(V value) {
    this.value = value;
  }

  public Pair<V, K> swap() {
    return new Pair<>(value, key);
  }

  @Override
  public String toString() {
    return "Pair{key=" + key + ", value=" + value + "}";
  }

  public static void main(String[] args) {
    final Pair<String, Integer> pair1 = new Pair<>("Age", 25);
    System.out.println(pair1);
    final Pair<Integer, String> swapped1 = pair1.swap();
    System.out.println(swapped1);

    final Pair<String, String> pair2 = new Pair<>(null, null);
    System.out.println(pair2);
    final Pair<String, String> swapped2 = pair2.swap();
    System.out.println(swapped2);

    final Pair<String, String> pair3 = new Pair<>(null, "check");
    System.out.println(pair3);
    final Pair<String, String> swapped3 = pair3.swap();
    System.out.println(swapped3);

    final Pair<String, String> pair4 = new Pair<>("check", null);
    System.out.println(pair4);
    final Pair<String, String> swapped4 = pair4.swap();
    System.out.println(swapped4);
  }

}
