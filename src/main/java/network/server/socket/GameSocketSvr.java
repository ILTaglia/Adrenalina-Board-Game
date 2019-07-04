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

    /**
     * Method that creates SocketServer and pool
     * @param port is the port for connection
     */
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

    /**
     * Method that runs socket server, accept connections and for every connectionHandler linked instantiate a ClientHandler
     */
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

    /**
     *
     * @param clientHandler is the backend
     * @param username is the username of the client
     */
    public void addClientToWR(ClientHandler clientHandler,String username){
        gameServer.addClientToWR(username,clientHandler);
    }

    /**
     *
     * @param requestedUsername is the requested username
     * @return true if it is already in the queue, false otherwise
     */
    public boolean isAlreadyInQueue(String requestedUsername) {
        return gameServer.isAlreadyInQueue(requestedUsername);
    }

    /**
     *
     * @param requestedUsername is the requested username
     * @return true if the player is disconnected, false otherwise
     */
    public boolean isPlayerDisconnected(String requestedUsername){
        return gameServer.hasPlayerDisconnected(requestedUsername);
    }

    /**
     *
     * @param message is the message to be handled
     */
    public void handleMessage(Message message){
        gameServer.handleMessage(message);
    }

    /**
     *
     * @param clientInterface is the backend
     */
    public void handleDisconnect(ClientInterface clientInterface) {
        gameServer.handleDisconnect(clientInterface);
    }

    /**
     *
     * @param userIDToReconnect the userID to be checked
     * @return true or false according to the result of connection
     */
    public boolean checkUserID(String userIDToReconnect) {
        return gameServer.checkUserID(userIDToReconnect);
    }

    /**
     *
     * @param userID is the userID to handle for connection
     * @param clientInterface is the backend
     */
    public void handleReConnect(String userID, ClientInterface clientInterface){
        gameServer.handleReConnect(userID,clientInterface);
    }

    /**
     *
     * @param userID is the userID to add the client
     * @param clientInterface is the backend
     */
    public void reAddClientToWR(String userID, ClientInterface clientInterface) {
        gameServer.reAddClientToWR(userID,clientInterface);
    }


    //Metodo che chiude il server, capire quando chiamarlo //TODO

    /**
     * Method that closes server
     */
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
