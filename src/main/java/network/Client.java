package network;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public abstract class Client implements Closeable {
    private final String host;
    private final int port;
    private Socket connection;
    private BufferedReader in;
    private PrintWriter out;

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }
}
