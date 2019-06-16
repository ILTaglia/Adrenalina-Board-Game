package network.messages.clientRequest;

import model.Coordinate;

public class SpawnPointClientRequest extends ClientRequestMessage{

    private Coordinate coordinate;

    public SpawnPointClientRequest(int xCoordinate,int yCoordinate, int powCardIndex, String playerID){
        super(Integer.toString(powCardIndex),playerID);
        this.coordinate=new Coordinate(xCoordinate,yCoordinate);
        this.content="SpawnPointRequest";
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }
}
