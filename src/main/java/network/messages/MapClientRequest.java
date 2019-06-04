package network.messages;



public class MapClientRequest extends ClientRequestMessage {

    public MapClientRequest(String requiredMap, String userID){
        super(requiredMap,userID);
        this.content="MapRequest";
    }

}
