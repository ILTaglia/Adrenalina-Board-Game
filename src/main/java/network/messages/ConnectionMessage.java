package network.messages;

public abstract class ConnectionMessage extends Message {
    public ConnectionMessage(String infoToConnect){
            this.type = "Connection";
            this.info = infoToConnect;
    }

}
