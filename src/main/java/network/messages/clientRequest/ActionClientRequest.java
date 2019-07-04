package network.messages.clientRequest;

public class ActionClientRequest extends ClientRequestMessage{
    /**
     * Message for requesting actions from client
     * @param chosenAction is the index of the chosen action
     * @param userID is the userID of the client that chose the action
     */
    public ActionClientRequest(String chosenAction,String userID){
        super(chosenAction,userID);
        this.content="ActionRequest";
    }
}
