package network.messages.error;

public class ActionError extends ErrorMessage {

    public ActionError(String error){
        super(error);
        this.content="ActionError";
    }
}
