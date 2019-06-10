package network.messages.ClientRequest;

import model.PowCard;

public class SpawnPointClientRequest extends ClientRequestMessage{

    private PowCard powCard;

    public SpawnPointClientRequest(PowCard powCard, String playerID){
        super(Integer.toString(powCard.getColor()),playerID);
        this.powCard=powCard;
        this.content="SpawnPointRequest";
    }

    public PowCard getPowCard() {
        return powCard;
    }
}
