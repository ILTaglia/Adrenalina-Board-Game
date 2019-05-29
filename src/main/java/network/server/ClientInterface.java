package network.server;

import network.messages.Message;

import java.rmi.Remote;

public interface ClientInterface extends Remote {
    void sendMessage(Message message);

}
