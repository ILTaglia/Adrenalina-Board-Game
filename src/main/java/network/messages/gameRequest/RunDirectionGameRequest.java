package network.messages.gameRequest;

public class RunDirectionGameRequest extends GameRequestMessage {
    public RunDirectionGameRequest(String request) {
        super(request);
        this.content="RunDirectionRequest";
    }
}