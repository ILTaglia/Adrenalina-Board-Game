package network.messages;

public abstract class ConfirmationMessage extends Message{

    public ConfirmationMessage(String info){
        this.info=info;
        this.type="Confirmation";
    }
}
