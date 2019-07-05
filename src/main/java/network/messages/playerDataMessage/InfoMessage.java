package network.messages.playerDataMessage;

import network.messages.Message;

/**
 * Generic info message
 */
public abstract class InfoMessage extends Message {

    public InfoMessage(String info){
        this.info=info;
        this.type="infoGame";
    }
}
