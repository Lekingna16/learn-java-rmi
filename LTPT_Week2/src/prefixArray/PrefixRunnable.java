package prefixArray;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

public class PrefixRunnable {

    public static void main(String[] args) throws  InterruptedException {
        final int threads = Runtime.getRuntime().availableProcessors();
        int size = 1000;
        int [] array = new int[size];
        int subRange = size / threads;

        Random random = new Random();

        for (int i = 0; i < size; i++) {
            array [i] = random.nextInt(10);
        }

        int [] prefix = new int [size];

        ExecutorService executorService = Executors.newFixedThreadPool(threads);

        List<Future<Integer>> results = new ArrayList<>();

        for (int i = 0; i < threads; i++) {
            int start = i * subRange;
            int end = (i == threads -1 ) ?  size : start + subRange;

           Callable<Integer> task = new PrefixTaskRunnable(array, prefix, start, end);
           results.add (executorService.submit(task));
        }

        int offset = 0;
        for (int i = 0; i < results.size(); i ++) {
            try {
                int sum = results.get(i).get();
                int start = i * subRange;
                int end = (i == threads - 1) ? size : subRange + start;

                for (int j = start; j < end; j++) {
                    prefix[j] += offset;
                }

                offset += sum;
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }
        }

        executorService.shutdown();
        while (!executorService.isTerminated()) {}
        for (int i = 0; i < prefix.length; i++) {
            System.out.println(array[i] + "   " + prefix[i]);
        }
    }

    }




class PrefixTaskRunnable implements Callable<Integer> {

    private int [] array;
    private int start;
    private int end;
    private int [] output;

    public PrefixTaskRunnable(int[] array, int[] output, int start, int end) {
        this.array = array;
        this.output = output;
        this.start = start;
        this.end = end;
    }



    @Override
    public Integer call() throws Exception {
        int sum = 0;
         for (int i = start; i < end; i++) {
             sum += array[i];
             output[i] = sum;
         }

         return sum;
    }
}
