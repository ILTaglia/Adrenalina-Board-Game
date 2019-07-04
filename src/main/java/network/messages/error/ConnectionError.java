package network.messages.error;

import network.messages.error.ErrorMessage;

public class ConnectionError extends ErrorMessage {
    /**
     * Method for connection error
     * @param info is the info for the type of error message
     */
    public ConnectionError(String info){
        super(info);
        this.content="ConnectionError";
    }
}
