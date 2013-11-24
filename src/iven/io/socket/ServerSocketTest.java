package iven.io.socket;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;

public class ServerSocketTest {

    /**
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket();
        SocketAddress address = new InetSocketAddress(8080);
        serverSocket.bind(address);
//        serverSocket.setSoTimeout(3000);
        while (true) {
            Socket socket = null; 
            try {
                socket = serverSocket.accept();
                Thread inputThread = new InputThread(socket.getInputStream());
                inputThread.start();
                Thread outPutThread = new OutputThread(socket.getOutputStream());
                outPutThread.start();
                inputThread.join();
                outPutThread.join();
            } catch (SocketTimeoutException | InterruptedException e) {
                e.printStackTrace();
            } finally {
                if (socket != null && !socket.isClosed()) {
                    socket.close();
                }
            }
        }
    }

}

class InputThread extends Thread {
    
    private InputStream in;
    
    public InputThread(InputStream in) {
        this.in = in;
    }

    @Override
    public void run() {

        try {
            while(in.read() != -1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    
}

class OutputThread extends Thread {
    
    private OutputStream out;
    
    public OutputThread(OutputStream out) {
        this.out = out;
    }

    @Override
    public void run() {
        BufferedWriter writer  = new BufferedWriter(new OutputStreamWriter(out));
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String b;
        try {
            while ((b = reader.readLine()) != null) {
                writer.write(b);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                writer.flush();
                writer.close();
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}