package network.messages.playerDataMessage;

public class OtherPlayerData extends InfoMessage {

    private String playerName;
    private int playerColor;

    public OtherPlayerData(String playerName, int playerColor){
        super("Other player in your match is: "+ playerName + "with color: " + playerColor);
        this.playerName=playerName;
        this.playerColor=playerColor;
        this.content="OtherPlayerData";
    }

    public String getPlayerName(){
        return playerName;
    }
    public int getPlayerColor() {
        return playerColor;
    }
}
