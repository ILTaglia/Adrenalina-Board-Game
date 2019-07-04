package network.client.socket;

import network.client.Client;
import network.client.ConnectionHandler;
import network.messages.ReConnectClientRequest;
import network.messages.clientRequest.ConnectionClientRequest;
import network.messages.Message;

public class SocketHandler implements ConnectionHandler {
    /**
     * Class that receives messages from Socket connection.
     * client is the Client
     * server is the SocketConnection
     * connectionEstablished is a boolean to check wheteìher the connection is established or not
     */
    private Client client;
    private SocketConnection server;
    private boolean connectionEstablished;

    /**
     *
     * @param serverIP is the IP of the SocketConnection
     * @param serverPort is the port of the server for Connection
     * @param client is the Client
     */
    public SocketHandler(String serverIP, int serverPort, Client client){
        this.client=client;
        this.server= new SocketConnection(serverIP,serverPort,this);
        server.start();
    }

    /**
     *
     * @param username is the username to whom send message to request connection
     */
    @Override
    public void registerToWR(String username) {
        sendMessage(new ConnectionClientRequest(username));
    }

    /**
     * Method to send messages from server to client
     * @param message is the message sent from server to client
     */
    @Override
    public void sendMessage(Message message) {
        server.sendMessage(message);
    }

    //METODO USATO DAL SocketConnection per inviare messaggio da gestire al connectionHandler

    /**
     * Method to send messages from SocketConnection to ConnectionHandler
     * @param message is the message sent from SocketConnection to ConnectionHandler
     */
    public void handleMessage(Message message){
        client.handleMessage(message);
    }

    /**
     * Method to ask to try to reconnect
     */
    @Override
    public void askToTryToReconnect() {
        server.closeSocket();
        client.askToTryToReconnect();
    }

    /**
     * Method to try to reconnect
     * @param userID is the ID of the host that wants to reconnect
     */
    @Override
    public void attemptToReconnect(String userID) {
        sendMessage(new ReConnectClientRequest(userID));
    }

    /**
     *
     * @param username is the username of the host
     */
    @Override
    public void newConnectionRequest(String username) {

    }

    /**
     * Method to set connected true (connect the player)
     */
    @Override
    public void setConnected() {
        connectionEstablished=true;
    }

    /**
     * Method to reconnect a disconnected host
     * @param userIDToReConnect is the ID of the user tha asked for reconnection
     */
    @Override
    public void reConnectRequest(String userIDToReConnect) {
        Message reConnectRequest = new ReConnectClientRequest(userIDToReConnect);      // lo USERNAME VIENE RIASSEGNATO AUTOMATICAMENTE!
        client.sendMessage(reConnectRequest);
    }

}
