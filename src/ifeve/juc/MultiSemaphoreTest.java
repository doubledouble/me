package ifeve.juc;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MultiSemaphoreTest {
    
    public static void main(String[] args) {
        PrintQuene quene = new PrintQuene();
        Thread[] threads = new Thread[10];
        for (int i=0; i<10; i++) {
            threads[i] = new Thread(new Job2(quene), "Thread " + i);
        }
        for (int i=0; i<10; i++) {
            threads[i].start();
        }
    }

}

class PrintQuene {
    private boolean freePrinters[];
    private final Semaphore semaphore;
    private Lock lockPrinters;
    
    public PrintQuene() {
        freePrinters = new boolean[3];
        for (int i=0; i<3; i++) {
            freePrinters[i] = true;
        }
        semaphore = new Semaphore(3);
        lockPrinters = new ReentrantLock();
    }
    
    
    public void printObj(Object document) {
        try {
            semaphore.acquire();
            int assignedPrinter = getPrinters();
            long duration = (long) (Math.random() * 10);
            System.out.printf("%s: PrintQueue: Printing a Job in Printer%d during %d seconds\n",Thread.currentThread().getName(), assignedPrinter,duration);
            Thread.sleep(duration);
            freePrinters[assignedPrinter] = true; 
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore.release();
        }
    }
    
    public int getPrinters() {
        int ret = 0;
        try {
            lockPrinters.lock();
            for (int i=0; i<3; i++) {
                if (freePrinters[i]) {
                    ret = i;
                    freePrinters[i] = false;
                    break;
                }
            }
        } finally {
            lockPrinters.unlock();
        }
        return ret;
    }
}

class Job2 implements Runnable {
    
    private PrintQuene printQueue;
    
    public Job2(PrintQuene printQueue) {
            this.printQueue = printQueue;
    }
    
    @Override
    public void run() {
        System.out.printf("%s: Going to print a job\n",Thread. currentThread().getName());
        printQueue.printObj(new Object());
        System.out.printf("%s: The document has been printed\n",Thread.currentThread().getName());
    }
   
  }
