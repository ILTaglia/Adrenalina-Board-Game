package network.messages.gameRequest;

public class AskScope extends  GameRequestMessage {
    public AskScope (String scopeid)
    {
        super(scopeid);
        this.content="idScope";
    }
}
