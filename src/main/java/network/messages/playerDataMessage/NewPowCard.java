package network.messages.playerDataMessage;

import model.PowCard;

public class NewPowCard extends InfoMessage {

    private PowCard powCard;

    public NewPowCard(PowCard powCard){
        super("You have drawn a new PowCard. Name: "+ powCard.getName() + "\nColor: "+ powCard.getColorAsString()+"\n");
        this.powCard=powCard;
        this.content="NewPowCard";
    }

    public PowCard getPowCard() {
        return powCard;
    }
}
