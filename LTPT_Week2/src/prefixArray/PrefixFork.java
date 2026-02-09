package prefixArray;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class PrefixFork {

    public static void main(String[] args) {
        final int THRES_HOLD = 100;
        int size = 1000;
        int [] input = new int [size];
        int [] output = new int [size];

        Random random = new Random();

        for (int i = 0;  i < size; i++) {
            input[i] = random.nextInt(10);
        }

        ForkJoinPool pool = new ForkJoinPool();

        PrefixTaskFork prefixTaskFork = new PrefixTaskFork(input, output, THRES_HOLD, 0, size);

        pool.invoke(prefixTaskFork);

        for (int i = 0; i < output.length; i++) {
            System.out.println(input[i] + "  " + output[i]);
        }


    }
}

class PrefixTaskFork extends RecursiveTask<Integer> {

    private int thres_hold;
    private int [] input;
    private int [] output;
    private int start;
    private int end;

    public PrefixTaskFork(int[] input, int[] output, int thres_hold, int start, int end) {
        this.input = input;
        this.output = output;
        this.thres_hold = thres_hold;
        this.start = start;
        this.end = end;

    }

    @Override
    protected Integer compute() {
        int sum = 0;
        if ((end - start) < thres_hold) {
            for (int i = start; i < end; i ++) {
                sum += input[i];
                output[i] = sum;
            }

        }

        else {
            int mid = start + (end - start) / 2;
            PrefixTaskFork leftTask = new PrefixTaskFork(input, output, thres_hold, start, mid);
            PrefixTaskFork rightTask = new PrefixTaskFork(input, output, thres_hold, mid, end);

            leftTask.fork();
            int rightResult = rightTask.compute();
            int leftResult = leftTask.join();


            for (int i = mid; i < end; i++) {
                output[i] += leftResult;
            }
            sum = leftResult + rightResult;

        }

        return sum;
    }
}
