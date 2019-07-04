package network.messages.gameRequest;

public class ColorGameRequest extends GameRequestMessage {

    /**
     * Method for game request for choosing color
     * @param color is the required color
     */
    public ColorGameRequest(String color){
        super(color);
        this.content="ColorRequest";
    }

}
