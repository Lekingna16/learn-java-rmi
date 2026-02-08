package excercise2;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class SumRangeApp {
    public static void main(String[] args) throws InterruptedException {
        int range = 1_000_000;
        int numberTask = 10;
        int subRange = range / numberTask;

        List<Callable<Long>> tasks = new ArrayList<>();

        for (int i = 0; i < numberTask; i++){
            int start = subRange * i;
            int end = (i == numberTask - 1 ? range : start + subRange);
            Callable<Long> task = new SumTask(start, end);

            tasks.add(task);

        }

        ExecutorService executorService = Executors.newCachedThreadPool();

        List<Future<Long>> results = executorService.invokeAll(tasks);
        Long total = results.stream()
                .mapToLong(result -> {
                    try{
                        return result.get();
                    } catch (InterruptedException | ExecutionException e){
                        e.printStackTrace();
                    }
                    return 0L;
                })
                .sum();
        executorService.shutdown();
        while (!executorService.isTerminated());


        System.out.println("SumRunnable: " + total);

        Long sum = 0L;
        for (int i = 0; i < range; i++){
            sum += i;
        }

        System.out.println("SumCheck: " + sum);

    }







}


class SumTask implements Callable<Long> {
    private int start;
    private int end;

    public SumTask(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public Long call() throws Exception {
        Long sum = 0L;
        for (int i = start; i < end; i++){
            sum += i;
        }
        return sum;
    }
}
