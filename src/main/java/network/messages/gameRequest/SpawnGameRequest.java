package network.messages.gameRequest;

public class SpawnGameRequest extends GameRequestMessage {
    /**
     * Method for game request for spawn
     * @param spawRequest is the required spawn
     */
    public SpawnGameRequest(String spawRequest){
        super(spawRequest);
        this.content="SpawnRequest";
    }
}
