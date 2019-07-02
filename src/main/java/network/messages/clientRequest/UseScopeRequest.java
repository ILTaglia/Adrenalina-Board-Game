package network.messages.clientRequest;

public class UseScopeRequest extends ClientRequestMessage {
    public UseScopeRequest (String index, String playerID)
    {
        super(index,playerID);
        this.content="idScope";
    }
}
