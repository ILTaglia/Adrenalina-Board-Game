package network.messages.gameRequest;

public class MovePlayerRequest extends GameRequestMessage {
    public MovePlayerRequest(String playerreq)
    {
        super(playerreq);
        this.content="indexPlayer";
    }
}
