package network.messages.gameRequest;

public class DirRequest extends GameRequestMessage {
    /**
     * Method for game request for direction
     * @param directionask to ask for direction
     */
    public DirRequest(String directionask)
    {
        super(directionask);
        this.content="indexDir";
    }
}
