package network.messages.ClientRequest;

public class ActionClientRequest extends ClientRequestMessage{
    public ActionClientRequest(String chosenAction,String userID){
        super(chosenAction,userID);
        this.content="ActionClientRequest";
    }
}
