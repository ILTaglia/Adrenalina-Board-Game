package network.messages.gameRequest;

public class RunDirectionGameRequest extends GameRequestMessage {
    /**
     * Method for game request to ask direction for run
     * @param request is the required direction
     */
    public RunDirectionGameRequest(String request) {
        super(request);
        this.content="RunDirectionRequest";
    }
}
