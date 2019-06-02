package network.messages;

public class ColorError extends ErrorMessage {

    public ColorError(String info) {
        super(info);
        this.content="ColorError";
    }
}
