package network.client.socket;

import network.client.Client;
import network.client.ConnectionHandler;
import network.messages.ReConnectClientRequest;
import network.messages.ConnectionClientRequest;
import network.messages.Message;

public class SocketHandler implements ConnectionHandler {

    private Client client;
    private SocketConnection server;
    private boolean connectionEstablished;

    public SocketHandler(String serverIP, int serverPort, Client client){
        this.client=client;
        this.server= new SocketConnection(serverIP,serverPort,this);
        server.start();
    }

    @Override
    public void registerToWR(String username) {
        sendMessage(new ConnectionClientRequest(username));
    }
    //METODO USATO DAL SERVER PER INVIARE MESSAGGI AL CLIENT
    @Override
    public void sendMessage(Message message) {
        server.sendMessage(message);
    }

    //METODO USATO DAL SocketConnection per inviare messaggio da gestire al connectionHandler
    public void handleMessage(Message message){
        client.handleMessage(message);
    }

    @Override
    public void askToTryToReconnect() {
        server.closeSocket();
        client.askToTryToReconnect();
    }

    @Override
    public void attemptToReconnect(String userID) {
        sendMessage(new ReConnectClientRequest(userID));
    }

    @Override
    public void newConnectionRequest(String username) {

    }

    @Override
    public void setConnected() {
        connectionEstablished=true;
    }

    @Override
    public void reConnectRequest(String userIDToReConnect) {
        Message reConnectRequest = new ReConnectClientRequest(userIDToReConnect);      // lo USERNAME VIENE RIASSEGNATO AUTOMATICAMENTE!
        client.sendMessage(reConnectRequest);
    }

}
