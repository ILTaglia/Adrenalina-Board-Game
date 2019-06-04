package network.client.socket;

import network.client.Client;
import network.client.ConnectionHandler;
import network.messages.ConnectionClientRequest;
import network.messages.Message;
import network.server.rmi.ServerInterface;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

    //TODO Aggiungere Booleani per verifiche sulla connessione


public class SocketHandler implements ConnectionHandler {

    private Client client;
    private SocketConnection server; //TODO: valutare una serverInterface più generica che raccolga sia RMI che Socket

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

}
