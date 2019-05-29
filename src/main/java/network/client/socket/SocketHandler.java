package network.client.socket;

import network.client.Client;
import network.client.ConnectionHandler;
import network.messages.ConnectionError;
import network.messages.Message;
import network.server.rmi.ServerInterface;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

    //TODO Aggiungere Booleani per verifiche sulla connessione


public class SocketHandler implements ConnectionHandler {

    private Client client;
    private ServerInterface server;

    @Override
    public void registerToQueue(String username) {

    }

    @Override
    public void sendMessage(Message message) {

    }


}
