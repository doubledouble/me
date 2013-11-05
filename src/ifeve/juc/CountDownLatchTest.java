package ifeve.juc;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchTest {

    /**
     * @param args
     */
    public static void main(String[] args) {
          int ATTEND_NUM = 10;
          Videoconference conference = new Videoconference(ATTEND_NUM);
          new Thread(conference, "video-conference-thread").start();
          for (int i=0; i<ATTEND_NUM; i++) {
              new Thread(new Participant(conference, "attend_" + i), "participant-thread-"+i).start();
          }
    }

}

class Videoconference  implements Runnable {

    private final CountDownLatch controller;
    
    public Videoconference(int ATTEND_NUM) {
        this.controller = new CountDownLatch(ATTEND_NUM);
    }

    public void arrive(String name) {
        System.out.printf("Participant %s has arrived%n ", name);
        controller.countDown();
        System.out.printf("there are %d persons no arrive%n", controller.getCount());
    }


    @Override
    public void run() {
        try {
            controller.await();
            System.out.printf("everyone has arrived, let's begin the video conference");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
    }
    
}

class Participant implements Runnable {

    private String name;
    private Videoconference conference;
    
    public Participant(Videoconference conference, String name) {
        this.conference = conference;
        this.name = name;
    }

    @Override
    public void run() {
        conference.arrive(name);
    }
    
}