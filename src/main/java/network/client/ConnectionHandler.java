package network.client;

import network.messages.Message;

public interface ConnectionHandler {

    void registerToQueue(String username);
    void sendMessage(Message message);
}
