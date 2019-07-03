package network.messages.error;

public class SecondConnectionError extends ErrorMessage{

    public SecondConnectionError(String info){
        super(info);
        this.content="ConnectionError";
    }
}
