package iven.io.socket;

import java.io.IOException;
import java.util.Properties;

public class SocketClient {

    /**
     * @param args
     */
    public static void main(String[] args) {
        
        Properties properties = new Properties();
        try {
            properties.load(ClassLoader.getSystemResourceAsStream("iven/io/socket/socket.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.printf("a.remote.address %s %n" , properties.get("a.remote.address"));
        
        
    }

}
