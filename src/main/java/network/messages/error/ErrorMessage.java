package network.messages.error;

import network.messages.Message;

public abstract class ErrorMessage extends Message {

    public ErrorMessage(String info){
        this.info=info;
        this.type="error";
    }

}
