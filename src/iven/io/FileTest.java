package iven.io;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class FileTest {

    /**
     * @param args
     * @throws IOException 
     * @throws URISyntaxException 
     */
    public static void main(String[] args) throws IOException, URISyntaxException {
        String path = "a.txt"; 
        String path2 = "iven/io/a.txt";
        String path3 = "/iven/io/a.txt";
        
        // http://www.javaworld.com/javaqa/2003-08/01-qa-0808-property.html?page=2
        
        // Thread classLoader是System
//        System.out.printf("==== url.toString : %s ====%n", Thread.class.getResource(path));
//        System.out.printf("==== uri.toString : %s ====%n", Thread.class.getResource(path).toURI());
        System.out.printf("==== Thread.currentThread().getContextClassLoader: %s ====%n", Thread.currentThread().getContextClassLoader()
                );
        System.out.printf("==== FileTest.class.getClassLoade: %s ====%n", FileTest.class.getClassLoader());

        // FileTest classLoader是AppClassLoader
        URI uri = FileTest.class.getResource(path3).toURI();
        URI uri2 = ClassLoader.getSystemResource(path2).toURI();
        System.out.printf("==== uri.toString : %s ====%n", uri.toString());
        System.out.printf("==== uri2.toString : %s ====%n", uri2);
        
        File file  = new File(uri);
        System.out.printf("==== isExist : %s ====%n", file.exists());
        System.out.printf("==== getPath : %s ====%n", file.getPath());
        System.out.printf("==== getCanonicalPath : %s ====%n", file.getCanonicalPath());
        System.out.printf("==== getAbsolutePath : %s ====%n", file.getAbsolutePath());
        
        // result : 
//        ==== getPath : .\iven\io\a.txt ====
//        ==== getCanonicalPath : E:\git\me\iven\io\a.txt ====
//        ==== getAbsolutePath : E:\git\me\.\iven\io\a.txt ====
        
    }

}
