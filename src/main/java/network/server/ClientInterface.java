package network.server;

import network.messages.Message;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientInterface extends Remote {
    void sendMessage(Message message) throws RemoteException;

}
