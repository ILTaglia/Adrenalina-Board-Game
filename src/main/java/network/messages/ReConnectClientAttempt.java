package network.messages;

public class ReConnectClientAttempt extends ConnectionMessage {
    public ReConnectClientAttempt(String userID){
        super(userID);
        this.content="ReConnectAttempt";
    }
}
