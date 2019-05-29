package network.server.socket;

import network.client.Client;
import network.messages.Message;
import network.server.GameServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GameSocketSvr {

    private ExecutorService pool;
    private boolean isStopped=false;
    private ServerSocket serverSocket;
    private GameServer gameServer;      //Necessario avere il GameServer in modo da poter chiamare metodi su quello

    public GameSocketSvr(GameServer server){
        this.gameServer=server;
    }

    //Metodo che crea il SocketServer e crea il pool
    public void start(int port){
        try {
            serverSocket = new ServerSocket(port);
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
        System.out.println("Listening on port:" + port);
        pool = Executors.newCachedThreadPool();
    }

    //Metodo che runna il SocketServer, accetta le connessioni e per ciascun client collegato istanzia un ClientHandler
    public void run(){
        while(!isStopped()){
            Socket clientSocket;
            try {
                clientSocket = serverSocket.accept();
            }catch (IOException e){
                if(isStopped()){
                    System.out.println("Server Closed.");                   //TODO: LOGGER
                    return;
                }
                throw new RuntimeException("Error connecting",e);
            }
            System.out.println("New Client connected at address: " + clientSocket.getRemoteSocketAddress());
            pool.submit(new ClientHandler(this,clientSocket));
        }
    }

    //------------------------Metodi usati dal ClientHandler----------------------------------------------------------//

    public void addClientToWR(ClientHandler clientHandler,String username){
        gameServer.addClientToWR(username,clientHandler);
    }

    public boolean isAlreadyInQueue(String requestedUsername) { //TODO: VERIFICA PARALLELISMI DI TUTTO QUESTO
        return gameServer.isAlreadyInQueue(requestedUsername);
    }

    public void handleMessage(Message message){
        gameServer.handleMessage(message);
    }


    //---------------------------------Metodi da completare-----------------------------------------------------------//

    //Metodo che chiude il server, TODO capire come usarlo
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

    //---------------------------------Metodi di dubbia utilità-----------------------------------------------------------//

}
