package network.messages.error;

import network.messages.error.ErrorMessage;

public class RunError extends ErrorMessage {

    public RunError(String error){
        super(error);
        this.content="RunError";
    }
}
