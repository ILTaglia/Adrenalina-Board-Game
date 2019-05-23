package network.messages;

public abstract class ErrorMessage extends Message{

    public ErrorMessage(String info){
        this.info=info;
        this.type="Error";
    }

}
