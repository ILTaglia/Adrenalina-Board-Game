package network.messages.playerDataMessage;

public class InfoMatch extends InfoMessage {
    public InfoMatch(String info){
        super(info);
        this.content="InfoMatch";
    }
}
