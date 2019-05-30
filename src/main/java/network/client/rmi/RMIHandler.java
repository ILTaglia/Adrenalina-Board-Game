package network.client.rmi;


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

public class RMIHandler implements ConnectionHandler {

    public ServerInterface server;
    public ClientInterface clientInterface;     //Da condividere con il server

    public RMIHandler(Client client) throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry();
        server=(ServerInterface) registry.lookup("Server");
        RMIConnection rmiConnection =new RMIConnection(client);
        clientInterface = (ClientInterface) UnicastRemoteObject.exportObject(rmiConnection, 0);
    }

    public void registerToWR(String username) throws UsernameAlreadyUsedException {
        try {
            server.registerToQueue(username, clientInterface);
        }catch (RemoteException e){
            System.out.println(e.getMessage());
        }
    }

    public void sendMessage(Message message){
        try {
            server.handleMessage(message);
        } catch (RemoteException e) {
            System.out.println(e.getMessage());
        }
    }

}
