package network.messages.clientRequest;

public class WeaponGrabClientRequest extends ClientRequestMessage {
    /**
     * Message for requesting to grab weapon
     * @param indexWeapon is the index of weapon
     * @param userID is the userID
     */
    public WeaponGrabClientRequest(String indexWeapon,String userID){
        super(indexWeapon,userID);
        this.content="WeaponGrabRequest";
    }
}
