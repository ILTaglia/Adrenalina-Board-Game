package network.messages;

public abstract class ConnectionMessage extends Message {
    public ConnectionMessage(String infoToConnect){
            this.type = "clientConnectionRequest";
            this.info = infoToConnect;
    }

}
