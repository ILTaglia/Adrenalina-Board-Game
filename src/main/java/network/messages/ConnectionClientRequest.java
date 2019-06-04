package network.messages;

public class ConnectionClientRequest extends ClientRequestMessage {

    public ConnectionClientRequest(String username){
        super(username,"noUserIDNeeded");       //TODO: trovare una soluzione più elegante
        this.content="ConnectionRequest";
    }
}
