package network.messages;

public class PlayerDataRequest extends RequestMessage{

    public PlayerDataRequest(String color){
        super(color);
        this.content="PlayerDataRequest";
    }

}
