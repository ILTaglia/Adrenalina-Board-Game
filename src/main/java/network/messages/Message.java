package network.messages;

import java.io.Serializable;

public abstract class Message implements Serializable {

    //TODO: enum ??

    protected String type;
    protected String content;

    public String getType() {
        return type;
    }

    public String getContent() {
        return content;
    }
}