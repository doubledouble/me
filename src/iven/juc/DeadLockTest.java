package iven.juc;

public class DeadLockTest {

    /**
     * @param args
     */
    public static void main(String[] args) {
        
        final Object a = new Object();
        final Object b = new Object();

        new Thread() {
            @Override
            public void run() {
                try {
                    synchronized (a) {
                        System.out.println("Thread 1 got the lock of a");
                        Thread.sleep(1000);
                        System.out.println("Thread 1 was trying to get the lock of b");
                        synchronized (b) {
                            System.out.println("Thread 1 win");
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
        new Thread() {
            @Override
            public void run() {
                try {
                    synchronized (b) {
                        System.out.println("Thread 2 got the lock of b");
                        Thread.sleep(1000);
                        System.out.println("Thread 2 was trying to get the lock of a");
                        synchronized (a) {
                            System.out.println("Thread 2 win");
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

}
