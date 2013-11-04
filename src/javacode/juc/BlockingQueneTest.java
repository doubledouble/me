package javacode.juc;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.charset.Charset;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class BlockingQueneTest {

    /**
     * @param args
     */
    public static void main(String[] args) {
          System.out.println("Charset.defaultCharset(): " + Charset.defaultCharset());
          int FIlE_QUENE_SIZE  = 10;
          int SEARCH_THREADS  = 100;
          BlockingQueue<File> quene = new ArrayBlockingQueue<>(FIlE_QUENE_SIZE);
          Scanner in = new Scanner(System.in);
          System.out.println("请输入搜索文件路径");
          String filePath = in.nextLine();
          System.out.println("请输入搜索词");
          String keyWord = in.nextLine();
          in.close();
          FileQuene fileQuene = new FileQuene(quene, new File(filePath));
          new Thread(fileQuene, "FileQuene Thread").start();
          for (int i=0; i<SEARCH_THREADS; i++) {
              new Thread(new Searcher(quene, keyWord)).start();
          }
    }

}

class FileQuene implements Runnable{
    
    private BlockingQueue<File> quene;
    private File startingDir;
    public static final File dump = new File("");
    
    public FileQuene(BlockingQueue<File> quene, File dir) {
        this.quene = quene;
        this.startingDir = dir; 
    }

    @Override
    public void run() {
        extractFile(startingDir);
        quene.offer(dump);
    }
    
    void extractFile(File dir) {
        for (File f : dir.listFiles()) {
            if (f.isDirectory()) {
                extractFile(f);
            } else {
                quene.offer(f);
            }
        }
    }
    
}

class Searcher implements Runnable {

    private BlockingQueue<File> quene;
    private String keyWord;
    
    public Searcher(BlockingQueue<File> quene, String keyWord) {
        this.quene = quene;
        this.keyWord = keyWord;
    }

    @Override
    public void run() {
        boolean done = false;
        while (!done) {
            File file = quene.poll();
            if (file == FileQuene.dump) {
                quene.offer(file);
                done = true;
            } else {
                searchFile(file);
            }
        }
        
    }
    
    void searchFile(File file) {
        try {
            Scanner in = new Scanner(file); //new Scanner(new FileInputStream(file));
            int linenum = 0;
            while (in.hasNextLine()) {
                String line = in.nextLine();
                linenum++;
                if (line.contains(keyWord)) {
                    System.out.printf("%s:%d:%s%n", file.getPath(), linenum, line);
                }
            }
            in.close();
        } catch (FileNotFoundException e) {
            
        }
    }
    
    
}