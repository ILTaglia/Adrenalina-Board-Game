package network.messages;

public class PowToWeaponGrabGameRequest extends GameRequestMessage {
    public PowToWeaponGrabGameRequest(String request){
        super(request);
        this.content="PowToWeaponGrabRequest";
    }
}
