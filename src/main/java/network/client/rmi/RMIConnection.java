package network.client.rmi;

//Questa classe funziona da RMI Callback, necessaria al server per inviare messaggi al connectionHandler. È questa che viene salvata sul server in coda/stanza

import network.client.Client;
import network.messages.Message;
import network.messages.ReConnectServerRequest;
import network.server.ClientInterface;

import static utils.Print.printOut;


public class RMIConnection implements ClientInterface {
    /**
     * This class is the RMI Callback, it is useful to send messages to connectionHandler.
     * This is saved in the queue/room
     *
     * playerID is the user ID
     * client is the Client
     * connected is a boolean to verify the client is connected or not.
     */

    private String playerID;
    private Client client;
    private boolean connected;

    /**
     *
     * @param client is the client to be connected
     */
    public RMIConnection(Client client) {
        this.client=client;

    }

    /**
     * Method to set the player ID
     * @param playerID is the player ID to be set
     */
    @Override
    public void setPlayerID(String playerID) {
        this.playerID=playerID;
    }

    /**
     * Method to have the player ID
     * @return the player ID
     */
    @Override
    public String getPlayerID() {
        return this.playerID;
    }

    /**
     * Method to send a message
     * @param message is the message to be send
     */
    @Override
    public void sendMessage(Message message) {
        (new Thread(() -> client.handleMessage(message))).start();
    }

    /**
     * Method to set connected
     * @param connected is true or false if the client is connected or not
     */
    @Override
    public void setConnection(boolean connected) {
        this.connected=connected;
    }

    /**
     * Method to close connection, in RMI there is nothing to do. Only connected flag set to false.
     */
    @Override
    public void closeConnection() {

    }

    /**
     * Method to request for reconnection
     */
    @Override
    public void requestToReconnect() {
        Message reConnectRequest = new ReConnectServerRequest();
        sendMessage(reConnectRequest);
    }

    /**
     * Method to set the Client connected (start connection of the Client)
     */
    @Override
    public void setClientConnected() {
        (new Thread(() -> client.setConnected())).start();
    }

}