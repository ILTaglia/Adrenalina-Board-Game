package network.messages.ClientRequest;

public class PowToWeaponGrabClientRequest extends ClientRequestMessage {

    private String indexPowCard;

    public PowToWeaponGrabClientRequest(String indexWeapon,String indexPowCard,String userID){
        super(indexWeapon,userID);
        this.indexPowCard=indexPowCard;
        this.content="PowToWeaponGrabRequest";
    }

    public String getIndexPowCard() {
        return indexPowCard;
    }
}
