package network.client;

import exceptions.UsernameAlreadyUsedException;
import network.messages.Message;

public interface ConnectionHandler {

    void registerToWR(String username) throws UsernameAlreadyUsedException;
    void sendMessage(Message message);
}
