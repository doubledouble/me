package iven.io.file;

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
        String relative_Path_4_class = "a.txt"; 
        String absoulte_Path_4_class = "/iven/io/file/a.txt";      // class 以'/'开头表示绝对路径， 其他情况表示相对路径
        String absoulte_Path_4_classLoader = "iven/io/file/a.txt"; // classloader 只有绝对路径，不能以'/'开始
        
        // http://www.javaworld.com/javaqa/2003-08/01-qa-0808-property.html?page=2

        URI uri = FileTest.class.getResource(relative_Path_4_class).toURI(); // relative_Path
        File file  = new File(uri);
        System.out.printf("==== isExist : %s ====%n", file.exists());
        System.out.printf("==== getPath : %s ====%n", file.getPath());
        System.out.printf("==== getName : %s ====%n", file.getName());
        System.out.printf("==== getCanonicalPath : %s ====%n", file.getCanonicalPath());
        System.out.printf("==== getAbsolutePath : %s ====%n%n", file.getAbsolutePath());
        
        uri = FileTest.class.getResource(absoulte_Path_4_class).toURI();  // absoulte_Path
        file  = new File(uri);
        System.out.printf("==== isExist : %s ====%n", file.exists());
        System.out.printf("==== getPath : %s ====%n", file.getPath());
        System.out.printf("==== getName : %s ====%n", file.getName());
        System.out.printf("==== getCanonicalPath : %s ====%n", file.getCanonicalPath());
        System.out.printf("==== getAbsolutePath : %s ====%n%n", file.getAbsolutePath());
        
        uri = ClassLoader.getSystemResource(absoulte_Path_4_classLoader).toURI();  // absoulte_Path_only_for_classLoader
        // 同样效果
//        uri = FileTest.class.getClassLoader().getResource(absoulte_Path_4_classLoader).toURI();  
//        uri = Thread.currentThread().getContextClassLoader().getResource(absoulte_Path_4_classLoader).toURI();
        file  = new File(uri);
        System.out.printf("==== isExist : %s ====%n", file.exists());
        System.out.printf("==== getPath : %s ====%n", file.getPath());
        System.out.printf("==== getName : %s ====%n", file.getName());
        System.out.printf("==== getCanonicalPath : %s ====%n", file.getCanonicalPath());
        System.out.printf("==== getAbsolutePath : %s ====%n%n", file.getAbsolutePath());
        
        String path = "../resource/b.txt";
        uri = FileTest.class.getResource(path).toURI();  
        file  = new File(uri);
        System.out.printf("==== isExist : %s ====%n", file.exists());
        System.out.printf("==== getPath : %s ====%n", file.getPath());
        System.out.printf("==== getName : %s ====%n", file.getName());
        System.out.printf("==== getCanonicalPath : %s ====%n", file.getCanonicalPath());
        System.out.printf("==== getAbsolutePath : %s ====%n%n", file.getAbsolutePath());
        
        //以下例子只是为了说明 应该使用getCanonicalPath 代替 getAbsolutePath
        //getCanonicalPath能删除冗余的'..' 和 '.' 的字符串
        path = "../resource/b.txt";
        file  = new File(path);
        System.out.printf("==== isExist : %s ====%n", file.exists());
        System.out.printf("==== getPath : %s ====%n", file.getPath());
        System.out.printf("==== getName : %s ====%n", file.getName());
        System.out.printf("==== getCanonicalPath : %s ====%n", file.getCanonicalPath());
        System.out.printf("==== getAbsolutePath : %s ====%n%n", file.getAbsolutePath());
    }

}
