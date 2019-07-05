package network.messages.playerDataMessage;

/**
 * Message for info about the match
 */
public class InfoMatch extends InfoMessage {
    public InfoMatch(String info){
        super(info);
        this.content="InfoMatch";
    }
}
