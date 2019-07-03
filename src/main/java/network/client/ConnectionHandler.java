package network.client;

import exceptions.UsernameAlreadyUsedException;
import network.messages.Message;

public interface ConnectionHandler {

    void registerToWR(String username) throws UsernameAlreadyUsedException;

    void sendMessage(Message message);

    void askToTryToReconnect();

    void attemptToReconnect(String userID);

    void newConnectionRequest(String username) throws UsernameAlreadyUsedException;

    void setConnected();
}
