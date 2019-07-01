package network.messages.gameRequest;
import network.messages.Message;

public class ShootingSerieIndexRequest extends GameRequestMessage {
    public ShootingSerieIndexRequest(String serieindex)
    {
        super(serieindex);
        this.content="serieIndex";
    }
}
