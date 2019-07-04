package network.messages.error;

public class SecondConnectionError extends ErrorMessage{

    /**
     * Method for second connection error
     * @param info is the info for the type of error message
     */
    public SecondConnectionError(String info){
        super(info);
        this.content="ConnectionError";
    }
}
