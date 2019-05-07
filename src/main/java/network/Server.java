package network;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public abstract class Server implements Closeable {
    private final int port;
    private ServerSocket serverSocket;

    public Server(int port) {
        this.port = port;
    }
}
