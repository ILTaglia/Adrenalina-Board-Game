package network.messages;

public class ColorClientRequest extends ClientRequestMessage {

    public ColorClientRequest(String requestedColor,String userID){
        super(requestedColor,userID);
        this.content="ColorRequest";
    }

}