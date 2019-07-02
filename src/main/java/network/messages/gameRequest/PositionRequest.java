package network.messages.gameRequest;

public class PositionRequest extends GameRequestMessage {
    public PositionRequest (String posreq)
    {
        super(posreq);
        this.content="indexPos";
    }
}
