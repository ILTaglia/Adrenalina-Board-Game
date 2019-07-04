package network.messages.connection;

import network.messages.Message;


public abstract class ConnectionMessage extends Message {
    /**
     * Message for requesting connection
     * @param infoToConnect is the info for the type of message
     */
    public ConnectionMessage(String infoToConnect){
            this.type = "Connection";
            this.info = infoToConnect;
    }

}
