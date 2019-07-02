package network.messages.gameRequest;

public class DirRequest extends GameRequestMessage {
    public DirRequest(String directionask)
    {
        super(directionask);
        this.content="indexDir";
    }
}
