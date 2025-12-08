package com.mipt.lysaleksandr.bank;

public class BankAccount {

  private final int id;
  int balance;

  public BankAccount(int id, int initialBalance) {
    this.id = id;
    this.balance = initialBalance;
  }

  public int getId() {
    return id;
  }

  public int getBalance() {
    return balance;
  }

  @Override
  public String toString() {
    return "Account[id=" + id + ", balance=" + balance + "]";
  }
}

class InsufficientFundsException extends Exception {

  public InsufficientFundsException(String message) {
    super(message);
  }
}
