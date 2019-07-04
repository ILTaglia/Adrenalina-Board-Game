package network.messages.clientRequest;

public class IndexForShootRequest extends ClientRequestMessage {
    /**
     * Message for requesting index for shooting
     * @param index is the index of the weapon
     * @param playerID is the userID
     */
    public IndexForShootRequest(String index, String playerID)
    {
        super(index,playerID);
        this.content="shootIndex";
    }
}
