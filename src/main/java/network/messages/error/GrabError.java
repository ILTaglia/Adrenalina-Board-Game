package network.messages.error;

import network.messages.error.ErrorMessage;

public class GrabError extends ErrorMessage {
    /**
     * Method for grabbing error
     * @param info is the info for the type of error message
     */
    public GrabError(String info){
        super(info);
        this.content="GrabError";
    }

}
