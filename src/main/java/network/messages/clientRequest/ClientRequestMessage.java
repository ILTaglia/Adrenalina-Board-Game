package network.messages.clientRequest;

import network.messages.Message;

public abstract class ClientRequestMessage extends Message {

    private String userID;

    /**
     * Message for requesting to client
     * @param requiredContent is the content to be required
     * @param userID is the userID
     */
    public ClientRequestMessage(String requiredContent, String userID) {
        this.info = requiredContent;
        this.type = "clientRequest";
        this.userID = userID;
    }

    public String getUserID() {
        return userID;
    }
}
