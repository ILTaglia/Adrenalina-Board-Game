package network.messages.gameRequest;

public class SpawnGameRequest extends GameRequestMessage {
    public SpawnGameRequest(String spawRequest){
        super(spawRequest);
        this.content="SpawnRequest";
    }
}
