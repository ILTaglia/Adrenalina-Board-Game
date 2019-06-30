package network.messages.gameRequest;

public class PlayerIndexRequest extends  GameRequestMessage {
    public PlayerIndexRequest (String playerIndex)
    {
        super(playerIndex);
        this.content="PlayerIndex";
    }
}
