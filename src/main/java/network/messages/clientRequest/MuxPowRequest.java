package network.messages.clientRequest;

public class MuxPowRequest extends ClientRequestMessage {
    public MuxPowRequest (String index, String userID)
    {
        super(index,userID);
        this.content="muxIndex";
    }
}
