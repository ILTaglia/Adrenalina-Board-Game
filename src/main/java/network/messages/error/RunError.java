package network.messages.error;

import network.messages.error.ErrorMessage;

public class RunError extends ErrorMessage {

    /**
     * Method for running error
     * @param error is the info for the type of error message
     */
    public RunError(String error){
        super(error);
        this.content="RunError";
    }
}
