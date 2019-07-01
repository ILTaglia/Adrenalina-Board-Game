package network.messages;

import network.messages.gameRequest.GameRequestMessage;

public class ReConnectServerRequest extends ConnectionMessage {
    public ReConnectServerRequest(){
        super("This username belongs to a user already in the game. If you have been logged out enter your ID");
        this.content="ReConnectRequest";
    }
}
