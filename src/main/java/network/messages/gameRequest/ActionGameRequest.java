package network.messages.gameRequest;

public class ActionGameRequest extends GameRequestMessage {

    public ActionGameRequest(String info){
        super(info);
        this.content="ActionRequest";
    }
}
