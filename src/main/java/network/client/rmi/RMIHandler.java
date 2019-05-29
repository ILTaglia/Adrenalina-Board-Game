package network.client.rmi;


import network.client.Client;
import network.client.ConnectionHandler;
import network.client.rmi.RMIConnection;
import network.messages.Message;
import network.server.ClientInterface;
import network.server.rmi.ServerInterface;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RMIHandler implements ConnectionHandler {

    public ServerInterface server;
    public ClientInterface clientInterface;     //Da condividere con il server

    public RMIHandler(Client client) throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry();
        server=(ServerInterface) registry.lookup("Server");
        RMIConnection rmiConnection =new RMIConnection(client);
        clientInterface = (ClientInterface) UnicastRemoteObject.exportObject(rmiConnection, 0);
    }

    public void registerToQueue(String username){
        server.registerToQueue(username,clientInterface);
    }

    public void sendMessage(Message message){
        server.handleMessage(message);
    }

}
