package network.messages;

public class InfoMatch extends ConfirmationMessage {

    public InfoMatch(String info){
        super(info);
        this.content="InfoMatch";
    }
}
