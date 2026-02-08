package excercise1;

import java.util.Arrays;
import java.util.concurrent.RecursiveAction;

public class MergeSortForkJoin extends RecursiveAction {

    private static final int THRESHOLD = 50_000;

    private int[] arr;
    private int[] temp;
    private int left, right;

    public MergeSortForkJoin(int[] arr, int[] temp, int left, int right) {
        this.arr = arr;
        this.temp = temp;
        this.left = left;
        this.right = right;
    }

    @Override
    protected void compute() {
        if (right - left <= THRESHOLD){
            Arrays.sort(arr, left, right);
            return;
        }

        int mid =  (left + right) / 2;

        MergeSortForkJoin leftTask = new MergeSortForkJoin(arr, temp, left, mid);
        MergeSortForkJoin rightTask = new MergeSortForkJoin(arr, temp, mid, right);

        invokeAll(leftTask, rightTask);

        merge(mid);
    }

    private void merge(int mid) {
        int i = left, j = mid, k = left;

        while (i < mid && j < right){
            temp[k++] = (arr[i] <=  arr[j]) ? arr[i++] : arr[j++];
        }

        while (i < mid) temp[k++] = arr[i++];
        while (j < right) temp[k++] = arr[j++];

        System.arraycopy(temp, left, arr, left, right - left);
    }
}
