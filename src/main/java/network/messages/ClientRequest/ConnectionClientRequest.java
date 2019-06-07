package network.messages.ClientRequest;

import network.messages.ClientRequest.ClientRequestMessage;

public class ConnectionClientRequest extends ClientRequestMessage {

    public ConnectionClientRequest(String username){
        super(username,"noUserIDNeeded");       //TODO: trovare una soluzione più elegante
        this.content="ConnectionRequest";
    }
}
