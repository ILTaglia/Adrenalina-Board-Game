package network.messages;

public class InfoPlayer extends ConfirmationMessage {
    public InfoPlayer(String info){
        super(info);
        this.content="PlayerData";

    }
}
