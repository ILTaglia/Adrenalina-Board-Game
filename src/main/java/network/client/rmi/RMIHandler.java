package network.client.rmi;


import exceptions.InvalidUserIDException;
import exceptions.UsernameAlreadyUsedException;
import network.client.Client;
import network.client.ConnectionHandler;
import network.messages.Message;
import network.server.ClientInterface;
import network.server.rmi.ServerInterface;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Timer;
import java.util.TimerTask;

import static utils.Print.printOut;

public class RMIHandler implements ConnectionHandler {
    /**
     * Class that receives messages from RMIConnection.
     * server is the ServerInterface
     * clientInterface is the Client, it is ClientInterface to be shared with server
     * connectionEstablished is a boolean to check wheteìher the connection is established or not
     */

    private Client client;
    private ServerInterface server;
    private ClientInterface clientInterface;     //Da condividere con il server
    private boolean connectionEstablished;
    /**
     *
     * @param client is the client
     * @throws RemoteException is a possible exception thrown with RMIConnection
     * @throws NotBoundException is a possible exception thrown with RMIConnection
     */

    public RMIHandler(Client client) throws RemoteException, NotBoundException {
        this.client=client;
        Registry registry = LocateRegistry.getRegistry();
        server=(ServerInterface) registry.lookup("Server");
        RMIConnection rmiConnection =new RMIConnection(client);
        clientInterface = (ClientInterface) UnicastRemoteObject.exportObject(rmiConnection, 0);
    }
    /**
     *
     * @param username is the username of the host to be registered
     * @throws UsernameAlreadyUsedException if the same username has already been used by an other host
     */

    @Override
    public void registerToWR(String username) throws UsernameAlreadyUsedException {
        try {
            server.registerToQueue(username, clientInterface);
        }catch (RemoteException e){
            System.out.println(e.getMessage());
        }
    }
    /**
     *
     * @param username is the username of the host to be reconnected
     * @throws UsernameAlreadyUsedException if the same username has already been used by an other host
     */

    @Override
    public void newConnectionRequest(String username) throws UsernameAlreadyUsedException{
        try{
            server.newRegistrationToQueue(username,clientInterface);
        }catch (RemoteException e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Method to establish connection
     */

    @Override
    public void setConnected() {
        connectionEstablished=true;
        (new Timer()).schedule(new TimerTask() {
            @Override
            public void run() {
                if(connectionEstablished){
                    connectionEstablished=false;        //In teoria dovrei ricevere "a breve" un altro set a true
                }else{
                    askToTryToReconnect();
                    cancel();
                }
            }
        },10000);
    }

    /**
     * Method to request fo reconnection
     * @param userIDToReConnect is the ID of the user to be reconnected
     */

    @Override
    public void reConnectRequest(String userIDToReConnect) throws InvalidUserIDException{
        try {
            server.reConnectRequest(userIDToReConnect,clientInterface);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to send messages
     * @param message is the message to be sent
     */

    @Override
    public void sendMessage(Message message){
        try {
            server.handleMessage(message);
        } catch (RemoteException e) {
            printOut(e.getMessage());
        }
    }
    /**
     * Method to ask to try to reconnect
     */

    @Override
    public void askToTryToReconnect() {
        connectionEstablished=false;
        client.askToTryToReconnect();
    }
    /**
     * Method to reconnect
     * @param userID is the ID of the user to be reconnected
     */

    @Override
    public void attemptToReconnect(String userID) {
        try {
            server.reConnectAttempt(userID,clientInterface);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }



}
