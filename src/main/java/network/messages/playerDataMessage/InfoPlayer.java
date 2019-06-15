package network.messages.playerDataMessage;

public class InfoPlayer extends InfoMessage {
    public InfoPlayer(String info){
        super(info);
        this.content="InfoPlayer";
    }
}
