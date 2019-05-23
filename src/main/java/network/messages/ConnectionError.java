package network.messages;

public class ConnectionError extends ErrorMessage{

    public ConnectionError(String info){
        super(info);
        this.content="ConnectionError";
    }
}
