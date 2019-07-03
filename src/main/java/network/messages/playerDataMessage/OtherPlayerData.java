package network.messages.playerDataMessage;

public class OtherPlayerData extends InfoMessage {

    private String playerName;
    private int playerColor;

    public OtherPlayerData(String playerName, String playerColor){
        super("Other player in your match are: "+ playerName + "with color: " + playerColor);
        this.playerName=playerName;
        if(playerColor.equals("blue")) this.playerColor=0;
        if(playerColor.equals("green")) this.playerColor=1;
        if(playerColor.equals("yellow")) this.playerColor=2;
        if(playerColor.equals("pink")) this.playerColor=3;
        if(playerColor.equals("grey")) this.playerColor=4;
        this.content="OtherPlayerData";
    }

    public String getPlayerName(){
        return playerName;
    }
    public int getPlayerColor() {
        return playerColor;
    }
}
