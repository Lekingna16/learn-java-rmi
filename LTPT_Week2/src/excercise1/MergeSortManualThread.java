package excercise1;

import java.util.Arrays;

public class MergeSortManualThread {
    private int [] arr;
    private static final int THREAD_HOLD = 10;

    public MergeSortManualThread(int[] arr) {
        this.arr = arr;
    }

    private void sort () {
        if (arr.length <= THREAD_HOLD) {
            Arrays.sort(arr);
            return;
        }
        int mid = (arr.length) / 2;
        int [] leftArr = new int [mid];
        int [] rightArr = new int [arr.length - mid];
        for (int i = 0; i < mid; i++) {
            leftArr[i] = arr[i];
        }

        int j = 0;
        for (int i = mid; i < arr.length; i ++){
            rightArr[j++] = arr[i];
        }

        MergeSortManualThread sortLeft = new MergeSortManualThread(leftArr);
        MergeSortManualThread sortRight = new MergeSortManualThread(rightArr);

        Thread thread1 = new Thread(sortLeft::sort);
        Thread thread2 = new Thread(sortRight::sort);

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        merge (leftArr, rightArr);
    }

    private void merge (int [] leftArr, int [] rightArr) {

        int i = 0, j = 0, k = 0;
        int [] result = new int [leftArr.length + rightArr.length];

        while (i < leftArr.length && j < rightArr.length){
            if (leftArr[i] < rightArr [j]){
                result[k++] = leftArr[i++];
            }
            else result[k++] = rightArr[j++];
        }
        while (i < leftArr.length) result[k++] = leftArr[i++];
        while (j < rightArr.length) result[k++] = rightArr[j++];

        System.arraycopy(result, 0, arr, 0, result.length);

    }

    public static void main(String[] args) {
        int [] arr = {3, 5, 1, 4, 6, 7, 2 , 5 , 4, 53 ,2, 11, 24, 19, 20, 45, 60};
        MergeSortManualThread sortManualThread = new MergeSortManualThread(arr);

        sortManualThread.sort();
        System.out.println(Arrays.toString(arr));
    }
}
