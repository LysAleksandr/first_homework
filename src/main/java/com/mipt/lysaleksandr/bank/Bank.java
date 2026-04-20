package com.mipt.lysaleksandr.bank;

public class Bank {

  public void sendToAccountDeadlock(BankAccount from, BankAccount to, int amount)
      throws InsufficientFundsException, InterruptedException {
    synchronized (from) {
      synchronized (to) {
        if (from.balance < amount) {
          throw new InsufficientFundsException("Insufficient funds");
        }
        from.balance -= amount;
        to.balance += amount;
      }
    }
  }

  public void sendToAccount(BankAccount from, BankAccount to, int amount)
      throws InsufficientFundsException {
    BankAccount first = from.getId() < to.getId() ? from : to;
    BankAccount second = from.getId() < to.getId() ? to : from;
    synchronized (first) {
      synchronized (second) {
        if (from.balance < amount) {
          throw new InsufficientFundsException("Insufficient funds");
        }
        from.balance -= amount;
        to.balance += amount;
      }
    }
  }
}
