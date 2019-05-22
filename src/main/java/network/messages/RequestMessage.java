package network.messages;

public abstract class RequestMessage extends Message {

    String username;

    public RequestMessage(String username){
        this.username=username;
        this.type="Request";
    }

    public String getUsername() {
        return username;
    }
}
