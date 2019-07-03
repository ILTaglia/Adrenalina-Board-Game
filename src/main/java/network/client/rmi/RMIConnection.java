package network.client.rmi;

//Questa classe funziona da RMI Callback, necessaria al server per inviare messaggi al connectionHandler. È questa che viene salvata sul server in coda/stanza

import network.client.Client;
import network.messages.Message;
import network.server.ClientInterface;

import java.rmi.RemoteException;
import java.util.Timer;
import java.util.TimerTask;


public class RMIConnection implements ClientInterface {

    private String playerID;
    private Client client;
    private boolean connected;

    public RMIConnection(Client client) {
        this.client=client;

    }

    @Override
    public void setPlayerID(String playerID) {
        this.playerID=playerID;
    }
    @Override
    public String getPlayerID() {
        return this.playerID;
    }

    @Override
    public void sendMessage(Message message) {
        (new Thread(() -> client.handleMessage(message))).start();
    }

    @Override
    public void setConnection(boolean connected) {
        this.connected=connected;
    }

    @Override
    public void closeConnection() {

    }

    @Override
    public void requestToReconnect() {

    }

}