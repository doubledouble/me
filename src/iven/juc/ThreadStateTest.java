package iven.juc;

import java.util.concurrent.TimeUnit;

public class ThreadStateTest {

    /**
     * @param args
     * @throws InterruptedException 
     */
    public static void main(String[] args) throws InterruptedException {
        T1 t1 = new T1();
        System.out.printf("==== before start state: %s====%n", t1.getState()); // NEW
        t1.start();
        System.out.printf("==== t1 %s====%n", t1); // NEW
        t1.myJoin();
        t1.myJoin2();
        System.out.printf("==== running state: %s====%n", t1.getState()); //RUNNABLE
        
        TimeUnit.SECONDS.sleep(3);
        
        System.out.printf("==== running state: %s====%n", t1.getState()); // RUNNABLE
        System.out.printf("==== before interrupt isAlive: %s====%n", t1.isAlive()); // true
//        t1.interrupt();
//        TimeUnit.MILLISECONDS.sleep(1); // 中断延迟
            System.out.printf("==== after interrupt state: %s====%n", t1.getState()); // TERMINATED
            System.out.printf("==== after interrupt isAlive: %s====%n", t1.isAlive()); // false
    }

}

class T1 extends Thread {

    public synchronized void myJoin() throws InterruptedException {
//        while (isAlive()) {
            System.out.println("before my join");
            System.out.println("before my join -- thread name " + Thread.currentThread().getName());            
            System.out.println("this: " + this);
            wait();
            System.out.println("after my join is alive " + isAlive());
            System.out.println("after my join");
//        }
    }
    public void myJoin2() throws InterruptedException {
//        while (isAlive()) {
        synchronized (this) {
            
        System.out.println("before my myJoin2");
        System.out.println("before my myJoin2 -- thread name " + Thread.currentThread().getName());            
        System.out.println("this: " + this);
        wait();
        System.out.println("after my myJoin2 is alive " + isAlive());
        System.out.println("after my myJoin2");
//        }
        }
    }
    
    @Override
    public void run() {
        while (!isInterrupted()) {
            for (int i=0; i < 10000; i++) {
                System.out.println(i);
            }
            break;
        }
    }
    
}