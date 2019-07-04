package network.messages.clientRequest;

import model.Coordinate;

public class SpawnPointClientRequest extends ClientRequestMessage{

    private Coordinate coordinate;

    /**
     * Message for requesting SpawnPoint cell
     * @param xCoordinate is the number of line
     * @param yCoordinate is the number of column
     * @param powCardIndex is the chosen PowCard to spawn (color must be the same of the cell)
     * @param playerID is the userID
     */
    public SpawnPointClientRequest(int xCoordinate,int yCoordinate, int powCardIndex, String playerID){
        super(Integer.toString(powCardIndex),playerID);
        this.coordinate=new Coordinate(xCoordinate,yCoordinate);
        this.content="SpawnPointRequest";
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }
}
