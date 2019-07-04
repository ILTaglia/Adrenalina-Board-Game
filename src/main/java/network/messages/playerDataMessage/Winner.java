package network.messages.playerDataMessage;

public class Winner extends InfoMessage {
    public Winner(String playerWinnerName){
        super("Winner player is: "+playerWinnerName +".\n End of the game.");
        this.content="WinnerMessage";
    }
}
