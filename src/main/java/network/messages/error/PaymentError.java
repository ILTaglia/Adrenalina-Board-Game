package network.messages.error;

public class PaymentError extends ErrorMessage {
    public PaymentError (String error)
    {
        super(error);
        this.content="PaymentError";
    }
}
