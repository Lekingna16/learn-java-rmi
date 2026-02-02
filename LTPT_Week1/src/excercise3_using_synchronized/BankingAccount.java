package excercise3_using_synchronized;

public class BankingAccount {


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

    public synchronized void withdraw (double amount){
        while (amount > balance){
            try {
                System.out.println("Balance < Amount");
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        if (amount < balance && amount > 0){
            balance -= amount;
            System.out.println(Thread.currentThread().getName() + " Success!" + balance);
            notifyAll();
        }

    }

    public synchronized void deposits (double amount){

        while (balance + amount > 100_000){
            try {
                System.out.println("Balance + Amount > 100_000");
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        if (amount > 0){
            balance += amount;
            System.out.println(Thread.currentThread().getName()+ " Success! " + balance);
            notifyAll();
        }

    }
}
