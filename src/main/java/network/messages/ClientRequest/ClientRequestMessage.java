package network.messages.ClientRequest;

import network.messages.Message;

public abstract class ClientRequestMessage extends Message {

    private String userID;

    public ClientRequestMessage(String requiredContent, String userID) {
        this.info = requiredContent;
        this.type = "ClientRequest";
        this.userID = userID;
    }

    public String getUserID() {
        return userID;
    }
}
