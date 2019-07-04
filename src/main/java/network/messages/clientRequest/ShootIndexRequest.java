package network.messages.clientRequest;

public class ShootIndexRequest extends ClientRequestMessage{
    /**
     * Message for requesting shooting
     * @param index is the chosen index
     * @param playerID is the userID
     */
    public ShootIndexRequest(String index, String playerID)
    {
        super(index,playerID);
        this.content="shootingIndexRequest";
    }
}
