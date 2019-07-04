package network.messages.gameRequest;

public class NextAttackRequest extends  GameRequestMessage {
    /**
     * Method for game request to ask for next attack
     * @param nextAttack is the next attack to be performed
     */
    public  NextAttackRequest(String nextAttack)
    {
        super(nextAttack);
        this.content="nextAttack";
    }
}
