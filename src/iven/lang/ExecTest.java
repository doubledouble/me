package iven.lang;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class ExecTest {

    /**
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
        String[] cmdArray = new String[]{"cd /usr/local/jboss/jboss-4.2.3.GA/server/default/log/", "cat boot.log"};
        if (args != null && args.length > 0 && !"".equals(args[0])) {
            cmdArray = new String[args.length];
            cmdArray = args;
        }
        for (String cmd : cmdArray) {
            if (cmd.indexOf("rm") != -1) {
                throw new RuntimeException("---- no allow ----");
            }
        }
        Process process = Runtime.getRuntime().exec(cmdArray);
        BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String tmp;
        while ( (tmp = in.readLine()) != null) {
            System.out.println(tmp);
        }
        in.close();
    }

}
