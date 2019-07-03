package network.messages.connection;

public class SecondConnectionClientRequest extends ConnectionMessage{
    public SecondConnectionClientRequest(String infoToConnect){
        super(infoToConnect);
        this.content="SecondConnectionRequest";
    }
}

