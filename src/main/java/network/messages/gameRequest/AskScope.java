package network.messages.gameRequest;

public class AskScope extends  GameRequestMessage {
    /**
     * Method for game request to use scope
     * @param scopeid is the selected scope
     */
    public AskScope (String scopeid)
    {
        super(scopeid);
        this.content="idScope";
    }
}
