package iven.io.socket;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class DayTimeClient {

    /**
     * @param args
     * @throws IOException 
     * @throws UnknownHostException 
     */
    public static void main(String[] args) throws UnknownHostException, IOException {
            String hostName = "vision.poly.edu";
//            Socket socket = new Socket(hostName, 80);
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(hostName, 80));
            System.out.printf("socket.getPort() %s %n", socket.getPort());
            
            StringBuffer sb = new StringBuffer();
            InputStream in =  socket.getInputStream();
            int c ;
            while ((c = in.read()) != -1) {
                sb.append((char)c);
            }
            String timeString = sb.toString().trim();
            System.out.printf("It is %s at %s %n", timeString, hostName);
            socket.shutdownInput();
            socket.close();
            
//            System.out.printf("InetAddress.getByName %s %n", InetAddress.getByName(hostName));
    }

}
