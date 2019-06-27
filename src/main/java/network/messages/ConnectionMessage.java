package network.messages;

abstract class ConnectionMessage extends Message {
    public ConnectionMessage(String infoToConnect){
            this.type = "clientRequest";
            this.info = infoToConnect;
    }

}
