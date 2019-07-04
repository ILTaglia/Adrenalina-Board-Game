package network.messages.error;

public class MaxPowCardError extends ErrorMessage {
    /**
     * Method for maximum number of PowCards error
     * @param info is the info for the type of error message
     */
    public MaxPowCardError(String info){
        super(info);
        this.content="MaxPowCardError";
    }
}
