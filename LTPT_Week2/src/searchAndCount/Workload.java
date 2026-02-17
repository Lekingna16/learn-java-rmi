package searchAndCount;

public class Workload {

    private WorkloadType workloadType;
    private int[] array;

    public Workload(int[] array, WorkloadType workloadType) {
        this.array = array;
        this.workloadType = workloadType;
    }

    public int doWork (int start, int end, WorkloadType workloadType) {
        int count = 0;
       if (WorkloadType.LIGHT == workloadType) {
           for (int i = start; i < end; i++){
              count = array[i] != 0 ? count + 1 : count;
           }
       }
       else {
           for (int i = start; i < end; i++) {
               if (isPrime(array[i]))
                   count++;
           }
       }
       return count;
    }

    private static boolean isPrime(int value) {
        if (value < 2)
            return false;
         if (value == 2)
            return true;
        for (int i = 2; i <= Math.sqrt(value); i++){
            if (value % i == 0)
                return false;
        }
        return true;
    }


}
