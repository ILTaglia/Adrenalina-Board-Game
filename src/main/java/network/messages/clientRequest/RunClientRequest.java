package network.messages.clientRequest;

import java.util.List;

public class RunClientRequest extends ClientRequestMessage {

    private List<String> direction;

    /**
     * Message for requesting to run
     * @param direction is the destination required
     * @param playerID is the userID
     */
    public RunClientRequest(List<String> direction,String playerID){
        super(Integer.toString(direction.size()),playerID);
        this.direction=direction;
        this.content="RunRequest";
    }

    public List<String> getDirection() {
        return direction;
    }
}
