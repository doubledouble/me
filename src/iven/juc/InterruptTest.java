package iven.juc;

import java.util.concurrent.TimeUnit;

public class InterruptTest {

    /**
     * @param args
     */
    public static void main(String[] args) {
        InterruptObj wo = new InterruptObj();
        wo.setName("interrupt-obj-thread");
        wo.start();
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
        }
        wo.interrupt(); // interrupted by main-thread
        
//        
//        NotifyThread nt = new NotifyThread(wo);  
//        nt.start();  // interrupted by notify-thread
        
    }

}

class InterruptObj extends Thread {
    
    public synchronized void notAlive() {
        synchronized (this) {
            try {
                System.out.println(" before : isInterrupted : " + isInterrupted()); // false
                System.out.println(" before interrupt, thread is alive : " + isAlive()); //true
                wait();
            } catch (InterruptedException e) {
                System.out.println(" after : isInterrupted : " + isInterrupted());  // false
                System.out.println("after interrupt, thread is alive : " + isAlive());// true
                e.printStackTrace();
            }
        }
    }
    
    public void stillAlive() {
        System.out.println(" before : thread is alive : " + isAlive()); // true
        System.out.println(" before : isInterrupted : " + isInterrupted()); // false
        interrupt();
        System.out.println(" after : isInterrupted : " + isInterrupted());  // true
        System.out.println(" after is alive : " + isAlive()); //true
        interrupted();  //reset flag
        System.out.println(" reset isInterrupted : " + isInterrupted()); //false
        interrupted();  //reset flag again
        System.out.println(" reset again isInterrupted : " + isInterrupted()); //false
    }

    @Override
    public void run() {
//        stillAlive();
        notAlive();
    }
    
}

class NotifyThread extends Thread {
    
    private InterruptObj obj;
    
    public NotifyThread(InterruptObj obj) {
        this.obj = obj;
    }
    
    @Override
    public void run() {
        obj.interrupt();
    }
    
    
}
