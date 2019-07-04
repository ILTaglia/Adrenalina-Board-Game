package network.messages.gameRequest;

public class IndexPowRequest extends GameRequestMessage {
    /**
     * Method for game request to choose index of PowCard
     * @param powrequest is the required PowCard
     */
    public IndexPowRequest (String powrequest)
    {
        super(powrequest);
        this.content="indexPow";
    }
}
