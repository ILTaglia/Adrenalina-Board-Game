package network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GameServer {
    private final ServerSocket serverSocket;
    private final ExecutorService pool;

    public GameServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        pool = Executors.newCachedThreadPool();
        System.out.println("In ascolto sulla porta:" + port);
    }

    public void run(){

    }

    public void close(){

    }
}
