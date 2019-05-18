package network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GameServer {
    private final ServerSocket serverSocket;
    private final ExecutorService pool;
    private int serverPort;
    private boolean isStopped=false;

    public GameServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);              //TODO: try/catch da ex
        serverPort=port;                                    //TODO: port (?)
        pool = Executors.newCachedThreadPool();
        System.out.println("In ascolto sulla porta:" + port);
    }

    public void run(){
        while(!isStopped()){
            Socket clientSocket=null;
            try {
                clientSocket = serverSocket.accept();
            }catch (IOException e){
                if(isStopped()){
                    System.out.println("Server Closed.");                   //TODO: LOGGER
                    return;
                }
                throw new RuntimeException("Error connecting",e);
            }
            System.out.println(">>> New connection " + clientSocket.getRemoteSocketAddress());
            pool.submit(new ClientHandler(clientSocket));
        }
    }

    public void close(){
        this.isStopped=true;
        try {
            this.serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException("Error closing server", e);
        }
    }

    private boolean isStopped(){        //TODO: verificare utilità metodo
        return this.isStopped;

    }
}
