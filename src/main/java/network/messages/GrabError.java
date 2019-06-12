package network.messages;

public class GrabError extends ErrorMessage {
    public GrabError(String info){
        super(info);
        this.content="GrabError";
    }

}
