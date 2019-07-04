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

    private Client client;
    private ServerInterface server;
    private ClientInterface clientInterface;     //Da condividere con il server
    private boolean connectionEstablished;
    private Timer timer;

    public RMIHandler(Client client) throws RemoteException, NotBoundException {
        this.client=client;
        timer=new Timer();
        Registry registry = LocateRegistry.getRegistry();
        server=(ServerInterface) registry.lookup("Server");
        RMIConnection rmiConnection =new RMIConnection(client);
        clientInterface = (ClientInterface) UnicastRemoteObject.exportObject(rmiConnection, 0);
    }
    @Override
    public void registerToWR(String username) throws UsernameAlreadyUsedException {
        try {
            server.registerToQueue(username, clientInterface);
        }catch (RemoteException e){
            System.out.println(e.getMessage());
        }
    }
    @Override
    public void newConnectionRequest(String username) throws UsernameAlreadyUsedException{
        try{
            server.newRegistrationToQueue(username,clientInterface);
        }catch (RemoteException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void setConnected() {
        connectionEstablished=true;
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(connectionEstablished){
                    connectionEstablished=false;        //In teoria dovrei ricevere "a breve" un altro set a true
                }else{
                    disconnected();
                }
            }
        },100000000);
    }

    private void disconnected(){
        connectionEstablished=false;
        client.close();
    }

    @Override
    public void reConnectRequest(String userIDToReConnect) throws InvalidUserIDException{
        try {
            server.reConnectRequest(userIDToReConnect,clientInterface);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendMessage(Message message){
        try {
            server.handleMessage(message);
        } catch (RemoteException e) {
            printOut(e.getMessage());
        }
    }

    @Override
    public void askToTryToReconnect() {
        //Not used in RMI
    }

    @Override
    public void attemptToReconnect(String userID) {
        try {
            server.reConnectAttempt(userID,clientInterface);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }



}
