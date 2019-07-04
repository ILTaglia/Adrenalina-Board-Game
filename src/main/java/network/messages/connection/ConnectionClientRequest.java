package network.messages.connection;

public class ConnectionClientRequest extends ConnectionMessage {

    /**
     * Message for requesting connection
     * @param username is the client username
     */
    public ConnectionClientRequest(String username){
        super(username);
        this.content="ConnectionRequest";
    }
}
