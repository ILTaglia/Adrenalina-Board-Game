package network.messages;

public class RunError extends ErrorMessage {

    public RunError(String error){
        super(error);
        this.content="RunError";
    }
}
