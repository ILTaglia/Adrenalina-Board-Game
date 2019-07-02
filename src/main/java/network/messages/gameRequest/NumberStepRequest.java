package network.messages.gameRequest;

public class NumberStepRequest extends GameRequestMessage {
    public NumberStepRequest (String stepreq)
    {
        super(stepreq);
        this.content="indexStep";
    }
}
