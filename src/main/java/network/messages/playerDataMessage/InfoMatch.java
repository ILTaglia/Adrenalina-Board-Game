package network.messages.playerDataMessage;

public class InfoMatch extends ConfirmationMessage {

    public InfoMatch(String info){
        super(info);
        this.content="InfoMatch";
    }
}
