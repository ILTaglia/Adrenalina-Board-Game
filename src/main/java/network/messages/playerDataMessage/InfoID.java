package network.messages.playerDataMessage;

public class InfoID extends InfoMessage {

    public InfoID(String info){
        super("This is your ID for the game. Memorize it in case you want to rejoin after disconnection: "+info);
        this.content="InfoID";
    }
}
