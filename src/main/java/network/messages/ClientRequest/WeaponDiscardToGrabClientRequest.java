package network.messages.ClientRequest;

public class WeaponDiscardToGrabClientRequest extends ClientRequestMessage{

    public WeaponDiscardToGrabClientRequest(String indexWeapon, String userID){
        super(indexWeapon,userID);
        this.content="WeaponDiscardToGrabRequest";
    }

}
