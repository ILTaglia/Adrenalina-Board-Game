package network.messages.gameRequest;

public class ColorGameRequest extends GameRequestMessage {

    public ColorGameRequest(String color){
        super(color);
        this.content="ColorRequest";
    }

}
