package network.messages.gameRequest;

public class ShootingSerieIndexRequest extends GameRequestMessage {
    /**
     * Method for game request to ask for serie of shooting
     * @param serieindex is the chosen serie
     */
    public ShootingSerieIndexRequest(String serieindex)
    {
        super(serieindex);
        this.content="serieIndex";
    }
}
