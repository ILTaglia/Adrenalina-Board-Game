package network.messages.connection;

public class SecondConnectionClientRequest extends ConnectionMessage{
    /**
     * Method for second connection request
     * @param infoToConnect is the info for the type of message
     */
    public SecondConnectionClientRequest(String infoToConnect){
        super(infoToConnect);
        this.content="SecondConnectionRequest";
    }
}

