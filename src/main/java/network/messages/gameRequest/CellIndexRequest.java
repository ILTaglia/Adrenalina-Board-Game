package network.messages.gameRequest;

public class CellIndexRequest extends  GameRequestMessage{
    /**
     * Method for game request cell index
     * @param cellIndex is the required cell index
     */
    public CellIndexRequest (String cellIndex)
    {
        super(cellIndex);
        this.content="cellIndex";
    }
}
