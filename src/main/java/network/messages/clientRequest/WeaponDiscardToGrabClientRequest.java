package network.messages.clientRequest;

public class WeaponDiscardToGrabClientRequest extends ClientRequestMessage{

    /**
     * Message for requesting to discard a weapon (to allow grabbing)
     * @param indexWeapon is the index of weapon
     * @param userID is the userID
     */
    public WeaponDiscardToGrabClientRequest(String indexWeapon, String userID){
        super(indexWeapon,userID);
        this.content="WeaponDiscardToGrabRequest";
    }

}
