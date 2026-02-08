package excercise1;

import java.util.Arrays;

public class MergeSortRunnable implements Runnable{

    private int[] arr;
    private int[] temp;
    private int left, right;

    private static final int THRESHOLD = 50_000;

    public MergeSortRunnable(int[] arr, int[] temp, int left, int right) {
        this.arr = arr;
        this.temp = temp;
        this.left = left;
        this.right = right;
    }

    @Override
    public void run() {
        if (right - left <= THRESHOLD){
            Arrays.sort(arr, left, right);
            return;
        }

        int mid = (left + right) / 2;

        MergeSortRunnable leftTask = new MergeSortRunnable(arr, temp, left, mid);
        MergeSortRunnable rightTask = new MergeSortRunnable(arr, temp, mid, right);

        Thread thread1 = new Thread(leftTask);
        Thread thread2 = new Thread(rightTask);

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e){
            e.printStackTrace();
        }

        merge (mid);
    }

    private void merge(int mid) {

        int i = left, j = mid, k = left;

        while (i < mid && j < right){
            temp[k++] = (arr[i] < arr[j] ? arr[i++] : arr[j++]);

        }

        while (i  < mid) temp[k++] = arr[i++];
        while (j < right) temp[k++] = arr[j++];

        System.arraycopy(temp, left, arr, left, right - left);
    }
}
