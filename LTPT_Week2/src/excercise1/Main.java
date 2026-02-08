package excercise1;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;

public class Main {
    public static void main(String[] args) {

        int size = 1_000_000;
        int [] arr = new int[size];
        Random random = new Random();

        for (int i = 0; i < size; i++){
            arr[i] = random.nextInt();
        }

        int [] arrRunnable = Arrays.copyOf(arr, arr.length);
        int [] arrForkJoin = Arrays.copyOf(arr, arr.length);
        int [] arrCheck = Arrays.copyOf(arr, arr.length);

        int[] tempRunnable = new int[size];

        long startRunnable = System.currentTimeMillis();

        MergeSortRunnable runnableTask = new MergeSortRunnable(arrRunnable, tempRunnable, 0, arrRunnable.length);
        Thread thread = new Thread(runnableTask);
        thread.start();

        try {
            thread.join();
        } catch (InterruptedException e){
            e.printStackTrace();
        }


        long endRunnable = System.currentTimeMillis();

        System.out.println("Runnable MergeSort time: " + (endRunnable - startRunnable) + "ms");

        int [] tempForkJoin = new int [size];

        ForkJoinPool pool = new ForkJoinPool();

        long startForkJoin = System.currentTimeMillis();

        MergeSortForkJoin task = new MergeSortForkJoin(arrForkJoin, tempForkJoin, 0, arrForkJoin.length);

        pool.invoke(task);

        long endForkJoin = System.currentTimeMillis();

        System.out.println("Fork/Join MergeSort time: " + (endForkJoin - startForkJoin) + "ms");


        long startSingle = System.currentTimeMillis();
        Arrays.sort(arrCheck);

        long endSingle = System.currentTimeMillis();
        System.out.println("Arrays.sort time: " + (endSingle - startSingle) + "ms");

        System.out.println("Runnable check: " + Arrays.equals(arrRunnable, arrCheck));

        System.out.println("Fork/Join check: " + Arrays.equals(arrForkJoin, arrCheck));



    }
}
