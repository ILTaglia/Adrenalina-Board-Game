package network.messages.error;

import network.messages.Message;

public abstract class ErrorMessage extends Message {

    /**
     * Generic method for error
     * @param info is the info for the type of error message
     */
    public ErrorMessage(String info){
        this.info=info;
        this.type="error";
    }

}
