package network.messages;

public class ActionError extends ErrorMessage {

    public ActionError(String error){
        super(error);
        this.content="ActionError";
    }
}
