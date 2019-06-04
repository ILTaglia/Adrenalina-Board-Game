package network.messages;

public class InfoID extends ConfirmationMessage {

    public InfoID(String info){
        super(info);
        this.content="InfoID";
    }
}
