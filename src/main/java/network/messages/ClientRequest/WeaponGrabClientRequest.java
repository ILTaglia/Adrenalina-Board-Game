package network.messages.ClientRequest;

public class WeaponGrabClientRequest extends ClientRequestMessage {
    public WeaponGrabClientRequest(String indexWeapon,String userID){
        super(indexWeapon,userID);
        this.content="WeaponGrabRequest";
    }
}
