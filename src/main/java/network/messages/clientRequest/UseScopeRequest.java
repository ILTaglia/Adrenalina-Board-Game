package network.messages.clientRequest;

public class UseScopeRequest extends ClientRequestMessage {
    /**
     * Message for requesting to use scope
     * @param index is the chosen index
     * @param playerID is the userID
     */
    public UseScopeRequest (String index, String playerID)
    {
        super(index,playerID);
        this.content="idScope";
    }
}
