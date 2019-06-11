package network.messages;

public class WeaponGrabGameRequest extends GameRequestMessage {

    public WeaponGrabGameRequest(String request){
        super(request);
        this.content="WeaponGrabRequest";
    }
}
