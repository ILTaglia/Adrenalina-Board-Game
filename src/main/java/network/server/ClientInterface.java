package network.server;

import network.messages.Message;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientInterface extends Remote {


    void setPlayerID(String playerID);
    String getPlayerID();
    void sendMessage(Message message) throws RemoteException;

    void setConnection(boolean connected);

    void closeConnection();
}
