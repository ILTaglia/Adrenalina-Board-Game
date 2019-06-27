package network.messages;

public class ReConnectClientRequest extends ConnectionMessage {

    private String userIDToReconnect;

    public ReConnectClientRequest(String infoToConnect,String userIDToReconnect){
        super(infoToConnect);
        this.userIDToReconnect=userIDToReconnect;
        this.content="ReConnectRequest";
    }

    public String getUserIDToReconnect() {
        return userIDToReconnect;
    }
}
