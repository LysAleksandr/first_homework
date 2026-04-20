package com.mipt.lysaleksandr.bank;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.BeforeEach;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.*;

public class BankTest {

  private Bank bank;
  private BankAccount account1;
  private BankAccount account2;

  @BeforeEach
  public void setup() {
    bank = new Bank();
    account1 = new BankAccount(1, 1000000);
    account2 = new BankAccount(2, 1000000);
  }

  @Test
  @Timeout(3)
  void testDeadlock() throws InterruptedException {
    Thread t1 = new Thread(() -> {
      try {
        bank.sendToAccountDeadlock(account1, account2, 100000);
      } catch (Exception e) {
      }
    });
    Thread t2 = new Thread(() -> {
      try {
        bank.sendToAccountDeadlock(account2, account1, 100000);
      } catch (Exception e) {
      }
    });
    t1.start();
    t2.start();
    t1.join(1000);
    t2.join(1000);
    assertFalse(t1.isAlive() && t2.isAlive());
    t1.interrupt();
    t2.interrupt();
  }

  @Test
  void testTransfer() throws InsufficientFundsException {
    bank.sendToAccount(account1, account2, 500000);
    assertEquals(500000, account1.getBalance());
    assertEquals(1500000, account2.getBalance());
  }

  @Test
  void testInsufficientFundsThrowsException() {
    InsufficientFundsException exception = assertThrows(
        InsufficientFundsException.class,
        () -> bank.sendToAccount(account1, account2, 1500000)
    );
    assertEquals("Insufficient funds", exception.getMessage());
    assertEquals(1000000, account1.getBalance());
  }

  @Test
  void testConcurrentTransfers() throws InterruptedException {
    ExecutorService executor = Executors.newFixedThreadPool(4);
    int transfersCount = 20;
    for (int i = 0; i < transfersCount; i++) {
      final int amount = 100000;
      executor.submit(() -> {
        try {
          if (ThreadLocalRandom.current().nextBoolean()) {
            bank.sendToAccount(account1, account2, amount);
          } else {
            bank.sendToAccount(account2, account1, amount);
          }
        } catch (InsufficientFundsException ignored) {
        }
      });
    }
    executor.shutdown();
    assertTrue(executor.awaitTermination(5, TimeUnit.SECONDS));
    int totalBalance = account1.getBalance() + account2.getBalance();
    assertEquals(2000000, totalBalance);
  }
}