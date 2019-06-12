package network.messages.ClientRequest;

import network.messages.ClientRequest.ClientRequestMessage;

public class PowCardDiscardClientRequest extends ClientRequestMessage {
    public PowCardDiscardClientRequest(String indexPowCard, String userID) {
        super(indexPowCard,userID);
        this.content="PowCardDiscardRequest";
    }
}
