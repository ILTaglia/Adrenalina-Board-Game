package network.messages;

public class ReConnectClientRequest extends ConnectionMessage {
    public ReConnectClientRequest(String infoToConnect){
        super(infoToConnect);
        this.content="ReconnectRequest";
    }
}
