package network.messages.error;

public class ActionError extends ErrorMessage {

    /**
     * Method for error of action
     * @param error is the info for the type of error message
     */
    public ActionError(String error){
        super(error);
        this.content="ActionError";
    }
}
