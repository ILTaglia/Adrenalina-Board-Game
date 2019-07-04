package network.messages.gameRequest;

public class ShootingSerieIndexRequest extends GameRequestMessage {
    public ShootingSerieIndexRequest(String serieindex)
    {
        super(serieindex);
        this.content="serieIndex";
    }
}
