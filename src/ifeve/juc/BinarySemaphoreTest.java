package ifeve.juc;

import java.util.concurrent.Semaphore;


public class BinarySemaphoreTest {


    public static void main(String[] args) {
        SinglePrintQueue quene = new SinglePrintQueue();
        Thread[] threads = new Thread[10];
        for (int i=0; i<10; i++) {
            threads[i] = new Thread(new Job(quene), "Thread " + i);
        }
        for (int i=0; i<10; i++) {
            threads[i].start();
        }
    }

}

class SinglePrintQueue {
    
    private final Semaphore semaphore ;
    
    public SinglePrintQueue() {
        semaphore = new Semaphore(1);
    }
    
    public void printObj(Object document) {
        try {
            semaphore.acquire();
            long duration = (long) (Math.random() * 10);
            System.out.printf("%s: PrintQueue: Printing a Job during %d seconds\n",Thread.currentThread().getName(),duration);
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore.release();
        }
    }
}

class Job implements Runnable {
    
    private SinglePrintQueue printQueue;
    
    public Job(SinglePrintQueue printQueue) {
            this.printQueue = printQueue;
    }
    
    @Override
    public void run() {
        System.out.printf("%s: Going to print a job\n",Thread. currentThread().getName());
        printQueue.printObj(new Object());
        System.out.printf("%s: The document has been printed\n",Thread.currentThread().getName());
    }
    
}