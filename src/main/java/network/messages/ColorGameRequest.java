package network.messages;

public class ColorGameRequest extends GameRequestMessage {

    public ColorGameRequest(String color){
        super(color);
        this.content="ColorRequest";
    }

}
