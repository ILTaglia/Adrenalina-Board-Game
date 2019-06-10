package network.messages;

import model.PowCard;

public class InfoPowCard extends ConfirmationMessage {

    private PowCard powCard;

    public InfoPowCard(PowCard powCard){
        super(powCard.getName());
        this.powCard=powCard;
        this.content="InfoPowCard";
    }

    public PowCard getPowCard() {
        return powCard;
    }
}
