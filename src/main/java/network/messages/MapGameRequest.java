package network.messages;

public class MapGameRequest extends GameRequestMessage {

    public MapGameRequest(String map){
        super(map);
        this.content="MapRequest";
    }
}
