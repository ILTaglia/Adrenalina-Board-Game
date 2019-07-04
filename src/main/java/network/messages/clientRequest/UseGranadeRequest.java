package network.messages.clientRequest;

public class UseGranadeRequest extends ClientRequestMessage {
    /**
     * Message for requesting to use granade
     * @param index is the chosen index
     * @param playerID is the userID
     */
    public UseGranadeRequest(String index, String playerID)
    {
        super(index,playerID);
        this.content="idGranade";
    }
}
