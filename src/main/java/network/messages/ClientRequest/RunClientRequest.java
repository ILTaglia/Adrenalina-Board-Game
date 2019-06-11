package network.messages.ClientRequest;

import java.util.List;

public class RunClientRequest extends ClientRequestMessage {

    private List<String> direction;

    public RunClientRequest(List<String> direction,String playerID){
        super(Integer.toString(direction.size()),playerID);
        this.direction=direction;
        this.content="RunRequest";
    }

    public List<String> getDirection() {
        return direction;
    }
}
