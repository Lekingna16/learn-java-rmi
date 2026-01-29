package excercise1;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadDislayNumber {

    public static void main(String[] args) {

        Runnable task = new CreatingTaskDisplayNumber(200);

        ExecutorService executorService = Executors.newFixedThreadPool(10);

        for (int i = 1; i <= 10; i++){
            executorService.submit(task);
        }
        executorService.shutdown();

        while (!executorService.isTerminated()){}




    }
}

class CreatingTaskDisplayNumber implements Runnable {

    private int number;

    public CreatingTaskDisplayNumber( int number) {
        this.number = number;
    }

    public CreatingTaskDisplayNumber() {
    }

    @Override
    public void run() {
        for (int i = 0; i <= number; i ++){
            System.out.println(Thread.currentThread().getName() + " " + i);
        }
    }
}