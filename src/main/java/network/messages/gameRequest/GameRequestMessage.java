package network.messages.gameRequest;

import network.messages.Message;

public abstract class GameRequestMessage extends Message {

    /**
     * Method for game request generically
     * @param requiredInfo is the required info fotr type game request
     */
    public GameRequestMessage(String requiredInfo){
        this.info=requiredInfo;
        this.type="gameRequest";
    }
}
