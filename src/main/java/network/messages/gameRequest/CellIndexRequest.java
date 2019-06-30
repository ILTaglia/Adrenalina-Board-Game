package network.messages.gameRequest;

public class CellIndexRequest extends  GameRequestMessage{
    public CellIndexRequest (String cellIndex)
    {
        super(cellIndex);
        this.content="cellIndex";
    }
}
