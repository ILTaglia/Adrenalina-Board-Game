package network.messages.clientRequest;

public class PowCardDiscardClientRequest extends ClientRequestMessage {
    /**
     * Message for requesting to discard a PowCard
     * @param indexPowCard is the chosen PowCard to discard
     * @param userID is the userID
     */
    public PowCardDiscardClientRequest(String indexPowCard, String userID) {
        super(indexPowCard,userID);
        this.content="PowCardDiscardRequest";
    }
}
