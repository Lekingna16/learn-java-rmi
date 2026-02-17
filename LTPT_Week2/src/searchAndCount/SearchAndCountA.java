package searchAndCount;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class SearchAndCountA {

    public static void main(String[] args) {
        int size = 1_000_000;
        int[] array = new int[size];
        int theshold = 100_000;

        Random random = new Random();

        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt();
        }


        WorkloadType workloadType = WorkloadType.HEAVY;
        Workload workload = new Workload(array, workloadType);

        long start = System.currentTimeMillis();

        UsingForkJoin task = new UsingForkJoin(0, size, theshold, workload, workloadType);
        ForkJoinPool pool = new ForkJoinPool();
        int resultFork = pool.invoke(task);
        System.out.println(resultFork);

        long end = System.currentTimeMillis();
        long totalTime = end - start;
        System.out.println("Time execute by Fork/Join: " + totalTime);

        start = System.currentTimeMillis();
        int result = workload.doWork(0, size, workloadType);
        end = System.currentTimeMillis();
        totalTime = end - start;
        System.out.println(result);
        System.out.println("Time execute by sequence: " + totalTime);
    }


}

class UsingForkJoin extends RecursiveTask<Integer> {
    private int[] array;
    private int ThresHold;
    private int start;
    private int end;
    private Workload workLoad;
    private WorkloadType workloadType;

    public UsingForkJoin(int start, int end, int thresHold, Workload workLoad, WorkloadType workloadType) {
        this.start = start;
        this.end = end;
        this.ThresHold = thresHold;
        this.workLoad = workLoad;
        this.workloadType = workloadType;

    }

    @Override
    protected Integer compute() {
        if ((end - start) <= ThresHold) {
            return workLoad.doWork(start, end, workloadType);
        }

        int mid = (end + start) / 2;

        UsingForkJoin leftTask = new UsingForkJoin(start, mid, ThresHold, workLoad, workloadType);
        UsingForkJoin rightTask = new UsingForkJoin(mid, end, ThresHold, workLoad, workloadType);

        leftTask.fork();
        int rightResult = rightTask.compute();
        int leftResult = leftTask.join();

        return rightResult + leftResult;

    }


}
