package network.messages.gameRequest;

public class IndexPowRequest extends GameRequestMessage {
    public IndexPowRequest (String powrequest)
    {
        super(powrequest);
        this.content="indexPow";
    }
}
