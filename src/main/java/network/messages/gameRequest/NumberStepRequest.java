package network.messages.gameRequest;

public class NumberStepRequest extends GameRequestMessage {
    /**
     * Method for game request to ask the number of steps
     * @param stepreq is the number of steps
     */
    public NumberStepRequest (String stepreq)
    {
        super(stepreq);
        this.content="indexStep";
    }
}
