package network.messages;

public class InfoID extends ResponseMessage {

    public InfoID(String info){
        super(info);
        this.content="InfoID";
    }
}
