package network.messages.clientRequest;

public class WeaponGrabClientRequest extends ClientRequestMessage {
    public WeaponGrabClientRequest(String indexWeapon,String userID){
        super(indexWeapon,userID);
        this.content="WeaponGrabRequest";
    }
}
