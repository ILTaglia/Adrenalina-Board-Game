package network.messages.gameRequest;

public class MapGameRequest extends GameRequestMessage {

    /**
     * Method for game request to ask for the type of map
     * @param map is the required map
     */
    public MapGameRequest(String map){
        super(map);
        this.content="MapRequest";
    }
}
