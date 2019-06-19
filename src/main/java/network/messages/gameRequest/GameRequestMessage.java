package network.messages.gameRequest;

import network.messages.Message;

public abstract class GameRequestMessage extends Message {

    public GameRequestMessage(String requiredInfo){
        this.info=requiredInfo;
        this.type="gameRequest";
    }
}
