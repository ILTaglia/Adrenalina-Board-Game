package network.server.rmi;

import exceptions.InvalidUserIDException;
import exceptions.UsernameAlreadyUsedException;
import network.messages.Message;
import network.server.ClientInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;

//Interfaccia del Server RMI

public interface ServerInterface extends Remote {

    void registerToQueue(String username, ClientInterface clientInterface) throws UsernameAlreadyUsedException,RemoteException;

    void handleMessage(Message message) throws RemoteException;

    void reConnectRequest(String userIDToReConnect, ClientInterface clientInterface)throws InvalidUserIDException,RemoteException;

    void newRegistrationToQueue(String username, ClientInterface clientInterface) throws UsernameAlreadyUsedException,RemoteException;

    void reConnectAttempt(String userID, ClientInterface clientInterface) throws RemoteException;
}
