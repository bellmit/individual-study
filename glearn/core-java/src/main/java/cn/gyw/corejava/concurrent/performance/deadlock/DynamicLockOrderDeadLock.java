package cn.gyw.corejava.concurrent.performance.deadlock;

import java.util.Random;

/**
 * 动态锁顺序死锁
 */
public class DynamicLockOrderDeadLock {

    private final static Object tieLock = new Object();

    /**
     * 通过锁顺序来避免死锁
     *
     * @param fromAccount
     * @param toAccount
     * @param amount
     */
    public void transferMoney2(Account fromAccount,
                               Account toAccount,
                               double amount) {
        class Helper {
            public void transfer() {
                if (fromAccount.getBalance() < amount) {
                    throw new RuntimeException("amount less");
                } else {
                    fromAccount.debit(amount);
                    toAccount.credit(amount);
                }
            }
        }

        int fromHash = System.identityHashCode(fromAccount);
        int toHash = System.identityHashCode(toAccount);

        if (fromHash < toHash) {
            synchronized (fromAccount) {
                synchronized (toAccount) {
                    new Helper().transfer();
                }
            }
        } else if (toHash < fromHash) {
            synchronized (toAccount) {
                synchronized (fromAccount) {
                    new Helper().transfer();
                }
            }
        } else {
            // 两个对象拥有相同的hash值
            synchronized (tieLock) {
                synchronized (fromAccount) {
                    synchronized (toAccount) {
                        new Helper().transfer();
                    }
                }
            }
        }
    }

    /**
     * 动态顺序死锁
     * <p>
     * 线程一：从A账户 -》 B账户
     * 线程二：从B账户 -》 A账户
     *
     * @param fromAccount
     * @param toAccount
     */
    public void transferMoney1(Account fromAccount,
                               Account toAccount,
                               double amount) {
        synchronized (fromAccount) {
            synchronized (toAccount) {
                if (fromAccount.getBalance() < amount) {
                    throw new RuntimeException("amount less");
                } else {
                    fromAccount.debit(amount);
                    toAccount.credit(amount);
                }
            }
        }
    }

    class Account {

        public double getBalance() {

            return 10000000D;
        }

        public void debit(double amount) {

        }

        public void credit(double amount) {

        }
    }

    public static void main(String[] args) {
        final DynamicLockOrderDeadLock demo = new DynamicLockOrderDeadLock();
        int numThreads = 20;
        int numAccount = 5;
        int numIterations = 100000;

        final Random rnd = new Random();
        final Account[] accounts = new Account[numAccount];

        for (int i = 0; i < numAccount; i++) {
            accounts[i] = demo.new Account();
        }

        class TransferThread extends Thread {
            @Override
            public void run() {
                for (int i = 0; i < numIterations; i++) {
                    int fromAcct = rnd.nextInt(numAccount);
                    int toAcct = rnd.nextInt(numAccount);

                    double balance = 1000;
                    demo.transferMoney1(accounts[fromAcct],
                            accounts[toAcct],
                            balance);
                }
            }
        }

        for (int i = 0 ; i < numThreads ; i ++) {
            new TransferThread().start();
        }
    }
}
