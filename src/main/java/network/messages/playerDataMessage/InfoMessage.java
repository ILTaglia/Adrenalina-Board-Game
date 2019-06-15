package network.messages.playerDataMessage;

import network.messages.Message;

public abstract class InfoMessage extends Message {

    public InfoMessage(String info){
        this.info=info;
        this.type="infoGame";
    }
}
