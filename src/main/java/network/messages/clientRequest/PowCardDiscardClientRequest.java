package network.messages.clientRequest;

public class PowCardDiscardClientRequest extends ClientRequestMessage {
    public PowCardDiscardClientRequest(String indexPowCard, String userID) {
        super(indexPowCard,userID);
        this.content="PowCardDiscardRequest";
    }
}
