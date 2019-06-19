package network.messages.gameRequest;

public class MapGameRequest extends GameRequestMessage {

    public MapGameRequest(String map){
        super(map);
        this.content="MapRequest";
    }
}
