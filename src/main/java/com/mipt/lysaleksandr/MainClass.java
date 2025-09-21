package com.mipt.lysaleksandr;

public class MainClass {
  private Integer thingInt;
  private String thingString;
  protected static double thingDouble;
  public final long thingLong = 69;
  public static void main(String[] args) {
    for (int ind = 0; ind < 15; ind++) {
      System.out.println("Iter: " + ind);
    }
  }

}
