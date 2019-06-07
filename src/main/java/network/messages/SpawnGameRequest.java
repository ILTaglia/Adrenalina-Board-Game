package network.messages;

public class SpawnGameRequest extends GameRequestMessage {
    public SpawnGameRequest(String spawRequest){
        super(spawRequest);
        this.content="SpawnGameRequest";
    }
}
