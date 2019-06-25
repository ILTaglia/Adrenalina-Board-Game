package network.messages.clientRequest;

public class CardToWeaponGrabClientRequest extends ClientRequestMessage {

    private String indexCard;

    public CardToWeaponGrabClientRequest(String indexWeapon, String indexCard, String userID){
        super(indexWeapon,userID);
        this.indexCard=indexCard;
        this.content="PowToWeaponGrabRequest";
    }

    public String getIndexCardToDiscard() {
        return indexCard;
    }
}
