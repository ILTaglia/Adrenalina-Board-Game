package network.server;

import network.messages.Message;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientInterface extends Remote {

    /**
     *
     * @param playerID is the ID to be set
     * @throws RemoteException if operation fails
     */
    void setPlayerID(String playerID) throws RemoteException;

    /**
     *
     * @return the playerID
     * @throws RemoteException if operation fails
     */
    String getPlayerID() throws RemoteException;

    /**
     *
     * @param message is the message to be sent
     * @throws RemoteException if operation fails
     */
    void sendMessage(Message message) throws RemoteException;

    /**
     *
     * @param connected is the boolean to connect the client
     * @throws RemoteException if operation fails
     */
    void setConnection(boolean connected) throws RemoteException;

    /**
     * Method to close connection
     * @throws RemoteException if operation fails
     */
    void closeConnection()throws RemoteException;

    /**
     * Method to request for reconnection
     * @throws RemoteException if operation fails
     */
    void requestToReconnect()throws RemoteException;

    /**
     * Method to set client connected
     * @throws RemoteException if operation fails
     */
    void setClientConnected() throws RemoteException;
}
