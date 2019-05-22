package network.Messages;

import java.io.Serializable;

public class Message implements Serializable {
    private String username;

    public Message(String username){
        this.username=username;
    }

    public String getUsername() {
        return username;
    }
}
