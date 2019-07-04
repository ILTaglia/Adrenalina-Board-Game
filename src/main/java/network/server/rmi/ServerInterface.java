package network.server.rmi;

import exceptions.InvalidUserIDException;
import exceptions.UsernameAlreadyUsedException;
import network.messages.Message;
import network.server.ClientInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;

//Interfaccia del Server RMI

public interface ServerInterface extends Remote {

    /**
     *
     * @param username is the username of the player
     * @param clientInterface is the backend
     * @throws UsernameAlreadyUsedException if the name has already been chosen
     * @throws RemoteException if connection fails
     */
    void registerToQueue(String username, ClientInterface clientInterface) throws UsernameAlreadyUsedException,RemoteException;

    /**
     *
     * @param message is the message to be handled
     * @throws RemoteException if connection fails
     */
    void handleMessage(Message message) throws RemoteException;

    /**
     *
     * @param userIDToReConnect is the userID to reconnect
     * @param clientInterface is the backend
     * @throws InvalidUserIDException if the user is not allowed
     * @throws RemoteException if connection fails
     */
    void reConnectRequest(String userIDToReConnect, ClientInterface clientInterface)throws InvalidUserIDException,RemoteException;

    /**
     *
     * @param username is the userID to signed up a new player in the queue
     * @param clientInterface is the backend
     * @throws UsernameAlreadyUsedException if the name has already been chosen
     * @throws RemoteException if the connection fails
     */
    void newRegistrationToQueue(String username, ClientInterface clientInterface) throws UsernameAlreadyUsedException,RemoteException;

    /**
     *
     * @param userID is the userID to try to reconnect
     * @param clientInterface is the backend
     * @throws RemoteException if conenction fails
     */
    void reConnectAttempt(String userID, ClientInterface clientInterface) throws RemoteException;
}
