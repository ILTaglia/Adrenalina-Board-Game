package network.messages.playerDataMessage;

public class NewCardUsed extends InfoMessage{
    private String ToC;
    private int indexOfCard;

    public NewCardUsed(String ToC,int indexOfCard){
        super("You have used a" + ToC +" index:" + indexOfCard);
        this.ToC=ToC;
        this.indexOfCard=indexOfCard;
        this.content="NewCardUsed";
    }

    public String getToC() {
        return ToC;
    }

    public int getIndexOfCard() {
        return indexOfCard;
    }
}