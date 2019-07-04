package network.messages.error;

public class ColorError extends ErrorMessage {

    /**
     * Method for color error
     * @param info is the info for the type of error message
     */
    public ColorError(String info) {
        super(info);
        this.content="ColorError";
    }
}
