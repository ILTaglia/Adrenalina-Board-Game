package network.client.rmi;

//Questa classe funziona da RMI Callback, necessaria al server per inviare messaggi al connectionHandler. È questa che viene salvata sul server in coda/stanza

import network.client.Client;
import network.messages.Message;
import network.server.ClientInterface;

public class RMIConnection implements ClientInterface {

    private Client client;

    public RMIConnection(Client client) {
        this.client=client;
    }

    @Override
    public void sendMessage(Message message) {
        //TODO: CHIEDERE
        (new Thread(() -> client.handleMessage(message))).start();
    }
}
