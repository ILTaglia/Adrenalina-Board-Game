package network.messages.gameRequest;

public class WeaponGrabGameRequest extends GameRequestMessage {

    public WeaponGrabGameRequest(String request){
        super(request);
        this.content="WeaponGrabRequest";
    }
}
