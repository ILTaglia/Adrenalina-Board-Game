package network.messages;

public abstract class ResponseMessage extends Message{

    public ResponseMessage(String info){
        this.info=info;
        this.type="Response";
    }
}
