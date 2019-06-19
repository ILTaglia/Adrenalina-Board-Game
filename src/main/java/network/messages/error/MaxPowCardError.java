package network.messages.error;

public class MaxPowCardError extends ErrorMessage {
    public MaxPowCardError(String info){
        super(info);
        this.content="MaxPowCardError";
    }
}
