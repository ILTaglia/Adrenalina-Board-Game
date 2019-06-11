package network.messages;

public class PowToPayGameRequest extends GameRequestMessage {
    public PowToPayGameRequest(String request){
        super(request);
        this.content="PowToPayRequest";
    }
}
