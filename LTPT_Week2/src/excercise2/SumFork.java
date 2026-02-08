package excercise2;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class SumFork {
    public static void main(String[] args) {
        final int range = 100_000_000;
        final int threshold = 100_000;

        RecursiveTask<Long> recursiveTask = new SumTask1(0, range, threshold);
        ForkJoinPool pool = new ForkJoinPool();

        long result = pool.invoke(recursiveTask);

        System.out.println("Result SumFork: " + result);
        long sum = 0L;
        for (int i = 0; i < range; i++){
            sum += i;
        }

        System.out.println("Sum check: " + sum);
    }


}

class SumTask1 extends RecursiveTask<Long> {
    private int start;
    private int end;
    private int threshold;

    public SumTask1(int start, int end, int threshold) {
        this.start = start;
        this.end = end;
        this.threshold = threshold;
    }

    @Override
    protected Long compute() {
        Long sum = 0L;
        if (end - start <= threshold) {
            for (int i = start; i < end; i++) {
                sum += i;
            }

        }
        else {
            int mid = start + (end - start) / 2;
            SumTask1 leftTask = new SumTask1(start, mid, threshold);
            SumTask1 rightTask = new SumTask1(mid, end, threshold);

            leftTask.fork();
            long resultRight = rightTask.compute();
            long resultLeft = leftTask.join();

            sum = resultLeft + resultRight;

        }

        return sum;
    }
}


