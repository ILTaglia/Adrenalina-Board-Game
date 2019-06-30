package network.messages.gameRequest;

public class NextAttackRequest extends  GameRequestMessage {
    public  NextAttackRequest(String nextAttack)
    {
        super(nextAttack);
        this.content="nextAttack";
    }
}
