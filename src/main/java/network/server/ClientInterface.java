package network.server;

import network.messages.InfoID;

public interface ClientInterface {
    void sendMessage(InfoID infoID);

}
