package excercise1;

public class ThreadUsingThreads {
    public static void main(String[] args) {

        Thread thread1 = new CreatingTasks("Thread 1", 1000);
        Thread thread2 = new CreatingTasks("Thread 2", 1000);

        thread1.start();
        thread2.start();

    }


}

class CreatingTasks extends Thread {
    private String threadName;
    private int count;

    public CreatingTasks(String threadName, int count) {
        this.threadName = threadName;
        this.count = count;
    }

    @Override
    public void run() {
        for (int i = 0; i < count; i++){
            System.out.println(threadName + " " + i);
        }
    }
}