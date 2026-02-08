package excercise4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CountValuesRunnable {

    public static void main(String[] args) throws InterruptedException {
        int size = 1_000_000;
        int numberTask = 10;
        int subTask = size / numberTask;

        int [] array = new int[size];
        Random random = new Random();

        for (int i = 0; i < size; i++){
            array[i] = random.nextInt();
        }

        int [] arrayCheck = Arrays.copyOf(array, array.length);

        ExecutorService executorService = Executors.newCachedThreadPool();
        List<Callable<Integer>> tasks = new ArrayList<>();

        for (int i = 0; i < numberTask; i++){
            int start = i * subTask;
            int end = (i == numberTask - 1) ? size : start + subTask;

            Callable<Integer> task = new CountValueRunnable(start, end, array);
            tasks.add(task);
        }

        List<Future<Integer>> results = executorService.invokeAll(tasks);

        Integer counts = results.stream()
                .mapToInt(result -> {
                    try {
                        return result.get();
                    } catch (Exception e ){
                        e.printStackTrace();
                    }
                    return 0;
                })
                .sum();

        executorService.shutdown();
        while (!executorService.isTerminated()){}

        System.out.println("Total numbers > 100 with runnable: " + counts);

        int countCheck = 0;
        for (int i = 0; i < size; i++){
            if (arrayCheck[i] > 100){
                countCheck++;
            }
        }
        System.out.println("Total numbers > 100 with check: " + countCheck);
    }




}

class CountValueRunnable implements Callable<Integer>{
    private int start;
    private int end;
    private int [] array;

    public CountValueRunnable(int start, int end, int[] array) {
        this.start = start;
        this.end = end;
        this.array = array;
    }

    @Override
    public Integer call() throws Exception {
        int counts = 0;
        for (int i = start; i < end; i++) {
            if (array[i] > 100){
                counts ++;
            }
        }
        return counts;
    }
}
