package network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GameSocketSvr {

    private ExecutorService pool;
    private int serverPort;
    private boolean isStopped=false;

    private ServerSocket serverSocket;

    private GameServer gameServer;      //Necessario avere il GameServer in modo da poter chiamare metodi su quello

    public GameSocketSvr(GameServer server){
        this.gameServer=server;
    }

    public void start(int port){
        try {
            serverSocket = new ServerSocket(port);
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
        System.out.println("In ascolto sulla porta:" + port);
        pool = Executors.newCachedThreadPool();
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
            System.out.println("New connection " + clientSocket.getRemoteSocketAddress());
            pool.submit(new ClientHandler(this,clientSocket));
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

    public void addClientToWR(ClientHandler clientHandler,String username){
        gameServer.addClientToWR(clientHandler,username);
    }

    public boolean isAlreadyInQueue(String requestedUsername) { //TODO: VERIFICA PARALLELISMI DI TUTTO QUESTO
        return gameServer.isAlreadyInQueue(requestedUsername);
    }

    public synchronized void assignIDtoUsername(String playerUsername) {
        gameServer.assignIDtoUsername(playerUsername);
    }
}
