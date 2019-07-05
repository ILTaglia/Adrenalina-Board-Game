package network.messages.playerDataMessage;

public class NewCardUsed extends InfoMessage{
    private String ToC;
    private int indexOfCard;

    /**
     * Message to notify card used
     * @param ToC is the name of the card
     * @param indexOfCard is the index of the card
     */
    public NewCardUsed(String ToC,int indexOfCard){
        super("You have used a" + ToC +" index:" + indexOfCard);
        this.ToC=ToC;
        this.indexOfCard=indexOfCard;
        this.content="NewCardUsed";
    }

    /**
     *
     * @return the name of the card
     */
    public String getToC() {
        return ToC;
    }

    /**
     *
     * @return the index of the card
     */
    public int getIndexOfCard() {
        return indexOfCard;
    }
}
