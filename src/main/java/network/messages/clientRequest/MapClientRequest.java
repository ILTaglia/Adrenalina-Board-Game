package network.messages.clientRequest;


public class MapClientRequest extends ClientRequestMessage {

    /**
     * Message for requesting the type of map
     * @param requiredMap is the index of the required map
     * @param userID is the userID
     */
    public MapClientRequest(String requiredMap, String userID){
        super(requiredMap,userID);
        this.content="MapRequest";
    }

}
