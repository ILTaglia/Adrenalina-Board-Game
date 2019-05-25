package network.messages;

public abstract class RequestMessage extends Message {

    public RequestMessage(String info){
        this.info=info;
        this.type="Request";
    }
}
