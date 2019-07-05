package network.messages.playerDataMessage;

/**
 * Info message for info data about player
 */
public class InfoID extends InfoMessage {

    public InfoID(String info){
        super(info);
        this.content="InfoID";
    }
}
