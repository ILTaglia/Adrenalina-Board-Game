package network.messages.clientRequest;

public class CardToWeaponGrabClientRequest extends ClientRequestMessage {

    private String indexCard;

    /**
     * Message for requesting which weapon to grab
     * @param indexWeapon is the chosen weapon to grab
     * @param indexCard is the PowCard that the player wants to convert to buy a PowCard
     * @param userID is the userID
     */
    public CardToWeaponGrabClientRequest(String indexWeapon, String indexCard, String userID){
        super(indexWeapon,userID);
        this.indexCard=indexCard;
        this.content="PowToWeaponGrabRequest";
    }

    public String getIndexCardToDiscard() {
        return indexCard;
    }
}
