package network.messages.clientRequest;

public class ShootIndexRequest extends ClientRequestMessage{
    public ShootIndexRequest(String index, String playerID)
    {
        super(index,playerID);
        this.content="shootingIndexRequest";
    }
}
