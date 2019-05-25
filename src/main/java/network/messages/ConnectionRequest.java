package network.messages;

public class ConnectionRequest extends RequestMessage{

    public ConnectionRequest(String username){
        super(username);
        this.content="Connection Request";
    }
}
