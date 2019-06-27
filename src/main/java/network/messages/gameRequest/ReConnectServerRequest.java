package network.messages.gameRequest;

public class ReConnectServerRequest extends GameRequestMessage {
    public ReConnectServerRequest(){
        super("This username belongs to a user already in the game. If you have been logged out enter your ID");
        this.content="ReConnectRequest";
    }
}
