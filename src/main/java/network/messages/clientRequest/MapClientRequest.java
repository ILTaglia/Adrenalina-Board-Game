package network.messages.clientRequest;


public class MapClientRequest extends ClientRequestMessage {

    public MapClientRequest(String requiredMap, String userID){
        super(requiredMap,userID);
        this.content="MapRequest";
    }

}
