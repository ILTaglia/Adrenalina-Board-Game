package network.messages.playerDataMessage;

import model.PowCard;

/**
 * Message for a new PowCard to the player
 */
public class NewPowCard extends InfoMessage {

    private PowCard powCard;

    public NewPowCard(PowCard powCard){
        super("You have drawn a new PowCard. Name: "+ powCard.getName() + "\nColor: "+ powCard.getColorAsString()+"\n");
        this.powCard=powCard;
        this.content="NewPowCard";
    }

    /**
     *
     * @return the PowCard
     */
    public PowCard getPowCard() {
        return powCard;
    }
}
