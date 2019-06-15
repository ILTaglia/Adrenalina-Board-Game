package network.messages.playerDataMessage;

public class InfoPlayer extends ConfirmationMessage {
    public InfoPlayer(String info){
        super(info);
        this.content="PlayerData";
    }
}
