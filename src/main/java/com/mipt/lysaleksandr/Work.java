package com.mipt.lysaleksandr;

public abstract class Work {

  public abstract void work(int money);

  public boolean goHome(String string1, String string2) {
    return string1.equals(string2);
  }
}