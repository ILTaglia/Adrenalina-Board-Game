package network.server.rmi;

import exceptions.UsernameAlreadyUsedException;
import network.messages.Message;
import network.server.ClientInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerInterface extends Remote {

    void registerToQueue(String username, ClientInterface clientInterface) throws UsernameAlreadyUsedException,RemoteException;

    void handleMessage(Message message) throws RemoteException;

}
