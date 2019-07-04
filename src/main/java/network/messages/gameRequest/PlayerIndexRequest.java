package network.messages.gameRequest;

public class PlayerIndexRequest extends  GameRequestMessage {
    /**
     * Method for game request to ask the player index
     * @param playerIndex is the required player index
     */
    public PlayerIndexRequest (String playerIndex)
    {
        super(playerIndex);
        this.content="PlayerIndex";
    }
}
