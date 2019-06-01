package network.messages;

public abstract class RequestMessage extends Message {

    public RequestMessage(String requiredInfo){
        this.info=requiredInfo;
        this.type="Request";
    }
}
