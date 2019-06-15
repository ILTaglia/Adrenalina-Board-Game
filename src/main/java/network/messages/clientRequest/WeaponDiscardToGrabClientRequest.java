package network.messages.clientRequest;

public class WeaponDiscardToGrabClientRequest extends ClientRequestMessage{

    public WeaponDiscardToGrabClientRequest(String indexWeapon, String userID){
        super(indexWeapon,userID);
        this.content="WeaponDiscardToGrabRequest";
    }

}
