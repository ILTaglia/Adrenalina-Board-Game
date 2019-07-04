package network.messages.gameRequest;

public class PositionRequest extends GameRequestMessage {
    /**
     * Method for game request position
     * @param posreq is the required position
     */
    public PositionRequest (String posreq)
    {
        super(posreq);
        this.content="indexPos";
    }
}
