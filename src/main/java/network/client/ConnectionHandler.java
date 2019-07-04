package network.client;

import exceptions.InvalidUserIDException;
import exceptions.UsernameAlreadyUsedException;
import network.messages.Message;

public interface ConnectionHandler {

    /**
     *
     * @param username is the username of the client
     * @throws UsernameAlreadyUsedException if the name has already been chosen
     */
    void registerToWR(String username) throws UsernameAlreadyUsedException;

    /**
     *Method to send message
     */
    void sendMessage(Message message);

    /**
     * Message to ask to try to reconnect
     */
    void askToTryToReconnect();

    /**
     *
     * @param userID is the userID to attempt to reconnect
     */
    void attemptToReconnect(String userID);

    /**
     *
     * @param username is the username of the client
     * @throws UsernameAlreadyUsedException if the name has already been chosen
     */
    void newConnectionRequest(String username) throws UsernameAlreadyUsedException;

    /**
     * Method to set connection
     */
    void setConnected();

    /**
     *
     * @param userIDToReConnect is the userID to reconnect
     * @throws InvalidUserIDException if the user is not valid
     */
    void reConnectRequest(String userIDToReConnect) throws InvalidUserIDException;
}
