package excercise3;

import java.util.*;
import java.util.concurrent.*;

public class MaxValueRunnable {
    public static void main(String[] args) throws InterruptedException {
        int size = 1_000_000;
        int [] arr = new int[size];
        int numTask = 10;
        int subRange = size / numTask;

        Random random = new Random();

        for (int i = 0; i < size; i++) {
            arr[i] = random.nextInt();
        }

        int [] arrayCopy = Arrays.copyOf(arr, arr.length);

        ExecutorService executorService = Executors.newCachedThreadPool();
        List<Callable<Integer>> tasks = new ArrayList<>();

        for (int i = 0; i < numTask; i ++){
            int start = i * subRange;
            int end = (i == numTask - 1) ? size : start + subRange;
            Callable<Integer> task = new FindMaxTask(start, end, arrayCopy);
            tasks.add(task);
        }

        List<Future<Integer>> results = executorService.invokeAll(tasks);

        OptionalInt maxValue = results.stream()
                .mapToInt(result -> {
                    try{
                        return result.get();
                    } catch (InterruptedException | ExecutionException e){
                        e.printStackTrace();
                    }
                    return 0;
                })
                        .max();

        executorService.shutdown();
        while (!executorService.isTerminated()){}

        System.out.println("Max value with Runnable: " + maxValue.getAsInt());
        int max = arrayCopy[0];
        for (int i = 0; i < arrayCopy.length; i++){
            if (max < arrayCopy[i])
                max = arrayCopy[i];
        }
        System.out.println("Max value with check: " + max);
    }
}

class FindMaxTask implements Callable<Integer>{
    private int start;
    private int end;
    private int [] array;

    public FindMaxTask(int start, int end, int [] array) {
        this.array = array;
        this.start = start;
        this.end = end;
    }

    @Override
    public Integer call() throws Exception {
        int max = array[start];

        for (int i = start; i < end; i++){
            if (array[i] > max)
                max = array[i];
        }
        return max;

    }
}
