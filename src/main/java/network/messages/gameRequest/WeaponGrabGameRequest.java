package network.messages.gameRequest;

public class WeaponGrabGameRequest extends GameRequestMessage {

    /**
     * Method for game request for grabbing weapon
     * @param request
     */
    public WeaponGrabGameRequest(String request){
        super(request);
        this.content="WeaponGrabRequest";
    }
}
