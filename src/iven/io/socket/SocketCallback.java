package iven.io.socket;

import java.net.Socket;

public interface SocketCallback {

    void call(Socket socket);

}
