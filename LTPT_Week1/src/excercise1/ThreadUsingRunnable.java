package excercise1;


import javax.swing.plaf.TableHeaderUI;

public class ThreadUsingRunnable {
    public static void main(String[] args) {

        Runnable task1 = new CreatingThread(1000, "Thread 1");
        Runnable task2 = new CreatingThread(1000, "Thread 2");

        new Thread(task1).start();
        new Thread(task2).start();


    }
}

class CreatingThread implements Runnable {

    private String threadName;
    private int count;

    public CreatingThread(int count, String threadName) {
        this.count = count;
        this.threadName = threadName;
    }

    public CreatingThread() {
    }

    public String getThreadName() {
        return threadName;
    }

    public void setThreadName(String threadName) {
        this.threadName = threadName;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public void run() {
        for (int i = 0; i < count; i++){
            System.out.println(threadName + " " + i);
        }
    }
}
