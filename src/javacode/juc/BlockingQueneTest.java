package javacode.juc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLConnection;
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
          Scanner in = new Scanner(System.in);   // windows GBK  ---> eclipse --> run as --> configurtion --> common --> GBK
          System.out.println("请输入搜索文件路径");  // D:/tomcat_group/apache-tomcat-7.0.32_s1/logs
          String filePath = in.nextLine();
          System.out.println("请输入搜索词");  // shopServiceContext.xml / 警告
          String keyWord = in.nextLine();
          in.close();
          System.out.printf("the filePath :%s, the keyWord : %s%n", filePath, keyWord);
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
    public static final File DUMMY = new File("");
    
    public FileQuene(BlockingQueue<File> quene, File dir) {
        this.quene = quene;
        this.startingDir = dir; 
    }

    @Override
    public void run() {
        try {
            extractFile(startingDir);
            quene.put(DUMMY);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    void extractFile(File dir) throws InterruptedException {
        for (File f : dir.listFiles()) {
            if (f.isDirectory()) {
                extractFile(f);
            } else {
                quene.put(f);
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
            File file;
            try {
                file = quene.take();
                if (file == FileQuene.DUMMY) { 
                    quene.put(file);
                    done = true;
                } else {
                    searchFile(file);
                }
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        
    }
    
    void searchFile(File file) throws IOException {
        try {
//            System.out.println("scaning the file : " + file.getPath());
            FileInputStream fs = new FileInputStream(file);
//            String charSet = URLConnection.guessContentTypeFromStream(fs);
//            if (charSet == null) 
//                charSet = Charset.defaultCharset().name();
            String charSet = "GBK";
            Scanner in = new Scanner(fs, charSet); //new Scanner(new FileInputStream(file));
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