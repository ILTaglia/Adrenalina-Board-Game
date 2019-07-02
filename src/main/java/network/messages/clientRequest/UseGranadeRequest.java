package network.messages.clientRequest;

public class UseGranadeRequest extends ClientRequestMessage {
    public UseGranadeRequest(String index, String playerID)
    {
        super(index,playerID);
        this.content="idGranade";
    }
}
