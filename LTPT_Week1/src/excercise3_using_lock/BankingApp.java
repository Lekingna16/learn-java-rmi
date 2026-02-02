package excercise3_using_lock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BankingApp {
    public static void main(String[] args) {
        BankingAccount account = new BankingAccount("Kim Ngan", 10000);

        ExecutorService executorService = Executors.newFixedThreadPool(4);

        for (int i = 0; i < 10; i++){
            executorService.submit(() -> account.withdraw(100));
        }

        executorService.shutdown();
        while (!executorService.isTerminated()) {}

        System.out.println("Finished!");
    }
}
