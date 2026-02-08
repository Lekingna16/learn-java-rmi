package excercise3;

import java.util.*;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class MaxValueFork {

    public static void main(String[] args) {
        int size = 1_000_000;
        int [] arr = new int[size];
        int thres_hold = 10_000;
        Random random = new Random();

        for (int i = 0; i < size; i++){
            arr[i] = random.nextInt();
        }

        int [] arrCopy = Arrays.copyOf(arr, arr.length);

        MaxValue maxValueTask = new MaxValue(arr, thres_hold, 0, size);

        ForkJoinPool pool = new ForkJoinPool();
        Integer maxValue = pool.invoke(maxValueTask);

        System.out.println("Max value using fork: " + maxValue);

        int max = arrCopy [0];
        for (int i = 1; i < size; i ++){
            if (max < arr[i])
                max = arr[i];
        }

        System.out.println("Max value: " + max);

    }
}

class MaxValue extends RecursiveTask<Integer>{


    private int [] array;
    private int thread_hold;
    private int start;
    private int end;

    public MaxValue( int[] array, int thread_hold, int start, int end) {
       this.thread_hold = thread_hold;
        this.array = array;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        int max = array [start];
        if (end - start <= thread_hold) {
            for (int i = start + 1; i < end; i++){
                if (array[i] > max){
                    max = array[i];
                }
            }
        }

        else {
            int mid = start + (end - start) / 2;
            MaxValue leftValue = new MaxValue(array, thread_hold, start, mid);
            MaxValue rightValue = new MaxValue(array, thread_hold, mid, end);

            leftValue.fork();
            Integer resultRight = rightValue.compute();
            Integer resultLeft = leftValue.join();

            return (resultLeft > resultRight) ? resultLeft : resultRight;
        }
        return max;
    }
}
