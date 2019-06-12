package network.messages;

public class MaxPowCardError extends ErrorMessage {
    public MaxPowCardError(String info){
        super(info);
        this.content="MaxPowCardError";
    }
}
