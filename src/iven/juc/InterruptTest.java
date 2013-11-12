package iven.juc;

import java.util.concurrent.TimeUnit;

/**
 * interrupt thread  <br/>
 * 1. thread 处于block(wait join sleep), throw InterruptedException, interrupt flag为false <br/> 
 * (处理情况: a. 继续throw, b. 设置interrupt status = true)<br/>
 * 2. 其他情况，见javadoc <br/>
 * at last : runnable thread 只设置interrupt flag 为true 不影响线程其他操作 
 * @author Administrator
 *
 */
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
