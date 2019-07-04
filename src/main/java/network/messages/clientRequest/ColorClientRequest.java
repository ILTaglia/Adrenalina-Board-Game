package network.messages.clientRequest;

public class ColorClientRequest extends ClientRequestMessage {

    /**
     * Message for requesting color to client
     * @param requestedColor is the required color for the player
     * @param userID is the userID
     */
    public ColorClientRequest(String requestedColor,String userID){
        super(requestedColor,userID);
        this.content="ColorRequest";
    }

}
