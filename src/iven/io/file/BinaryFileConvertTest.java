package iven.io.file;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * 流、字符串 互转
 * @author Administrator
 *
 */
public class BinaryFileConvertTest {

    private static final String resourcePath = ClassLoader.getSystemResource("iven/io/resource/").getPath() ;
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        System.out.println(Charset.defaultCharset());
        System.out.println(resourcePath);
        String binaryStr = convertBinaryToString("color.jpg");
        String strFile = "photo_str";
        createFile(binaryStr, strFile);
        reverseStringToFile(strFile, "color_2.jpg");
    }

    public static String convertBinaryToString(String photo) {
        ByteArrayOutputStream out = new ByteArrayOutputStream(1024 * 8);
        int c;
        try {
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(new File(resourcePath, photo)));
            while ((c = in.read()) != -1) {
                out.write(c);
            }
            in.close();
            out.flush();
            out.close();
            return out.toString("ISO-8859-1");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static void createFile(String binaryStr, String filename) {
        try {
            FileOutputStream out = new FileOutputStream(new File(resourcePath, filename)); 
            out.write(binaryStr.getBytes("ISO-8859-1"));
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void reverseStringToFile(String strFile, String pathname) {
        try {
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(new File(resourcePath, strFile)));    
            FileOutputStream out = new FileOutputStream(new File(resourcePath, pathname));
            byte[] b = new byte[1024];
            while (in.read(b) != -1) {
                out.write(b);
            }
            in.close();
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
