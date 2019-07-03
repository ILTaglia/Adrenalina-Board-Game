package network.server.socket;

import network.messages.Message;
import network.server.ClientInterface;
import network.server.GameServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static utils.Print.printOut;

public class GameSocketSvr extends Thread {

    private ExecutorService pool;           //pool per ClientHandler in parallelo
    private boolean isStopped=false;        //boolean per registrare stato attività Server
    private ServerSocket serverSocket;      //serverSocket per accettare nuove connessioni dai client, istanzio in questa classe
    private GameServer gameServer;          //Necessario avere il GameServer in modo da poter chiamare metodi su quello

    public GameSocketSvr(GameServer server){
        this.gameServer=server;
    }

    //Metodo che crea il SocketServer e crea il pool
    public void start(int port){
        try {
            serverSocket = new ServerSocket(port);
        }catch (IOException e){
            printOut(e.getMessage());
        }
        printOut("Socket ON");
        pool = Executors.newCachedThreadPool();
    }

    //Metodo che runna il SocketServer, accetta le connessioni e per ciascun connectionHandler collegato istanzia un ClientHandler
    @Override
    public void run(){
        while(!isStopped){
            Socket clientSocket;
            try {
                clientSocket = serverSocket.accept();
            }catch (IOException e){
                clientSocket=null;
                if(isStopped) printOut("Server Closed.");
                else printOut("Error accepting new Connection");
            }
            if(clientSocket!=null) {
                printOut("New Socket Client connected at address: " + clientSocket.getRemoteSocketAddress());
                pool.submit(new ClientHandler(this, clientSocket));
            }
        }
    }

    //------------------------Metodi usati dal ClientHandler----------------------------------------------------------//

    public void addClientToWR(ClientHandler clientHandler,String username){
        gameServer.addClientToWR(username,clientHandler);
    }

    public boolean isAlreadyInQueue(String requestedUsername) {
        return gameServer.isAlreadyInQueue(requestedUsername);
    }

    public boolean isPlayerDisconnected(String requestedUsername){
        return gameServer.hasPlayerDisconnected(requestedUsername);
    }
    public void handleMessage(Message message){
        gameServer.handleMessage(message);
    }
    public void handleDisconnect(ClientInterface clientInterface) {
        gameServer.handleDisconnect(clientInterface);
    }

    public boolean checkUserID(String userIDToReconnect) {
        return gameServer.checkUserID(userIDToReconnect);
    }

    public void handleReConnect(String userID, ClientInterface clientInterface){
        gameServer.handleReConnect(userID,clientInterface);
    }

    public void reAddClientToWR(String userID, ClientInterface clientInterface) {
        gameServer.reAddClientToWR(userID,clientInterface);
    }


    //Metodo che chiude il server, capire quando chiamarlo //TODO
    public void close(){
        this.isStopped=true;
        try {
            this.serverSocket.close();
        } catch (IOException e) {
            printOut("Error while closing Server");
            printOut(e.getCause().toString());
        }
    }


}
