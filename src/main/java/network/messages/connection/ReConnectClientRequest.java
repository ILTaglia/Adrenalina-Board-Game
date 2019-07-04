package network.messages.connection;

public class ReConnectClientRequest extends ConnectionMessage {

    /**
     * Message for requesting to attempt reconnection
     * @param infoToConnect is the info for the type of message
     */
    public ReConnectClientRequest(String infoToConnect){
        super(infoToConnect);
        this.content="ReConnectRequest";
    }
}
