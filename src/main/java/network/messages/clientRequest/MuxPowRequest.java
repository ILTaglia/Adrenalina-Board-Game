package network.messages.clientRequest;

public class MuxPowRequest extends ClientRequestMessage {

    /**
     * Message for requesting PowCards
     * @param index is the chosen index
     * @param userID is the userID
     */
    public MuxPowRequest (String index, String userID)
    {
        super(index,userID);
        this.content="muxIndex";
    }
}
