package javacode.juc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class FutureTest {

    /**
     * @param args
     */
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);   // windows GBK  ---> eclipse --> run as --> configurtion --> common --> GBK
        System.out.println("请输入搜索文件路径");  // D:/tomcat_group/apache-tomcat-7.0.32_s1/logs
        String filePath = in.nextLine();
        System.out.println("请输入搜索词");  // shopServiceContext.xml / 警告
        String keyWord = in.nextLine();
        in.close();
        System.out.printf("the filePath :%s, the keyWord : %s%n", filePath, keyWord);
        MatcherCount matcherCount = new MatcherCount(new File(filePath), keyWord);
        FutureTask<Integer> task = new FutureTask<>(matcherCount);
        new Thread(task, "match-main-Thread").start();
        try {
            System.out.println(task.get() + " matching files");
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println("=====");
    }

}

class MatcherCount implements Callable<Integer> {

    public MatcherCount(File startingDir, String keyWord) {
        this.startingDir = startingDir;
        this.keyWord = keyWord;
    }

    private List<Future<Integer>> list = new ArrayList<Future<Integer>>();
    
    @Override
    public Integer call() throws Exception {
        count = 0 ;
        for (File file : startingDir.listFiles()) {
            if (file.isDirectory()) {
                MatcherCount matcherCount = new MatcherCount(file, keyWord);
                FutureTask<Integer> task = new FutureTask<>(matcherCount);
                list.add(task);
                new Thread(task, "match-Thread-" + list.size()).start();
            } else {
                if (search(file)) count++;
            }
        }
        
        for (Future<Integer> future : list) {
            count = count + future.get();
        }
        return count;
    }
    
    boolean search(File file) throws FileNotFoundException {
        boolean found = false;
        Scanner in = new Scanner(new FileInputStream(file), "GBK");
        while (in.hasNextLine()) {
            String line = in.nextLine();
            if (line.contains(keyWord)) {
                found = true;
                break;
            }
        }
        in.close();
        return found;
    }
    
    private File startingDir;
    private String keyWord;
    private int count;
}
