package iven.juc;

import java.util.concurrent.TimeUnit;

public class WaitingTest {

    
    /**
     * @param args
     * @throws InterruptedException 
     */
    public static void main(String[] args) throws InterruptedException {
        Obj o = new Obj();
//        o.testWait2(); //IllegalMonitorStateException
//        o.testWait4(); //IllegalMonitorStateException
        Thread t1 = new Thread(o, "obj-thread");
        t1.start();
        
        TimeUnit.SECONDS.sleep(5);
        
        Thread notifyInst = new Thread(new NotifyObj(o), "notify-inst-thread");
        notifyInst.start();
        notifyInst.join();
        
        TimeUnit.SECONDS.sleep(5);
        
        Thread notifyInnerLock = new Thread(new NotifyObjInnerLock(o), "notify-innerlcok-thread");
        notifyInnerLock.start();
        notifyInnerLock.join();
    }

}

class Obj implements Runnable {
    
    private final Object lock = new Object();
    
    public synchronized void testInstWait() { 
       try {
           System.out.println("=== before testInstWait waiting ===");
           wait();  // 由持有实例的线程notify
           System.out.println("=== after testInstWait waiting ===");
       } catch (InterruptedException e) {
           e.printStackTrace();
       }   
    }
    
    /**
     * notify testWait1
     */
    public synchronized void notifyInst() {
        System.out.println("=== going to notify instance waiting ===");
        notifyAll();  
    }
    
    public void testWait2() {
        try {
            wait();  // 没有持有锁 抛出异常
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IllegalMonitorStateException e) {
            System.out.println("=== testWait2 exception ===");
            e.printStackTrace();
        }
        
    }

    public void testInnerLock() {
        synchronized (lock) {
           try {
               System.out.println("=== before testWait3 waiting ===");
               lock.wait(); // 私有锁waiting 由持私有锁的线程 notify
               System.out.println("=== after testWait3 waiting ===");
           } catch (InterruptedException e) {
            e.printStackTrace();
           }   
        }
    }
    
    /**
     * notify testWait3
     */
    public void notifyInnerLock() {
        synchronized (lock) {
            System.out.println("=== going to notify inner lock waiting ===");
            lock.notifyAll();
        }
    }
    
    public void testWait4() {
            try {
                lock.wait(); // 没有持有私有锁waiting 抛出异常
            } catch (InterruptedException e) {
                e.printStackTrace();
            }  catch (IllegalMonitorStateException e) {
                System.out.println("=== testWait4 exception ===");
                e.printStackTrace();
            } 
    }
    
    @Override
    public void run() {
        testInstWait();
        testInnerLock();
    }
    
    
}

class NotifyObj implements Runnable {

    private Obj obj;
    
    public NotifyObj(Obj obj) {
        this.obj = obj;
    }

    @Override
    public void run() {
        obj.notifyInst();
    }
    
}
class NotifyObjInnerLock implements Runnable {
    
    private Obj obj;
    
    public NotifyObjInnerLock(Obj obj) {
        this.obj = obj;
    }
    
    @Override
    public void run() {
        obj.notifyInnerLock();
    }
    
}
