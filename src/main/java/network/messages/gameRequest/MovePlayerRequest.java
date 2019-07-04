package network.messages.gameRequest;

public class MovePlayerRequest extends GameRequestMessage {
    /**
     * Method for game request to ask for move
     * @param playerreq is the required player move
     */
    public MovePlayerRequest(String playerreq)
    {
        super(playerreq);
        this.content="indexPlayer";
    }
}
