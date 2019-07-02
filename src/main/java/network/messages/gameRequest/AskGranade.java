package network.messages.gameRequest;

public class AskGranade extends  GameRequestMessage {
    public AskGranade (String idGranade)
    {
        super(idGranade);
        this.content="idGranade";
    }
}
