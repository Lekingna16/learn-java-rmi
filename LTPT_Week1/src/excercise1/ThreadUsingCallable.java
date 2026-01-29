package excercise1;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class ThreadUsingCallable {
    public static void main(String[] args) {

        Callable<Long> task1 = new CreatingTask("Thread 1", 1000);
        Callable<Long> task2 = new CreatingTask("Thread 2", 2000);

        FutureTask<Long> futureTask1 = new FutureTask<>(task1);
        FutureTask<Long> futureTask2 = new FutureTask<>(task2);

        Thread thread1 = new Thread(futureTask1);
        Thread thread2 = new Thread(futureTask2);

        thread1.start();
        thread2.start();

        try {
            Long result_1 = futureTask1.get();
            Long result_2 = futureTask2.get();
            System.out.println("Result 1: "+ result_1);
            System.out.println("Result 2: " + result_2);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }


    }
}

class CreatingTask implements Callable<Long> {

    private String threadName;
    private int count;

    public CreatingTask(String threadName, int count) {
        this.threadName = threadName;
        this.count = count;
    }

    public CreatingTask() {
    }

    public String getThreadName() {
        return threadName;
    }

    public void setThreadName(String threadName) {
        this.threadName = threadName;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public Long call() throws Exception {
        long sum = 0L;
        for (int i = 0; i <= count; i++) {
            sum += i;
        }
        return sum;
    }
}
