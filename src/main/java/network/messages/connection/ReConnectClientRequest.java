package network.messages.connection;

public class ReConnectClientRequest extends ConnectionMessage {

    public ReConnectClientRequest(String infoToConnect){
        super(infoToConnect);
        this.content="ReConnectRequest";
    }
}
