package iven.juc;

import java.rmi.server.ServerCloneException;
import java.util.concurrent.TimeUnit;

/**
 * Thread run方法结束后 nofityAll会被调用 持有该thread实例的线程会被唤醒
 * @author Administrator
 *
 */
public class AfterRunTest {

    /**
     * @param args
     * @throws InterruptedException 
     */
    public static void main(String[] args) throws InterruptedException {
        Thread run = new AfterRun();
        run.setName("obj-run-thread");
        T t1 = new T(run, "wait-obj-t1-thread");
        T t2 = new T(run, "wait-obj-t2-thread");
        t1.start();
        t2.start();
        TimeUnit.SECONDS.sleep(1);
        run.start();
    }

}

class AfterRun extends Thread {


    @Override
    protected Object clone() throws ServerCloneException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void run()  {
        System.out.printf("==== before run current thread %s ====%n", Thread.currentThread().getName());
        for (int i=0 ; i< 1000 * 1000; i++) {
            
        }
        System.out.printf("==== after run current thread %s, the nofityAll is called ====%n", Thread.currentThread().getName());
    }
    
}

class T extends Thread {
    
    private Thread t;
    
    public T(Thread t, String name) {
        this.t = t;
        this.setName(name);
    }

    @Override
    public void run() {
        synchronized (t) {
            try {
                System.out.printf("==== before wait current name %s ====%n", Thread.currentThread().getName());
                for (;;) {
                    t.wait();
                    break;
                }
                System.out.printf("==== after wait current name %s ====%n", Thread.currentThread().getName());
            } catch (InterruptedException e) {
                
            }
        }
    }
    
}

