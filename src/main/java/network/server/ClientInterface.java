package network.server;

import network.messages.Message;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientInterface extends Remote {


    void setPlayerID(String playerID) throws RemoteException;
    String getPlayerID() throws RemoteException;
    void sendMessage(Message message) throws RemoteException;

    void setConnection(boolean connected) throws RemoteException;

    void closeConnection()throws RemoteException;
}
