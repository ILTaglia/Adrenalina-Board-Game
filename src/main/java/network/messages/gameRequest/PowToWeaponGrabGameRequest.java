package network.messages.gameRequest;

public class PowToWeaponGrabGameRequest extends GameRequestMessage {
    /**
     * Method for game request to require if want to use a PowCard to grab a weapon
     * @param request
     */
    public PowToWeaponGrabGameRequest(String request){
        super(request);
        this.content="PowToWeaponGrabRequest";
    }
}
