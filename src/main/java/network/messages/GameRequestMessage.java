package network.messages;

public abstract class GameRequestMessage extends Message {

    public GameRequestMessage(String requiredInfo){
        this.info=requiredInfo;
        this.type="GameRequest";
    }
}
