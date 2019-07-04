package network.messages.gameRequest;

public class ActionGameRequest extends GameRequestMessage {

    /**
     * Method for requesting action
     * @param info is the info for the type of game request message
     */
    public ActionGameRequest(String info){
        super(info);
        this.content="ActionRequest";
    }
}
