package excercise3_using_lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BankingAccount {
    private Lock lock = new ReentrantLock();
    private Condition condition_withdraw = lock.newCondition();
    private  Condition condition_deposites = lock.newCondition();




    private String userName;
    private double balance;

    public BankingAccount(String userName, double balance) {
        this.userName = userName;
        this.balance = balance;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void withdraw (double amount){
        lock.lock();
        try {
            while (amount > balance){
                condition_withdraw.await();
                System.out.println("Amount > Balance");
            }
            if (amount > 0 ){
                balance -=  amount;
                System.out.println("Success! " + balance);
                condition_deposites.signal();

            }
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }

    public void deposits (double amount){
lock.lock();
try {
    while (balance + amount > 100_000) {
        try {
            System.out.println("Balance + Amount > 100_000");
            condition_deposites.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    if (amount > 0) {
        balance += amount;
        System.out.println(Thread.currentThread().getName() + " Success! " + balance);
        condition_withdraw.signal();
    }
}finally {
    lock.unlock();

}


    }
}
