package network.messages.error;

import network.messages.error.ErrorMessage;

public class ConnectionError extends ErrorMessage {
    public ConnectionError(String info){
        super(info);
        this.content="ConnectionError";
    }
}
