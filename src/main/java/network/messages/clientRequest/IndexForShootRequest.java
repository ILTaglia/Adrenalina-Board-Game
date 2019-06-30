package network.messages.clientRequest;

public class IndexForShootRequest extends ClientRequestMessage {
    public IndexForShootRequest(String index, String playerID)
    {
        super(index,playerID);
        this.content="shootIndex";
    }
}
