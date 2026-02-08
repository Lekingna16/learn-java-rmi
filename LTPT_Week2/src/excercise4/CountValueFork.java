package excercise4;


import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class CountValueFork {

    public static void main(String[] args) {
         int size = 1_000_000;
         int [] array = new int[size];
         int thres_hold = 10_000;
         Random random = new Random();

         for (int i = 0; i < size; i++) {
             array[i] = random.nextInt();
         }

         int [] arrayCheck = Arrays.copyOf(array, array.length);

        ForkJoinPool pool = new ForkJoinPool();

        CountValue countValue = new CountValue(0, size, thres_hold, array);

        int result = pool.invoke(countValue);

        System.out.println("Result with Fork: " + result);

        int result_check = 0;
        for (int i = 0; i < arrayCheck.length; i++) {
            if (arrayCheck[i] > 100)
                result_check ++;
        }

        System.out.println("Result with check: " + result_check);


    }
}

class CountValue extends RecursiveTask<Integer>{

    private int start;
    private int end;
    private int thres_hold;
    private int [] arr;

    public CountValue(int start, int end, int thres_hold, int[] arr) {
        this.start = start;
        this.end = end;
        this.thres_hold = thres_hold;
        this.arr = arr;
    }

    @Override
    protected Integer compute() {
        Integer count = 0;
        if (end - start <= thres_hold){
            for (int i = start; i < end; i++){
                if (arr[i] > 100)
                    count++;
            }
        }
        else {
            int mid = start + (end - start) / 2;
            CountValue leftCount = new CountValue(start, mid, thres_hold, arr);
            CountValue rightCount = new CountValue(mid, end, thres_hold, arr);

            leftCount.fork();
            int rightResult = rightCount.compute();
            int leftResult = leftCount.join();

            count = rightResult + leftResult;
        }
        return count;
    }
}
