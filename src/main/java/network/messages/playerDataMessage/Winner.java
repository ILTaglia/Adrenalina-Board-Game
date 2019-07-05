package network.messages.playerDataMessage;

public class Winner extends InfoMessage {
    /**
     * Message for winning player
     * @param playerWinnerName is the winner player
     */
    public Winner(String playerWinnerName){
        super("Winner player is: "+playerWinnerName +".\n End of the game.");
        this.content="WinnerMessage";
    }
}
