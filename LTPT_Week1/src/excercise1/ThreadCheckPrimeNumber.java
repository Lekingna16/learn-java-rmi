package excercise1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ThreadCheckPrimeNumber {
    public static void main(String[] args) {

        // init array number to check prime number
        int [] numbers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};

        // create executorService to create threads to check prime number
        ExecutorService executorService = Executors.newFixedThreadPool(4);

        // create list future task to save result thread
        List<FutureTask<String>> futureTasks = new ArrayList<>();

        // loop to every elements to check prime number
        for (int i = 0; i < numbers.length; i++){

            // create task
          Callable<String> task = new CheckedPrimeNumber(numbers[i]);

          // executorServer submit will be return Future -> create Future to save
          Future<String> future = executorService.submit(task);

          // add result in list futureTasks
          futureTasks.add((FutureTask<String>) future);
        }

        // Finish, must shutdown()
        executorService.shutdown();

        // check threads complete
        while (!executorService.isTerminated()){}

        // print result to check
       futureTasks.stream()
               .forEach(e -> {
                   try {
                       System.out.println(e.get());
                   } catch (InterruptedException ex) {
                       throw new RuntimeException(ex);
                   } catch (ExecutionException ex) {
                       throw new RuntimeException(ex);
                   }
               });
    }


}


class CheckedPrimeNumber implements Callable <String> {
    private int number;

    public CheckedPrimeNumber(int number) {
        this.number = number;
    }

    @Override
    public String call() throws Exception {
        if (number < 2)
            return Thread.currentThread().getName() + " " + number + " isn't prime number";
        if (number == 2)
            return Thread.currentThread().getName() + " " + number + " is prime number";
        for (int i = 2; i <= Math.sqrt(number); i++){
            if (number % i == 0)
                return Thread.currentThread().getName() + " " + number + " isn't prime number";
        }
        return Thread.currentThread().getName() + " " + number + " is prime number";
    }
}
