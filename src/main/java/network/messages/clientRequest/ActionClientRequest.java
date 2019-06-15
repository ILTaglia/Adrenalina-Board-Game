package network.messages.clientRequest;

public class ActionClientRequest extends ClientRequestMessage{
    public ActionClientRequest(String chosenAction,String userID){
        super(chosenAction,userID);
        this.content="ActionRequest";
    }
}
