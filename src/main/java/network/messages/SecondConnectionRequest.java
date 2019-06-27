package network.messages;

public class SecondConnectionRequest extends ConnectionMessage{
    public SecondConnectionRequest(String infoToConnect){
        super(infoToConnect);
        this.content="ReconnectRequest";
    }
}

