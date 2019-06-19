package network.messages.error;

public class ConnectionError extends ErrorMessage{

    public ConnectionError(String info){
        super(info);
        this.content="ConnectionError";
    }
}
