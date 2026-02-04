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
            Arrays.sort(arr); // neu mang nho thi su dung sort mang nhu binh thuong
            return;
        }
        int mid = (arr.length) / 2; // chia mang ra lam 2
        int [] leftArr = new int [mid]; // khoi tao mang luu tru phan ben trai
        int [] rightArr = new int [arr.length - mid]; // khoi tao mang luu tru phan ben phai
        for (int i = 0; i < mid; i++) {
            leftArr[i] = arr[i]; // copy mang ben trai
        }

        int j = 0;
        for (int i = mid; i < arr.length; i ++){
            rightArr[j++] = arr[i]; // copy mang ben phai
        }

        MergeSortManualThread sortLeft = new MergeSortManualThread(leftArr); // de quy sort mang trai
        MergeSortManualThread sortRight = new MergeSortManualThread(rightArr); // de quy sort mang phai

        Thread thread1 = new Thread(sortLeft::sort); // goi thread thuc hien sort mang trai
        Thread thread2 = new Thread(sortRight::sort); // goi thread thuc hien sort mang phai

        thread1.start();
        thread2.start();

        try {
            thread1.join(); // goi pthuc join de thuc hien chan khong cho main thuc hien merge 2 mang lai
            thread2.join(); // khi 2 thread chua sort xong
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        merge (leftArr, rightArr); // merge 2 mang lai voi nhau
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
        // cu phap:
        // System.arraycopy (nguon, vi tri nguon, dich, vi tri dich, so phan tu);


    }

    public static void main(String[] args) {
        int [] arr = {3, 5, 1, 4, 6, 7, 2 , 5 , 4, 53 ,2, 11, 24, 19, 20, 45, 60};
        MergeSortManualThread sortManualThread = new MergeSortManualThread(arr);

        sortManualThread.sort();
        System.out.println(Arrays.toString(arr));
    }
}
