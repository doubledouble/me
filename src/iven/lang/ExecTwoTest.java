package iven.lang;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class ExecTwoTest {

    /**
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
        String[] cmdArray = new String[]{"cd /usr/local/jboss/jboss-4.2.3.GA/server/default/log/", "cat boot.log"};
        
        Process proc = null;
        try {
           proc = Runtime.getRuntime().exec("/bin/bash");
        }
        catch (IOException e) {
           e.printStackTrace();
        }
        if (proc != null) {
           BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
           PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(proc.getOutputStream())), true);
           out.println("cd /usr/local/jboss/jboss-4.2.3.GA/server/default/log/");
           out.println("pwd");
           out.println("ls -l");
           try {
              String line;
              while ((line = in.readLine()) != null) {
                 System.out.println(line);
              }
              proc.waitFor();
              in.close();
              out.close();
              proc.destroy();
           }
           catch (Exception e) {
              e.printStackTrace();
           }
        }
        
    }

}
