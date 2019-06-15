package network.messages.playerDataMessage;

import network.messages.Message;

public abstract class ConfirmationMessage extends Message {

    public ConfirmationMessage(String info){
        this.info=info;
        this.type="confirmation";
    }
}
