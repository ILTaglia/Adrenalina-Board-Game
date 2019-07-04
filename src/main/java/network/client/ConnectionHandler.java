package network.client;

import exceptions.UsernameAlreadyUsedException;
import network.messages.Message;

public interface ConnectionHandler {

    /**
     * Method to register client's username
     * @param username is the username chosen by the Client
     * @throws UsernameAlreadyUsedException if the name has already been chosen by an other player
     */
    void registerToWR(String username) throws UsernameAlreadyUsedException;

    /**
     * Method to send messages
     * @param message is the message to be sent
     */
    void sendMessage(Message message);

    /**
     * Method to ask to try for reconnection
     */
    void askToTryToReconnect();

    /**
     * Method to attempt reconnection
     * @param userID is the userID of the client that want to reconnect
     */
    void attemptToReconnect(String userID);

    /**
     * Method for a new connection request
     * @param username is the username of the client that requested the new connection
     * @throws UsernameAlreadyUsedException if the name has already been chosen by an other player
     */
    void newConnectionRequest(String username) throws UsernameAlreadyUsedException;

    /**
     * Method to set connection
     */
    void setConnected();

    /**
     * Method to request for reconnection
     * @param userIDToReConnect is the userID of the client that wants to reconnect
     */
    void reConnectRequest(String userIDToReConnect);
}
