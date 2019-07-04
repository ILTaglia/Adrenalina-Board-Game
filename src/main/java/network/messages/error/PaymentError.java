package network.messages.error;

public class PaymentError extends ErrorMessage {
    /**
     * Method for payment error
     * @param error is the info for the type of error message
     */
    public PaymentError (String error)
    {
        super(error);
        this.content="PaymentError";
    }
}
