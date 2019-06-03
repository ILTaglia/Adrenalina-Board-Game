package network.messages;

public class MapRequest extends RequestMessage{
    public MapRequest(String map){
        super(map);
        this.content="MapRequest";
    }
}
