package network.messages.gameRequest;

public class AskGranade extends  GameRequestMessage {
    /**
     * Method for game request to ask to use granade
     * @param idGranade is the selected granade to use
     */
    public AskGranade (String idGranade)
    {
        super(idGranade);
        this.content="idGranade";
    }
}
