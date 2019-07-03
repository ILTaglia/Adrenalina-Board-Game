package network.messages.connection;

public class ConnectionClientRequest extends ConnectionMessage {

    public ConnectionClientRequest(String username){
        super(username);
        this.content="ConnectionRequest";
    }
}
