package network.messages.error;

import network.messages.error.ErrorMessage;

public class GrabError extends ErrorMessage {
    public GrabError(String info){
        super(info);
        this.content="GrabError";
    }

}
