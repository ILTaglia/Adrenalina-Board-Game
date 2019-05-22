package network.messages;

public abstract class ErrorMessage extends Message{

    public String message;

    public ErrorMessage(){
        this.type="Error";
    }

}
