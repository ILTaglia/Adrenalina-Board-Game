package network.messages.connection;

public class ReConnectClientAttempt extends ConnectionMessage {
    /**
     * Message for attempting reconnection
     * @param userID is the userID
     */
    public ReConnectClientAttempt(String userID){
        super(userID);
        this.content="ReConnectAttempt";
    }
}
