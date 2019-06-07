package network.messages.ClientRequest;


import network.messages.ClientRequest.ClientRequestMessage;

public class MapClientRequest extends ClientRequestMessage {

    public MapClientRequest(String requiredMap, String userID){
        super(requiredMap,userID);
        this.content="MapRequest";
    }

}
